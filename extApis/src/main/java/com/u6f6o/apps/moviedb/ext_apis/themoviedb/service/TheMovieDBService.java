package com.u6f6o.apps.moviedb.ext_apis.themoviedb.service;

import com.u6f6o.apps.moviedb.ext_apis.themoviedb.config.TheMovieDBConfig;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBCast;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBSearchResult;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Service
public class TheMovieDBService {

    private final RestTemplate restTemplate;
    private final TheMovieDBConfig config;

    @Autowired
    public TheMovieDBService(TheMovieDBConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }


    public TheMovieDBSearchResult searchMovie( String movieTitle ) throws EncoderException {
        String encodedMovieTitle = new URLCodec().encode( movieTitle );
        String url = apiURL() + "search/movie?api_key=" + apiKey() + "&query=" + encodedMovieTitle;

        return restTemplate.getForObject( url, TheMovieDBSearchResult.class );
    }

    public TheMovieDBMovie fetchMovie( Long theMovieDBId, boolean includeCast ) {
        String movieURL = apiURL() + "movie/" + theMovieDBId + "?api_key=" + apiKey();
        TheMovieDBMovie fetchedMovie = restTemplate.getForObject( movieURL, TheMovieDBMovie.class );

        if( fetchedMovie == null || !includeCast ) return fetchedMovie;

        String castURL = apiURL() + "movie/" + theMovieDBId + "/casts?api_key=" + apiKey();
        TheMovieDBCast fetchedCast = restTemplate.getForObject( castURL, TheMovieDBCast.class );

        fetchedMovie.setCast( fetchedCast );
        return fetchedMovie;
    }

    public TheMovieDBCast getCast( Long theMovieDBId ) {
        String url = apiURL() + "movie/" + theMovieDBId + "/casts?api_key=" + apiKey();
        return restTemplate.getForObject( url, TheMovieDBCast.class );
    }

    private String apiKey(){
        return config.getApiKey();
    }

    private String apiURL(){
        return config.getApiURL();
    }
}

