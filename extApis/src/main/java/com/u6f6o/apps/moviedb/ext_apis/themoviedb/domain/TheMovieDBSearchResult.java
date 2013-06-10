package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.LinkedHashSet;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class TheMovieDBSearchResult {

    private Integer page;
    @JsonProperty( "total_pages" )
    private Integer totalPages;
    @JsonProperty( "total_results" )
    private Integer totalResults;

    @JsonProperty( "results" )
    private LinkedHashSet<TheMovieDBMovie> searchResults;

    @JsonProperty( "op_path" )
    private String opPatch;


    public Integer getPage() {
        return page;
    }

    public void setPage( Integer page ) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages( Integer totalPages ) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults( Integer totalResults ) {
        this.totalResults = totalResults;
    }

    public LinkedHashSet<TheMovieDBMovie> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults( LinkedHashSet<TheMovieDBMovie> searchResults ) {
        this.searchResults = searchResults;
    }

    public String getOpPatch() {
        return opPatch;
    }

    public void setOpPatch( String opPatch ) {
        this.opPatch = opPatch;
    }
}
