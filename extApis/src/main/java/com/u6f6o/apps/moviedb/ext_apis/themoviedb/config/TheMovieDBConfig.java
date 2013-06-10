package com.u6f6o.apps.moviedb.ext_apis.themoviedb.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * @author u6f6o
 */
@Service
public class TheMovieDBConfig implements EnvironmentAware {

    private static final String DEFAULT_URL = "http://api.themoviedb.org/3/";

    private Environment environment;


    public String getApiURL(){
        return environment.getProperty("THE_MOVIE_DB_URL", DEFAULT_URL);
    }

    public String getApiKey(){
        return environment.getProperty("THE_MOVIE_DB_API_KEY");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
