package com.arsenymalkov.sightsofrussiancities.main;

import android.support.annotation.NonNull;

public class Region implements Comparable<Region> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(@NonNull Region region) {
        return 0;
    }

    public Region copy() {
        Region copy = new Region();
        copy.name = name;
        return copy;
    }

}
