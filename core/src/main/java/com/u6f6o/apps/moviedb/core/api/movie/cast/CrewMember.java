package com.u6f6o.apps.moviedb.core.api.movie.cast;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Author: u6f6o
 */
public class CrewMember {
    @NotNull
    private String realName;
    @NotNull
    private String department;
    @NotNull
    private String job;


    public CrewMember() {
    }

    public CrewMember( String realName ) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName( String realName ) {
        this.realName = realName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment( String department ) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob( String job ) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass( )) return false;

        CrewMember that = (CrewMember) o;

        return !( job != null ? !job.equals( that.job ) : that.job != null )
                && !( realName != null ? !realName.equals( that.realName ) : that.realName != null );

    }

    @Override
    public int hashCode() {
        int result = realName != null ? realName.hashCode() : 0;
        result = 31 * result + ( job != null ? job.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString() {
        return "CrewMember{" +
                "realName='" + realName + '\'' +
                ", department='" + department + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
