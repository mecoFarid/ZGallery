package com.mzelzoghbi.zgallery;

import android.app.Activity;
import android.content.Intent;

import com.mzelzoghbi.zgallery.activities.ZGalleryActivity;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ZGallery {
    private Activity mActivity;
    private ArrayList<String> imagesURLs;
    private HashMap<String, String> headers;
    private String title;
    private int spanCount = 2;
    private int toolbarColor = -1;
    private int imgPlaceHolderResId = -1;
    private ZColor color;
    private int selectedImgPosition;
    private ZColor backgroundColor;
    private int colorCaptionTextResId = -1;
    private int colorCaptionBgResId = -1;
    private float captionTextSizeResId = -1;
    private boolean enableCaption = false;

    private ZGallery() {
    }

    /**
     * @param activity   Refrence from current activity
     * @param imagesURLs Image URLs to be displayed
     */
    public static ZGallery with(Activity activity, ArrayList<String> imagesURLs) {
        return new ZGallery(activity, new HashMap<String, String>(),imagesURLs);
    }

    /**
     * @param activity   Refrence from current activity
     * @param imagesURLs Image URLs to be displayed
     */
    public static ZGallery withHeaders(Activity activity, HashMap<String, String> headers, ArrayList<String> imagesURLs) {
        return new ZGallery(activity, headers,imagesURLs);
    }


    private ZGallery(Activity activity, HashMap<String, String> headers,ArrayList<String> imagesURLs) {
        this.imagesURLs = imagesURLs;
        this.headers = headers;
        this.mActivity = activity;
    }

    /**
     * Set z_toolbar title
     *
     * @param title
     * @return
     */
    public ZGallery setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Setting z_toolbar Color ResourceId
     *
     * @param colorResId
     * @return
     */
    public ZGallery setToolbarColorResId(int colorResId) {
        this.toolbarColor = colorResId;
        return this;
    }

    /**
     * Setting z_toolbar color
     *
     * @param color enum color may be black or white
     * @return
     */
    public ZGallery setToolbarTitleColor(ZColor color) {
        this.color = color;
        return this;
    }

    /**
     * Setting starting position
     *
     * @param selectedImgPosition
     * @return
     */
    public ZGallery setSelectedImgPosition(int selectedImgPosition) {
        this.selectedImgPosition = selectedImgPosition;
        return this;
    }

    public ZGallery setGalleryBackgroundColor(ZColor backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /**
     * Setting caption text color
     * @return
     */
    public ZGallery setCaptionTextColorResId(int color) {
        this.colorCaptionTextResId = color;
        return this;
    }

    /**
     * Setting caption text color
     * @return
     */
    public ZGallery setCaptionBackgroundColorResId(int color) {
        this.colorCaptionBgResId = color;
        return this;
    }

    /**
     * Setting caption text size
     * @return
     */
    public ZGallery setCaptionTextSizeResId(float textSizeResId) {
        this.captionTextSizeResId = textSizeResId;
        return this;
    }

    /**
     * Setting enable/disable caption
     * @return
     */
    public ZGallery setCaptionEnabled(boolean enable) {
        this.enableCaption = enable;
        return this;
    }

    /**
     * Start the gallery activity with builder settings
     */
    public void show() {
        Intent gridActivity = new Intent(mActivity, ZGalleryActivity.class);
        gridActivity.putExtra(Constants.IntentPassingParams.IMAGES, imagesURLs);
        gridActivity.putExtra(Constants.IntentPassingParams.HEADERS, headers);
        gridActivity.putExtra(Constants.IntentPassingParams.TITLE, title);
        gridActivity.putExtra(Constants.IntentPassingParams.TOOLBAR_COLOR_ID, toolbarColor);
        gridActivity.putExtra(Constants.IntentPassingParams.TOOLBAR_TITLE_COLOR, color);
        gridActivity.putExtra(Constants.IntentPassingParams.SELECTED_IMG_POS, selectedImgPosition);
        gridActivity.putExtra(Constants.IntentPassingParams.BG_COLOR, backgroundColor);

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
