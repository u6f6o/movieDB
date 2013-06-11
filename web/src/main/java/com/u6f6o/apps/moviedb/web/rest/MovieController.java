package com.u6f6o.apps.moviedb.web.rest;

import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import com.u6f6o.apps.moviedb.core.service.MovieLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@Controller
@RequestMapping( "/movies" )
public class MovieController {

    private final MovieLoaderService movieLoaderService;

    @Autowired
    public MovieController( MovieLoaderService movieLoaderService ) {
        this.movieLoaderService = movieLoaderService;
    }


    @ResponseBody
    @RequestMapping( method = RequestMethod.GET, value = "/{id}", produces = "application/json")
    public Movie loadMovie( @PathVariable String id ) {
        return movieLoaderService.load( Long.valueOf( id ));
    }
}
