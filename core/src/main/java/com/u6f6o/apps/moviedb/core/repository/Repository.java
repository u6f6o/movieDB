package com.u6f6o.apps.moviedb.core.repository;

import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public abstract class Repository<T> {

    private final MongoOperations mongoTemplate;

    protected Repository( MongoOperations mongoTemplate ) {
        this.mongoTemplate = mongoTemplate;
    }


    public abstract Class<T> getEntityClass();


    public void save( T entity ) {
        mongoTemplate.save( entity );
    }

    public void remove( T entity ) {
        mongoTemplate.remove( entity );
    }

    public List<T> getAll() {
        return mongoTemplate.findAll( getEntityClass( ));
    }

    public long count() {
        return mongoTemplate.count(null, getEntityClass());
    }

    /**
     * Should not be used in production environment, thus removed it from the interface.
     */
    public void removeAll() {
        mongoTemplate.dropCollection( getEntityClass( ));
    }

    public MongoOperations getMongoTemplate() {
        return mongoTemplate;
    }
}
