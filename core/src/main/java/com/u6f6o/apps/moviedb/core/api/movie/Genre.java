package com.u6f6o.apps.moviedb.core.api.movie;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */

public enum Genre {

    ACTION( "Action" ),
    ADVENTURE( "Adventure" ),
    ANIMATION( "Animation" ),
    COMEDY( "Comedy" ),
    CRIME( "Crime" ),
    DISASTER( "Disaster" ),
    DOCUMENTARY( "Documentary" ),
    DRAMA( "Drama" ),
    EASTERN( "Eastern" ),
    EROTIC( "Erotic" ),
    FAMILY( "Family" ),
    FAN_FILM( "Fan Film" ),
    FANTASY( "Fantasy" ),
    FILM_NOIR( "Film Noir" ),
    FOREIGN( "Foreign" ),
    HISTORY( "History" ),
    HOLIDAY( "Holiday" ),
    HORROR( "Horror" ),
    INDIE( "Indie" ),
    MUSIC( "Music" ),
    MUSICAL( "Musical" ),
    MYSTERY( "Mystery" ),
    NEO_NOIR( "Neo-noir" ),
    ROAD_MOVIE( "Road Movie" ),
    ROMANCE( "Romance" ),
    SCIENCE_FICTION( "Science Fiction" ),
    SHORT( "Short" ),
    SPORT( "Sport" ),
    SPORTING_EVENT( "Sporting Event" ),
    SPORTS_FILM( "Sports Film" ),
    SUSPENSE( "Suspense" ),
    TV_MOVIE( "TV movie" ),
    THRILLER( "Thriller" ),
    WAR( "War" ),
    WESTERN( "Western" );

    private String name;


    Genre( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Genre forName( String name ) {
        for ( Genre genre : Genre.values( )) {
            if( genre.getName().equalsIgnoreCase( name ))
                return genre;
        }
        return null;
    }
}