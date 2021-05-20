package com.mzelzoghbi.zgallery.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.mzelzoghbi.zgallery.ImageViewHolder;
import com.mzelzoghbi.zgallery.R;
import com.mzelzoghbi.zgallery.adapters.listeners.GridClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamedzakaria on 8/7/16.
 */
public class GridImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private ArrayList<String> imageURLs;
    private HashMap<String, String> headers;
    private Activity mActivity;
    private int imgPlaceHolderResId = -1;
    private GridClickListener clickListener;

    public GridImagesAdapter(Activity activity, HashMap<String, String> headers, ArrayList<String> imageURLs, int imgPlaceHolderResId) {
        this.imageURLs = imageURLs;
        this.headers = headers;
        this.mActivity = activity;
        this.imgPlaceHolderResId = imgPlaceHolderResId;
        this.clickListener = (GridClickListener) activity;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.z_item_image, null));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions().placeholder(imgPlaceHolderResId != -1 ? imgPlaceHolderResId : R.drawable.placeholder);

        RequestBuilder<Drawable> requestBuilder;
        if (!headers.isEmpty()){

            LazyHeaders.Builder lazyHeaderBuilder = new LazyHeaders.Builder();
            for (Map.Entry<String, String> entry: headers.entrySet()){
                lazyHeaderBuilder.addHeader(entry.getKey(), entry.getValue());
            }

            GlideUrl glideUrl = new GlideUrl(
                    imageURLs.get(position),
                    lazyHeaderBuilder.build()
            );

            requestBuilder = Glide.with(mActivity).load(glideUrl);
        }else {
            requestBuilder =  Glide.with(mActivity).load(imageURLs.get(position));
        }

        requestBuilder.apply(requestOptions).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageURLs != null ? imageURLs.size() : 0;
    }
}
