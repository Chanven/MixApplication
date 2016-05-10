package com.example.guoc.myapplication;

import android.os.Environment;

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
    private DownloadListener mDownloadListener;

    public void setDownloadListener(DownloadListener listener) {
        this.mDownloadListener = listener;
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
                connection.setReadTimeout(5000);
                connection.setRequestProperty("Range=", "bytes=" + start + "-" + end);
                RandomAccessFile accessFile = new RandomAccessFile(new File(fileName), "rwd");
                accessFile.seek(start);
                InputStream in = connection.getInputStream();
                byte[] bytes = new byte[1024*4];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    accessFile.write(bytes, 0, len);
                }
                if (null != in) {
                    in.close();
                }
                if (null != accessFile) {
                    accessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void startDownload(String url) {
        try {
            URL fileUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) fileUrl.openConnection();
            connection.setReadTimeout(5000);
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
                DownloadRunnable runnable = new DownloadRunnable(url,file.getAbsolutePath(), start, end);
                pool.execute(runnable);
            }
            if (null != mDownloadListener) {
                mDownloadListener.downloadFinish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface DownloadListener {
        public void downloadFinish();
    }
}
