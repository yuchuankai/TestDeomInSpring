package 循环依赖_三层循环依赖;


import 循环依赖_三层循环依赖.entity.A;
import 循环依赖_三层循环依赖.entity.B;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @CreateTime: 2024年 12月 13日 13:44
 * @Description:
 * @Author: MR.YU
 */
public class Test {

    //成品缓存
    private static final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    //半成品缓存
    private static final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    //从缓存中获取
    private static Object getSingleton(String className) {
        //先从成品缓存中查找
        Object singletonObject = singletonObjects.get(className);
//        if (singletonObject == null) {
//            //再从半成品缓存中查找
//            singletonObject = earlySingletonObjects.get(className);
//        }
        return singletonObject;
    }

    private static <T> T getBean(Class<T> clazz) throws IllegalAccessException, InstantiationException {

        //先从缓存中获取
        String className = clazz.getSimpleName();
        Object singleton = getSingleton(className);
        if (singleton != null) {
            return (T) singleton;
        }

        //实例化对象
        T instance = clazz.newInstance();

        //实例化完成后，就将这个半成品放入到缓存中
        singletonObjects.put(className, instance);

        if ("ThreadA".equals(Thread.currentThread().getName())) {
            try {
                Thread.sleep(1000*2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //获取当前类中的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //允许访问私有变量
            field.setAccessible(true);
            //判断字段是否被@Load注解修饰
            boolean isUseLoad = field.isAnnotationPresent(Load.class);
            if (!isUseLoad) {
                continue;
            }
            //获取需要被注入的字段的class
            Class<?> fieldType = field.getType();
            //递归获取字段的实例对象
            Object fieldBean = getBean(fieldType);
            //将实例对象注入到该字段中
            field.set(instance, fieldBean);
        }
        //完成属性注入后，从半成品缓存中移除，加入到成品缓存中
//        earlySingletonObjects.remove(className);
//        singletonObjects.put(className, instance);

        return instance;
    }
    public static void main(String[] args) {
        test();
    }

    public static void test(){
        Thread ThreadA = new Thread(() -> {
            A a = null;
            try {
                a = getBean1(A.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " A: " + a.hashCode());
            System.out.println(Thread.currentThread().getName() + " B: " + a.getB().hashCode());
        }, "ThreadA");
        Thread ThreadB = new Thread(() -> {
            A a = null;
            try {
                a = getBean1(A.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " A: " + a.hashCode());
            a.getB().setName("B");
            System.out.println(Thread.currentThread().getName() + " B: " + a.getB().hashCode());
        }, "ThreadB");

        Thread ThreadC = new Thread(() -> {
            B b= null;
            try {
                b = getBean1(B.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " B: " + b.hashCode());
            System.out.println(Thread.currentThread().getName() + " A: " + b.getA().hashCode());
        }, "ThreadC");
        ThreadA.start();
        ThreadB.start();
        ThreadC.start();
    }








    //由类名可以获取到对应的实例对象
    private static Map<String, Object> singletonObjects1= new HashMap<>();

    private static <T> T getBean1(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        //先从缓存中获取
        String className = clazz.getSimpleName();
        if (singletonObjects1.containsKey(className)) {
            return (T) singletonObjects1.get(className);
        }

        //实例化对象
        T instance = clazz.newInstance();
        //实例化完成后，就将这个半成品放入到缓存中
        singletonObjects1.put(className, instance);

        //获取当前类中的所有字段
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //允许访问私有变量
            field.setAccessible(true);
            //判断字段是否被@Load注解修饰
            boolean isUseLoad = field.isAnnotationPresent(Load.class);
            if (!isUseLoad) {
                continue;
            }
            //获取需要被注入的字段的class
            Class<?> fieldType = field.getType();
            //递归获取字段的实例对象
            Object fieldBean = getBean1(fieldType);
            //将实例对象注入到该字段中
            field.set(instance, fieldBean);
        }

        return instance;
    }
}
