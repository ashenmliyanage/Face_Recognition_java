package org.example;

import java.util.HashSet;
import java.util.Set;

public class Mail {
    public static void main(String[] args) {
        int x = 10;
        int y = 20;

        x = x+y;
        y = x-y;
        x = x-y;

        System.out.println(x);
        System.out.println(y);

        for (int i = 1; i < 101; i++) {
            System.out.println(i);
        }

        int[] ar = {1,2,3,4,5,1,2,3};

        Set<Integer> list = new HashSet<>();

        for (int List : ar){
            list.add(List);
        }
        System.out.println(list);


    }
}
