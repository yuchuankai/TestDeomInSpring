package 重写equals;

/**
 * @CreateTime: 2025年 04月 27日 9:33
 * @Description:
 * @Author: MR.YU
 */
public class Main {

    /**
     * Java对象契约明确规定：
     *      如果两个对象根据equals()方法是相等的，那么它们的hashCode()必须返回相同的值
     *      反之则不一定成立（哈希码相同的对象不一定相等）
     * 违反这一契约会导致对象在使用基于哈希的集合类（如HashMap、HashSet）时出现不可预测的行为。
     * @param args
     */
    public static void main(String[] args) {
        Person person1 = new Person("张三");
        Person person2 = new Person("张三");
        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());
    }
}
