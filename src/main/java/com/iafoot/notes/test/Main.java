package com.iafoot.notes.test;

import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main1(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(br.readLine());
        char [][] count = new char [a][];
        for (int i = 0;  i < a; i++) {
            count[i]  = br.readLine().toCharArray();
        }
        digui(count,0,0);


    }    private static void digui(char[][] a, int i, int j) {
        if (i!=a.length) {
            if (j==a[i].length-1) {
                System.out.println(a[i][j]);
                digui(a, ++i, 0);
            }else if (i<a.length){
                System.out.print(a[i][j]);
                digui(a, 0, ++j);
                digui(a, i, ++j);
            }
        }
    }



}