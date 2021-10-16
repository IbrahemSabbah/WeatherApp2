//
// Created by Ibrahem Sabbah on 15/10/2021.
//

#include <jni.h>
#include <string>



extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_datasource_di_NetworkModule_apiKeyFromJni(JNIEnv *env, jobject thiz) {
    std::string hello = "bf1a32b25e074aef814192344211110";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_datasource_di_NetworkModule_apiUrlFromJni(JNIEnv *env, jobject thiz) {
    std::string hello = "https://api.worldweatheronline.com/premium/v1/";
    return env->NewStringUTF(hello.c_str());
}