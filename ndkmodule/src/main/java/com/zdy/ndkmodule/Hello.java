package com.zdy.ndkmodule;

/**
 *sheng cheng tou wen jia nming ling: javah -d ../jni com.zdy.ndkmodule.Hello
 */
public class Hello {

    static {
        System.loadLibrary("hello");
    }

    public static native String sayHello();
}
