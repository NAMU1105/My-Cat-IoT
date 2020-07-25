package com.example.team_cat_iot2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.VideoView;

import com.example.team_cat_iot2020.retrofit.Data;
import com.example.team_cat_iot2020.retrofit.RetrofitService;
import com.example.team_cat_iot2020.retrofit.RetrofitService2;
import com.example.team_cat_iot2020.retrofit.RetrofitService_food;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class cctv1 extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private Button cctv1_btn_play, cctv1_btn_music, cctv1_btn_water, cctv1_btn_feed, cctv1_btn_stop_play;
    private ImageView cctv1_btn_left_top, cctv1_btn_center, cctv1_btn_right_top, cctv1_btn_left, cctv1_btn_right;
    private VideoView cctv1_video_view;
    private WebView webView2;
//    private Spinner
    private RetrofitService retrofitService_play;
    private RetrofitService2 retrofitService;
    private RetrofitService_food retrofitService_food;
    private Retrofit retrofit_play;
    private Retrofit retrofit;
    private Retrofit retrofit_food;


    private String play_time;
    private int play_time_int;


    private String food_amount;
    private int food_amount_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우

        setContentView(R.layout.activity_cctv1);


        //FVBID
        cctv1_btn_play = findViewById(R.id.cctv1_btn_play);
        cctv1_btn_music = findViewById(R.id.cctv1_btn_music);
        cctv1_btn_water = findViewById(R.id.cctv1_btn_water);
        cctv1_btn_feed = findViewById(R.id.cctv1_btn_feed);
        cctv1_btn_left_top = findViewById(R.id.cctv1_btn_left_top);
        cctv1_btn_center = findViewById(R.id.cctv1_btn_center);
        cctv1_btn_right_top = findViewById(R.id.cctv1_btn_right_top);
        cctv1_btn_left = findViewById(R.id.cctv1_btn_left);
        cctv1_btn_right = findViewById(R.id.cctv1_btn_right);
        cctv1_video_view = findViewById(R.id.cctv1_video_view);
        cctv1_btn_stop_play = findViewById(R.id.cctv1_btn_stop_play);
        webView2 = findViewById(R.id.webView2);



        //cctv카메라_웹뷰
        webView2.setPadding(0,0,0,0);
//        webView.setInitialScale(100);
        webView2.getSettings().setBuiltInZoomControls(true); //줌기능
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.getSettings().setLoadWithOverviewMode(true);
        webView2.getSettings().setUseWideViewPort(true);
        //webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        String url ="http://192.168.43.149:8081"; //0117
//        String url ="http://192.168.43.184:8081"; //0117
//        String url ="http://192.168.0.62:8081"; //큰화면
//        String url ="http://192.168.0.7:8081";
        webView2.loadUrl(url);
        // 자신이 실행시킨 스트리밍의 주소를 넣어주세요.
//        webView.loadUrl("http://192.168.0.62:8081/");



        //1. 레트로핏_cctv 카메라 각도 이동
        retrofit =  new Retrofit.Builder()
                .baseUrl(RetrofitService2.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService2.class);



        //2. 레트로핏_밥주기
        retrofit_food =  new Retrofit.Builder()
                .baseUrl(RetrofitService_food.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService_food = retrofit_food.create(RetrofitService_food.class);


        //3. 레트로핏_놀아주기
        retrofit_play =  new Retrofit.Builder()
                .baseUrl(retrofitService_play.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService_play = retrofit_play.create(RetrofitService.class);






//        //1. 오른쪽 상단 화살표 각도 바꿔서 setImage
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_call_made_black_24dp);
//
//        Matrix matrix = new Matrix();
////        matrix.postRotate(90);
//        matrix.postRotate(270);
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 350, 350, true);
//        bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
//
//        cctv1_btn_left_top.setImageBitmap(bitmap);
//
//
//        //2. 중앙이미지
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_vertical_align_center_black_24dp);
//
//        Matrix matrix2 = new Matrix();
//        matrix2.postRotate(90);
////        matrix2.postRotate(180);
//        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, 350, 350, true);
//        bitmap2 = Bitmap.createBitmap(scaledBitmap2, 0, 0, scaledBitmap2.getWidth(), scaledBitmap2.getHeight(), matrix2, true);
//
//        cctv1_btn_center.setImageBitmap(bitmap2);




        //기본 세팅: 놀아주기 버튼 visible, 그만놀기 버튼 gone처리 >> 놀기 버튼 클릭 시 반대 처리
        cctv1_btn_stop_play.setVisibility(View.GONE);


        /**카메라 이동 클릭 리스너들**/
        //1. 왼쪽상단
        cctv1_btn_left_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_left_center();
                Log.e(TAG, "go_to_left_center");

            }
        });

        //2. 왼쪽끝
        cctv1_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_left();
                Log.e(TAG, "go_to_left");
            }
        });

        //3. 오른쪽상단
        cctv1_btn_right_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_right_center();
            }
        });


        //4. 오른쪽끝
        cctv1_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            go_to_right();
            Log.e(TAG, "go_to_right");
            }
        });


        //5. 중앙
        cctv1_btn_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_to_center();

            }
        });


        /**밥주기, 물주기, 놀아주기 메서드들 호출하는 클릭 리스너**/

        //1. 밥주기 >>많이, 중간, 적게 세 가지 중 선택할 수 있는 다이얼로그 호출 >>&& 대형묘, 중형묘, 소형묘에 따라 밥양 달리 주기
        cctv1_btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //먹이양 선택
                final CharSequence[] food_items = {"가득", "중간", "적게"}; //이것도 주관적인 수치라 욕먹을 것 같다. 나중에 테스트하면서 그램수 세어보자

                AlertDialog.Builder food_dialog = new AlertDialog.Builder(cctv1.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                food_dialog.setTitle("얼마나 줄까요?")
                        .setItems(food_items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
//                                  나중에 숫자 조절할 것>>필요없음
                                    food_amount = "6";
                                    food_amount_int=6;

                                    get_large_food();
                                    Log.e(TAG, "get_large_food");

                                } else if (which == 1) {
                                    food_amount = "4";
                                    food_amount_int=4;
                                    get_medium_food();

                                    Log.e(TAG, "get_medium_food");


                                }else  {
                                    food_amount = "2";
                                    food_amount_int=2;
                                    get_small_food();
                                    Log.e(TAG, "get_small_food");


                                }


                            }
                        })
                        .setCancelable(true)
                        .show();


            }
        });


        //2. 물주기
        cctv1_btn_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        //3. 놀아주기 >> 5분단위로 놀이 시간 선택할 수 있는 스피너 or 다이얼로그 띄우기
        cctv1_btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //놀기 버튼 gone처리, 그만 놀기 버튼 visible 처리
                cctv1_btn_stop_play.setVisibility(View.VISIBLE);
                cctv1_btn_play.setVisibility(View.GONE);


                //놀아줄 시간 선택
                final CharSequence[] minute_items = {"5분", "10분", "15분", "20분", "25분", "30분"};

                AlertDialog.Builder oDialog = new AlertDialog.Builder(cctv1.this,
                        android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);

                oDialog.setTitle("몇 분동안 놀까요?")
                        .setItems(minute_items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    play_time = "5";
                                    play_time_int=5;

                                    getData_5min();

                                } else if (which == 1) {
                                    play_time = "10";
                                    play_time_int=10;
                                    getData_10min();

                                } else if (which == 2) {
                                    play_time = "15";
                                    play_time_int=15;
                                    getData_15min();

                                } else if (which == 3) {
                                    play_time = "20";
                                    play_time_int=20;

                                    getData_20min();

                                } else if (which == 4) {
                                    play_time = "25";
                                    play_time_int=25;

                                    getData_25min();

                                }else  {
                                    play_time = "30";
                                    play_time_int=30;

                                    getData_30min();

                                }


                            }
                        })
                        .setCancelable(true)
                        .show();

            }
        });



        //그만 놀아주기 버튼 온클릭리스너
        cctv1_btn_stop_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //놀기 버튼 visible, 그만 놀기 버튼 gone 처리
                cctv1_btn_stop_play.setVisibility(View.GONE);
                cctv1_btn_play.setVisibility(View.VISIBLE);

                getData_off();



            }
        });





        //3. 음악틀어주기 >> todo 음악 선택할 수 있는 다이얼로그 띄우기
        cctv1_btn_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }




    /**카메라 이동 메서드**/
    public void go_to_center(){
        retrofitService.go_to_center().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }


    public void go_to_left(){
        retrofitService.go_to_left().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }



    public void go_to_left_center(){
        retrofitService.go_to_left_center().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }


    public void go_to_right(){
        retrofitService.go_to_right().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }

    public void go_to_right_center(){
        retrofitService.go_to_right_center().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }

/**밥주기 메서드**/
    public void get_small_food(){
        retrofitService_food.get_small_food().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }


    public void get_medium_food(){
        retrofitService_food.get_medium_food().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });

    }

    public void get_large_food(){
        retrofitService_food.get_large_food().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }


    //레트로핏 3. 놀아주기


    public void getData_5min(){
        retrofitService_play.getData_5min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }
    public void getData_10min(){
        retrofitService_play.getData_10min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }
    public void getData_15min(){
        retrofitService_play.getData_15min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }
    public void getData_20min(){
        retrofitService_play.getData_20min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }
    public void getData_25min(){
        retrofitService_play.getData_25min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }

    public void getData_30min(){
        retrofitService_play.getData_30min().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }


    public void getData_off(){
        retrofitService_play.getData_off().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Log.e(TAG, "레트로핏 성공 ");
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.e(TAG, "레트로핏 실패 ");
            }
        });
    }



}
