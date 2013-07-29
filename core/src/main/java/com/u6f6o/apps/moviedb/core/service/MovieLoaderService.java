package com.u6f6o.apps.moviedb.core.service;

import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import com.u6f6o.apps.moviedb.core.repository.MovieRepository;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBSearchResult;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBUpcoming;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.service.TheMovieDBService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Service()
public class MovieLoaderService {

    private static final Log LOGGER = LogFactory.getLog( MovieLoaderService.class );

    private final MovieRepository movieRepository;
    private final TheMovieDBService theMovieDBService;
    private final AsyncMovieSaveService asyncMovieSaveService;
    private final MovieTransformerService movieTransformerService;

    @Autowired
    public MovieLoaderService( MovieRepository movieRepository, TheMovieDBService theMovieDBService,
            AsyncMovieSaveService asyncMovieSaveService, MovieTransformerService movieTransformerService ) {

        this.movieRepository = movieRepository;
        this.theMovieDBService = theMovieDBService;
        this.asyncMovieSaveService = asyncMovieSaveService;
        this.movieTransformerService = movieTransformerService;
    }


    public TheMovieDBSearchResult search(String movieTitle) {
        return theMovieDBService.search(movieTitle);
    }

    public Movie load( long theMovieDBId ) {
        Movie movieFromInternalDB = loadMovieFromInternalDB( theMovieDBId );
        if( movieFromInternalDB != null ) return movieFromInternalDB;

        TheMovieDBMovie movieFromExternalDB = loadMovieFromExternalProvider( theMovieDBId );
        if( movieFromExternalDB == null ) return null;

        Movie translatedMovie = translateMovie( movieFromExternalDB );
        saveMovie( translatedMovie );
        return translatedMovie;
    }

    public TheMovieDBUpcoming fetchUpcomingMovies() {
        return theMovieDBService.fetchUpcoming();
    }

    protected Movie loadMovieFromInternalDB( long theMovieDBId ) {
        Movie fetchedMovie = movieRepository.fetchMovieByTheMovieDBId( theMovieDBId );
        LOGGER.info(( fetchedMovie != null ? "Successfully fetched" : "Unable to fetch" ) + " movie from internal "
                + "database." );
        return fetchedMovie;
    }

    // ULF use interface instead of concrete class
    protected TheMovieDBMovie loadMovieFromExternalProvider( long theMovieDBId ) {
        TheMovieDBMovie fetchedMovie = theMovieDBService.fetch(theMovieDBId, true);
        LOGGER.info(( fetchedMovie != null ? "Successfully fetched" : "Unable to fetch" ) + " movie from external "
                + "movie database." );
        return fetchedMovie;
    }

    protected Movie translateMovie( TheMovieDBMovie movieFromExternalDB ) {
        return movieTransformerService.transform(movieFromExternalDB);
    }

    protected void saveMovie( Movie movieToSave ) {
        asyncMovieSaveService.saveAsynchronously( movieToSave );
    }
}
