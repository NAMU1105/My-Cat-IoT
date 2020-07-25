package com.example.team_cat_iot2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private TextView main_txt_main_text, main_txt_cat_status, main_txt_food_percentage, main_txt_water_percentage, main_txt_cctv, main_txt_stastic;
    private ImageView main_img_cctv, main_img_stastic, main_img_current_water, main_img_current_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FVBID
        main_txt_main_text = findViewById(R.id.main_txt_main_text);
        main_txt_cat_status = findViewById(R.id.main_txt_cat_status);
        main_img_cctv = findViewById(R.id.main_img_cctv);
        main_img_stastic = findViewById(R.id.main_img_stastic);
        main_img_current_water = findViewById(R.id.main_img_current_water);
        main_img_current_food = findViewById(R.id.main_img_current_food);
        main_txt_food_percentage = findViewById(R.id.main_txt_food_percentage);
        main_txt_water_percentage = findViewById(R.id.main_txt_water_percentage);
        main_txt_cctv = findViewById(R.id.main_txt_cctv);
        main_txt_stastic = findViewById(R.id.main_txt_stastic);




        //main_img_current_water, main_img_current_food 현재 물양, 사료양에 따라 UI 바꾸기






//        화면전환
//        1. cctv
        main_img_cctv.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "cctv이미지 클릭");

                Intent cctv = new Intent(MainActivity.this, cctv.class);
                startActivity(cctv);
            }
        });


        main_txt_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cctv = new Intent(MainActivity.this, cctv.class);
                startActivity(cctv);
            }
        });




        // 2. 통계
        main_img_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent health_stastic = new Intent(MainActivity.this, health_stastic.class);
                startActivity(health_stastic);
            }
        });
        main_txt_stastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent health_stastic = new Intent(MainActivity.this, health_stastic.class);
                startActivity(health_stastic);
            }
        });

        // 3. 현재 물양>>물주기 기능
        main_img_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent health_stastic = new Intent(MainActivity.this, health_stastic.class);
//                startActivity(health_stastic);
            }
        });

        // 4. 현재 밥양 >>밥 주기 기능->> 클릭 시 다이얼로그로 밥양 많이, 중간, 적게 중 고를 수 있게 할 예정
        main_img_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });


    }
}
