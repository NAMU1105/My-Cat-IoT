package com.example.team_cat_iot2020;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;


public class sign_up extends AppCompatActivity {

    TextView txt_signup_email_email, txt_signup_email_nickname, txt_signup_email_pw, txt_signup_email_pw_check;
    CheckBox cb_signup_agree_rule;
    ImageView img_sign_email_foto;
    Button btn_signup_email_submit;


//    public static String static_email;
    private String email;


    String TAG = getClass().getSimpleName();
    final static int TAKE_PICTURE = 1;
    private static final int PICK_IMAGE_REQUEST = 0;
    private Uri mImageUri;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //FVBID
        txt_signup_email_email = findViewById(R.id.txt_signup_email_email);
        txt_signup_email_nickname = findViewById(R.id.txt_signup_email_nickname);
        txt_signup_email_pw = findViewById(R.id.txt_signup_email_pw);
        txt_signup_email_pw_check = findViewById(R.id.txt_signup_email_pw_check);
        cb_signup_agree_rule = findViewById(R.id.cb_signup_agree_rule);
        img_sign_email_foto = findViewById(R.id.img_sign_email_foto);
        btn_signup_email_submit = findViewById(R.id.btn_signup_email_submit);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //1. 프로필 사진 설정>> 이미지 버튼 누를 시 갤러리, 사진 촬영 다이얼로그 선택해 프로필 이미지 지정할 수 있다.
        img_sign_email_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "갤러리에서 사진 선택 리스너 실행");
                imageSelect();

            }
        });

//        2. 회원가입 버튼 클릭 리스너
        btn_signup_email_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txt_signup_email_email.getText().toString();
                String nickname = txt_signup_email_nickname.getText().toString();
                String pw = txt_signup_email_pw.getText().toString();

                String image = getBase64String(bitmap);

                Log.e(TAG, "이미지 변환임");
//                byte[] image_byte = bitmapToByteArray(bitmap);

                Log.e(TAG, "email: " + email);
                Log.e(TAG, "name: " + nickname);
                Log.e(TAG, "pw: " + pw);
                Log.e(TAG, "image(bitmap): " + image);


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response 111111: " + response);
                        try {
//                            php 파일에 다녀옴, 인서트가 석세스인지 여부를 response라는 이름의 제이슨으로 받아옴
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.e(TAG, "jsonResponse: " + jsonResponse);

                            boolean success = jsonResponse.getBoolean("success");

//php 인서트가 성공했다면
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(sign_up.this);
                                builder.setMessage("회원가입이 완료되었습니다.")
                                        .setPositiveButton("확인",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        // 다이얼로그를 취소한다
                                                        dialog.cancel();

//                                                        확인 버튼을 누르면 로그인 화면으로 이동 >>ㄴㄴ 고양이 정보 페이지로 이동
//                                                        Intent go_to_login = new Intent(sign_up.this, login.class);

//                                                        0117  static_email 여기로 옮김 >>다시 주석처리
//                                                        static_email =email;
//                                                        Log.e(TAG, "email: " + email);
//                                                        Log.e(TAG, "static_email: " + static_email);


                                                        Intent go_to_login = new Intent(sign_up.this, cat_info.class);
                                                        sign_up.this.startActivity(go_to_login);

                                                        finish(); //1211
                                                        Log.e(TAG, "finish();");
                                                    }
                                                }).create().show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(sign_up.this);
                                builder.setMessage("회원가입에 실패했습니다.")
                                        .setNegativeButton("다시 시도",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        // 다이얼로그를 취소한다
                                                        dialog.cancel();
                                                    }
                                                }).create().show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                class sign_up_action extends StringRequest {
                    String TAG = getClass().getSimpleName();


                    final static private String URL = "http://49.247.208.236:80/signup.php";
                    private Map<String, String> parameters;

                    public sign_up_action(String email, String pw, String nickname, String image, Response.Listener<String> listener) {
                        super(Method.POST, URL, listener, null);
                        parameters = new HashMap<>();
                        parameters.put("email", email);
                        parameters.put("pw", pw);
                        parameters.put("name", nickname);
                        parameters.put("image", image);
                        Log.e(TAG, "email: " + email);
                        Log.e(TAG, "pw: " + pw);
                        Log.e(TAG, "name: " + nickname);
                        Log.e(TAG, "image: " + image);
                    }

                    @Override
                    public Map<String, String> getParams() {
                        return parameters;
                    }


                }


                sign_up_action sign_up_a = new sign_up_action(email, pw, nickname, image, responseListener);
                RequestQueue queue = Volley.newRequestQueue(sign_up.this);
                queue.add(sign_up_a);
                Log.e(TAG, "dfdfdfdfdfdffdfdfdfd");
            }
        });


    }


    //    프로필 사진 setImage
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    mImageUri = data.getData();
                    Log.e(TAG, "mImageUri: " + mImageUri.toString());
                    try {


                        // 회전된 사진을 원래대로 돌려 표시한다.
                        if (mImageUri != null) {
//                            String mCurrentPhotoPath = getRealPathFromURI(mImageUri);
//                            ExifInterface ei = new ExifInterface(mCurrentPhotoPath);

                            InputStream inputStream = getContentResolver().openInputStream(mImageUri); //1210 주석처리
                            bitmap = BitmapFactory.decodeStream(inputStream); //1210 주석처리
                            bitmap = Bitmap.createScaledBitmap(bitmap, 250, 300, true);
                            Log.e(TAG, "bitmap: " + bitmap.toString());

//                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                                    ExifInterface.ORIENTATION_UNDEFINED);
//                            switch (orientation) {
//
//                                case ExifInterface.ORIENTATION_ROTATE_90:
//                                    bitmap = rotateImage(bitmap, 90);
//                                    Log.e(TAG, "비트맵 90도 회전");
//
//                                    break;
//
//                                case ExifInterface.ORIENTATION_ROTATE_180:
//                                    bitmap = rotateImage(bitmap, 180);
//                                    Log.e(TAG, "비트맵 180도 회전");
//
//                                    break;
//
//                                case ExifInterface.ORIENTATION_ROTATE_270:
//                                    bitmap = rotateImage(bitmap, 270);
//                                    Log.e(TAG, "비트맵 270도 회전");
//
//                                    break;
//
//                                case ExifInterface.ORIENTATION_NORMAL:
//                                default:
//                                    bitmap = bitmap;
//                                    Log.e(TAG, "비트맵 회전 안 함");
//
//                            }
                        }

//                        img_sign_email_foto.setImageBitmap(bitmap);
//                        Log.e(TAG, "Uri to 비트맵");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    img_sign_email_foto.setImageURI(mImageUri);


                }
            }
        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void imageSelect() {
        permissionsCheck();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    public String getBase64String(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

    //    1210 uri로부터 이미지 경로를 얻는 메서드
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null); //DB 테이블의 실제 Data를 가지고 옵니다.
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst(); //Cursor를 제일 첫번째 행(Row)으로 이동 시킨다.
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); //DB 테이블의 해당 필드(컬럼) 이름을 얻어 옵니다.
            result = cursor.getString(idx); // DB 테이블의 실제 Data를 가지고 옵니다.
            cursor.close();
        }

        return result;
    }
}
