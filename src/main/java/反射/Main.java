package 反射;

import java.lang.reflect.Modifier;

/**
 * @CreateTime: 2025年 08月 19日 15:00
 * @Description:
 * @Author: MR.YU
 */
public class Main {
    public static void main(String[] args) {
        Reader.Job job = new Reader.Job();
        Reader reader = new Reader();
        try {
            Class<?> readerClass = Class.forName("反射.Reader");
            // 获取 Reader 类中定义的所有内部类
            Class<?>[] declaredClasses = readerClass.getDeclaredClasses();

            // 查找名为 Job 的内部类
            for (Class<?> cls : declaredClasses) {
                if (cls.getSimpleName().equals("Job")) {
                    System.out.println("找到了 Job 类: " + cls.getName());

                    Object o = cls.newInstance();
                    // 可以进一步检查是否是静态内部类
                    if (Modifier.isStatic(cls.getModifiers())) {
                        System.out.println("Job 是一个静态内部类");
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
