package com.test.java.sort;

import java.util.Random;

/**
 * 洗牌
 * Created by Chanven on 2017/9/26.
 */

public class ShuffleSort {
    public static void shuffleSort(int[] data) {
        for (int i = data.length - 1; i > 0; i --) {
//            int random = new Random().nextInt(i);
            int random = (int) (data.length * Math.random());
            data[i] = data[i] + data[random];
            data[random] = data[i] - data[random];
            data[i] = data[i] - data[random];
        }
    }

    public class DeskOfCard {
        Card[] cards;

        /**
         * 初始化一副牌，不包括大小王
         */
        public void initCards() {
            String[] suit = {"红桃", "方块", "黑桃", "梅花"};
            String[] num = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
            for (int i = 0; i < 52; i ++) {
                cards[i] = new Card(suit[i/13], num[i%13]);
            }
        }
    }

    public class Card {
        public String suit;
        public String num;
        public Card(String suit, String num) {
            this.suit = suit;
            this.num = num;
        }

        public String toString() {
            return suit + ":" + num + "\t";
        }
    }
}
