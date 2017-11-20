package com.example.guoc.myapplication.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.guoc.myapplication.ITestService;
import com.example.guoc.myapplication.R;
import com.example.guoc.myapplication.TestService;

/**
 * IPC通信-aidl客户端 (安装client端时需要更改包名 TestService中认可的包名
 * Created by Chanven on 2017/11/8.
 */

public class TestAidlClientActivity extends AppCompatActivity {
    private ITestService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_client);
        findViewById(R.id.btn_aidl_client_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(TestAidlClientActivity.this, TestService.class);
                TestAidlClientActivity.this.bindService(serviceIntent, mServiceConnection, BIND_AUTO_CREATE);
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ITestService.Stub.asInterface(service);
            try {
                String msg = mService.getDes();
                Toast.makeText(TestAidlClientActivity.this, msg, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    protected void onDestroy() {
        if (null != mService) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}
