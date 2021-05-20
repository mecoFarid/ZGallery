package com.mzelzoghbi.zgallery.adapters;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mzelzoghbi.zgallery.Constants;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.util.Utils;

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
    private int colorCaptionTextResId;
    private int colorCaptionBgResId;
    private boolean enableCaption;
    private float captionTextSize;

    public ViewPagerAdapter(Activity activity, HashMap<String, String> headers, ArrayList<String> images, Toolbar toolbar, RecyclerView imagesHorizontalList) {
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
        this.headers = headers;
        this.toolbar = toolbar;
        this.imagesHorizontalList = imagesHorizontalList;

        colorCaptionTextResId = activity.getIntent().getIntExtra(Constants.IntentPassingParams.COLOR_CAPTION_TEXT, android.R.color.darker_gray);
        colorCaptionBgResId = activity.getIntent().getIntExtra(Constants.IntentPassingParams.COLOR_CAPTION_BG, android.R.color.white);
        enableCaption = activity.getIntent().getBooleanExtra(Constants.IntentPassingParams.ENABLE_CAPTION, false);
        captionTextSize = activity.getIntent().getFloatExtra(Constants.IntentPassingParams.CAPTION_TEXT_SIZE, 12f);
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
        final TextView captionView = (TextView) itemView.findViewById(R.id.caption);
        initCaptionViewStyle(captionView);

        RequestBuilder<Drawable> requestBuilder;
        String filePath = images.get(position);
        captionView.setText(Utils.getFileName(filePath));
        if (!headers.isEmpty()){

            LazyHeaders.Builder lazyHeaderBuilder = new LazyHeaders.Builder();
            for (Map.Entry<String, String> entry: headers.entrySet()){
                lazyHeaderBuilder.addHeader(entry.getKey(), entry.getValue());
            }

             GlideUrl glideUrl = new GlideUrl(
                    filePath,
                    lazyHeaderBuilder.build()
            );

            requestBuilder = Glide.with(activity).load(glideUrl);
        }else {
            requestBuilder =  Glide.with(activity).load(filePath);
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
                            float transformBy = -toolbar.getBottom();
                            TimeInterpolator interpolator = new AccelerateInterpolator();
                            toolbar.animate().translationY(transformBy).setInterpolator(interpolator).start();
                            captionView.animate().translationY(transformBy).setInterpolator(interpolator).start();
                            imagesHorizontalList.animate().translationY(imagesHorizontalList.getBottom()).setInterpolator(interpolator).start();
                        } else {
                            isShowing = true;
                            TimeInterpolator interpolator = new DecelerateInterpolator();
                            toolbar.animate().translationY(0).setInterpolator(interpolator).start();
                            captionView.animate().translationY(0).setInterpolator(interpolator).start();;
                            imagesHorizontalList.animate().translationY(0).setInterpolator(interpolator).start();
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

    private void initCaptionViewStyle(TextView captionView){
        captionView.setVisibility(enableCaption ? View.VISIBLE : View.GONE);

        if (enableCaption) {
            captionView.setBackgroundColor(captionView.getResources().getColor(colorCaptionBgResId));
            captionView.setTextColor(captionView.getResources().getColor(colorCaptionTextResId));
            captionView.setTextSize(captionTextSize);
        }
    }
}
