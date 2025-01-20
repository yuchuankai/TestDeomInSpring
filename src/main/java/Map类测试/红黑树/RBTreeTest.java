package Map类测试.红黑树;

import java.util.Scanner;

/**
 * @CreateTime: 2024年 12月 27日 13:43
 * @Description:
 * @Author: MR.YU
 */
public class RBTreeTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RBTree<Integer, Object> rbt = new RBTree();
        while (true){
            System.out.println("请输入key");
            int key = scanner.nextInt();
            System.out.println();
            rbt.insert(key, null);
            TreeOperation.show(rbt.getRoot());
            rbt.inOrderPrint();
        }
    }
}
