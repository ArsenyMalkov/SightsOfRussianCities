package com.arsenymalkov.sightsofrussiancities;

import android.support.annotation.NonNull;

public class Sight implements Comparable<Sight> {

    private String name;
    private String imageUrl;
    private String geo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    @Override
    public int compareTo(@NonNull Sight sight) {
        return 0;
    }

    public Sight copy() {
        Sight copy = new Sight();
        copy.name = name;
        copy.imageUrl = imageUrl;
        copy.geo = geo;
        return copy;
    }

}
