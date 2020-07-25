package com.example.team_cat_iot2020;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class health_stastic_stay extends Fragment {
    String TAG = getClass().getSimpleName();
    private Context context;
    private VideoView cctv1_video_view;

    LineDataSet dataset_day, dataset_week, dataset_month;
    LineChart hss_chart_day, hss_chart_week, hss_chart_month;

    private Button hss_btn_day, hss_btn_week, hss_btn_month;
    private TextView hss_txt_day, hss_txt_week, hss_txt_month;
    private LinearLayout hss_day_control_layout, hss_week_control_layout, hss_month_control_layout;


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


    //리사이클러뷰
    RecyclerView recyclerView_toilet, hss_recyclerview_week, hss_recyclerview_month;
    RecyclerViewAdapter_toilet recyclerViewAdapter_toilet;

    private ArrayList<String> in_time_list = new ArrayList<>();
    private ArrayList<String> duration_list = new ArrayList<>();

    public health_stastic_stay() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.health_stastic_stay, container, false);

        context = container.getContext();
//        cctv1_video_view = rootView.findViewById(R.id.cctv1_video_view);
        hss_chart_day = rootView.findViewById(R.id.hss_chart_day);
        hss_chart_week = rootView.findViewById(R.id.hss_chart_week);
        hss_chart_month = rootView.findViewById(R.id.hss_chart_month);

        hss_btn_day = rootView.findViewById(R.id.hss_btn_day);
        hss_btn_week = rootView.findViewById(R.id.hss_btn_week);
        hss_btn_month = rootView.findViewById(R.id.hss_btn_month);

        hss_txt_day = rootView.findViewById(R.id.hss_txt_day);
        hss_txt_week = rootView.findViewById(R.id.hss_txt_week);
        hss_txt_month = rootView.findViewById(R.id.hss_btn_month);

        hss_day_control_layout = rootView.findViewById(R.id.hss_day_control_layout);
        hss_week_control_layout = rootView.findViewById(R.id.hss_week_control_layout);
        hss_month_control_layout = rootView.findViewById(R.id.hss_month_control_layout);

        hss_recyclerview_week = rootView.findViewById(R.id.hss_recyclerview_week);
        hss_recyclerview_month = rootView.findViewById(R.id.hss_recyclerview_month);


        recyclerView_toilet = rootView.findViewById(R.id.hss_recyclerview_day);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewAdapter_toilet = new RecyclerViewAdapter_toilet(in_time_list, duration_list, context);
        recyclerView_toilet.setLayoutManager(layoutManager);
        recyclerView_toilet.setAdapter(recyclerViewAdapter_toilet);


        //주간 리사이클러뷰
        hss_recyclerview_week = rootView.findViewById(R.id.hss_recyclerview_week);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
//        recyclerViewAdapter_toilet = new RecyclerViewAdapter_toilet(in_time_list, duration_list, context);
        hss_recyclerview_week.setLayoutManager(layoutManager2);
        hss_recyclerview_week.setAdapter(recyclerViewAdapter_toilet);


//        //최신 추가된 아이템이 위로 올라오도록 조치
//        bd3_process_recyclerview.setLayoutManager(myLinearLayoutManager);
//        myLinearLayoutManager.setReverseLayout(false);
//
//        //레이아웃 매니저 set
//        //>>리사이클러뷰에 레이아웃매니저를 연결>>아이템을 진짜로 보여주는 역할을 하는 애는 레이아웃 매니저임
//        bd3_process_recyclerview.setLayoutManager(myLinearLayoutManager);
//        Log.e(TAG, "레이아웃매니저 set");
//
//
//        //아이템 별 구분선 추가(DividerItemDecoration)
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bd3_process_recyclerview.getContext(), myLinearLayoutManager.getOrientation());
//        bd3_process_recyclerview.addItemDecoration(dividerItemDecoration);
//        Log.e(TAG, "아이템 구분선 추가");


        //날짜 정보 setText
        //1.현재 날짜를 구한다.
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date();
        today_string = format1.format(time);
        Log.e(TAG, "today_string: " + today_string);

        hss_txt_day.setText(today_string);


        //2.현재 주를 구한다.
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
        //2. hss_txt_week 금주 setText
        hss_txt_week.setText(this_week_string);


        this_month_int = Integer.parseInt(this_month_string);

        //3. hss_btn_month 금월 setText
        hss_txt_month.setText(this_month_string + "월");


        //디폴트 뷰 처리
        hss_day_control_layout.setVisibility(View.VISIBLE);
        hss_week_control_layout.setVisibility(View.GONE);
        hss_month_control_layout.setVisibility(View.GONE);
        hss_btn_day.setTextColor(Color.RED);



        draw_line_graph_day(); //디폴트- 하루 통계 보기


        //1. 일자별 기록 보기
        hss_btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hss_btn_day.setTextColor(Color.RED);
                hss_btn_week.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hss_btn_month.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hss_day_control_layout.setVisibility(View.VISIBLE);
                hss_week_control_layout.setVisibility(View.GONE);
                hss_month_control_layout.setVisibility(View.GONE);

                //일별 기록 그래프 그리는 메서드

                draw_line_graph_day();


            }
        });

        //2. 주별 기록 보기
        hss_btn_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hss_btn_week.setTextColor(Color.RED);
                hss_btn_day.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hss_btn_month.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hss_day_control_layout.setVisibility(View.GONE);
                hss_week_control_layout.setVisibility(View.VISIBLE);
                hss_month_control_layout.setVisibility(View.GONE);


                draw_line_graph_week();

            }


        });


        //3. 월별 기록 보기
        hss_btn_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                hss_btn_month.setTextColor(Color.RED);
                hss_btn_day.setTextColor(Color.BLACK); //나머지 색은 원상복구
                hss_btn_week.setTextColor(Color.BLACK); //나머지 색은 원상복구

                hss_day_control_layout.setVisibility(View.GONE);
                hss_week_control_layout.setVisibility(View.GONE);
                hss_month_control_layout.setVisibility(View.VISIBLE);


            }
        });


        return rootView;


    }

    //1. 일별그래프 그리기
    private void draw_line_graph_day() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONArray tags_array = new JSONArray(response);

                    if (tags_array.length() == 0) {


                    } else {

                        in_time_list.clear();
                        duration_list.clear();

                        for (int i = 0; i < tags_array.length(); i++) {
                            JSONObject tag_obj = tags_array.getJSONObject(i);

                            String in_time = tag_obj.getString("in_time");
                            String duration = tag_obj.getString("duration");

                            Log.e(TAG, "in_time: " + in_time);
                            Log.e(TAG, "duration: " + duration);

                            in_time_list.add(in_time);
                            duration_list.add(duration);


                        }
                        recyclerViewAdapter_toilet.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        class get_line_graph_visit_day extends StringRequest {
            String TAG = getClass().getSimpleName();
            final static private String URL = "http://49.247.208.236:80/stastic_day_stay.php";
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

    //2. 주별 그래프 그리기
    private void draw_line_graph_week() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject tags_array = new JSONObject(response);


                    in_time_list.clear();
                    duration_list.clear();


                    String sun_avg = "(일) "+ tags_array.getString("sun_avg");
                    String mon_avg = "(월) "+tags_array.getString("mon_avg");
                    String tue_avg = "(화) "+tags_array.getString("tue_avg");
                    String wed_avg = "(수) "+tags_array.getString("wed_avg");
                    String thu_avg = "(목) "+tags_array.getString("thu_avg");
                    String fri_avg = "(금) "+tags_array.getString("fri_avg");
                    String sat_avg = "(토) "+tags_array.getString("sat_avg");


                    String sun_num = tags_array.getString("sun_num");
                    String mon_num = tags_array.getString("mon_num");
                    String tue_num = tags_array.getString("tue_num");
                    String wed_num = tags_array.getString("wed_num");
                    String thu_num = tags_array.getString("thu_num");
                    String fri_num = tags_array.getString("fri_num");
                    String sat_num = tags_array.getString("sat_num");


                    in_time_list.add(sun_avg);
                    in_time_list.add(mon_avg);
                    in_time_list.add(tue_avg);
                    in_time_list.add(wed_avg);
                    in_time_list.add(thu_avg);
                    in_time_list.add(fri_avg);
                    in_time_list.add(sat_avg);

                    duration_list.add(sun_num);
                    duration_list.add(mon_num);
                    duration_list.add(tue_num);
                    duration_list.add(wed_num);
                    duration_list.add(thu_num);
                    duration_list.add(fri_num);
                    duration_list.add(sat_num);


                    recyclerViewAdapter_toilet.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        class get_line_graph_visit_week extends StringRequest {
            String TAG = getClass().getSimpleName();
            final static private String URL = "http://49.247.208.236:80/stastic_week_stay.php";
            private Map<String, String> parameters;

            public get_line_graph_visit_week(String email, String today_string, Response.Listener<String> listener) {
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


        get_line_graph_visit_week get_line_graph_visit_w = new get_line_graph_visit_week(static_email, today_string, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(get_line_graph_visit_w);
        Log.e(TAG, "RequestQueue");

    }


}
