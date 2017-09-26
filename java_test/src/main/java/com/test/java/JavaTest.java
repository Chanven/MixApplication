package com.test.java;

import java.text.SimpleDateFormat;

public class JavaTest {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String test = dateFormat.format((long)1720277302*1000);
        System.out.print(test);
    }
}
