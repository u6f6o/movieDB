package com.u6f6o.apps.moviedb.samples

import org.codehaus.jackson.map.ObjectMapper

/**
 * ULF hhhhm, maybe can enhance this a bit
 * @author u6f6o
 */
enum MovieSample {

    BAD_TASTE("Bad Taste"),
    BEN_HUR("Ben Hur"),
    BRAINDEAD("Dead Alive"),
    FEARLESS("Fearless"),
    INCEPTION("Inception"),
    MACHETE("Machete"),
    MEET_THE_FEEBLES("Meet the Feebles"),
    SHINING("The Shining"),
    STAR_WARS("Star Wars: Episode V - The Empire Strikes Back"),
    THE_DARK_KNIGHT("The Dark Knight"),
    THE_GREEN_MILE("The Green Mile")

    def pkgFragmentsToFolder = [
            ['core', 'movie'] : 'core/movie',
            ['ext_apis', 'themoviedb'] : 'themoviedb/movie'
    ]
    private String title;

    MovieSample(String title) {
        this.title = title;
    }


    def readMovieFromJSON(Class<?> movieClazz) {
        def absolutePath = "/" + pkgFragmentsToFolder.entrySet().find{it.key.every{movieClazz.package.name
                .contains(it)}}.value + "/" + this.name().toLowerCase() + ".json"

        def movie = new ObjectMapper().readValue(MovieSample.class.getResource(absolutePath), movieClazz)
        assert movie != null
        return movie
    }

    def getTitle(){
        return title;
    }

    static MovieSample byTitle(String title){
        return MovieSample.values().find{ it.title.equals(title) }
    }
}
