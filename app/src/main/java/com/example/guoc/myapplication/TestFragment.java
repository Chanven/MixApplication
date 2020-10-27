package com.example.guoc.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Created by Chanven on 2016/1/6.
 */
public class TestFragment extends Fragment {
    private TextView mStorageTv;

    private static final String TAG = "TestFragment";

//    private static final String ImageUrl = "http://lensbuyersguide.com/gallery/219/2/23_iso100_14mm.jpg";
    private static final String ImageUrl = "https://images.pexels.com/photos/1468527/pexels-photo-1468527.jpeg?cs=srgb&dl=agriculture-boy-child-1468527.jpg&fm=jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        mStorageTv = (TextView) view.findViewById(R.id.tv_about_storage);
        aboutStorage();
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.btn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DownloadUtil download = new DownloadUtil();
                download.setDownloadListener(new DownloadUtil.DownloadListener() {
                    @Override
                    public void downloadFinish() {
                        Log.i(TAG, "download success!");
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download.startDownload(ImageUrl);
                    }
                }).start();
            }
        });
    }

    private void aboutStorage() {
        String innerPath = getContext().getFilesDir().getAbsolutePath();
        String externalDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        String externalPublicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        mStorageTv.setText(innerPath + "\n" + externalDir + "\n" + externalPublicDir);
        File file = new File(externalPublicDir, "test.txt");
        try {
            FileOutputStream ops = new FileOutputStream(file);
            ops.write(externalPublicDir.getBytes());
            ops.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!file.mkdir()) {
//            Log.i(TAG, "file create failed!");
//        }
    }
}
