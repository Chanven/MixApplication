package com.test.java;

import com.test.java.sort.ShuffleSort;

import java.text.SimpleDateFormat;

public class JavaTest {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String test = dateFormat.format((long)1720277302*1000);
        System.out.println(test);
        int[] data = {1,2,3,4,5,6,7};
        ShuffleSort.shuffleSort(data);
        for (int aData : data) {
            System.out.print(aData + "\t");
        }
    }
}
