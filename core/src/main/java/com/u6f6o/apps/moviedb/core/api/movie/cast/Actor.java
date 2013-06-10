package com.u6f6o.apps.moviedb.core.api.movie.cast;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class Actor implements Comparable<Actor> {
    @NotNull
    private String realName;
    @NotNull
    private String characterName;
    @NotNull
    private Integer order;


    public String getRealName() {
        return realName;
    }

    public void setRealName( String realName ) {
        this.realName = realName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName( String characterName ) {
        this.characterName = characterName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder( Integer order ) {
        this.order = order;
    }

    @Override
    public int compareTo( Actor another ) {
        if( this.getOrder() == null || another.getOrder() == null )
            throw new IllegalStateException( "Order on actor object must not be null!" );

        return this.getOrder().compareTo( another.getOrder( ));
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass( )) return false;

        Actor actor = (Actor) o;

        return !(characterName != null ? !characterName.equals( actor.characterName ) : actor.characterName != null )
                && !( realName != null ? !realName.equals( actor.realName ) : actor.realName != null );
    }

    @Override
    public int hashCode() {
        int result = realName != null ? realName.hashCode() : 0;
        result = 31 * result + ( characterName != null ? characterName.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "characterName='" + characterName + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
