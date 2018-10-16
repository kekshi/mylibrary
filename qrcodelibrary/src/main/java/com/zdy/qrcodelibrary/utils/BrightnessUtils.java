package com.zdy.qrcodelibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

public class BrightnessUtils {

    /**
     * 获取系统亮度
     */
    public static int getSystemBrightness(Context context) {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();

        }
        return systemBrightness;

    }

    /**
     * 设置系统亮度
     */
    public static void putSystemBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, getSystemBrightness(context));
    }

    /**
     * 获取系统亮度模式
     */
    public static int getSystemBrightnessMode(Context context) {
        int systemBrightnessMode = 0;
        try {
            systemBrightnessMode = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();

        }
        return systemBrightnessMode;

    }

    /**
     * 设置系统亮度模式
     */
    public static void putSystemBrightnessMode(Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, getSystemBrightnessMode(context));
    }

    /**
     * 设置当前屏幕的亮度为最亮
     */
    public static void changeAppBrightness(Activity context) {
        changeAppBrightness(context, 255);
    }

    /**
     * 设置当前屏幕的亮度
     * screenBrightness   0-1,暗到全亮  -1 跟随系统亮度
     */
    public static void changeAppBrightness(Activity context, int brightness) {
        Window window = context.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
}
