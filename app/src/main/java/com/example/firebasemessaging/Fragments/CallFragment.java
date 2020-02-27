package com.example.firebasemessaging.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.firebasemessaging.Adapters.CallAdapter;
import com.example.firebasemessaging.R;

public class CallFragment extends Fragment {

    private RecyclerView callcycler;
    private CallAdapter adapter;
    private Button callvideo;
    public CallFragment() {
        //very important
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        callcycler = view.findViewById(R.id.callCycler);
        callvideo = view.findViewById(R.id.callvideo);
        adapter = new CallAdapter();
        callcycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        callcycler.setAdapter(adapter);
        callvideo.setOnClickListener(videocallListner);
        return view;
    }
private View.OnClickListener videocallListner = new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
};
}
