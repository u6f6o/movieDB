package com.u6f6o.apps.moviedb.core.repository;

import com.u6f6o.apps.moviedb.core.api.movie.Genre;
import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import com.u6f6o.apps.moviedb.core.api.movie.cast.CrewMember;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository()
public class MovieRepository extends com.u6f6o.apps.moviedb.core.repository.Repository<Movie> {

    private static final Class<Movie> ENTITY_CLASS = Movie.class;

    @Autowired
    public MovieRepository( MongoOperations mongoTemplate ) {
        super( mongoTemplate );
    }


    public Movie fetchMovieById( Long id ) {
        return getMongoTemplate().findById( id, getEntityClass( ));
    }

    public Movie fetchMovieByTheMovieDBId( Long theMovieDBId ) {
        return getMongoTemplate().findById( theMovieDBId, getEntityClass( ));
    }

    public List<Movie> fetchMoviesByTitle( String title ) {
        return getMongoTemplate().find( new Query( Criteria.where( "title" ).is( title )), getEntityClass( ));
    }

    public List<Movie> fetchMoviesByAuthor( CrewMember author ) {
        return getMongoTemplate().find( new Query( Criteria.where( "cast.crew.realName" ).is( author.getRealName( ))),
                getEntityClass());
    }

    public List<Movie> fetchMoviesByGenre( Genre genre ) {
        return getMongoTemplate().find( new Query( Criteria.where( "genres" ).is( genre )), getEntityClass( ));
    }

    public List<Movie> fetchMoviesByInterval( Interval interval ) {
        Date startDate = interval.getStart().toDate();
        Date endDate = interval.getEnd().toDate();

        return getMongoTemplate().find( new Query( Criteria.where( "releaseDate" ).gte( startDate ).lte( endDate )),
                getEntityClass( ));
    }

    @Override
    public Class<Movie> getEntityClass() {
        return ENTITY_CLASS;
    }
}
