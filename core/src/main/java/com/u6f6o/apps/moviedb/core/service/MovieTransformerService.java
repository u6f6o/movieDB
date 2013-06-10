package com.u6f6o.apps.moviedb.core.service;

import com.u6f6o.apps.moviedb.core.api.movie.Genre;
import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import com.u6f6o.apps.moviedb.core.api.movie.cast.Actor;
import com.u6f6o.apps.moviedb.core.api.movie.cast.Cast;
import com.u6f6o.apps.moviedb.core.api.movie.cast.CrewMember;
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.util.*;


@Service( "movieTransformerService" )
public class MovieTransformerService {
    private static final Log LOGGER = LogFactory.getLog( MovieTransformerService.class );
    private static final DateTimeFormatter THE_MOVIE_DB_DATE_FORMATTER = DateTimeFormat.forPattern( "yyyy-MM-dd" );


    public Movie transform( TheMovieDBMovie source ) {
        LOGGER.info( "Create movie '" + source.getTitle() + "' based on themoviedb.org source." );
        Movie result = createMovie(source.getId());

        result.setTitle( source.getTitle( ));
        result.setDescription( source.getOverview( ));
        result.setReleaseDate( parseReleaseDate( source.getReleaseDate( )));
        result.setRuntime( source.getRuntime( ));
        result.setGenres( transformGenres(source.getGenres()));
        result.setCast( transformCast(source.getCast()));
        result.setWebSite( source.getHomepage( ));

        return result;
    }

    protected Movie createMovie(Long id) {
        return new Movie(id);
    }

    private Date parseReleaseDate( String releaseDate ) {
        if( StringUtils.isBlank( releaseDate )) {
            LOGGER.info( "No release date provided" );
            return null;
        }
        try {
            // TODO: find out how to use joda time localdate together with mongo db
            LocalDate result = THE_MOVIE_DB_DATE_FORMATTER.parseLocalDate(releaseDate);
            return result != null ? result.toDate() : null;
        } catch( Exception e ) {
            LOGGER.warn( "Release date: " + releaseDate + " cannot be parsed." );
        }
        return null;
    }

    protected Set<Genre> transformGenres(Set<TheMovieDBGenre> theMovieDBGenres) {
        if( CollectionUtils.isEmpty( theMovieDBGenres )) {
            LOGGER.info( "No genres provided." );
            return null;
        }
        Set<Genre> result = new LinkedHashSet<Genre>();

        for( TheMovieDBGenre theMovieDBGenre : theMovieDBGenres ) {
            result.add(transformGenre(theMovieDBGenre));
        }
        return result;
    }

    protected Genre transformGenre(TheMovieDBGenre genre){
        Genre result = Genre.forName( genre.getName( ));
        if( result == null ) {
            LOGGER.error( "Genre name: " + genre.getName() + " cannot be parsed. Probably "
                    + "themoviedb.org added a new genre." );
        }
        return result;
    }

    protected Cast transformCast(TheMovieDBCast cast) {
        if( cast == null ) {
            LOGGER.info( "No cast provided." );
            return null;
        }
        Cast result = new Cast();
        result.setActors( transformActors(cast.getActors()));
        result.setCrew( transformCrew(cast.getCrew()));
        return result;
    }

    protected Set<CrewMember> transformCrew(LinkedHashSet<TheMovieDBCrewMember> crew) {
        if( CollectionUtils.isEmpty( crew )) {
            LOGGER.info( "No crew members provided." );
            return null;
        }
        Set<CrewMember> result = new LinkedHashSet<CrewMember>();

        for ( TheMovieDBCrewMember theMovieDBCrewMember : crew ) {
            CrewMember crewMember = transformCrewMember(theMovieDBCrewMember);
            result.add( crewMember );
        }
        return result;
    }

    protected CrewMember transformCrewMember(TheMovieDBCrewMember theMovieDBCrewMember) {
        CrewMember crewMember = new CrewMember();
        crewMember.setRealName( theMovieDBCrewMember.getName( ));
        crewMember.setDepartment( theMovieDBCrewMember.getDepartment( ));
        crewMember.setJob( theMovieDBCrewMember.getJob( ));

        return crewMember;
    }

    protected SortedSet<Actor> transformActors(LinkedHashSet<TheMovieDBActor> actors) {
        if( CollectionUtils.isEmpty( actors )) {
            LOGGER.info( "No actors provided." );
            return null;
        }
        SortedSet<Actor> result = new TreeSet<Actor>();

        for ( TheMovieDBActor theMovieDBActor : actors ) {
            result.add(transformActor(theMovieDBActor));
        }
        return result;
    }

    protected Actor transformActor(TheMovieDBActor theMovieDBActor) {
        Actor actor = new Actor();

        actor.setCharacterName(theMovieDBActor.getCharacter());
        actor.setRealName(theMovieDBActor.getName());
        actor.setOrder(theMovieDBActor.getOrder());

        return actor;
    }
}