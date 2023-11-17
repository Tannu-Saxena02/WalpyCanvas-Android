package com.example.walpy.Response.GetSearchByItemResponse;

import java.util.List;

public class GetSearchByItemResponse {
	private String nextPage;
	private int perPage;
	private int page;
	private List<PhotosItem> photos;
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

	public int getTotalResults(){
		return totalResults;
	}
}