package com.mzelzoghbi.zgallery.adapters;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.mzelzoghbi.zgallery.HorizontalImageViewHolder;
import com.mzelzoghbi.zgallery.OnImgClick;
import com.mzelzoghbi.zgallery.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamedzakaria on 8/12/16.
 */
public class HorizontalListAdapters extends RecyclerView.Adapter<HorizontalImageViewHolder> {
    ArrayList<String> images;
    HashMap<String, String> headers;
    Activity activity;
    int selectedItem = -1;
    OnImgClick imgClick;

    public HorizontalListAdapters(Activity activity, HashMap<String, String> headers, ArrayList<String> images, OnImgClick imgClick) {
        this.activity = activity;
        this.images = images;
        this.headers = headers;
        this.imgClick = imgClick;
    }

    @Override
    public HorizontalImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.z_item_image_horizontal, null));
    }

    @Override
    public void onBindViewHolder(HorizontalImageViewHolder holder, final int position) {

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

            requestBuilder = Glide.with(activity).load(glideUrl);
        }else {
            requestBuilder =  Glide.with(activity).load(images.get(position));
        }
        requestBuilder.into(holder.image);

        ColorMatrix matrix = new ColorMatrix();
        if (selectedItem != position) {
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(0.5f);
        } else {
            matrix.setSaturation(1);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(1f);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }
}
