package com.u6f6o.apps.moviedb.core.api.movie.cast;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Set;
import java.util.SortedSet;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class Cast {
    @NotEmpty
    private SortedSet<Actor> actors;
    @NotEmpty
    private Set<CrewMember> crew;

    public SortedSet<Actor> getActors() {
        return actors;
    }

    public void setActors( SortedSet<Actor> actors ) {
        this.actors = actors;
    }

    public Set<CrewMember> getCrew() {
        return crew;
    }

    public void setCrew( Set<CrewMember> crew ) {
        this.crew = crew;
    }
}
