package com.example.team_cat_iot2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.VideoView;

public class cctv extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private VideoView cctv_video_view_cctv1, cctv_video_view_cctv2;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_cctv);

        //FVBID
        cctv_video_view_cctv1 = findViewById(R.id.cctv_video_view_cctv1);
        cctv_video_view_cctv2 = findViewById(R.id.cctv_video_view_cctv2);
        webView = findViewById(R.id.webView);


        webView.setPadding(0,0,0,0);
//        webView.setInitialScale(100);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        String url ="http://192.168.43.149:8081"; //0117
//        String url ="http://192.168.43.184:8081"; //0117
//        String url ="http://192.168.0.62:8081"; //큰화면
//        String url ="http://192.168.0.7:8081";
        webView.loadUrl(url);
        // 자신이 실행시킨 스트리밍의 주소를 넣어주세요.
//        webView.loadUrl("http://192.168.0.62:8081/");


//        화면전환
//        1. cctv1
//        cctv_video_view_cctv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent cctv1 = new Intent(cctv.this, cctv1.class);
//                startActivity(cctv1);
//            }
//        });

        //webView로 바꿈 >>안 되고 필요 없음
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cctv1 = new Intent(cctv.this, cctv1.class);
                startActivity(cctv1);
            }
        });



//        2. cctv2
        cctv_video_view_cctv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cctv2 = new Intent(cctv.this, cctv2.class);
                startActivity(cctv2);
            }
        });





    }
}
