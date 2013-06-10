package com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Set;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
@JsonSerialize( include = Inclusion.NON_EMPTY )
public class TheMovieDBMovie {

    private Long id;
    @JsonProperty( "imdb_id" )
    private String imdbId;

    private String title;
    @JsonProperty( "original_title" )
    private String originalTitle;
    @JsonProperty( "tagline" )
    private String tagLine;
    private String overview;

    @JsonProperty( "release_date" )
    private String releaseDate;

    private TheMovieDBCast cast;

    @JsonProperty( "vote_count" )
    private Integer voteCount;
    @JsonProperty( "vote_average" )
    private Double voteAverage;
    private Double popularity;

    private Set<TheMovieDBGenre> genres;
    @JsonProperty( "production_companies" )
    private Set<TheMovieDBComapny> productionCompanies;
    @JsonProperty( "production_countries" )
    private Set<TheMovieDBCountry> productionCountries;
    @JsonProperty( "spoken_languages")
    private Set<TheMovieDBLanguage> spokenLanguages;

    private Long budget;
    private Long revenue;

    private String status;
    private Integer runtime;
    private Boolean adult;

    @JsonProperty( "backdrop_path" )
    private String backDropPath;
    @JsonProperty( "belongs_to_collection" )
    private TheMovieDBCollection belongsToCollection;
    private String homepage;
    @JsonProperty( "poster_path" )
    private String posterPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public TheMovieDBCast getCast() {
        return cast;
    }

    public void setCast(TheMovieDBCast cast) {
        this.cast = cast;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Set<TheMovieDBGenre> getGenres() {
        return genres;
    }

    public void setGenres(Set<TheMovieDBGenre> genres) {
        this.genres = genres;
    }

    public Set<TheMovieDBComapny> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(Set<TheMovieDBComapny> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public Set<TheMovieDBCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(Set<TheMovieDBCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public Set<TheMovieDBLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(Set<TheMovieDBLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getRevenue() {
        return revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public TheMovieDBCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection( TheMovieDBCollection belongsToCollection ) {
        this.belongsToCollection = belongsToCollection;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals( Object o ){
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass( )) return false;

        TheMovieDBMovie that = (TheMovieDBMovie) o;

        if ( id != null ? !id.equals( that.id ) : that.id != null ) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
