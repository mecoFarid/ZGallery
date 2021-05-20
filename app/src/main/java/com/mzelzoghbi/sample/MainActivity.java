package com.mzelzoghbi.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.mzelzoghbi.zgallery.ZGallery;
import com.mzelzoghbi.zgallery.ZGrid;
import com.mzelzoghbi.zgallery.entities.ZColor;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void gridActivity(View v) {
        ZGrid.with(this, getDummyImageList())
                .setToolbarColorResId(R.color.colorPrimary)
                .setTitle("Zak Gallery")
                .setToolbarTitleColor(ZColor.WHITE)
                .setSpanCount(3)
                .setGridImgPlaceHolder(R.color.colorPrimary)
                .show();
    }


    public void galleryActivity(View v) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-AuthToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiI2OTciLCJ1bmlxdWVfbmFtZSI6Im1lY29mYXJpZCIsIm5iZiI6MTU4ODQyMjg5MCwiZXhwIjoxNjE5NTI2ODkwLCJpYXQiOjE1ODg0MjI4OTAsImlzcyI6Imh0dHA6Ly93d3cudGF5cWF0ZWNoLmNvbSIsImF1ZCI6Imh0dHA6Ly93d3cudGF5cWF0ZWNoLmNvbSJ9.K4bj8O_Q_EnSkqpOJpyRmMsH8W0Zp8et7HWkS1xJGA0");
        headers.put("X-DeviceId", "dc2f3552-ebf1-45d4-8c71-3c4930c494a6");
        ZGallery.withHeaders(this, headers,  getDummyImageList())
                .setToolbarTitleColor(ZColor.WHITE)
                .setGalleryBackgroundColor(ZColor.WHITE)
                .setToolbarColorResId(R.color.colorPrimary)
                .setTitle("Zak Gallery")
                .setCaptionEnabled(true)
                .show();
    }

    private ArrayList<String> getDummyImageList() {
        ArrayList<String> imagesList = new ArrayList<>();
        imagesList.add("https://img-9gag-fun.9cache.com/photo/aK6WAw3_700bwp.webp");
        imagesList.add("/storage/emulated/0/Download/Image_created_with_a_mobile_phone.png");
        return imagesList;
    }
}
