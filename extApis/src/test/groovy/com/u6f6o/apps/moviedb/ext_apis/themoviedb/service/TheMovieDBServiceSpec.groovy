package com.u6f6o.apps.moviedb.ext_apis.themoviedb.service

import groovy.json.JsonSlurper

import static com.u6f6o.apps.moviedb.samples.MovieSample.*
import com.u6f6o.apps.moviedb.ext_apis.base.config.ServiceLayerConfig
import com.u6f6o.apps.moviedb.ext_apis.themoviedb.domain.TheMovieDBMovie
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
            def movieResults = theMovieDBService.search(movieTitle)
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
            def fetchedMovie = theMovieDBService.fetch(movieSample.id, false)
            println fetchedMovie.title
            println fetchedMovie.posterPath
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
            movieSample << [ FEARLESS, MACHETE, STAR_WARS ]
                    .collect{ it.readMovieFromJSON(TheMovieDBMovie.class) }
    }


    def "check upcoming movies"(){
        given:
            def apiKey = System.getenv('THE_MOVIE_DB_API_KEY')
            def apiUrl = new URL("http://api.themoviedb.org/3/movie/upcoming?api_key=$apiKey")
            def upcomingJSON = new JsonSlurper().parseText(apiUrl.text)
        when:
            def upcomingMovies = theMovieDBService.fetchUpcoming()
        then:
            upcomingMovies != null
            upcomingMovies.totalResults == upcomingJSON.total_results
            upcomingJSON.results.every { t ->
                upcomingMovies.searchResults.any { p ->
                    p.title == t.title
                }
            }
    }

}
