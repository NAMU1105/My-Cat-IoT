package com.example.team_cat_iot2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.team_cat_iot2020.login.static_email;
//import static com.example.team_cat_iot2020.sign_up.static_email;

public class health_stastic_visit extends Fragment {
    String TAG = getClass().getSimpleName();
    private Context context;
    private VideoView cctv1_video_view;
    private Button hsv_btn_day, hsv_btn_week, hsv_btn_month;
    private TextView hsv_txt_day, hsv_txt_week, hsv_txt_month;
    private LinearLayout hsv_day_control_layout, hsv_week_control_layout, hsv_month_control_layout;

    LineDataSet dataset_day, dataset_week, dataset_month;
    //    ArrayList<String> labels;
    LineChart hsv_chart_day, hsv_chart_week, hsv_chart_month;


    //오늘
    private String today_string;
    private int today_int;

    //금주 >> 이건 계산을 어떻게 하지?  https://javafactory.tistory.com/1330 <- 이 곳 코드 참고함
    private String this_week_string;
    private int this_week_int;

    //금월 //월은 +1해줘야 한다(0부터 시작)
    private String this_month_string;
    private int this_month_int;


    //파라메터로 보낼 풀 week 시작 날짜와 끝나는 날짜(2020-01-17 이런 형식)
    private String full_this_week_start, full_this_week_end;


    public health_stastic_visit() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.health_stastic_visit, container, false);

        context = container.getContext();
        cctv1_video_view = rootView.findViewById(R.id.cctv1_video_view);
        hsv_btn_day = rootView.findViewById(R.id.hsv_btn_day);
        hsv_btn_week = rootView.findViewById(R.id.hsv_btn_week);
        hsv_btn_month = rootView.findViewById(R.id.hsv_btn_month);
        hsv_txt_day = rootView.findViewById(R.id.hsv_txt_day);
        hsv_txt_week = rootView.findViewById(R.id.hsv_txt_week);
        hsv_txt_month = rootView.findViewById(R.id.hsv_txt_month);

        hsv_day_control_layout = rootView.findViewById(R.id.hsv_day_control_layout);
        hsv_week_control_layout = rootView.findViewById(R.id.hsv_week_control_layout);
        hsv_month_control_layout = rootView.findViewById(R.id.hsv_month_control_layout);
        hsv_chart_day = rootView.findViewById(R.id.hsv_chart_day);
        hsv_chart_week = rootView.findViewById(R.id.hsv_chart_week);


        //디폴트: 금일 통계 보여주기
        hsv_btn_day.setTextColor(Color.RED);
        hsv_btn_week.setTextColor(Color.BLACK); //나머지 색은 원상복구
        hsv_btn_month.setTextColor(Color.BLACK); //나머지 색은 원상복구


        hsv_day_control_layout.setVisibility(View.VISIBLE);
        hsv_week_control_layout.setVisibility(View.GONE);
        hsv_month_control_layout.setVisibility(View.GONE);


        //날짜 정보 setText
        //1.현재 날짜를 구한다.
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        today_string = format1.format(time);
        Log.e(TAG, "today_string: " + today_string);


//        //구한 현재 날짜를를 int로 형변환한다. >>"-"때문에 안 됨, 그리고 --할 때 월이 바뀌는 것도 고려해야 함
//        today_int = Integer.parseInt(today_string);
//        Log.e(TAG, "today_int: " + today_int);

        //1. hsv_txt_day 금일 날짜 setText
        hsv_txt_day.setText(today_string);


        //2.현재 주를 구한다.
//       todo 전, 후 주는 각각 시작과 끝 날짜에 +-7하면 되지 않을까?
        DecimalFormat df = new DecimalFormat("00");
        Calendar currentCalendar = Calendar.getInstance();

        //이번 달
        this_month_string = df.format(currentCalendar.get(Calendar.MONTH) + 1);


        //이번 주 첫째 날짜
        currentCalendar.add(Calendar.DATE, 1 - currentCalendar.get(Calendar.DAY_OF_WEEK));
        String firstWeekDay = df.format(currentCalendar.get(Calendar.DATE));


        //이번 주 마지막 날짜
        currentCalendar.add(Calendar.DATE, 7 - currentCalendar.get(Calendar.DAY_OF_WEEK));
        String lastWeekDay = df.format(currentCalendar.get(Calendar.DATE));

        this_week_string = this_month_string + "/" + firstWeekDay + "~" + this_month_string + "/" + lastWeekDay;
        //2. hsv_txt_week 금주 setText
        hsv_txt_week.setText(this_week_string);


        //3.현재 월을 구한다.
//        SimpleDateFormat format_month = new SimpleDateFormat("MM");
//        Date time_month = new Date();
//        this_month_string = format_month.format(time_month);
//        Log.e(TAG, "this_month_string: " + this_month_string);
//
//        //구한 현재 날짜를를 int로 형변환한다.
//        this_month_int = Integer.parseInt(this_month_string);
//
//        this_month_int = this_month_int+1;
//        Log.e(TAG, "this_month_int: " + this_month_int);
//
//        this_month_string= String.valueOf(this_month_int);


//        this_month_string  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
        this_month_int = Integer.parseInt(this_month_string);

        //3. hsv_btn_month 금월 setText
        hsv_txt_month.setText(this_month_string + "월");


//통계 파라메터로 보낼 주 시작일자와 끝나는 일자
        SimpleDateFormat format_week_year = new SimpleDateFormat("yyyy");
        Date time_week_year = new Date();
        String this_week_year_string = format_week_year.format(time_week_year);
        Log.e(TAG, "this_week_year_string: " + this_week_year_string);

        full_this_week_start = this_week_year_string + "-" + this_month_string + "-" + firstWeekDay;
        Log.e(TAG, "full_this_week_start: " + full_this_week_start);

        full_this_week_end = this_week_year_string + "-" + this_month_string + "-" + lastWeekDay;
        Log.e(TAG, "full_this_week_end: " + full_this_week_end);


        /**통계(기록)보기 기능**/
        // 클릭하는 뷰에 따라 다른 뷰들 visibility바꿔주기


        draw_line_graph_day(); //디폴트- 하루 통계 보기


        //1. 일자별 기록 보기
        hsv_btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                hsv_btn_day.setBackgroundColor(Color.BLUE);//클릭하면 버튼 파란색으로 바꾸기
//                hsv_btn_week.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구
//                hsv_btn_month.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구

                hsv_btn_day.setTextColor(Color.RED);
                hsv_btn_week.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hsv_btn_month.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hsv_day_control_layout.setVisibility(View.VISIBLE);
                hsv_week_control_layout.setVisibility(View.GONE);
                hsv_month_control_layout.setVisibility(View.GONE);

                //일별 기록 그래프 그리는 메서드

                draw_line_graph_day();


            }
        });

        //2. 주별 기록 보기
        hsv_btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                hsv_btn_week.setBackgroundColor(Color.BLUE);//클릭하면 버튼 파란색으로 바꾸기
//                hsv_btn_day.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구
//                hsv_bt
//                n_month.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구

                hsv_btn_week.setTextColor(Color.RED);
                hsv_btn_day.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hsv_btn_month.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hsv_day_control_layout.setVisibility(View.GONE);
                hsv_week_control_layout.setVisibility(View.VISIBLE);
                hsv_month_control_layout.setVisibility(View.GONE);


                draw_line_graph_week();

            }
        });


        //3. 월별 기록 보기
        hsv_btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                hsv_btn_month.setBackgroundColor(658175);//클릭하면 버튼 파란색으로 바꾸기
//                hsv_btn_month.setBackgroundColor(Color.BLUE);//클릭하면 버튼 파란색으로 바꾸기
//                hsv_btn_day.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구
//                hsv_btn_week.setBackgroundColor(Color.LTGRAY); //나머지 색은 원상복구

                hsv_btn_month.setTextColor(Color.RED);
                hsv_btn_day.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hsv_btn_week.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hsv_day_control_layout.setVisibility(View.GONE);
                hsv_week_control_layout.setVisibility(View.GONE);
                hsv_month_control_layout.setVisibility(View.VISIBLE);


            }
        });


        return rootView;
    }


    //그래프 그리는 메서드 1. 일일 그래프
    private void draw_line_graph_day() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            ArrayList<Entry> entries_day = new ArrayList<>();

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray tags_array = new JSONArray(response);

                    if (tags_array.length() == 0) {


                    } else {


                        for (int i = 0; i < tags_array.length(); i++) {
                            JSONObject tag_obj = tags_array.getJSONObject(i);

                            String visit_num = tag_obj.getString("hour_visit_num");
                            String hour = tag_obj.getString("hour");

                            Log.e(TAG, "visit_num: " + visit_num);
                            Log.e(TAG, "hour: " + hour);

                            float hour_f = Float.valueOf(hour);
                            float visit_num_f = Float.valueOf(visit_num);

                            entries_day.add(new Entry(hour_f, visit_num_f));

//                        //총 몇 개의 태그를 등록했는지에 대한 정보>>setText
//                        bd4_btn_tag_num.setText(num2+"개");

                        }


                        dataset_day = new LineDataSet(entries_day, "화장실 방문수");
                        LineData data_line = new LineData(dataset_day);
                        hsv_chart_day.setData(data_line);
                        hsv_chart_day.animateY(500); //애니메이션 효과
                        hsv_chart_day.invalidate(); //여기임!!! it works!
                        Log.e(TAG, "hsv_chart_day.invalidate();, 그래프 갱신");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        class get_line_graph_visit_day extends StringRequest {
            String TAG = getClass().getSimpleName();
            final static private String URL = "http://49.247.208.236:80/stastic_day_visit.php";
            private Map<String, String> parameters;

            public get_line_graph_visit_day(String email, String today_string, Response.Listener<String> listener) {
                super(Method.POST, URL, listener, null);
                parameters = new HashMap<>();
                parameters.put("email", email);
                parameters.put("date_param", today_string);
                Log.e(TAG, "email: " + email);
                Log.e(TAG, "date_param: " + today_string);
            }

            @Override
            public Map<String, String> getParams() {
                return parameters;
            }
        }


        get_line_graph_visit_day get_line_graph_visit_d = new get_line_graph_visit_day(static_email, today_string, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(get_line_graph_visit_d);
        Log.e(TAG, "RequestQueue");

    }


    //그래프 그리는 메서드 2. 주 그래프
    private void draw_line_graph_week() {

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {

            ArrayList<Entry> entries2 = new ArrayList<>();

            @Override
            public void onResponse(String response) {

                try {


                    JSONObject jsonResponse2 = new JSONObject(response);
                    Log.e(TAG, "1");
                    String mon = jsonResponse2.getString("mon");
                    Log.e(TAG, "1");
                    String tue = jsonResponse2.getString("tue");
                    String wed = jsonResponse2.getString("wed");
                    String thur = jsonResponse2.getString("thur");
                    String fri = jsonResponse2.getString("fri");
                    String sat = jsonResponse2.getString("sat");
                    String sun = jsonResponse2.getString("sun");


                    float mon_f = Float.valueOf(mon);
                    float tue_f = Float.valueOf(tue);
                    float wed_f = Float.valueOf(wed);
                    float thur_f = Float.valueOf(thur);
                    float fri_f = Float.valueOf(fri);
                    float sat_f = Float.valueOf(sat);
                    float sun_f = Float.valueOf(sun);

                    Log.e(TAG, "mon_f: " + mon_f);
                    Log.e(TAG, "tue_f: " + tue_f);
                    Log.e(TAG, "wed_f: " + wed_f);
                    Log.e(TAG, "thur_f: " + thur_f);
                    Log.e(TAG, "fri_f: " + fri_f);
                    Log.e(TAG, "sat_f: " + sat_f);
                    Log.e(TAG, "sun_f: " + sun_f);


                    entries2.add(new Entry(0f, sun_f));
                    entries2.add(new Entry(1f, mon_f));
                    entries2.add(new Entry(2f, tue_f));
                    entries2.add(new Entry(3f, wed_f));
                    entries2.add(new Entry(4f, thur_f));
                    entries2.add(new Entry(5f, fri_f));
                    entries2.add(new Entry(6f, sat_f));

//                        //총 몇 개의 태그를 등록했는지에 대한 정보>>setText
//                        bd4_btn_tag_num.setText(num2+"개");


                    dataset_week = new LineDataSet(entries2, "화장실 방문수");
                    LineData data_line_week = new LineData(dataset_week);
                    hsv_chart_week.setData(data_line_week);
                    hsv_chart_week.animateY(500); //애니메이션 효과
                    hsv_chart_week.invalidate(); //여기임!!! it works!
                    Log.e(TAG, "hsv_chart_week.invalidate();, 그래프 갱신");


                } catch (JSONException e) {
                    Log.e(TAG, "errorrorrrorr");
                    e.printStackTrace();
                }

            }
        };

        class get_line_graph_visit_week extends StringRequest {
            String TAG = getClass().getSimpleName();
            final static private String URL = "http://49.247.208.236:80/stastic_week_visit.php";
            private Map<String, String> parameters;

            public get_line_graph_visit_week(String email, Response.Listener<String> listener) {
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


        get_line_graph_visit_week get_line_graph_visit_w = new get_line_graph_visit_week(static_email, responseListener2);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(get_line_graph_visit_w);
        Log.e(TAG, "RequestQueue");

    }


}
