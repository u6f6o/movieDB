package com.u6f6o.apps.moviedb.ext_apis.themoviedb.service

import com.u6f6o.apps.moviedb.ext_apis.base.config.ServiceLayerConfig
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.sample_data.TheMovieDBSampleData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll


/**
 * TODO ULF add test for cast
 * @author u6f6o
 */
@ActiveProfiles( "dev" )
@ContextConfiguration( classes = [ ServiceLayerConfig.class ])
class TheMovieDBServiceSpec extends Specification {

    @Autowired
    TheMovieDBService theMovieDBService


    @Unroll("should find #expResults movie proposals for '#movieTitle'")
    def "check count of found movies"(){

        when:
            def movieResults = theMovieDBService.searchMovie(movieTitle)
        then:
            movieResults.totalResults == expResults;
            movieResults.searchResults.every { it.title.contains(movieTitle)}
        where:
            movieTitle                  |   expResults
            "Machete"                   |   6
            "House of Flying Daggers"   |   1
            "Dark Knight"               |   9
    }

    @Unroll("should check content for '#movieSample.title'")
    def "check content of found movies" (){

        when:
            def fetchedMovie = theMovieDBService.fetchMovie(movieSample.id, false)
        then:
            fetchedMovie.adult == movieSample.adult
            fetchedMovie.backDropPath == movieSample.backDropPath
            fetchedMovie.belongsToCollection == movieSample.belongsToCollection
            fetchedMovie.budget == movieSample.budget
            fetchedMovie.genres == movieSample.genres
            fetchedMovie.homepage == movieSample.homepage
            fetchedMovie.id == movieSample.id
            fetchedMovie.imdbId == movieSample.imdbId
            fetchedMovie.originalTitle == movieSample.originalTitle
            fetchedMovie.overview == movieSample.overview
            fetchedMovie.posterPath == movieSample.posterPath
            fetchedMovie.productionCompanies == movieSample.productionCompanies
            fetchedMovie.productionCountries == movieSample.productionCountries
            fetchedMovie.releaseDate == movieSample.releaseDate
            fetchedMovie.revenue == movieSample.revenue
            fetchedMovie.runtime == movieSample.runtime
            fetchedMovie.spokenLanguages == movieSample.spokenLanguages
            fetchedMovie.status == movieSample.status
            fetchedMovie.tagLine == movieSample.tagLine
            fetchedMovie.title == movieSample.title
        where:
            movieSample << [ TheMovieDBSampleData.FEARLESS, TheMovieDBSampleData.MACHETE, TheMovieDBSampleData.STAR_WARS ]
                    .collect{ it.readMovieFromJSON() }
    }
}
