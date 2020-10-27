package com.test.java;

import com.test.java.sort.ShuffleSort;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaTest {
    public static void main(String[] args) {
        System.out.println("enter total nums:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(solve(n));
        testFibo(n);
    }


    /**
     * 斐波那契数列的递归实现，效率低
     * @param n
     * @return
     */
    public static int solve(int n) {
        if (n == 1 || n == 2) {
            return n;
        } else if (n <= 0) {
            return 0;
        } else {
            return solve(n - 1) + solve(n - 2);
        }
    }

    /**
     * 斐波那契数列
     */
    public static void testFibo(int n) {
        int now = 0;
        int first = 0;
        int last = 1;
        for (int i = 1; i <= n; i++) {
            now = first + last;
            first = last;
            last = now;
        }
        System.out.println("total:" + now);
    }

    /**
     * 正则表达式，截取某一种格式的字符串
     */
    public static void testPattern() {
        String regular = "^.+\\$JK.+JK\\$.+$";
        String get_regular = "\\$JK(.*?)JK\\$";
        String msg = "$JK{\"joinTime\":\"\",\"groupId\":\"37a23fec9c564699b76a23cb1243f37d\"}JK$dfad$JKfxxx$JK$";
        Pattern pattern = Pattern.compile(get_regular);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    /**
     * 两个集合求交集
     */
    public static void testListRetain() {
        List<String> aStrings = new ArrayList<>();
        aStrings.add("23");
        aStrings.add("34");

        List<String> bStrings = new ArrayList<>();
        bStrings.add("56");
        bStrings.add("314");

        List<String> cStrings = new ArrayList<>();
        cStrings.addAll(aStrings);

        System.out.println(cStrings.retainAll(bStrings) + "--->"+ cStrings + aStrings + " " + bStrings);
    }

    /**
     * 时间戳
     */
    public static void testTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String test = dateFormat.format((long)1720277302*1000);
        System.out.println(test);
    }

    /**
     * 同步问题
     */
    public static void testSyncronized() {
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

    /**
     * 整型与双精度型 除以 0
     */
    public static void testDouble() {
        System.out.println(1 / 0.0);
        System.out.println('a' / 1);
        System.out.print('A' / 1);
//        System.out.print("a" / 0.0);  //报错
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
