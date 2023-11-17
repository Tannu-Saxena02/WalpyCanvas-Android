package com.example.walpy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walpy.Models.CategoryRVModel;
import com.example.walpy.Response.GetSearchByItemResponse.GetSearchByItemResponse;
import com.example.walpy.Response.GetWallpaperResponse.GetWallpaperResponse;
import com.example.walpy.Response.GetWallpaperResponse.PhotosItem;
import com.example.walpy.ViewModel.GetWallpaperByCategoryViewModel;
import com.example.walpy.ViewModel.GetWallpaperViewModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface, View.OnClickListener {
    EditText searchTextInput;
    ImageView searchImage;
    RecyclerView horizontalImageRecyclerView, verticalImageRecyclerView;
    ProgressBar loader;
    Dialog dialog;
    AlertDialog alertDialog;
    RelativeLayout mainLayout;
    private ArrayList<PhotosItem> wallpaperArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;
    GetWallpaperViewModel getWallpaperViewModel;
    NestedScrollView nestedScrollView;
    private int currentItems, totalItems, scrolledOutItems;
    private boolean loadMore = true;
    private int offset=1;
    private GridLayoutManager layoutManager;
    int count=0;
    GetWallpaperByCategoryViewModel getWallpaperByCategoryViewModel;
    com.airbnb.lottie.LottieAnimationView lottieImage;
    NestedScrollView.OnScrollChangeListener listener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//            Toast.makeText(MainActivity.this, "scrolled", Toast.LENGTH_SHORT).show();
            currentItems = layoutManager.getChildCount();
            totalItems = layoutManager.getItemCount();
            scrolledOutItems = layoutManager.findFirstVisibleItemPosition();
            if (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1) != null) {
                if ((scrollY >= (nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
//                    Toast.makeText(MainActivity.this, "scrolled1", Toast.LENGTH_SHORT).show();
                    count++;
                    if (loadMore) {
                        offset++;
                        hitApi();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWallpaperViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(GetWallpaperViewModel.class);
        getWallpaperByCategoryViewModel= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(GetWallpaperByCategoryViewModel.class);
        searchImage = findViewById(R.id.searchImage);
        mainLayout=findViewById(R.id.mainlayout);
        lottieImage=findViewById(R.id.lottieImage);
        searchTextInput = findViewById(R.id.searchTextInput);
        nestedScrollView=findViewById(R.id.nestedScroll);
        nestedScrollView.setOnScrollChangeListener(listener);
        horizontalImageRecyclerView = findViewById(R.id.horizontalImageRecyclerview);
        verticalImageRecyclerView = findViewById(R.id.verticalImageRecyclerView);
        loader = findViewById(R.id.loader);
        wallpaperArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();
//        horizontalImageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList, this, this::onCategoryClick);
        horizontalImageRecyclerView.setAdapter(categoryRVAdapter);
        layoutManager=new GridLayoutManager(this,2);
        verticalImageRecyclerView.setLayoutManager(layoutManager);
//        wallpaperRVAdapter = new WallpaperRVAdapter(wallpaperArrayList, this);

// Check if no view has focus:
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }

// Then just use the following:
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        mainLayout.setOnClickListener(this);
        getCategories();
//        getWallpapers();
        hitApi();
        searchImage.setOnClickListener(this);

    }

    public void getCategories() {
        categoryRVModelArrayList.add(new CategoryRVModel("Technology", "https://images.pexels.com/photos/2007647/pexels-photo-2007647.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Programming", "https://images.pexels.com/photos/270404/pexels-photo-270404.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Nature", "https://images.pexels.com/photos/3225517/pexels-photo-3225517.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Animals", "https://images.pexels.com/photos/45170/kittens-cat-cat-puppy-rush-45170.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Travel", "https://images.pexels.com/photos/2499699/pexels-photo-2499699.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Architecture", "https://images.pexels.com/photos/4322027/pexels-photo-4322027.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Arts", "https://images.pexels.com/photos/102127/pexels-photo-102127.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Music", "https://images.pexels.com/photos/3971985/pexels-photo-3971985.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Abstract", "https://images.pexels.com/photos/4068339/pexels-photo-4068339.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Cars", "https://images.pexels.com/photos/1164778/pexels-photo-1164778.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVModelArrayList.add(new CategoryRVModel("Flowers", "https://images.pexels.com/photos/1697912/pexels-photo-1697912.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        categoryRVAdapter.notifyDataSetChanged();
    }
    public void hitApi(){
        if (ConnectivityUtils.isConnected(this)) {
//            if (offset != 1) {
//                loader.setVisibility(View.VISIBLE);
//            }
            try {
                getWallpapers(offset);

            } catch (Exception e) {
                e.printStackTrace();
                showCommonErrorDialog("Eror ::"+e.getMessage().toString());
            }
        } else {
            showCommonErrorDialog(getString(R.string.no_internet_connection));
        }
    }
    public void showCommonErrorDialog(String message) {
//        if(dialog!=null && dialog.isShowing())
//            dialog.dismiss();
//        dialog = new Dialog(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Show the Alert Dialog box
//        alertDialog.show();
        final View view = this.getLayoutInflater().inflate(R.layout.custom_commondialog, null);
        builder.setView(view);
//        builder.setContentView(R.layout.custom_commondialog);
//        builder.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btnOk = view.findViewById(R.id.btnOk);
        //btnOk.setText("Cancel");
        TextView txtMsg =view.findViewById(R.id.txtInfoMsg);
        //String errorMsg=transactionSummaryResponse.getMessage().getErrorMessage();
        txtMsg.setText(message);
//        txtMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX,
//                getResources().getDimension(16sp));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                //finish();
            }
        });
     alertDialog = builder.create();
        alertDialog.show();
    }

    public void getWallpapers(int offset) {
        wallpaperArrayList.clear();
        loader.setVisibility(View.VISIBLE);
        getWallpaperViewModel.getPhotos(offset,60);
        getWallpaperViewModel.GetWallpaperResponseMutableLiveData.observe(this, new Observer<GetWallpaperResponse>() {
        @Override
        public void onChanged(GetWallpaperResponse getWallpaperResponse) {
            if (getWallpaperResponse.getPhotos()!= null) {
                loader.setVisibility(View.GONE);
//                    ((MyBaseActivity) getActivity()).hideProgressDialog();
                if (getWallpaperResponse.getPhotos() != null &&
                        getWallpaperResponse.getPhotos().isEmpty()!=true) {
///
                    List<PhotosItem> list=getWallpaperResponse.getPhotos();
                    verticalImageRecyclerView.setVisibility(View.VISIBLE);
                    lottieImage.setVisibility(View.GONE);
                    wallpaperRVAdapter = new WallpaperRVAdapter(list,MainActivity.this);
                    verticalImageRecyclerView.setAdapter(wallpaperRVAdapter);
                    wallpaperRVAdapter.notifyDataSetChanged();


                }
                 if (getWallpaperResponse.getPage() == getWallpaperResponse.getTotalResults()) {
                    loadMore = false;
                }
                 if(getWallpaperResponse.getPage() != getWallpaperResponse.getTotalResults())
                     loadMore=true;
                else {
                    showCommonErrorDialog("No Data Available");
//                    horizontalImageRecyclerView.setVisibility(View.GONE);
                    verticalImageRecyclerView.setVisibility(View.GONE);
                    lottieImage.setVisibility(View.VISIBLE);

                    Log.d("error ","is"+new Gson().toJson(getWallpaperResponse));
//                    Toast.makeText(MainActivity.this, "error is"+getWallpaperResponse, Toast.LENGTH_SHORT).show();

                }
//                    myLoginResponse.getpersonalDetail().removeObserver(this);

            }
            else {
                loader.setVisibility(View.GONE);
//                horizontalImageRecyclerView.setVisibility(View.GONE);
                verticalImageRecyclerView.setVisibility(View.GONE);
                lottieImage.setVisibility(View.VISIBLE);
//                    ((MyBaseActivity) getActivity()).hideProgressDialog();
                try {
                    Log.d("out error", "onChanged: "+getWallpaperResponse);
//                    horizontalImageRecyclerView.setVisibility(View.GONE);
                    verticalImageRecyclerView.setVisibility(View.GONE);
                    lottieImage.setVisibility(View.VISIBLE);
                    showCommonErrorDialog("error comes");
//                        loginViewModel.getpersonalDetails().removeObserver(this);

                } catch (Exception e) {
                    showCommonErrorDialog("Eror occured"+e);
//                    horizontalImageRecyclerView.setVisibility(View.GONE);
                    verticalImageRecyclerView.setVisibility(View.GONE);
                    lottieImage.setVisibility(View.VISIBLE);
                }
            }
        }
    });
    }

    public void getWallpaperByCategory(String category) {
        wallpaperArrayList.clear();
        loader.setVisibility(View.VISIBLE);
        getWallpaperByCategoryViewModel.getPhotoBySearch(category,1,30);
        getWallpaperByCategoryViewModel.GetSearchResponseMutableLiveData.observe(this, new Observer<GetWallpaperResponse>() {
            @Override
            public void onChanged(GetWallpaperResponse getWallpaperResponse) {
                if (getWallpaperResponse.getPhotos()!= null) {
//                    ((MyBaseActivity) getActivity()).hideProgressDialog();
                    if (getWallpaperResponse.getPhotos() != null &&
                            getWallpaperResponse.getPhotos().isEmpty()!=true) {
                        loader.setVisibility(View.GONE);
                        List<PhotosItem> photolists=getWallpaperResponse.getPhotos();
                        verticalImageRecyclerView.setVisibility(View.VISIBLE);
                        lottieImage.setVisibility(View.GONE);
                        wallpaperRVAdapter = new WallpaperRVAdapter(photolists,MainActivity.this);
                        verticalImageRecyclerView.setAdapter(wallpaperRVAdapter);
                        wallpaperRVAdapter.notifyDataSetChanged();


                    }
                    else {
//                        Toast.makeText(MainActivity.this, "error is"+getWallpaperResponse, Toast.LENGTH_SHORT).show();
                        showCommonErrorDialog("No Data available");
//                        horizontalImageRecyclerView.setVisibility(View.GONE);
                        verticalImageRecyclerView.setVisibility(View.GONE);
                        lottieImage.setVisibility(View.VISIBLE);
                    }
//                    myLoginResponse.getpersonalDetail().removeObserver(this);

                }
                else {
//                    ((MyBaseActivity) getActivity()).hideProgressDialog();
                    try {
                        Log.d("out error", "onChanged: "+getWallpaperResponse);
                        showCommonErrorDialog("No Data available");
//                        horizontalImageRecyclerView.setVisibility(View.GONE);
                        verticalImageRecyclerView.setVisibility(View.GONE);
                        lottieImage.setVisibility(View.VISIBLE);
//                    showCommonErrorDialog(LoginResponse.errorBody().string(),0);
//                        loginViewModel.getpersonalDetails().removeObserver(this);

                    } catch (Exception e) {
                        showCommonErrorDialog("error::"+e);
//                        horizontalImageRecyclerView.setVisibility(View.GONE);
                        verticalImageRecyclerView.setVisibility(View.GONE);
                        lottieImage.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelArrayList.get(position).getCategory();
        getWallpaperByCategory(category);
    }

    public void searchByImage() {
        String searchstr = searchTextInput.getText().toString();
        if (searchstr.isEmpty()) {
            showCommonErrorDialog("Please enter your search query");
            Toast.makeText(this, "Please enter your search query", Toast.LENGTH_SHORT).show();
        }
    else
    {
        getWallpaperByCategory(searchstr);
    }
    }
    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {

            case R.id.searchImage:
                hideKeyboard(view);
                searchByImage();
            case R.id.mainlayout:
                hideKeyboard(view);

        }

    }
}