package Map类测试;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @CreateTime: 2024年 12月 27日 10:39
 * @Description:
 * @Author: MR.YU
 */
public class RedBlackTree<K extends Comparable<? super K>> {
    /**
     * 红黑树定义：
     * 1.节点是红色或黑色
     * 2.根是黑色
     * 3.叶子节点（外部节点，空节点）都是黑色，这里的叶子节点指的是最底层的空节点（外部节点），下图中的那些null节点才是叶子节点，null节点的父节点在红黑树里不将其看作叶子节点
     *         红色节点的子节点都是黑色
     *         红色节点的父节点都是黑色
     * 4.从根节点到叶子节点的所有路径上不能有 2 个连续的红色节点
     * 5.从任一节点到叶子节点的所有路径都包含相同数目的黑色节点
     */

    private Node<K> root;


    private static final int BLACK = 1;    // BLACK must be 1
    private static final int RED   = 0;

    RedBlackTree(){}

    public void insert(K element) {
        Node<K> node = new Node<>(element);
        // 新节点默认为红色
        node.color = RED;
        insertTree(node);
    }

    private void insertTree(Node<K> node) {
        if (root == null) {
            node.color = BLACK;
            root = node;
        } else {
            Node<K> parent = null;
            Node<K> x = root;
            while(x != null) {
                parent = x;
                //cmp > 0 说明node.key 大于x.key 需要到x的右子树查找
                //cmp = 0 说明node.key 等于 x.key 需要进行value的替换操作
                //cmp < 0 说明node.key 小于 x.key 需要到x的左子树查找
                int cmp = node.element.compareTo(x.element);
                if (cmp < 0) {
                    x = x.left;
                } else if (cmp == 0) {
                    // 插入的节点已经存在 todo 更新操作
                    return;
                } else {
                    x = x.right;
                }
            }
            node.parent = parent;
            int cmp = node.element.compareTo(parent.element);
            if (cmp < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            // todo 节点插入完成之后，可能会破坏红黑树的平衡性，需要调用修复红黑树平衡的方法
            fixUp(node);
        }
    }

    /**
     * 修复红黑树平衡:
     *      1.插入的节点父节点为黑色，则不需要修复（4中情况）
     *      2.插入的节点父节点为红色，则需要修复（8种情况）
     */
    private void fixUp(Node<K> node) {
        Node<K> parent = parentOf(node);
        if (isRed(parent)) {
            Node<K> grandParent = parentOf(parent);
            Node<K> uncle = uncleOf(grandParent,parent);

            if (isRed(uncle)) {
                // 叔父节点为红色（4种情况）
                setBlack(parent);
                setBlack(uncle);
                setRed(grandParent);
                fixUp(grandParent);

            } else {
                // 叔父节点不为红色则叔父节点为空（四种情况）
                // 1.LL情况（父节点为红色且为组父节点的左节点插入的节点是父节点的左节点）.
                if (parent == grandParent.left && node == parent.left) {
                    parent.color = BLACK;
                    grandParent.color = RED;
                    rightRotate(grandParent,parentOf(grandParent));
                } else if (parent == grandParent.right && node == parent.right) {
                    // 2.RR(父节点为红色且为组父节点的右节点插入的节点是父节点的右节点)
                    parent.color = BLACK;
                    grandParent.color = RED;
                    leftRotate(grandParent,parentOf(grandParent));
                } else if (parent == grandParent.left && node == parent.right) {
                    // LR
                    node.color = BLACK;
                    grandParent.color = RED;
                    leftRotate(parent,parentOf(parent));
                    rightRotate(grandParent,parentOf(grandParent));
                } else if (parent == grandParent.right && node == parent.left) {
                    // RL
                    node.color = BLACK;
                    grandParent.color = RED;
                    rightRotate(parent,parentOf(parent));
                    leftRotate(grandParent,parentOf(grandParent));
                }


            }
        }
    }

    private void setBlack(Node<K> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    private void setRed(Node<K> node) {
        if (node.parent != null) {
            node.color = RED;
        }
    }

    /**
     * TODO
     *  右旋方法: 右旋是节点旋转为其左孩子的右孩子
     *  右旋示意图：右旋y节点
     *      p                       p
     *      |                       |
     *      y                       x
     *     / \          ---->      / \
     *    x   ry                  lx  y
     *   / \                         / \
     *  lx  rx                      rx  ry
     *
     * TODO
     *  右旋都做了几件事？
     *  1.将x的右子节点 赋值 给了 y 的左子节点，并且更新x的右子节点(如果ly存在)的父节点为 y
     *  2.将y的父节点（不为空时）的 子节点 指向x，更新x的父节点为y的父节点
     *  3.将x的右子节点指向y，更新y的父节点为x
     */
    private void rightRotate(Node<K> node,Node<K> parent) {
        Node<K> left = node.left;
        setParentChild(parent,node,left);

        node.left = left.right;
        left.right = node;
        node.parent = left;
        left.parent = parent;
    }

    /**
     * TODO
     *  左旋方法:左旋是节点旋转为其右孩子的左孩子
     *  左旋示意图：左旋x节点
     *     p                   p
     *     |                   |
     *     x                   y
     *    / \         ---->   / \
     *   lx  y               x   ry
     *      / \             / \
     *     ly  ry          lx  ly
     *
     *TODO
     *  左旋做了几件事？
     *  1. 将x的右子节点指向y的左子节点(ly)，并将y的左子节点(如果ly存在)的父节点更新为x
     *  2. 当x的父节点不为空时，跟新y的父节点为x的父节点，并将x的父节点 指定的子树（当前x的子树位置） 指定为y
     *  3. 将x的父节点更新为y，将y的左子节点更新为x
     *
     */
    private void leftRotate(Node<K> node,Node<K> parent) {
        Node<K> right = node.right;
        setParentChild(parent,node,right);
        node.right = right.left;
        right.left = node;
        node.parent = right;
        right.parent = parent;
    }

    private void setParentChild(Node<K> parent, Node<K> node, Node<K> child) {
        if (parent == null) {
            // 此时node是根节点
            node.parent = child;
            child.parent = null;
            root = child;
        } else if (parent.right != null) {
            int flag = parent.right.element.compareTo(node.element);
            if (flag == 0) {
                parent.right = child;
            } else {
                parent.left = child;
            }
        } else {
            parent.left = child;
        }
    }

    private boolean isRed(Node<K> node) {
        if (node != null) {
            return node.color == RED;
        }
        return false;
    }

    private Node<K> uncleOf(Node<K> grandParent, Node<K> parent) {
        if (parent == grandParent.left) {
            return grandParent.right;
        } else {
            return grandParent.left;
        }
    }

    private Node<K> parentOf(Node<K> node) {
        if (node == null) {
            return null;
        }
        return node.parent;
    }

    // 打印红黑树
    public void printTree() {
        if (root == null) {
            System.out.println("EMPTY!");
            return;
        }
        Node<K> n = root;
        showTree(n);
    }

    private void showTree(Node<K> n) {
        List<List<K>> result = new ArrayList<>();
        Queue<Node<K>> queue = new LinkedList<>();
        queue.offer(n);
        while (!queue.isEmpty()) {
            int size = queue.size();

            List<K> level = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Node<K> node = queue.poll();
                if (node == null) {
                    throw new RuntimeException("Unexpected condition");
                }
                level.add(node.element);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(level);
        }
        System.out.println(result);
    }


    private static class Node<K extends Comparable<? super K>> {
        private final K element; // 节点元素
        private Node<K> left; // 左子节点
        private Node<K> right; // 右子节点
        private int color;
        private Node<K> parent;

        Node(K element) {
            this(element,null,null);
        }
        Node(K element,Node<K> left,Node<K> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.color = RedBlackTree.BLACK;
        }
    }
}
