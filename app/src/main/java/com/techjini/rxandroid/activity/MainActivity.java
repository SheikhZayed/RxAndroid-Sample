package com.techjini.rxandroid.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.techjini.rxandroid.R;
import com.techjini.rxandroid.adapter.OSAdapter;
import com.techjini.rxandroid.databinding.ActivityMainBinding;
import com.techjini.rxandroid.model.OSModel;
import com.techjini.rxandroid.network.ApiInterface;
import com.techjini.rxandroid.network.RetrofitClient;
import com.techjini.rxandroid.observables.Observables;
import com.techjini.rxandroid.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static MainActivity mContext;
    private ActivityMainBinding mBinding;
    private ApiInterface mApiInterface;
    private CompositeDisposable mDisposable;
    private ProgressDialog mProgressDialog;

    public static MainActivity getHomeContext() {
        return mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        mApiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        mDisposable = new CompositeDisposable();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("loading .. ");
        mProgressDialog.show();
        loadJSON();
        setClickListeners();
    }

    private void setClickListeners() {
        mBinding.IntegObs.setOnClickListener(this);
        mBinding.stringObs.setOnClickListener(this);
        mBinding.ObjObserv.setOnClickListener(this);
        mBinding.classObs.setOnClickListener(this);
    }

    private void loadFromObservable() {
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(7);
                e.onNext(8);
                e.onNext(6);
                e.onComplete();
            }
        });

        Observer<Integer> integerObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                CommonUtils.showToast(getApplicationContext(), "subscribed to integer observer");
            }

            @Override
            public void onNext(Integer value) {
                CommonUtils.showToast(getApplicationContext(), String.valueOf(value));
            }

            @Override
            public void onError(Throwable e) {
                CommonUtils.showToast(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onComplete() {
                CommonUtils.showToast(getApplicationContext(), "completed observable integer");
            }
        };
        integerObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerObserver);
    }

    private void loadRandomString() {
        DisposableObserver<String> s = getFiles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String value) {
                        CommonUtils.showToast(getApplicationContext(), value + " -- got from observable");
                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getApplicationContext(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        CommonUtils.showToast(getApplicationContext(), "call completed");
                    }
                });
        mDisposable.add(s);
    }

    private void loadJSON() {
        mDisposable.add(mApiInterface.register()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<List<OSModel>>() {
                    @Override
                    public void onNext(List<OSModel> value) {
                        OSAdapter osAdapter = new OSAdapter((ArrayList<OSModel>) value);
                        mBinding.recyclerView.setAdapter(osAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CommonUtils.showToast(getApplicationContext(), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        CommonUtils.showToast(getApplicationContext(), "call completed");
                        mProgressDialog.dismiss();
                    }
                })
        );
    }

    public Observable<String> getFiles() {
        return Observable.just("RxJava is awesome ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag", "onDestroy: destroyed" + mDisposable.size() + "observable");
        mDisposable.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stringObs:
                loadRandomString();
                break;
            case R.id.IntegObs:
                loadFromObservable();
                break;
            case R.id.ObjObserv:
                loadObjObservable();
                break;
            case R.id.classObs:
                loadOuterObservable();
        }
    }

    private void loadOuterObservable() {
        Observable outer = Observables.OuterObservable();
        DisposableObserver<String> stringDisposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String value) {
                CommonUtils.showToast(getApplicationContext(),value);
            }

            @Override
            public void onError(Throwable e) {
                CommonUtils.showToast(getApplicationContext(),e.getMessage());
            }

            @Override
            public void onComplete() {
                CommonUtils.showToast(getApplicationContext(),"call completed outside observables");
            }
        };
        outer.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringDisposableObserver);
        mDisposable.add(stringDisposableObserver);
    }

    private void loadObjObservable() {
        ArrayList<OSModel> list = new ArrayList<>();
        OSModel osModel = new OSModel();
        osModel.setApi("27");
        osModel.setName("Android Ashif");
        osModel.setVer("77");

        OSModel osModel1 = new OSModel();
        osModel1.setApi("22");
        osModel1.setName("iOS Ashif");
        osModel1.setVer("72");

        list.add(osModel);
        list.add(osModel1);

        Observable<ArrayList<OSModel>> listObservable = Observable.just(list);

        DisposableObserver<ArrayList<OSModel>> disposableObserver = new DisposableObserver<ArrayList<OSModel>>() {
            @Override
            public void onNext(ArrayList<OSModel> value) {
                for (OSModel os : value) {
                    CommonUtils.showToast(getApplicationContext(), os.getName() + " " +os.getVer() + " " + os.getApi());
                }
            }

            @Override
            public void onError(Throwable e) {
                CommonUtils.showToast(getApplicationContext(), e.getMessage());
            }

            @Override
            public void onComplete() {
                CommonUtils.showToast(getApplicationContext(), "completed");
            }
        };
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
