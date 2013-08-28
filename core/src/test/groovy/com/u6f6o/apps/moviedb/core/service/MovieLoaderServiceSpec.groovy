package com.u6f6o.apps.moviedb.core.service

import com.u6f6o.apps.moviedb.core.api.movie.Movie
import com.u6f6o.apps.moviedb.core.config.service.ServiceLayerConfig
import com.u6f6o.apps.moviedb.core.repository.MovieRepository
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.service.TheMovieDBService
import com.u6f6o.apps.moviedb.samples.MovieSample
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.u6f6o.apps.moviedb.samples.MovieSample.*

/**
 * @author u6f6o
 */
@ActiveProfiles( "dev" )
@ContextConfiguration( classes = [ ServiceLayerConfig.class ])
class MovieLoaderServiceSpec extends Specification{

    @Autowired
    MovieLoaderService movieLoaderService

    @Autowired
    MovieRepository movieRepository

    // MOCKS
    def movieRepositoryMock = Mock(MovieRepository)
    def theMovieDBServiceMock = Mock(TheMovieDBService)
    def movieTransformerServiceMock = Mock(MovieTransformerService)
    def movieSaveServiceMock = Mock(AsyncMovieSaveService)


    def setup() {
        movieRepository.removeAll();
    }

    @Unroll("should load and transform '#sampleMovie.title' from themoviedb.org" )
    def "load and transform movies from themoviedb.org"(){
        expect:
            movieRepository.fetchMovieByTheMovieDBId(sampleMovie.id) == null
        when:
            def movie = movieLoaderService.load(sampleMovie.id)
        then:
            movie != null
            movie.id != null
        when:
            def internalMovie = movieLoaderService.loadMovieFromInternalDB(sampleMovie.id)
        then:
            internalMovie != null
            internalMovie.equals(movie)
        where:
            sampleMovie << [BRAINDEAD, INCEPTION, THE_DARK_KNIGHT].collect{it.readMovieFromJSON(Movie.class)}
    }


    def "should not find movie in internal nor in external database" (){
        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 1111l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId(1111l) >> null
            1 * theMovieDBServiceMock.fetch(1111l, true) >> null
            0 * _
            movie == null
    }


    def "should find movie in internal database"(){
        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 8216l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId(8216l) >> movieInternal
            0 * _
            movie != null
            movie.equals(movieInternal)
        where:
            movieInternal = MEET_THE_FEEBLES.readMovieFromJSON(Movie.class)
    }


    def "should find movie in external database"(){
        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 7549l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId( 7549l ) >> null
            1 * theMovieDBServiceMock.fetch( 7549l, true ) >> movieExternal
            1 * movieTransformerServiceMock.transform(movieExternal) >> movieInternal
            1 * movieSaveServiceMock.saveAsynchronously(movieInternal)
            0 * _
            movie != null
            movie.equals(movieInternal)
        where:
            movieInternal = FEARLESS.readMovieFromJSON(Movie.class)
            movieExternal = MovieSample.FEARLESS.readMovieFromJSON(TheMovieDBMovie.class)
    }
}

