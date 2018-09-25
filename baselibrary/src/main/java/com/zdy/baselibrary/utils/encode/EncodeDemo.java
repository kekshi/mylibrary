package com.zdy.baselibrary.utils.encode;

import android.util.Log;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class EncodeDemo {
    public static void main(String args[]) {
        String optKey = "9a999f8501a11d7";
        TimeBasedOneTimePassword timeBasedOneTimePassword = new TimeBasedOneTimePassword(30, TimeUnit.SECONDS, 60);
        HmacBasedOneTimePassword hmacBasedOneTimePassword = new HmacBasedOneTimePassword(HmacBasedOneTimePassword.Algorithm.SHA1, 6, optKey.getBytes(Charset.defaultCharset()));
        String otpCode = timeBasedOneTimePassword.generatePasswordString(hmacBasedOneTimePassword);
        Log.e("otpcode", otpCode);
    }
}
