package com.example.walpy.Response.GetSearchByItemResponse;

public class PhotosItem{
	private Src src;
	private int width;
	private String avgColor;
	private String alt;
	private String photographer;
	private String photographerUrl;
	private int id;
	private String url;
	private int photographerId;
	private boolean liked;
	private int height;

	public Src getSrc(){
		return src;
	}

	public int getWidth(){
		return width;
	}

	public String getAvgColor(){
		return avgColor;
	}

	public String getAlt(){
		return alt;
	}

	public String getPhotographer(){
		return photographer;
	}

	public String getPhotographerUrl(){
		return photographerUrl;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}

	public int getPhotographerId(){
		return photographerId;
	}

	public boolean isLiked(){
		return liked;
	}

	public int getHeight(){
		return height;
	}
}
