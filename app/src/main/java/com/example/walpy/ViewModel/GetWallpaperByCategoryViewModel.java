package com.example.walpy.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.walpy.Response.GetSearchByItemResponse.GetSearchByItemResponse;
import com.example.walpy.Response.GetWallpaperResponse.GetWallpaperResponse;
import com.example.walpy.Services.RetrofitClient;
import com.example.walpy.Services.RetrofitFactory;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetWallpaperByCategoryViewModel extends AndroidViewModel {
    RetrofitFactory retrofitFactory;
    public MutableLiveData<GetWallpaperResponse> GetSearchResponseMutableLiveData;
    private  MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();
    RetrofitClient retrofitClient;
    public GetWallpaperByCategoryViewModel(@NonNull Application application) {
        super(application);
    }
    public void getPhotoBySearch(String category,int page,int per_page)
    {
        GetSearchResponseMutableLiveData =new MutableLiveData<>();
        retrofitFactory=new RetrofitFactory();
        retrofitClient = RetrofitFactory.getClient().create(RetrofitClient.class);
        Log.d("retrofit","is"+retrofitClient);
        Observable<GetWallpaperResponse> GetWallpaperResponseObservable =retrofitClient.getWallpaperBySearch("2vQxU15W9pCAmgy7DmucnxESEGXi2c9VqcToSxPzmocbIDmBv3CiDS8L",category,page,per_page);
        Log.d("response","is"+new Gson().toJson(GetWallpaperResponseObservable));
        GetWallpaperResponseObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<GetWallpaperResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetWallpaperResponse value) {
                GetSearchResponseMutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                errorLiveData.setValue(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
