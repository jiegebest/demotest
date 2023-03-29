package com.wangjie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class CustomizedClassLoader extends ClassLoader {
    private String name;
    private String path;
    private String fileExtension = ".class";

    public CustomizedClassLoader(String name) {
        super();
        this.name = name;
    }

    public CustomizedClassLoader(ClassLoader parent, String name) {
        super(parent);
        this.name = name;
    }

    @Override
    public Class<?> findClass(String className) {
        System.out.println("findClass invoked : " + className);
        System.out.println("class loader name : " + this.name);
        // 根据类路径和类名读入流并输出流到字节数组当中
        byte[] data = this.loadClassData(className);
        // 将字节数组转化为Class类对象
        return this.defineClass(className, data, 0, data.length);
    }

    private byte[] loadClassData(String className) {
        byte[] data = null;
        className = className.replace(".", "/");
        try(InputStream in = new FileInputStream(new File(this.path + className + this.fileExtension));
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int ch;
            while(-1 != (ch = in.read())) {
                out.write(ch);
            }
            data = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void setPath(String path) {
        this.path = path;
    }
}