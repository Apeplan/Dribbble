package com.simon.dribbble.data.model;

public class ImagesEntity {
    private String hidpi;
    private String normal;
    private String teaser;

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getHidpi() {
        return hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public String getTeaser() {
        return teaser;
    }
}