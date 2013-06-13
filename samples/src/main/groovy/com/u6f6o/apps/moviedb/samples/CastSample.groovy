package com.u6f6o.apps.moviedb.samples

import org.codehaus.jackson.map.ObjectMapper

/**
 * @author u6f6o
 */
enum CastSample {

    FEARLESS("Fearless"),
    MACHETE("Machete"),
    STAR_WARS("Star Wars: Episode V - The Empire Strikes Back"),

    def pkgFragmentsToFolder = [
            ['core', 'movie'] : 'core/cast',
            ['ext_apis', 'themoviedb'] : 'themoviedb/cast'
    ]
    private String title;

    CastSample(String title) {
        this.title = title;
    }


    def readCastFromJSON(Class<?> castClazz) {
        def absolutePath = "/" + pkgFragmentsToFolder.entrySet().find{it.key.every{castClazz.package.name
                .contains(it)}}.value + "/" + this.name().toLowerCase() + ".json"

        def cast = new ObjectMapper().readValue(CastSample.class.getResource(absolutePath), castClazz)
        assert cast != null
        return cast
    }

    def getTitle(){
        return title;
    }

    static MovieSample byTitle(String title){
        return MovieSample.values().find{ it.title.equals(title) }
    }
}
