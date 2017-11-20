package com.example.guoc.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 *
 * Created by Chanven on 2016/4/13.
 */
public class TestService extends Service {

    private static final String PACKAGE_TEST = "com.example.guoc.client";

    private ITestObject mObject;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showToast("service has started !");
        return mBinder;
    }

    private ITestService.Stub mBinder = new ITestService.Stub() {

        @Override
        public String getDes() throws RemoteException {
            return mObject.getDes();
        }

        @Override
        public void addObject(ITestObject object) throws RemoteException {
            mObject = object;
        }

        //在这里可以做权限认证，return false意味着客户端的调用就会失败，比如下面，只允许包名为com.example.guoc.client的客户端通过，
        //其他apk将无法完成调用过程
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String packageName = null;
            String[] packages = TestService.this.getPackageManager().getPackagesForUid(getCallingUid());
            if (null != packages && packages.length > 0) {
                packageName = packages[0];
            }
            return PACKAGE_TEST.equals(packageName) && super.onTransact(code, data, reply, flags);
        }
    };

    @Override
    public void onCreate() {
        mObject = new ITestObject();
        mObject.setDes("it is a object");
        mObject.setValue(7);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
