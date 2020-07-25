//package com.example.team_cat_iot2020;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.VideoView;
//
//public class cctv1 extends Fragment {
//    String TAG = getClass().getSimpleName();
//    private Context context;
//    private VideoView cctv1_video_view;
//
//    public cctv1() {
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cctv1, container, false);
//
//        context = container.getContext();
//        cctv1_video_view = rootView.findViewById(R.id.cctv1_video_view);
//
//
//        return rootView;
//    }
//
//}
