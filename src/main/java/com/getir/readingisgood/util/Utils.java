package com.getir.readingisgood.util;

public class Utils {
    public static String convertMonthToName(int month){
        String[] monthNames = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
