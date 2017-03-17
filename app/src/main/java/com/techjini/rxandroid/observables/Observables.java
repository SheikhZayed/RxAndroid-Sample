package com.techjini.rxandroid.observables;

import com.techjini.rxandroid.activity.MainActivity;
import com.techjini.rxandroid.utils.CommonUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ashif on 17/3/17,March,2017
 * TechJini Solutions
 * Banglore,India
 */

public class Observables {

    public static Observable OuterObservable(){
        return Observable.just("i'am an observer from outer class");
    }
}
