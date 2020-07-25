package com.example.team_cat_iot2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import static com.example.team_cat_iot2020.sign_up.static_email;

import static com.example.team_cat_iot2020.login.static_email;

public class cat_info extends AppCompatActivity {
    String TAG = getClass().getSimpleName();

    private Spinner cat_info_txt_kidney_spinner, cat_info_txt_kidney_size;
    private Button cat_info_btn_submit;
    private EditText cat_info_et_bowl_size, cat_info_et_age, cat_info_et_name, cat_info_et_arduino_index, cat_info_et_water_size;
    private ImageView img_cat_foto;
    private String cat_name, spinner_cat_size, spinner_cat_kidney, bowl_size, age, arduino_index, water_size;
    private static final int PICK_IMAGE_REQUEST = 0;

    private Uri mImageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_info);

        cat_info_txt_kidney_spinner = findViewById(R.id.cat_info_txt_kidney_spinner);
        cat_info_txt_kidney_size = findViewById(R.id.cat_info_txt_kidney_size);
        cat_info_btn_submit = findViewById(R.id.cat_info_btn_submit);
        img_cat_foto = findViewById(R.id.img_cat_foto);

        cat_info_et_bowl_size = findViewById(R.id.cat_info_et_bowl_size);
        cat_info_et_age = findViewById(R.id.cat_info_et_age);
        cat_info_et_name = findViewById(R.id.cat_info_et_name);
        cat_info_et_age = findViewById(R.id.cat_info_et_age);
        cat_info_et_arduino_index = findViewById(R.id.cat_info_et_arduino_index);
        cat_info_et_water_size = findViewById(R.id.cat_info_et_water_size);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        //1. 신장정보 입력 스피너
        final ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2"); //1225 위에 처럼 빈칸으로 나와있길래 고쳐봄
        list.add("3");

        String[] list2 = new String[3];
        list2[0] = "좋음";
        list2[1] = "보통";
        list2[2] = "관리필요";


        //using ArrayAdapter
        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list2);
        cat_info_txt_kidney_spinner.setAdapter(spinnerAdapter);


        //event listener
        cat_info_txt_kidney_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,"선택된 아이템 : "+mb_spinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
//                spinner_cat_kidney = (String) cat_info_txt_kidney_spinner.getItemAtPosition(position);
//                Log.e(TAG, "spinner_cat_kidney: "+spinner_cat_kidney);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //2. 사이즈(대형묘, 중형묘, 소형묘)정보 입력 스피너
        final ArrayList<String> list3 = new ArrayList<>();
        list3.add("1");
        list3.add("2"); //1225 위에 처럼 빈칸으로 나와있길래 고쳐봄
        list3.add("3");

        String[] list4 = new String[3];
        list4[0] = "대형묘";
        list4[1] = "중형묘";
        list4[2] = "소형묘";


        //using ArrayAdapter
        ArrayAdapter spinnerAdapter2;
        spinnerAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list4);
        cat_info_txt_kidney_size.setAdapter(spinnerAdapter2);


        //event listener
        cat_info_txt_kidney_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,"선택된 아이템 : "+mb_spinner.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
//                spinner_cat_size = (String) cat_info_txt_kidney_size.getItemAtPosition(position);
//                Log.e(TAG, "spinner_cat_size: "+spinner_cat_size);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //3. 고양이 사진 설정
        img_cat_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "갤러리에서 사진 선택 리스너 실행");
                imageSelect();

            }
        });


        //저장버튼 클릭 >>서버 db에 자료 저장, 메인화면으로 화면 이동
        cat_info_btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cat_name = cat_info_et_name.getText().toString();
                Log.e(TAG, "cat_name: " + cat_name);

                bowl_size = cat_info_et_bowl_size.getText().toString();
                Log.e(TAG, "bowl_size: " + bowl_size);

                String image = getBase64String(bitmap);
                Log.e(TAG, "image: " + image);

                age = cat_info_et_age.getText().toString();
                Log.e(TAG, "age: " + age);

                arduino_index = cat_info_et_arduino_index.getText().toString();
                Log.e(TAG, "arduino_index: " + arduino_index);

//                water_size = cat_info_et_water_size.getText().toString();
//                Log.e(TAG, "water_size: "+water_size);
                water_size = "0"; //0으로 바꾸기
                Log.e(TAG, "water_size: " + water_size);

                spinner_cat_size = cat_info_txt_kidney_size.getSelectedItem().toString();
                Log.e(TAG, "spinner_cat_size: " + spinner_cat_size);

                spinner_cat_kidney = cat_info_txt_kidney_spinner.getSelectedItem().toString();
                Log.e(TAG, "spinner_cat_kidney: " + spinner_cat_kidney);


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
                                AlertDialog.Builder builder = new AlertDialog.Builder(cat_info.this);
                                builder.setMessage("정보 등록이 완료되었습니다.")
                                        .setPositiveButton("확인",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface dialog, int id) {
                                                        // 다이얼로그를 취소한다
                                                        dialog.cancel();

//                                                        확인 버튼을 누르면 로그인 화면으로 이동
                                                        Intent intent = new Intent(cat_info.this, login.class);
                                                        startActivity(intent);

                                                        finish(); //1211
                                                        Log.e(TAG, "finish();");
                                                    }
                                                }).create().show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(cat_info.this);
                                builder.setMessage("에러가 발생했습니다.")
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


                class save_cat_info_action extends StringRequest {
                    String TAG = getClass().getSimpleName();


                    final static private String URL = "http://49.247.208.236:80/save_cat_info.php";
                    private Map<String, String> parameters;

                    public save_cat_info_action(String email, String cat_name, String spinner_cat_kidney, String spinner_cat_size, String bowl_size, String image,
                                                String age, String arduino_index, String water_size,
                                                Response.Listener<String> listener) {
                        super(Method.POST, URL, listener, null);
                        parameters = new HashMap<>();
                        parameters.put("email", static_email);
                        parameters.put("cat_name", cat_name);
                        parameters.put("cat_kidney", spinner_cat_kidney);
                        parameters.put("cat_size", spinner_cat_size);
                        parameters.put("bowl_size", bowl_size);
                        parameters.put("image", image);
                        parameters.put("age", age);
                        parameters.put("index", arduino_index);
                        parameters.put("water_size", water_size);

                        Log.e(TAG, "email: " + email);
                        Log.e(TAG, "cat_name: " + cat_name);
                        Log.e(TAG, "spinner_cat_kidney: " + spinner_cat_kidney);
                        Log.e(TAG, "spinner_cat_size: " + spinner_cat_size);
                        Log.e(TAG, "bowl_size: " + bowl_size);
                        Log.e(TAG, "image: " + image);
                        Log.e(TAG, "age: " + age);
                        Log.e(TAG, "index: " + arduino_index);
                        Log.e(TAG, "water_size: " + water_size);
                    }

                    @Override
                    public Map<String, String> getParams() {
                        return parameters;
                    }


                }


                save_cat_info_action save_cat_info_a = new save_cat_info_action(static_email, cat_name, spinner_cat_kidney, spinner_cat_size, bowl_size,
                        image, age, arduino_index, water_size, responseListener);
                RequestQueue queue = Volley.newRequestQueue(cat_info.this);
                queue.add(save_cat_info_a);
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


                    img_cat_foto.setImageURI(mImageUri);


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
