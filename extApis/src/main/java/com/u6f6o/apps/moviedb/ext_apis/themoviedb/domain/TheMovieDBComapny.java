package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class TheMovieDBComapny {

    private Long id;
    private String name;

    public TheMovieDBComapny() {
    }

    public TheMovieDBComapny( Long id, String name ) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        TheMovieDBComapny that = (TheMovieDBComapny) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
