package com.example.guoc.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * Created by Chanven on 2016/4/13.
 */
public class TestService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private ITestService.Stub mBinder = new ITestService.Stub() {

        @Override
        public int getCount() throws RemoteException {
            return 0;
        }
    };

}
