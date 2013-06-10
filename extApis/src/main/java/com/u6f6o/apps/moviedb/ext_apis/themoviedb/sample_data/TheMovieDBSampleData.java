package com.u6f6o.apps.moviedb.ext_apis.themoviedb.sample_data;

import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBCast;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * @author u6f6o
 */
public enum TheMovieDBSampleData {

    FEARLESS("Fearless"),
    MACHETE("Machete"),
    STAR_WARS("Star Wars: Episode V - The Empire Strikes Back");

    private final String title;

    private TheMovieDBSampleData(String title){
        this.title = title;
    }

    public TheMovieDBMovie readMovieFromJSON() {
        String relativePath = "movie/" + title.toLowerCase() + ".json";
        try {
            return new ObjectMapper().readValue(TheMovieDBSampleData.class.getResource(relativePath), TheMovieDBMovie.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TheMovieDBCast readCastFromJSON(){
        String relativePath = "cast/" + title.toLowerCase() + ".json";
        try {
            return new ObjectMapper().readValue(TheMovieDBSampleData.class.getResource(relativePath), TheMovieDBCast.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String getTitle(){
        return title;
    }

    public static TheMovieDBSampleData byTitle(String title){
        for (TheMovieDBSampleData theMovieDBSampleData : TheMovieDBSampleData.values()) {
            if(theMovieDBSampleData.getTitle().equals(title)){
                return theMovieDBSampleData;
            }
        }
        throw new IllegalStateException("Sample with title: " + title + " not found");
    }
}