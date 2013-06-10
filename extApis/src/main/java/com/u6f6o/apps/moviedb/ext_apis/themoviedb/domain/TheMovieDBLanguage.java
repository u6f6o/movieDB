package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class TheMovieDBLanguage {

    @JsonProperty( "iso_639_1" )
    private String isoCode;
    private String name;

    public TheMovieDBLanguage() {
    }

    public TheMovieDBLanguage( String isoCode, String name ) {
        this.isoCode = isoCode;
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TheMovieDBLanguage that = (TheMovieDBLanguage) o;

        if (isoCode != null ? !isoCode.equals(that.isoCode) : that.isoCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isoCode != null ? isoCode.hashCode() : 0;
    }
}
