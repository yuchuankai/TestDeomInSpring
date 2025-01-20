package Map类测试;

/**
 * @CreateTime: 2024年 12月 27日 14:10
 * @Description:
 * @Author: MR.YU
 */
public class RedBlackTreeTest {
    public static void main(String[] args) {
        RedBlackTree<Integer> tree = test1();
        test2(tree);

    }

    private static RedBlackTree<Integer> test1() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        return tree;
    }

    private static void test2(RedBlackTree<Integer> tree) {
        tree.printTree();
    }
}
