package com.example.walpy.Services;

import com.example.walpy.Models.WallpaperResponse;
import com.example.walpy.Response.GetSearchByItemResponse.GetSearchByItemResponse;
import com.example.walpy.Response.GetWallpaperResponse.GetWallpaperResponse;


//import io.reactivex.Observable;
//import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitClient {

    @GET("/v1/curated")
    Observable<GetWallpaperResponse> getWallpaper(@Header("Authorization") String Credentials, @Query("page")int pageCount, @Query("per_page")int perPage);

    @GET("/v1/search")
    Observable<GetWallpaperResponse> getWallpaperBySearch(@Header("Authorization") String Credentials, @Query("query")String category, @Query("page")int pageCount, @Query("per_page")int perPage);
}
