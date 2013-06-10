package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class TheMovieDBCast {
    private String id;
    @JsonProperty( "cast" )
    private LinkedHashSet<TheMovieDBActor> actors;
    private LinkedHashSet<TheMovieDBCrewMember> crew;

    public TheMovieDBCast() {
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public LinkedHashSet<TheMovieDBActor> getActors() {
        return actors;
    }

    public void setActors(LinkedHashSet<TheMovieDBActor> actors) {
        this.actors = actors;
    }

    public LinkedHashSet<TheMovieDBCrewMember> getCrew() {
        return crew;
    }

    public void setCrew(LinkedHashSet<TheMovieDBCrewMember> crew) {
        this.crew = crew;
    }
}
