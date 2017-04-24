package com.example;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

public class MyClass {


    public static void main(String arg[]) throws IOException {
        MethodSpec main = MethodSpec
                .methodBuilder("main") //添加方法的名称
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC) //方法修饰的关键字
                .addParameter(String[].class, "args") //添加方法的参数
                .addStatement("$T.out.println($S)", System.class, "hello world") //添加代码
                .build();
        TypeSpec hello = TypeSpec.classBuilder("HelloWorld") //添加类的名称
                .addModifiers(Modifier.PUBLIC) //添加修饰的关键字
                .addMethod(main)  //添加该类中的方法
                .build();

        String packgeName = "com.example.generate";//生成类的包名
        JavaFile file = JavaFile.builder(packgeName, hello).build();
        file.writeTo(System.out); //在控制台输出
//        file.writeTo(new File("E:\\Apt_vincent\\javapoet\\src\\main\\java"));//生成文件到制定的目录下，这里生成文件的路径是：E:\Apt_vincent\javapoet\src\main\java\com\example\generate\HelloWorld.java

    }
}
