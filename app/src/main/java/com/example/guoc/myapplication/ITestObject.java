package com.example.guoc.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chanven on 2017/11/8.
 */

public class ITestObject implements Parcelable {
    private int value;

    private String des;

    public ITestObject() {

    }

    protected ITestObject(Parcel in) {
        value = in.readInt();
        des = in.readString();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static final Creator<ITestObject> CREATOR = new Creator<ITestObject>() {
        @Override
        public ITestObject createFromParcel(Parcel in) {
            return new ITestObject(in);
        }

        @Override
        public ITestObject[] newArray(int size) {
            return new ITestObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
        dest.writeString(des);
    }
}
