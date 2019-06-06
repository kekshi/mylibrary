package com.zdy.baselibrary.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射相关的API
 */
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

    public static void main(String[] args) {
        Test test = new Test();
        Class<? extends Test> testClass = test.getClass();
        //返回类的所有构造方法
//        Constructor<?>[] constructors = testClass.getDeclaredConstructors();
        //返回类中所有 public 类型的构造方法
//        Constructor<?>[] constructors = testClass.getConstructors();
//        for (int i = 0; i < constructors.length; i++) {
//            System.out.print(Modifier.toString(constructors[i].getModifiers()) + "参数：");
//            Class<?>[] parameterTypes = constructors[i].getParameterTypes();
//            for (int j = 0; j < parameterTypes.length; j++) {
//                System.out.print(parameterTypes[j].getName() + " ");
//            }
//            System.out.println("");
//        }
        /**
         * 执行结果：
         * public参数：
         * private参数：java.lang.String
         * public参数：int java.lang.String
         * public参数：int
         * */

        /**
         * 获取特定参数类型的构造方法,包括私有方法
         * */
//        try {
//            //获取特定参数类型的构造方法,包括私有方法,获取无参构造方法直接不传参数,获取对应参数的构造方法就传入对应参数
//            Constructor<? extends Test> constructors = testClass.getDeclaredConstructor();
//            System.out.print(Modifier.toString(constructors.getModifiers()));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        /**
         * public
         * */
//        try {
//            Constructor<? extends Test> constructors = testClass.getDeclaredConstructor(int.class, String.class);
//            System.out.print(Modifier.toString(constructors.getModifiers()));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        /**
         * public
         * */
//        try {
//            Constructor<? extends Test> constructors = testClass.getDeclaredConstructor(String.class);
//            System.out.print(Modifier.toString(constructors.getModifiers()) + " 参数：");
//            Class<?>[] parameterTypes = constructors.getParameterTypes();
//            for (int i = 0; i < parameterTypes.length; i++) {
//                System.out.print(parameterTypes[i].getName() + " ");
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
        /**
         * private 参数：java.lang.String
         * */

        /**
         * 反射调用构造方法
         * */
//        try {
            //根据参数来决定调用哪个构造方法
//            Constructor<? extends Test> constructor = testClass.getDeclaredConstructor(int.class, String.class);
//            constructor.newInstance(24,"啦啦啦");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        /**
         * 反射执行私有构造方法
         * */
//        try {
//            //反射执行私有构造方法
//            Constructor<? extends Test> constructor = testClass.getDeclaredConstructor(String.class);
//            constructor.setAccessible(true);//不加这行反射私有构造函数会报 IllegalAccessException 错
//            constructor.newInstance("啦啦啦");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        /**
         * 调用类的私有方法
         * */
//        try {
//            Method method = testClass.getDeclaredMethod("welcome", String.class);
//            //调用无参构造函数,创建一个实例
////            Test instance = testClass.newInstance();
//            method.setAccessible(true);
//            //执行方法，第一个参数是传入方法所在类的实例，第二个参数是传入方法的参数
//            method.invoke(test,"反射调用私有方法");
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

//        获取类的私有字段并修改值
        try {
            Field field = testClass.getDeclaredField("name");
            field.setAccessible(true);
            field.set(test,"啦啦啦");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(test.getName());
    }

    public static class Test {
        private int age;
        private String name;
        private int testint;

        public Test(int age) {
            this.age = age;
        }

        public Test(int age, String name) {
            this.age = age;
            this.name = name;
            System.out.println("我是公开构造函数，hello" + name + " i am" + age);
        }

        private Test(String name) {
            this.name = name;
            System.out.println("我是私有构造函数，My Name is" + name);
        }

        public Test() {
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTestint() {
            return testint;
        }

        public void setTestint(int testint) {
            this.testint = testint;
        }

        private void welcome(String tips){
            System.out.println(tips);
        }
    }
}
