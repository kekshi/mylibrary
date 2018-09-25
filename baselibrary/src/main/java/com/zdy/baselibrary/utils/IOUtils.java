package com.zdy.baselibrary.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/*
 * IO流（输入、输出流）（字节流、字符流）
 * 1.字节流：InputStream、OutputStream
 * InputStream抽象了应用程序读取数据的方式
 * OutputStream抽象了应用程序写出数据的方式
 * 2.EOF(文件结束)=End 读到-1就是结尾
 * 3.输入流基本方法
 * in.read();读取一个字节，无符号填充到int低八位，-1是eof
 * in.read(byte[] bufyes)读取数据填充到字节数组
 * in.read(byte[] bufyes，int start,int size)读取字节数组bufyes,从start的位置开始到size长度的字节流
 * 4.输出流基本方法
 * out.write(int b);读取一个byte字节，b的低8位
 * out.write(byte[] bufyes)将bufyeszijie数组都写入到流
 * out.write(byte[] bufyes，int start,int size)写入字节数组bufyes,从start的位置开始到size长度的字节流
 *
 * 5.FileInputStream-->具体实现了在文件上读取数据
 * 6.FileOutputStream-->具体实现了向文件中写出byte数据的方法
 * 7.DataOutputStream/DataInputStream-->对流功能的拓展，可以更加方便的读取int，long字符等类型数据
 * 8.BufferedInputStream/BufferedOutputStream-->提供了带缓冲区的操作
 *
 * 字符流
 * 1.编码问题
 * 2.文本和文本文件。java文本（char）是位无符号证书，是支付的unicode编码（双字节编码）。
 *   文本本件是文本序列按照魔宗编码方案（utf-8）序列化为byte存储。
 * 3.字符流（Reader Writer）
 *   字符的处理，一次处理一个字符
 *   字符流的基本实现
 *   InputStreamReader-->完成byte流解析为char流，按照编码解析
 *   OutputStreamWriter-->提供char流到byte流按照编码处理
 *
 *   FileReader/FileWriter
 * */

public class IOUtils {

    /**
     * 读取指定文件内容，按照16进制输出到控制台，并且每输出10个byte换行
     */
    public static void printHex(String fileName) throws IOException {
        //把文件作为字节流进行读操作
        FileInputStream in = new FileInputStream(fileName);
        int b;
        int i = 1;
        while ((b = in.read()) != -1) {
            if (b <= 0xf) {
                //单位数前面补0
                System.out.print("0");
            }
            System.out.print(Integer.toHexString(b) + "  ");
            if (i++ % 10 == 0) {
                System.out.println("");
            }
        }
        in.close();
    }

    public static void printByteArray(String fileName) throws IOException {
        //把文件作为字节流进行读操作
        FileInputStream in = new FileInputStream(fileName);
        byte[] buff = new byte[8 * 1024];
        //从in中批量读取字节，放入到buff这个字节数组中，从0开始，到最后一个。返回的是督导的字节个数
//        int bytes = in.read(buff, 0, buff.length);
//        int j = 1;
//        for (int i = 0; i < bytes; i++) {
//            if (buff[i] <= 0xf) {
//                //单位数前面补0
//                System.out.print("0");
//            }
//            //byte类型8位，int类型32位，为了避免数据转换错误，通过& 0xff将高24位清零
//            System.out.print(Integer.toHexString(buff[i] & 0xff) + "  ");
//            if (j++ % 10 == 0) {
//                System.out.println("");
//            }
//        }
        int bytes = 0;
        int j = 1;
        while ((bytes = in.read(buff, 0, buff.length)) != -1) {
            for (int i = 0; i < bytes; i++) {
                System.out.print(Integer.toHexString(buff[i] & 0xff) + "  ");
            }
            if (j++ % 10 == 0) {
                System.out.println("");
            }
        }
    }

    /**
     * 拷贝文件
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        //如果该文件不存在，则直接创建，如果存在，则删除后创建。
        // 如果构造函数中第二个参数为true，则不删除而是追加
//        FileOutputStream out = new FileOutputStream("demo/out.txt");
        if (!srcFile.exists()) {
            throw new IllegalAccessError("文件" + srcFile + "不存在");
        }
//        if (srcFile.isFile()) {
//            throw new IllegalAccessError(srcFile + "不是文件");
//        }
        FileInputStream in = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] buff = new byte[8 * 1024];
        int b;
        while ((b = in.read(buff, 0, buff.length)) != -1) {
            out.write(buff, 0, b);
            out.flush();
        }
        in.close();
        out.close();
    }

    /**
     * 拷贝文件，带缓冲的字节流
     */
    public static void copyFileByBuff(File srcFile, File destFile) throws IOException {
        if (!srcFile.exists()) {
            throw new IllegalAccessError("文件" + srcFile + "不存在");
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
        int b;
        while ((b = bufferedInputStream.read()) != -1) {
            bufferedOutputStream.write(b);
            bufferedOutputStream.flush();
        }
        bufferedInputStream.close();
        bufferedOutputStream.close();
    }

    /**
     * 字符流读写
     */
    public static void zifuliuInfo() throws IOException {
        FileInputStream in = new FileInputStream("C:\\Users\\admin\\Desktop\\杂类\\下载密码.txt");
        InputStreamReader isr = new InputStreamReader(in, "utf-8");
        FileOutputStream out = new FileOutputStream("C:\\Users\\admin\\Desktop\\杂类\\copyTest.txt");
        OutputStreamWriter osr = new OutputStreamWriter(out, "utf-8");
        char[] buff = new char[8 * 1024];
        int c;
        while ((c = isr.read(buff, 0, buff.length)) != -1) {
//            String str = new String(buff, 0, c);
//            System.out.println(str);
            osr.write(buff, 0, c);
            osr.flush();
        }
        in.close();
        out.close();
        isr.close();
        osr.close();
    }

    /**
     * 文件字符流读写
     */
    public static void frandfw() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\admin\\Desktop\\杂类\\jira账户.txt");
        //追加
        FileWriter fw = new FileWriter("C:\\Users\\admin\\Desktop\\杂类\\copyTest.txt",true);
        char[] buff = new char[8 * 1024];
        int c;
        while ((c = fr.read(buff, 0, buff.length)) != -1) {
//            String str = new String(buff, 0, c);
//            System.out.println(str);
            fw.write(buff, 0, c);
            fw.flush();
        }
        fr.close();
        fw.close();
    }

    /**
     * 列出指定目录下（包括其子目录）的所有文件
     */
    public static void listDirectory(File dir) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录：" + dir + "不存在");
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + "不是目录");
        }
//        String[] fileNames = dir.list();//返回目录下的所有子目录及文件名称
//        for (String str : fileNames) {
//            System.out.println(dir + "\\" + str);
//        }
        //遍历子目录下的内容
        File[] files = dir.listFiles();//返回目录下的所有子目录及文件的抽象路径
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {//如果是目录则递归，不是则打印
                    listDirectory(file);
                } else {
                    System.out.println(file);
                }
            }
        }
    }
}
