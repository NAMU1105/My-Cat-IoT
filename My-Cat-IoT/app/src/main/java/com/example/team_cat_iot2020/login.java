package com.example.team_cat_iot2020;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.team_cat_iot2020.ui.home.HomeFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private EditText login_et_email, login_et_pw;
    private Button login_btn_submit;
    private TextView txt_login_signup;
    private String email;
    private String pw;
    public static String static_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //FVBID
        login_et_email = findViewById(R.id.login_et_email);
        login_et_pw = findViewById(R.id.login_et_pw);
        login_btn_submit = findViewById(R.id.login_btn_submit);
        txt_login_signup = findViewById(R.id.txt_login_signup);



        //1. 회원가입 화면으로 이동 인텐트
        txt_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, sign_up.class);
                startActivity(intent);

            }
        });


        //2. 로그인 버튼 클릭
        login_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               email = login_et_email.getText().toString();
               pw = login_et_pw.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Log.e(TAG, "성공");

                                String email = jsonResponse.getString("email");
                                static_email =email;
                                Log.e(TAG, "email: " + email);
                                Log.e(TAG, "static_email: " + static_email);



////                                이름이랑, 프로필 사진도 넘겨주기
                                String name = jsonResponse.getString("name");
                                Log.e(TAG, "name: " + name);


                                Intent go_to_main = new Intent(login.this, Main2Activity.class);
//                                Intent go_to_main = new Intent(login.this, MainActivity.class);
//                                Intent go_to_main = new Intent(login.this, HomeFragment.class);
                                go_to_main.putExtra("email", email);
                                go_to_main.putExtra("name", name);
                                startActivity(go_to_main);

                                finish();
                                Log.e(TAG, "finish();");

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
                                builder.setMessage("로그인에 실패했습니다, 아이디 혹은 비밀번호를 확인해주세요.")
                                        .setNegativeButton("다시 시도",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        // 다이얼로그를 취소한다
                                                        dialog.cancel();
                                                    }
                                                }).create().show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };


                class login_action extends StringRequest {
                    String TAG = getClass().getSimpleName();


                    final static private String URL = "http://49.247.208.236:80/login.php";
                    private Map<String, String> parameters;

                    public login_action(String email, String pw, Response.Listener<String> listener) {
                        super(Method.POST, URL, listener, null);
                        parameters = new HashMap<>();
                        parameters.put("email", email);
                        parameters.put("pw", pw);
                        Log.e(TAG, "email: " + email);
                        Log.e(TAG, "pw: " + pw);
                    }

                    @Override
                    public Map<String, String> getParams() {
                        return parameters;
                    }


                }




                login_action login_a = new login_action(email, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login.this);
                queue.add(login_a);
                Log.e(TAG, "RequestQueue");




            }
        });


    }
}
