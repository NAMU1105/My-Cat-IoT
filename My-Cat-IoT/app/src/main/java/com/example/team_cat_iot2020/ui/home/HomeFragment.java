package com.example.team_cat_iot2020.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.team_cat_iot2020.MainActivity;
import com.example.team_cat_iot2020.R;
import com.example.team_cat_iot2020.cctv;
import com.example.team_cat_iot2020.cctv1;
import com.example.team_cat_iot2020.health_stastic;
import com.example.team_cat_iot2020.retrofit.Data;
import com.example.team_cat_iot2020.retrofit.RetrofitService2;
import com.example.team_cat_iot2020.retrofit.RetrofitService_food;
import com.example.team_cat_iot2020.retrofit.RetrofitService_ultrasonic;
import com.example.team_cat_iot2020.retrofit.RetrofitService_ultrasonic_food;
import com.example.team_cat_iot2020.retrofit.ultrawave_result;
import com.example.team_cat_iot2020.retrofit.ultrawave_result_water;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.team_cat_iot2020.login.static_email;

//import static com.example.team_cat_iot2020.sign_up.static_email;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    //물
    private RetrofitService_ultrasonic retrofitService_ultrasonic;
    private Retrofit retrofit_current_water;


    //밥
    private RetrofitService_ultrasonic_food retrofitService_ultrasonic_food;
    private Retrofit retrofit_current_food;

    String TAG = getClass().getSimpleName();
    private TextView main_txt_main_text, main_txt_cat_status, main_txt_food_percentage, main_txt_water_percentage, main_txt_cctv, main_txt_stastic;
    private ImageView main_img_cctv, main_img_stastic, main_img_current_water, main_img_current_food;
    private Context context;

    private String current_water;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);

        context = container.getContext();



        main_txt_main_text = root.findViewById(R.id.main_txt_main_text);
        main_txt_cat_status = root.findViewById(R.id.main_txt_cat_status);
        main_txt_food_percentage = root.findViewById(R.id.main_txt_food_percentage);
        main_txt_water_percentage = root.findViewById(R.id.main_txt_water_percentage);
        main_txt_cctv = root.findViewById(R.id.main_txt_cctv);
        main_txt_stastic = root.findViewById(R.id.main_txt_stastic);
        main_img_cctv = root.findViewById(R.id.main_img_cctv);
        main_img_stastic = root.findViewById(R.id.main_img_stastic);
        main_img_current_water = root.findViewById(R.id.main_img_current_water);
        main_img_current_food = root.findViewById(R.id.main_img_current_food);






        //1. 레트로핏 물양
        retrofit_current_water =  new Retrofit.Builder()
                .baseUrl(RetrofitService_ultrasonic.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService_ultrasonic = retrofit_current_water.create(RetrofitService_ultrasonic.class);



        //2. 레트로핏 밥양
        retrofit_current_food =  new Retrofit.Builder()
                .baseUrl(RetrofitService_ultrasonic_food.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService_ultrasonic_food = retrofit_current_food.create(RetrofitService_ultrasonic_food.class);




        get_info(); //냥이이름,
        Log.e(TAG, "get_info();");

        get_current_water(); //현재 물양
        Log.e(TAG, "get_current_water();");

        get_current_food(); //현재 밥양
        Log.e(TAG, "get_current_food();");





//        화면전환
//        1. cctv
        main_img_cctv.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "cctv이미지 클릭");

//                Intent cctv = new Intent(context, com.example.team_cat_iot2020.cctv.class);
                Intent cctv2 = new Intent(context, cctv1.class);
                startActivity(cctv2);
            }
        });


        main_txt_cctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent cctv = new Intent(context, cctv.class);
                Intent cctv = new Intent(context, cctv1.class); //0117 수정
                startActivity(cctv);
            }
        });




        // 2. 통계
        main_img_stastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent health_stastic = new Intent(context, com.example.team_cat_iot2020.health_stastic.class);
                startActivity(health_stastic);
            }
        });
        main_txt_stastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent health_stastic = new Intent(context, health_stastic.class);
                startActivity(health_stastic);
            }
        });

        // 3. 현재 물양>>물주기 기능
        main_img_current_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent health_stastic = new Intent(MainActivity.this, health_stastic.class);
//                startActivity(health_stastic);
            }
        });

        // 4. 현재 밥양 >>밥 주기 기능->> 클릭 시 다이얼로그로 밥양 많이, 중간, 적게 중 고를 수 있게 할 예정
        main_img_current_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });






//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        get_current_water();
        get_current_food();

    }

    public void get_current_water(){
        retrofitService_ultrasonic.valuePost().enqueue(new Callback<ultrawave_result_water>() {
            @Override
            public void onResponse(Call<ultrawave_result_water> call, retrofit2.Response<ultrawave_result_water> response) {
                int current_water_int = response.body().getValue_water();
                Log.e(TAG, "current_water_int: "+current_water_int);

                if (current_water_int<=300){ //비어있음

                    main_img_current_water.setImageDrawable(getResources().getDrawable(R.drawable.littledeep_waterdrop_file_style1));

                }else if(current_water_int>300&&current_water_int<850){ // 30프로

                    main_img_current_water.setImageDrawable(getResources().getDrawable(R.drawable.thirty_per));

                }else if(current_water_int>850&&current_water_int<1000){ // 50프로

                    main_img_current_water.setImageDrawable(getResources().getDrawable(R.drawable.fifty_per));


                }else if(current_water_int>1000){

                    main_img_current_water.setImageDrawable(getResources().getDrawable(R.drawable.hundred_per));

                }

            }

            @Override
            public void onFailure(Call<ultrawave_result_water> call, Throwable t) {




            }
        });

    }




    public void get_current_food(){
        retrofitService_ultrasonic_food.valuePost().enqueue(new Callback<ultrawave_result>() {
            @Override
            public void onResponse(Call<ultrawave_result> call, retrofit2.Response<ultrawave_result> response) {
                int current_food_int = response.body().getValue();
                Log.e(TAG, "current_food_int: "+current_food_int);

                if (current_food_int>=17){ //비어있음

                    main_img_current_food.setImageDrawable(getResources().getDrawable(R.drawable.cat_food_zero));

//                }else if(current_food_int>1&&current_food_int<850){ // 30프로
//                    main_img_current_food.setImageDrawable(getResources().getDrawable(R.drawable.cat_food_thirty));

                }else if(current_food_int>15&&current_food_int<17){ // 50프로

                    main_img_current_food.setImageDrawable(getResources().getDrawable(R.drawable.cat_food_fifty));


                }else if(current_food_int<=15){

                    main_img_current_food.setImageDrawable(getResources().getDrawable(R.drawable.cat_food_hundred));

                }

            }

            @Override
            public void onFailure(Call<ultrawave_result> call, Throwable t) {




            }
        });

    }



    //1230 태그정보 가지고 오는 메서드
    private void get_info() {

        Response.Listener<String> responseListener_tag = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONArray tags_array = new JSONArray(response);

                    for (int i=0; i<tags_array.length(); i++){
                        JSONObject tag_obj = tags_array.getJSONObject(i);

                        String cat_name = tag_obj.getString("cat_name");
                        main_txt_main_text.setText(cat_name);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };



        class get_info_for_main_action extends StringRequest {
            String TAG = getClass().getSimpleName();
            final static private String URL = "http://49.247.208.236:80/get_main_info.php";
            private Map<String, String> parameters;

            public get_info_for_main_action(String email,  Response.Listener<String> listener) {
                super(Method.POST, URL, listener, null);
                parameters = new HashMap<>();
                parameters.put("email", email);
                Log.e(TAG, "email: " + email);
            }

            @Override
            public Map<String, String> getParams() {
                return parameters;
            }
        }

        get_info_for_main_action get_info_for_main_a = new get_info_for_main_action(static_email, responseListener_tag);
        Log.e(TAG, "메인화면에 set할 정보들 가져오기");
        Log.e(TAG, "static_email: " + static_email);
        RequestQueue queue_tag = Volley.newRequestQueue(context);
        queue_tag.add(get_info_for_main_a);


    }


}