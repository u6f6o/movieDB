package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class TheMovieDBCollection {
    private Long id;
    private String name;
    @JsonProperty( "poster_path" )
    private String posterPath;
    @JsonProperty( "backdrop_path" )
    private String backDropPath;

    public TheMovieDBCollection() {
    }

    public TheMovieDBCollection( Long id, String name, String posterPath, String backDropPath ) {
        this.id = id;
        this.name = name;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath( String posterPath ) {
        this.posterPath = posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath( String backDropPath ) {
        this.backDropPath = backDropPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TheMovieDBCollection that = (TheMovieDBCollection) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
