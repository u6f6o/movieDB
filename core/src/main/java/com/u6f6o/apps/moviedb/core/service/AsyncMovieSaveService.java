package com.u6f6o.apps.moviedb.core.service;

import com.u6f6o.apps.moviedb.core.api.movie.Movie;
import com.u6f6o.apps.moviedb.core.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service( "asyncMovieSaveService" )
public class AsyncMovieSaveService {

    private final MovieRepository movieRepository;

    @Autowired
    public AsyncMovieSaveService( MovieRepository movieRepository ) {
        this.movieRepository = movieRepository;
    }


    @Async
    public void saveAsynchronously( Movie movieToSave ) {
        movieRepository.save( movieToSave );
    }
}
