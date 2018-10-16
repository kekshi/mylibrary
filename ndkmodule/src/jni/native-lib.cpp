#include <jni.h>
#include <string>

//将包名定义到宏
#define XONGFUNC(name)Java_com_zdy_ndkmodule_##name

extern "C" JNIEXPORT jstring

//JNICALL
//Java_com_zdy_ndkmodule_MainActivity_stringFromJNI(JNIEnv *env, jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());//c_str()其实是又将string对象转成了char[]数组)
//}

JNICALL
XONGFUNC(MainActivity_stringFromJNI)(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF("Hello from C++");
}
