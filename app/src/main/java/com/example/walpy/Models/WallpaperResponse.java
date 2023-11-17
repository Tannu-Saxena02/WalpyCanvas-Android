package com.example.walpy.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WallpaperResponse {
    @SerializedName("total_results")
    private int total_results;

    @SerializedName("page")
    private int page;

    @SerializedName("per_page")
    private int per_page;

    @SerializedName("photos")
    private List<Wallpaper> photoList;

    @SerializedName("next_page")
    private String next_Page;
}
