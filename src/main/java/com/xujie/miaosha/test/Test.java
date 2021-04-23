package com.xujie.miaosha.test;

import java.util.Arrays;

public class Test {
    public static void main( String[] args ) {
        Solution s = new Solution();
        System.out.println(s.convert("AB", 1));
    }
}

class Solution {
    public String convert( String s, int numRows ) {
        if (s == null || s.length() <= 1 || numRows < 2) {
            return s;
        }
        int n = s.length();
        char[][] ch = new char[numRows][n];
        for (char[] c: ch) {
            Arrays.fill(c,'0');
        }
        int index = 0, i = 0, j = 0;
        while (index < n) {
            while (i < numRows && index < n) {
                ch[i++][j] = s.charAt(index++);
            }
            i--;
            j++;
            while (i >= 0 && index < n) {
                ch[i][j] = s.charAt(index++);
                j++;
                i--;
            }
            j--;
            i += 2;
        }
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < numRows; k++) {
            for (int l = 0; l < n; l++) {
                if(ch[k][l] != '0'){
                    sb.append(ch[k][l]);
                }
            }
        }
        return sb.toString();
    }

}