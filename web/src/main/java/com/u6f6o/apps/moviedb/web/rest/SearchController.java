package com.u6f6o.apps.moviedb.web.rest;

import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBSearchResult;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.service.TheMovieDBService;
import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping( "/search/movie" )
public class SearchController { // TODO rename?

    private final TheMovieDBService theMovieDBService;

    @Autowired
    public SearchController( TheMovieDBService theMovieDBService ) {
        this.theMovieDBService = theMovieDBService;
    }


    @ResponseBody
    @RequestMapping( method = RequestMethod.GET, produces = "application/json" )
    public Map<String, Object> search( @RequestParam String movieTitle ) {
        Map<String, Object> response = new HashMap<String, Object>();

        try {
            TheMovieDBSearchResult searchResult =  theMovieDBService.searchMovie( movieTitle );
            response.put( "result", searchResult );
            response.put( "success", "true" );
        } catch ( EncoderException e ) {
            response.put( "success", "false" );
        }
        return response;
    }
}
