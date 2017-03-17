package com.techjini.rxandroid.network;

import com.techjini.rxandroid.model.OSModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Ashif on 10/1/17,January,2017
 * TechJini Solutions
 * Banglore,India
 */

public interface ApiInterface {
    @GET("android/jsonarray/")
    Observable<List<OSModel>> register();
}
