package com.example.guoc.myapplication;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * Created by Chanven on 2016/3/22.
 */
public class DownloadUtil {
    Executor pool = Executors.newFixedThreadPool(3);
    static DownloadListener mDownloadListener;

    public void setDownloadListener(DownloadListener listener) {
        mDownloadListener = listener;
    }

    static class DownloadRunnable implements Runnable {
        private String url;
        private String fileName;
        private long start;
        private long end;

        public DownloadRunnable(String url,String fileName, long start, long end) {
            this.url = url;
            this.fileName = fileName;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                URL fileUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000); // 连接超时
                connection.setReadTimeout(5000); // 读取超时
                // 设置请求属性，请求制定范围的文件流
                connection.setRequestProperty("Range=", "bytes=" + start + "-" + end);
                RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                // 移动RandomAccessFile写入位置，可从上次记录的位置开始
                accessFile.seek(start);
                connection.connect();
                InputStream in = connection.getInputStream(); // 输入流
                byte[] bytes = new byte[1024*4];
                int len; // 每次读取的数组长度
                int complete = 0;
                while ((len = in.read(bytes)) != -1) {
                    accessFile.write(bytes, 0, len);
                    complete += len;
                    int progress = (int) ((float) complete / (float) end * 100);
                    Log.i("download--->>", progress + "%");
                }
                // 下载完成
                if (null != mDownloadListener) {
                    mDownloadListener.downloadFinish();
                }
                in.close();
                accessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startDownload(String url) {
        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setReadTimeout(50000);
            connection.setRequestMethod("GET");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "test.jpg");
            int count = connection.getContentLength();
            int block = count/3;
            for (int i = 0; i < 3; i ++) {
                long start = block * i;
                long end = block * (i + 1) - 1;
                if (i == 2) {
                    end = count - 1;
                }
            }
            Log.i("count ----->>", count + "");
            // 第二种获取文件大小的方式
            Log.i("length ---->>", connection.getHeaderField("content-length"));
            DownloadRunnable runnable = new DownloadRunnable(url, file.getAbsolutePath(), 0, count - 1);
            pool.execute(runnable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface DownloadListener {
        public void downloadFinish();
    }
}
