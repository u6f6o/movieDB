package com.u6f6o.apps.moviedb.core.api.movie.aggregation;

import com.u6f6o.apps.moviedb.core.api.movie.Movie;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ulf Gitschthaler
 */
public class MovieQueryResult {

    private Set<Movie> candidates = new LinkedHashSet<Movie>();
    private int count = 0;

    public Set<Movie> getCandidates() {
        return Collections.unmodifiableSet(candidates);
    }

    public void addCandidate(Movie movie){
        candidates.add(movie);
        count++;
    }

    private int getCount(){
        return count;
    }
}
