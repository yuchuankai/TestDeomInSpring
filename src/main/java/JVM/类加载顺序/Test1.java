package JVM.类加载顺序;

/**
 * @CreateTime: 2025年 01月 07日 16:02
 * @Description:
 * @Author: MR.YU
 */
public class Test1 {

    public static void main(String[] args) {
        /**
         * 变量没有被final修饰则先执行顶级父类的静态代码块然后是子类的静态代码块，直至执行到变量所在的类不在往下执行子类的静态代码块，被final修饰的变量直接输出变量值。
         */
        System.out.println(Son.FatherName);
    }
}


class YeYe {
    static {
        System.out.println("YeYe --------------");
    }
}

class Father extends YeYe {

    public final static String FatherName = "Father";
    static {
        System.out.println("Father -----------");
    }
}

class Son extends Father {

    public final static String SonName = "SonName";
    static {
        System.out.println("Son ----------------");
    }
}
