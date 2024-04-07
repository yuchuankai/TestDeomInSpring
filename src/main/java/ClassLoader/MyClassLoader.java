/**
 * @Project: testDeomInSpring
 * @ClassName: MyClassLoader
 * @Date: 2024年 03月 19日 16:03
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package ClassLoader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Description:
 * @Date: 2024年 03月 19日 16:03
 * @Author: MR.Yu
 **/
public class MyClassLoader extends URLClassLoader {


    public MyClassLoader(String[] paths) {
        this(paths,MyClassLoader.class.getClassLoader());
    }
    public MyClassLoader(String[] paths, ClassLoader classLoader) {
        super(getURLs(paths), classLoader);
    }

    public MyClassLoader(URL[] paths, ClassLoader classLoader) {
        super(paths, classLoader);
    }

    private static URL[] getURLs(String[] paths) {
        Validate.isTrue(null != paths && 0 != paths.length,
                "jar包路径不能为空.");

        List<String> dirs = new ArrayList<String>();
        for (String path : paths) {
            dirs.add(path);
        }

        List<URL> urls = new ArrayList<URL>();
        for (String path : dirs) {
            urls.addAll(doGetURLs(path));
        }

        return urls.toArray(new URL[0]);
    }

    private static List<URL> doGetURLs(final String path) {
        Validate.isTrue(!StringUtils.isBlank(path), "jar包路径不能为空.");

        File jarPath = new File(path);

        Validate.isTrue(jarPath.exists() && jarPath.isDirectory(),
                "jar包路径必须存在且为目录.");

        /* set filter */
        FileFilter jarFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".jar");
            }
        };

        /* iterate all jar */
        File[] allJars = new File(path).listFiles(jarFilter);
        List<URL> jarURLs = new ArrayList<URL>(allJars.length);
        File[] allLibJars = new File(path + "\\libs").listFiles(jarFilter);
        for (int i = 0; i < allJars.length; i++) {
            try {
                jarURLs.add(allJars[i].toURI().toURL());
            } catch (Exception e) {
                System.out.println("jar file error:" + e);
            }
        }

        for (int i = 0; i < allLibJars.length; i++) {
            try {
                jarURLs.add(allLibJars[i].toURI().toURL());
            } catch (Exception e) {
                System.out.println("jar file error:" + e);
            }
        }

        return jarURLs;
    }
}



class Test {
    public static void main(String[] args) {
        String patch = "D:\\projects\\shenruanDatax\\target\\datax\\datax\\\\plugin\\reader\\mysqlreader";
        String className = "com.alibaba.datax.plugin.reader.mysqlreader.MysqlReader";
        MyClassLoader myClassLoader = new MyClassLoader(new String[]{patch});
        try {
            Class<?> aClass = myClassLoader.loadClass(className + "$Job");
            Class<?>[] interfaces = aClass.getInterfaces();
            System.out.println(interfaces);
            System.out.println(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
