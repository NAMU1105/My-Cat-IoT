package com.example.team_cat_iot2020;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerViewAdapter_toilet extends RecyclerView.Adapter<RecyclerViewAdapter_toilet.ViewHolder> {

    private static final String TAG = "Adapter_toilet";  //디버깅하기 위해 사용됨


    private ArrayList<String> in_time = new ArrayList<>();
    private ArrayList<String> duration = new ArrayList<>();

    private Context mContext;

    public RecyclerViewAdapter_toilet(ArrayList<String> in_time, ArrayList<String> duration, Context mContext){
        this.in_time = in_time;
        this.duration = duration;

        this.mContext = mContext;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder : called.");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toilet_item, parent, false);
        ViewHolder holder = new ViewHolder(view);


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder : called.");

         holder.text_in_time.setText(in_time.get(position));
         holder.text_duration.setText(duration.get(position));

    }

    @Override
    public int getItemCount() {         //아이템 전체 개수를 알려주는 메소드

        return in_time.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView text_in_time;
        TextView text_duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text_in_time = itemView.findViewById(R.id.text_intime);
            text_duration = itemView.findViewById(R.id.text_duration);

        }
    }
}
