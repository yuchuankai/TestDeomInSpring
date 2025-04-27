package Map类测试;

import java.util.HashMap;

/**
 * @CreateTime: 2024年 12月 27日 9:18
 * @Description:
 * @Author: MR.YU
 */
public class TestHashMap {

    public static void main(String[] args) {
        test2("4",16);
        System.out.println("----------");
        test2(2, 32);
    }

    private static void test1() {
        HashMap<String, String> map = new HashMap<>(10);
        map.put("1", "1");
        map.put("2", "2");
        map.put("3", "3");
        map.put("4", "4");
        map.put("5", "5");
        map.put("6", "6");
        map.put("7", "7");
        map.put("8", "8");
        map.put("9", "9");
        map.put("10", "10");
        System.out.println(map);
    }

    /**
     * 1.key值的hash计算：
     *      使用扰动函数 降低hash碰撞的概率，算法要高效因此使用位运算。
     * 2.key.hashCode()的范围在-2147483648到2147483647之间，也就是32位。
     * 3.数组长度-1正好相当于一个“地位掩码”，“与”运算后散列值的高位全部归，只保留低位值用作数组访问下标
     * 4. 位运算 “^” 运算符，两个数同时参与运算，运算结果为两个数位相异，则结果为1，相同则结果为0。
     *    “&” 运算符，两个数同时参与运算，运算结果为两个数位都相同，则结果为1，否则为0。
     *    “|” 运算符，两个数同时参与运算，运算结果为两个数位存在1，则结果为1，否则为0。
     * 5.n 为2的幂次方 ，也代表hashmap的容量
     * 6.为什么选择右移16位：
     *      (hashCode >>> 16) 因为hashcode的值是32位数值，右移16位后，低16位变为了高16位，
     *      在此基础上做异或运算加大了低位的随机性。
     */
    private static void test2(Object key, int n) {
        int hashCode = key.hashCode();
        System.out.println("hashCode: " + hashCode);
        System.out.println(Integer.toBinaryString(hashCode));
        System.out.println("hashCode >>> 16: " + (hashCode >>> 16));
        System.out.println(Integer.toBinaryString(hashCode >>> 16));
        int hash = hashCode ^ (hashCode >>> 16);
        System.out.println("hash: " + hash);
        System.out.println(Integer.toBinaryString(hash));

        System.out.println("n: " + n );
        System.out.println(Integer.toBinaryString(n));
        System.out.println("n - 1: " + (n - 1));
        System.out.println(Integer.toBinaryString(n - 1));
        System.out.println("hash & (n - 1) : " + (hash & (n - 1)));
        System.out.println(Integer.toBinaryString(hash & (n - 1)));
    }

    /**
     * 1.为什么用红黑树而不用平衡二叉树：
     *      平衡二叉树插入和删除的时间复杂度都是O(logN)，但是平衡二叉树会出现一种极端的情况，那就是插入的数据是有序的那么所有的节点会在根节点的一侧，测试二叉树进变成了一个链表，这样查找的时间复杂度就变成了O(N)。
     *      而红黑树的时间复杂度是O(logN)，但是红黑树在插入和删除的时候会进行旋转，旋转之后树的结构会重新调整（红黑树可以保证最长路径不会查过最短路径的二倍），保证树的平衡。
     */
    private static void test3() {
    }
}
