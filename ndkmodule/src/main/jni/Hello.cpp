//
// Created by admin on 2018/9/21.
//

#include "com_zdy_ndkmodule_Hello.h"

JNIEXPORT jstring JNICALL Java_com_zdy_ndkmodule_Hello_sayHello(JNIEnv * env, jclass jclass1){
        return env ->NewStringUTF("from c");
};
