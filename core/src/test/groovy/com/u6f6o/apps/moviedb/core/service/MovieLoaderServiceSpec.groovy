package com.u6f6o.apps.moviedb.core.service

import com.u6f6o.apps.moviedb.core.config.service.ServiceLayerConfig
import com.u6f6o.apps.moviedb.core.repository.MovieRepository
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.sample_data.TheMovieDBSampleData
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.service.TheMovieDBService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.u6f6o.apps.moviedb.core.sample_data.MovieSample.*

/**
 * @author u6f6o
 */
@ActiveProfiles( "dev" )
@ContextConfiguration( classes = [ ServiceLayerConfig.class ])
class MovieLoaderServiceSpec extends Specification{

    @Autowired
    MovieLoaderService movieLoaderService

    @Autowired
    MovieRepository movieRepository

    // MOCKS
    def movieRepositoryMock = Mock(MovieRepository)
    def theMovieDBServiceMock = Mock(TheMovieDBService)
    def movieTransformerServiceMock = Mock(MovieTransformerService)
    def movieSaveServiceMock = Mock(AsyncMovieSaveService)


    @Unroll("should load and transform '#sampleMovie.title' from themoviedb.org" )
    def "load and transform movies from themoviedb.org"(){

        expect:
            movieRepository.fetchMovieByTheMovieDBId(sampleMovie.id) == null
        when:
            def movie = movieLoaderService.load(sampleMovie.id)
        then:
            movie != null
            movie.id != null
        when:
            def internalMovie = movieLoaderService.loadMovieFromInternalDB(sampleMovie.id)
        then:
            internalMovie != null
            internalMovie.equals(movie)
        where:
            sampleMovie << [BRAINDEAD, INCEPTION, THE_DARK_KNIGHT].collect{it.readMovieFromJSON()}
    }


    def "should not find movie in internal nor in external database" (){

        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 1111l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId(1111l) >> null
            1 * theMovieDBServiceMock.fetchMovie(1111l, true) >> null
            0 * _
            movie == null
    }


    def "should find movie in internal database"(){

        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 8216l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId(8216l) >> movieInternal
            0 * _
            movie != null
            movie.equals(movieInternal)
        where:
            movieInternal = MEET_THE_FEEBLES.readMovieFromJSON()
    }


    def "should find movie in external database"(){

        given:
            def service = new MovieLoaderService(movieRepositoryMock, theMovieDBServiceMock,
                    movieSaveServiceMock, movieTransformerServiceMock)
        when:
            def movie = service.load( 7549l )
        then:
            1 * movieRepositoryMock.fetchMovieByTheMovieDBId( 7549l ) >> null
            1 * theMovieDBServiceMock.fetchMovie( 7549l, true ) >> movieExternal
            1 * movieTransformerServiceMock.transform(movieExternal) >> movieInternal
            1 * movieSaveServiceMock.saveAsynchronously(movieInternal)
            0 * _
            movie != null
            movie.equals(movieInternal)
        where:
            movieInternal = FEARLESS.readMovieFromJSON()
            movieExternal = TheMovieDBSampleData.FEARLESS.readMovieFromJSON()
    }
}
