package com.techjini.rxandroid.model;

/**
 * Created by Ashif on 17/3/17,March,2017
 * TechJini Solutions
 * Banglore,India
 */

public class OSModel {
    private String ver;
    private String name;
    private String api;

    public String getVer() {
        return ver;
    }

    public String getName() {
        return name;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }
}
