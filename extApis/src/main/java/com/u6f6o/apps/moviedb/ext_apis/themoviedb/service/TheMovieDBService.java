package com.u6f6o.apps.moviedb.ext_apis.themoviedb.service;

import com.u6f6o.apps.moviedb.ext_apis.themoviedb.config.TheMovieDBConfig;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBCast;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBSearchResult;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBUpcoming;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Service
public class TheMovieDBService {

    private final Log logger = LogFactory.getLog(TheMovieDBMovie.class);

    private final RestTemplate restTemplate;
    private final TheMovieDBConfig config;

    @Autowired
    public TheMovieDBService(TheMovieDBConfig config, RestTemplate restTemplate) {
        this.config = config;
        this.restTemplate = restTemplate;
    }


    public TheMovieDBSearchResult search(String movieTitle) {
        String encodedMovieTitle = encodeMovieTitle(movieTitle);
        String url = apiURL() + "search/movie?api_key=" + apiKey() + "&query=" + encodedMovieTitle;

        return restTemplate.getForObject(url, TheMovieDBSearchResult.class);
    }

    public TheMovieDBMovie fetch(Long theMovieDBId, boolean includeCast) {
        String movieURL = apiURL() + "movie/" + theMovieDBId + "?api_key=" + apiKey();
        TheMovieDBMovie fetchedMovie = restTemplate.getForObject( movieURL, TheMovieDBMovie.class );

        if (fetchedMovie == null || !includeCast) {
            return fetchedMovie;
        }
        fetchedMovie.setCast(fetchCast(theMovieDBId));
        return fetchedMovie;
    }

    public TheMovieDBCast fetchCast(Long theMovieDBId) {
        String url = apiURL() + "movie/" + theMovieDBId + "/casts?api_key=" + apiKey();
        return restTemplate.getForObject(url, TheMovieDBCast.class );
    }

    public TheMovieDBUpcoming fetchUpcoming() {
        String url = apiURL() + "movie/upcoming?api_key=" + apiKey();
        return restTemplate.getForObject(url, TheMovieDBUpcoming.class);
    }

    private String encodeMovieTitle(String movieTitle) {
        try {
            return new URLCodec().encode( movieTitle );
        } catch (EncoderException e) {
            logger.fatal("Cannot encode movie title", e);
            return null;
        }
    }

    private String apiKey(){
        return config.getApiKey();
    }

    private String apiURL(){
        return config.getApiURL();
    }
}

