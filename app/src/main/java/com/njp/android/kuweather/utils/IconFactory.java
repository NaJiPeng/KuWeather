package com.njp.android.kuweather.utils;

import android.content.Context;

import com.njp.android.kuweather.R;
import java.util.Calendar;

/**
 * 切换图标和背景用到的工厂类
 */

public class IconFactory {

    private static final String TAG = "IconFactory";

    private static boolean isNight() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 18 || hour < 5) {
            return true;
        }
        return false;
    }


    public static int getBlackIcon(Context context, int weather) {
        String drawableName = "black_" + weather;
        int resId = context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
        return resId;
    }

    public static int getWhiteIcon(Context context, int weather) {
        String drawableName = "white_" + weather;
        int resId = context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName());
        return resId;
    }

    public static int getLargeIcon(int weather) {
        if (weather == 100) {
            if (isNight()) {
                return R.drawable.sunny_night;
            } else {
                return R.drawable.sunny;
            }
        } else if (weather == 103) {
            if (isNight()) {
                return R.drawable.partly_cloudy_night;
            } else {
                return R.drawable.partly_cloudy;
            }
        } else if (weather >= 101 && weather <= 104) {
            return R.drawable.cloudy;
        } else if (weather >= 200 && weather <= 211) {
            return R.drawable.windy;
        } else if (weather == 212 || weather == 213) {
            return R.drawable.tornado;
        } else if (weather == 300 || weather == 305 || weather == 309 || weather == 313) {
            return R.drawable.rainy_light;
        } else if (weather == 301 || weather == 306) {
            return R.drawable.rainy_medium;
        } else if (weather >= 302 && weather <= 304) {
            return R.drawable.thundery;
        } else if (weather >= 307 && weather <= 312) {
            return R.drawable.rainy_heavy;
        } else if (weather >= 400 && weather <= 407) {
            return R.drawable.snowy;
        } else if (weather >= 500 && weather <= 508) {
            return R.drawable.dusty;
        } else if (weather == 900 || weather == 901) {
            return R.drawable.temp;
        } else {
            return R.drawable.unknown;
        }
    }

    public static int getBackground(Context context) {
        int random;
        String drawableName = "bg_";
        if (isNight()) {
            random = (int) (Math.random() * 10);
            drawableName += "night_" + random;
        } else {
            random = (int) (Math.random() * 30);
            drawableName += "morning_" + random;
        }
        return context.getResources()
                .getIdentifier(drawableName, "drawable", context.getPackageName()
                );

    }


}
