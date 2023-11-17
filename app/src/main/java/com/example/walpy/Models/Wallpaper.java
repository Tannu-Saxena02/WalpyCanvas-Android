package com.example.walpy.Models;

import com.google.gson.annotations.SerializedName;

public class Wallpaper {
    @SerializedName("id")
    private long id;
    @SerializedName("width")
    private long width;
    @SerializedName("height")
    private long height;
    @SerializedName("url")
    private String url;
    @SerializedName("photographer")
    private String photographer;
    @SerializedName("photographer_url")
    private String photographer_url;
    @SerializedName("photographer_id")
    private String photographer_id;
    @SerializedName("avg_color")
    private String avg_color;
    @SerializedName("src")
    private ImageDimensions src;
    @SerializedName("liked")
    private boolean liked;
    @SerializedName("alt")
    private String alt;
}
