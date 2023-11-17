package com.example.walpy.Response.GetWallpaperResponse;

import java.util.List;

public class GetWallpaperResponse{
	private String nextPage;
	private int perPage;
	private int page;
	private List<PhotosItem> photos;
	private String prevPage;
	private int totalResults;

	public String getNextPage(){
		return nextPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public int getPage(){
		return page;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	public String getPrevPage(){
		return prevPage;
	}

	public int getTotalResults(){
		return totalResults;
	}
}