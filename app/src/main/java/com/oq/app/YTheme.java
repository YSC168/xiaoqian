package com.oq.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Window;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by log on 2017/7/29.
 */

public class YTheme {
    /**
     * 配置主题
     * @param activity  指定的activity
     */
    public static void setTheme(Activity activity) {
        SharedPreferences preferences = activity
			.getSharedPreferences("item", MODE_PRIVATE);
        int themeId = preferences.getInt("theme", 0);

        switch (themeId) {
            case R.style.AppTheme_Purple:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Green:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Yanzhi:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Blue:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Grey:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_LightGreen:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Indigo:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Teal:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Amber:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Red:
                activity.setTheme(themeId);
                break;
            case R.style.AppTheme_Brown:
                activity.setTheme(themeId);
                break;
            default:
                activity.setTheme(R.style.AppTheme_Pink);
        }     
    }
}

