package com.zdy.baselibrary.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射相关的API
 * */
public class ClassUtil {
    public static void getClassInfo(Object obj) {
        Class<?> c = obj.getClass();
        System.out.println("类的名称是：" + c.getName());

//        Method[] methods = c.getMethods();//获取类的所有方法
        Method[] methods = c.getDeclaredMethods();//获取所有该类自己声明的方法的信息
        for (Method m : methods) {
            System.out.println("方法返回值类型是：" + m.getReturnType().getName());
            System.out.println("方法名是：" + m.getName());
            Class<?>[] parameterTypes = m.getParameterTypes();
            for (Class par : parameterTypes) {
                System.out.print("参数类型是：" + par.getName());
            }

        }
    }

    public static void printFieldMessage(Object obj) {
        Class<?> c = obj.getClass();
        //Field[] fields = c.getFields();//获取所有public的成员变量信息
        Field[] declaredFields = c.getDeclaredFields();//获取所有该类自己声明的成员变量的信息
        for (Field field : declaredFields) {
            Class<?> type = field.getType();//得到成员变量的类类型
            String typeName = type.getName();
            System.out.println("成员变量的类类型名称是：" + typeName);
            String name = field.getName();
            System.out.println("成员变量的名称是：" + name);
        }
    }

    public static void listFanShe() {
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        list1.add("hello word");
        Class list1Class = list1.getClass();
        Class list2Class = list2.getClass();
        System.out.println(list1Class == list2Class);
        try {
            Method method = list1Class.getMethod("add", Object.class);
            method.invoke(list1, 10);
            System.out.println("list1:" + list1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
