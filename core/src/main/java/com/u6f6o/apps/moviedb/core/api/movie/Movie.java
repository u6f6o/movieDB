package com.u6f6o.apps.moviedb.core.api.movie;

import com.u6f6o.apps.moviedb.core.api.movie.cast.Cast;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Document( collection = "movies" )
public class Movie {

    @Id
    private final Long id;

    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Date releaseDate;
    @NotEmpty
    private Set<Genre> genres;
    @NotNull
    private Cast cast;
    @NotNull
    private Integer runtime;
    private String webSite;


    @PersistenceConstructor
    @JsonCreator
    public Movie(@JsonProperty("id") Long id) {
        this.id = id;
    }

    // for testing only
    public Movie( String title, String author, Integer year, String genre ) {
        this.title = title;
        this.id = null;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP")
    public Date getReleaseDate() {
        return releaseDate;
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP2")
    public void setReleaseDate( Date releaseDate ) {
        this.releaseDate = releaseDate;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres( Set<Genre> genres ) {
        this.genres = genres;
    }

    public Cast getCast() {
        return cast;
    }

    public void setCast( Cast cast ) {
        this.cast = cast;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime( Integer runtime ) {
        this.runtime = runtime;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite( String webSite ) {
        this.webSite = webSite;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this, ToStringStyle.SIMPLE_STYLE );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!id.equals(movie.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}