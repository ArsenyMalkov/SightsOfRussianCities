package com.arsenymalkov.sightsofrussiancities.main;

import android.support.annotation.NonNull;

public class City implements Comparable<City> {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull City city) {
        return 0;
    }

    public City copy() {
        City copy = new City();
        copy.id = id;
        copy.name = name;
        return copy;
    }

}
