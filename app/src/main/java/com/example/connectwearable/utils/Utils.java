package com.example.connectwearable.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getTime(String format, int maxHour) {
        Calendar cal = Calendar.getInstance(); // creates calendar
        SimpleDateFormat format1 = new SimpleDateFormat(format, Locale.getDefault());
        cal.setTime(new Date());               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, maxHour);      // adds n hour
        cal.getTime();
        return format1.format(cal.getTime());
    }

    public static String priceFormat(double totalPrice) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(totalPrice);
    }
}
