package com.example.guoc.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chanven on 2016/3/27.
 */
public class FirstFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn_client).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.btn_server).setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_client:
                    toActivity(MyClientActivity.class);
                    break;
                case R.id.btn_server:
                    toActivity(MyServerActivity.class);
                    break;
                default:
                    break;
            }
        }
    };

    private void toActivity(Class cls) {
        Context context = getContext();
        context.startActivity(new Intent(context, cls));
    }
}
