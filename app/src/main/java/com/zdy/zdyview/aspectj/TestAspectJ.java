package com.zdy.zdyview.aspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class TestAspectJ {
    private static final String TAG = "TestAspectJ";

    @Around("execution(* *(..))")
    public Object weaveAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNanoTime = System.nanoTime();

        Object returnObject = joinPoint.proceed();

        long stopNanoTime = System.nanoTime();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //方法名
        String name = signature.getName();
        Log.i(TAG, "name=" + name);
        Method method = signature.getMethod();

        //返回值类型
        Class returnType = signature.getReturnType();
        Log.i(TAG, "returnType=" + returnType.getName());

        //方法所在类名
        Class declaringType = signature.getDeclaringType();
        Log.i(TAG, "declaringType=" + declaringType.getCanonicalName());

        //参数类型
        Class[] parameterTypes = signature.getParameterTypes();
        for (Class cls : parameterTypes) {
            Log.i(TAG, "cls=" + cls.getSimpleName());
        }

        //参数名
        String[] parameterNames = signature.getParameterNames();
        for (String param : parameterNames) {
            Log.i(TAG, "param=" + param);
        }

        Log.i(TAG, "执行时间是：" + (stopNanoTime - startNanoTime));
        return returnObject;
    }
}
