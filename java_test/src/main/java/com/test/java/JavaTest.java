package com.test.java;

import com.test.java.sort.ShuffleSort;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class JavaTest {
    public static void main(String[] args) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String test = dateFormat.format((long)1720277302*1000);
        System.out.println(test);
        int[] data = {1,2,3,4,5,6,7};
        ShuffleSort.shuffleSort(data);
        for (int aData : data) {
            System.out.print(aData + "   ");
        }

        SaleTicket st = new SaleTicket();
        for (int i = 1; i <= 5; i++) {
            new Thread(st, "售票点：" + i).start();
        }
    }

    public static class SaleTicket implements Runnable {

        public int count;

        public SaleTicket() {
            count = 30;
        }

        public /*synchronized*/ void startP(){
            while (count > 0) {
                synchronized (SaleTicket.class) {
                    if (count > 0) {
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "     当前票号：" + count--);
                    }
                }
            }
        }

        public void run() {
            startP();
        }

    }
}
