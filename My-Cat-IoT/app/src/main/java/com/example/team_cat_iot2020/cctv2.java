package com.example.team_cat_iot2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.VideoView;

public class cctv2 extends AppCompatActivity {
    private VideoView cctv2_video_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv2);

        cctv2_video_view = findViewById(R.id.cctv2_video_view);



    }
}
