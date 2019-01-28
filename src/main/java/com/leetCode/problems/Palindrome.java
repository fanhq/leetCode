package com.leetCode.problems;

/**
 * Created by Hachel on 2019/1/28
 *
 * @describe 回文数字
 */
public class Palindrome {
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String ps = String.valueOf(x);
        if (ps.length() == 1) {
            return true;
        }
        int mid = ps.length() / 2;
        for (int index = 0; index < mid; index++) {
            int left = mid - index - 1;
            int right;
            if ((ps.length() & 1) == 0){
                right = mid + index;
            }else {
                right = mid + index + 1;
            }
            if (!ps.substring(left, left + 1).equals(ps.substring(right, right + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Palindrome pa = new Palindrome();
        System.out.println(pa.isPalindrome(10));
    }
}
