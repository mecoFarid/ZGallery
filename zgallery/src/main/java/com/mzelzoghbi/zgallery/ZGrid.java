package com.mzelzoghbi.zgallery;

import android.app.Activity;
import android.content.Intent;

import com.mzelzoghbi.zgallery.activities.ZGridActivity;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamedzakaria on 8/7/16.
 */
public class ZGrid {
    private Activity mActivity;
    private ArrayList<String> imagesURLs;
    private HashMap<String, String> headers;
    private String title;
    private int spanCount = 2;
    private int toolbarColor = -1;
    private int imgPlaceHolderResId = -1;
    private ZColor color;
    private int colorCaptionTextResId = -1;
    private int colorCaptionBgResId = -1;
    private float captionTextSizeResId = -1;
    private boolean enableCaption = false;

    private ZGrid() {
    }

    /**
     * @param activity   Refrence from current activity
     * @param imagesURLs Image URLs to be displayed
     */
    public static ZGrid with(Activity activity, ArrayList<String> imagesURLs) {
        return new ZGrid(activity, new HashMap<String, String>(),imagesURLs);
    }

    /**
     * @param activity   Refrence from current activity
     * @param imagesURLs Image URLs to be displayed
     */
    public static ZGrid withHeaders(Activity activity, HashMap<String, String> headers, ArrayList<String> imagesURLs) {
        return new ZGrid(activity, headers,imagesURLs);
    }


    private ZGrid(Activity activity, HashMap<String, String> headers, ArrayList<String> imagesURLs) {
        this.imagesURLs = imagesURLs;
        this.headers = headers;
        this.mActivity = activity;
    }

    /**
     * Set toolbar title
     *
     * @param title
     * @return
     */
    public ZGrid setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set grid layout colums count (default: 2)
     *
     * @param count integer number for colum count
     * @return
     */
    public ZGrid setSpanCount(int count) {
        this.spanCount = count;
        return this;
    }

    /**
     * Setting toolbar Color ResourceId
     *
     * @param colorResId
     * @return
     */
    public ZGrid setToolbarColorResId(int colorResId) {
        this.toolbarColor = colorResId;
        return this;
    }

    /**
     * Set placeholder image for images in the grid
     * @param imgPlaceHolderResId
     * @return
     */
    public ZGrid setGridImgPlaceHolder(int imgPlaceHolderResId) {
        this.imgPlaceHolderResId = imgPlaceHolderResId;
        return this;
    }

    /**
     * Setting toolbar color
     *
     * @param color enum color may be black or white
     * @return
     */
    public ZGrid setToolbarTitleColor(ZColor color) {
        this.color = color;
        return this;
    }

    /**
     * Setting caption text color
     * @return
     */
    public ZGrid setCaptionTextColorResId(int color) {
        this.colorCaptionTextResId = color;
        return this;
    }

    /**
     * Setting caption text color
     * @return
     */
    public ZGrid setCaptionBackgroundColorResId(int color) {
        this.colorCaptionBgResId = color;
        return this;
    }

    /**
     * Setting caption text size
     * @return
     */
    public ZGrid setCaptionTextSizeResId(float textSizeResId) {
        this.captionTextSizeResId = textSizeResId;
        return this;
    }

    /**
     * Setting enable/disable caption
     * @return
     */
    public ZGrid setCaptionEnabled(boolean enable) {
        this.enableCaption = enable;
        return this;
    }

    /**
     * Start the grid activity with builder settings
     */
    public void show() {
        Intent gridActivity = new Intent(mActivity, ZGridActivity.class);
        gridActivity.putExtra(Constants.IntentPassingParams.IMAGES, imagesURLs);
        gridActivity.putExtra(Constants.IntentPassingParams.HEADERS, headers);
        gridActivity.putExtra(Constants.IntentPassingParams.COUNT, spanCount);
        gridActivity.putExtra(Constants.IntentPassingParams.TITLE, title);
        gridActivity.putExtra(Constants.IntentPassingParams.TOOLBAR_COLOR_ID, toolbarColor);
        gridActivity.putExtra(Constants.IntentPassingParams.IMG_PLACEHOLDER, imgPlaceHolderResId);
        gridActivity.putExtra(Constants.IntentPassingParams.TOOLBAR_TITLE_COLOR, color);

        if (colorCaptionBgResId > -1) {
            gridActivity.putExtra(Constants.IntentPassingParams.COLOR_CAPTION_BG, colorCaptionBgResId);
        }
        if (colorCaptionTextResId > -1) {
            gridActivity.putExtra(Constants.IntentPassingParams.COLOR_CAPTION_TEXT, colorCaptionTextResId);
        }
        if (captionTextSizeResId > -1) {
            gridActivity.putExtra(Constants.IntentPassingParams.CAPTION_TEXT_SIZE, captionTextSizeResId);
        }
        gridActivity.putExtra(Constants.IntentPassingParams.ENABLE_CAPTION, enableCaption);
        mActivity.startActivity(gridActivity);
    }
}
