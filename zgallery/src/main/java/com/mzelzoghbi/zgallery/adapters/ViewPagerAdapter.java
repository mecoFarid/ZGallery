package com.mzelzoghbi.zgallery.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mzelzoghbi.zgallery.R;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<String> images;
    HashMap<String, String> headers;
    PhotoViewAttacher mPhotoViewAttacher;
    private boolean isShowing = true;
    private Toolbar toolbar;
    private RecyclerView imagesHorizontalList;

    public ViewPagerAdapter(Activity activity, HashMap<String, String> headers, ArrayList<String> images, Toolbar toolbar, RecyclerView imagesHorizontalList) {
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
        this.headers = headers;
        this.toolbar = toolbar;
        this.imagesHorizontalList = imagesHorizontalList;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.z_pager_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv);

        RequestBuilder<Drawable> requestBuilder;
        if (!headers.isEmpty()){

            LazyHeaders.Builder lazyHeaderBuilder = new LazyHeaders.Builder();
            for (Map.Entry<String, String> entry: headers.entrySet()){
                lazyHeaderBuilder.addHeader(entry.getKey(), entry.getValue());
            }

             GlideUrl glideUrl = new GlideUrl(
                    images.get(position),
                    lazyHeaderBuilder.build()
            );

            new SimpleTask().execute("");
            System.out.println("mecoFarid url zg "+images.get(position));

            requestBuilder = Glide.with(activity).load(glideUrl);
        }else {
            requestBuilder =  Glide.with(activity).load(images.get(position));
        }

        requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mPhotoViewAttacher = new PhotoViewAttacher(imageView);

                mPhotoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        if (isShowing) {
                            isShowing = false;
                            toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                            imagesHorizontalList.animate().translationY(imagesHorizontalList.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        } else {
                            isShowing = true;
                            toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                            imagesHorizontalList.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        }
                    }

                    @Override
                    public void onOutsidePhotoTap() {

                    }
                });


                return false;
            }

        }).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    private static class SimpleTask extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL("https:/www.google.com/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                System.out.println("mecoFarid url zg e "+urlConnection.getResponseCode());
            } catch (Exception e){
                System.out.println("mecoFarid url zg e "+e);
            }
            return null;
        }
    }
}
