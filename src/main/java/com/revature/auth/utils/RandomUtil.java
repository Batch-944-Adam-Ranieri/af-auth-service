package com.revature.auth.utils;

public class RandomUtil{

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase();

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;



    public static String generate(int size) {

        StringBuilder builder = new StringBuilder();
        int random;
        for (int i = 0; i < size; i++) {
            random = (int)(Math.random() * alphanum.length());
            builder.append(alphanum.charAt(random));

        }


        return builder.toString();
    }
}
