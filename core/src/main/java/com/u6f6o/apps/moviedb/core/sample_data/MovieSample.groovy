package com.u6f6o.apps.moviedb.core.sample_data

import com.u6f6o.apps.moviedb.core.api.movie.Movie
import org.codehaus.jackson.map.ObjectMapper


/**
 * @author u6f6o
 */
public enum MovieSample {

    BAD_TASTE("Bad Taste"),
    BEN_HUR("Ben Hur"),
    BRAINDEAD("Dead Alive"),
    FEARLESS("Fearless"),
    INCEPTION("Inception"),
    MACHETE("Machete"),
    MEET_THE_FEEBLES("Meet the Feebles"),
    SHINING("The Shining"),
    STAR_WARS("Star Wars: Episode V - The Empire Strikes Back"),
    THE_DARK_KNIGHT("The Dark Knight"),
    THE_GREEN_MILE("The Green Mile");

    private String title;


    public MovieSample(String title) {
        this.title = title;
    }


    public Movie readMovieFromJSON(){
        return null;
//        def relativePath = "internal/" + this.name().toLowerCase() + ".json"
//        def movie = new ObjectMapper().readValue(MovieSample.class.getResource(relativePath), Movie.class)
//
//        assert movie != null
//        assert movie.id != null
//
//        return movie
    }

    public String getTitle(){
        return title;
    }

    public static MovieSample byTitle(String title){
        return null;
    }
}
