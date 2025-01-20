package Map类测试.红黑树;
/**
 * ClassName： RBTree
 * Description：
 * date：2021/2/3 16:36
 * TODO
 *  1. 创建RBTree，定义颜色
 *  2. 创建RBNode
 *  3. 辅助方法定义：parentOf(node), isRed(node), isBlack(node), setRed(node), setBlack(node), inOrderPrint()
 *  4. 左旋方法定义：leftRotate(node)
 *  5. 右旋方法定义：rightRotate(node)
 *  6. 公开插入接口方法定义：insert(K key, V value);
 *  7. 内部插入接口方法定义 insert(RBNode node);
 *  8. 修正插入导致红黑树失衡的方法定义：insertFixUp(RBNode node);
 *  9. 测试红黑树正确性
 */

public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /*定义树根*/
    private RBNode root;

    public RBNode getRoot() {
        return root;
    }

    /**
     * 获取当前节点的父节点
     * @param node
     */
    private RBNode parentOf(RBNode node){
        if(node != null){
            return node.parent;
        }
        return null;
    }

    /**
     * 根据 父节点 和 祖父节点 获取 叔叔节点
     * @param parentNode 父节点
     * @param gparentNode 祖父节点
     * @return
     */
    private RBNode uncleOf(RBNode parentNode, RBNode gparentNode){
        RBNode uncle = null;
        if(parentNode == gparentNode.getLeft())
        {
            uncle = gparentNode.getRight();
        }else{
            uncle = gparentNode.getLeft();
        }
        return uncle;
    }

    /**
     * 判断节点是否为红色
     * @param node
     * @return
     */
    private boolean isRed(RBNode node){
        if (node != null){
            return node.color == RED;
        }
        return false;
    }

    /**
     * 判断节点是否为黑色
     * @param node
     * @return
     */
    private boolean isBlack(RBNode node){
        if (node!=null){
            return node.color == BLACK;
        }
        return false;
    }

    /**
     * 设置节点颜色为红色
     * @param node
     */
    private void setRed(RBNode node){
        if (node != null){
            node.color = RED;
        }
    }

    /**
     * 设置节点颜色为黑色
     * @param node
     */
    private void setBlack(RBNode node){
        if (node != null){
            node.color = BLACK;
        }
    }

    /**
     * 中序打印二叉树
     */
    public void inOrderPrint(){
        this.inOrderPrint(this.root);
    }
    private void inOrderPrint(RBNode node){
        if(node != null){
            inOrderPrint(node.left);
            System.out.println("key:"+node.key+"----"+"value:"+node.value);
            inOrderPrint(node.right);
        }
    }

    /**
     * 插入节点的公共方法
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        RBNode node = new RBNode();
        node.setKey(key);
        node.setValue(value);
        //TODO 新节点一定是红色
        node.setColor(RED);
        insert(node);
    }

    /**
     * * TODO
     *      *  # 红黑树插入节点情景分析：
     *      *  ** 情景1：红黑树为空树
     *      *  	最简单的一种情景，直接把插入节点作为根节点就行
     *      *  	注意：根据红黑树性质2：根节点是黑色。还需要把插入节点设为黑色
     *      *  ** 情景2：插入节点的key已经存在
     *      *  	处理：更新当前节点的值，为插入节点的值
     *      *  ** 情景3：插入节点的父节点为黑色节点
     *      *  	由于插入的节点是红色的，当插入节点的父节点是黑色时，并不会影响红黑树的平衡
     *      *  	直接插入即可，无需做自平衡
     * @param node
     */
    private void insert(RBNode node) {
        //第一步：查找当前node的父节点
        RBNode parent = null;
        RBNode x = this.root;
        while (x != null) {
            parent = x;
            //cmp > 0 说明node.key 大于x.key 需要到x的右子树查找
            //cmp = 0 说明node.key 等于 x.key 需要进行value的替换操作
            //cmp < 0 说明node.key 小于 x.key 需要到x的左子树查找
            int cmp = node.key.compareTo(x.key);
            if (cmp > 0) {
                x = x.getRight();
            }else if(cmp==0){
                //插入情景2：插入的节点已经存在，更新其value的值
                x.setValue(node.getValue());
                return;
            }else{
                x = x.getLeft();
            }
        }
        //TODO while循环结束之后 parent即为找到的待插入位置的父节点
        //进一步将node节点插入到树中
        node.parent = parent;
        if(parent!=null){
            int flag = node.getKey().compareTo(parent.getKey());
            if (flag<0) parent.setLeft(node);
            else parent.setRight(node);
        }else{
            this.root = node;
        }
        //TODO 节点插入完成之后，可能会破坏红黑树的平衡性，需要调用修复红黑树平衡的方法
        //insertFixUp();
        insertFixUp(node);
    }

    /**
     *TODO
     *  ** 情景4：插入节点的父节点为红色
     *  	[-] * 插入情景4.1：叔叔节点存在并且为红节点（叔--父 双红）
     *  		处理：（**P为父节点，U为叔叔节点，PP为祖父节点**）
     *  			1. 将P和U节点改为黑色
     *  			2. 将PP改为红色
     *  			3. 将PP设置为当前节点，进行后续处理
     *                 {可以看出，我们把PP节点设为红色了，如果PP的父节点是黑色，那么无需再做任何处理}
     *         {但如果PP的父节点是红色，则违反红黑树性质了。所以需要将PP设置为当前节点，继续做插入操作自平衡处理，直到平衡为止}
     *  	[-] * 插入情景4.2：叔叔节点不存在或为黑节点，并且插入节点的父节点是祖父节点的左子节点
     *  	（注意** 单纯从插入前来看，叔叔节点非红即空{NIL节点}，否则的话破坏了红黑树性质5，此路径会比其他路径多一个黑色节点。）
     *  		[--] *插入情景4.2.1：新插入节点，为其父节点的左子节点(LL红色情况  LL双红)
     *  			处理：
     *  				1. 变颜色：将P设置为黑色，将PP设置为红色
     *  				2. 对PP节点进行右旋
     *  		[--] *插入场景4.2.2：新插入节点，为其父节点的右子节点（LR红色情况 LR双红）
     *  			处理：
     *  				1. 对P进行左旋 ==>得到LL双红情景
     *  				2. 将P设置为当前节点，得到LL红色情况（进而运用4.2.1的处理方式进一步处理）
     *  	[-] * 插入情景4.3 叔叔节点不存在或为黑色节点，并且插入节点的父亲节点是祖父节点的右子节点
     *  		[--] *插入场景4.3.1：新插入节点，为其父节点的右子节点（RR红色情况）
     *  			处理：
     *  				1. 变颜色：将P设置为黑色，将PP设置为红色
     *  				2. 对PP节点进行左旋
     *  		[--] * 插入场景4.3.2：新插入节点，为其父节点的左子节点（RL红色情况）
     *  			处理：
     *  				1. 对P进行右旋 ==> 得到RR双红情况
     *  				2. 将P设置为当前节点，得到RR红色情况
     *  				3. 按照RR红色情况进一步进行处理（变色，左旋PP）
     *
     *
     *
     */
    private void insertFixUp(RBNode node) {
        //插入情景1：将根节点设置为  黑色
        this.root.setColor(BLACK);

        RBNode parent = parentOf(node);
        RBNode gparent = parentOf(parent);
        //插入情景4：插入节点的父节点是红色
        if (isRed(parent)){
            //获取叔叔节点
            RBNode uncle = uncleOf(parent, gparent);
            //情景4.1 叔叔节点是红色
            // 叔父节点双红情况：将叔父节点都设置为黑色，爷爷节点设置为红色，将爷爷节点设置为当前节点，进一步调整
            if (isRed(uncle)){
                setBlack(parent);
                setBlack(uncle);
                setRed(gparent);
                insertFixUp(gparent);
            }
            //情景4.2/4.3 如果叔叔节点不是红色，那么叔叔节点一定为空节点 Nil（黑色）
            else{
                //情景4.2:叔叔节点一定为空节点 Nil（黑色）,且父亲节点是爷爷节点的左子节点
                if (parent == gparent.getLeft()){
                    //情景4.2.1 当前节点是父节点的左子节点  即LL双红情况
                    if (node == parent.getLeft()){
                        //父节点设置为黑色， 爷爷节点设置为红色，以爷爷节点为当前节点，进行右旋
                        setBlack(parent);
                        setRed(gparent);
                        rightRotate(gparent);
                    }
                    //情景4.2.2 当前节点是父节点的右子节点  即LR双红情况
                    else{
                        //以父节点为当前节点进行左旋后，得到LL双红情况。
                        // 以旋转后的父亲节点所在节点为当前节点进一步进行处理
                        leftRotate(parent);
                        insertFixUp(parent);
                    }
                }
                //情景4.3：叔叔节点一定为空节点 Nil（黑色）,且父亲节点是爷爷节点的右子节点
                else{
                    //情景4.3.1： 当前节点是父亲节点的右子节点  RR双红情况
                    if (node == parent.getRight()){
                        //父节点设置为黑色，爷爷节点设置为红色，以爷爷节点为当前节点，进行左旋
                        setBlack(parent);
                        setRed(gparent);
                        leftRotate(gparent);
                    }
                    //情景4.3.2： 当前节点是父节点的左子节点  RL双红情况
                    else{
                        //以父节点为当前节点进行右旋后， 得到RR双红情况
                        //以旋转后的父亲节点所在节点为当前节点进一步进行处理
                        rightRotate(parent);
                        insertFixUp(parent);
                    }
                }
            }
        }
    }


    /**
     * TODO
     *  左旋方法
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
    private void leftRotate(RBNode x){
        RBNode y = x.getRight();
        RBNode p = x.getParent();
        RBNode ly = y.getLeft();
        //1.2将x的右子节点指向y的左子节点ly
        x.setRight(ly);
        if (ly != null){
            //1.1将y的左子节点的父节点更新为x
            ly.setParent(x);
        }
        //2.1 当x的父节点不为空时，更新y的父节点为x的父节点
        if (p!=null){
            y.setParent(p);
            //TODO 2.2 将x的父节点 指定的子树（当前x的子树的位置） 指定为y
            //x为p的左子节点
            if(p.getLeft() == x){
                p.setLeft(y);
            }
            //x为p的右子节点
            else{
                p.setRight(y);
            }
        }
        // TODO 如果当前x的父节点为空时，此时将y设置为  树根root
        else{
            this.root = y;
            this.root.parent = null;
        }
        //3.1 将x的父节点更新为y
        x.setParent(y);
        //3.2 将y的左子节点更新为x
        y.setLeft(x);
    }

    /**
     * TODO
     *  右旋方法
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
    private void rightRotate(RBNode y) {
        RBNode x = y.getLeft();
        RBNode rx = x.getRight();
        RBNode p = y.getParent();
        //1.1 将x的右子节点ly赋值给y的左子节点
        y.setLeft(rx);
        //1.2 更新x的右子节点（如果ly存在）的父节点为y
        if (rx != null) {
            rx.setParent(y);
        }
        //2.1 如果y的父节点p不为空，将y的父节点p的子节点指向x
        if (p != null) {
            //2.2更新x的父节点为y的父节点p
            x.setParent(p);
            // 将y的父节点的 原先指向y的子树  指向为x
            if(y == p.getLeft()){
                p.setLeft(x);
            }else{
                p.setRight(x);
            }
        }
        //TODO 如果y的父节点p为空，则将x设置为根节点root
        else{
            this.root = x;
            this.root.parent = null;
        }

        //3.1 将y的父节点更新为x
        y.setParent(x);
        //3.2 将x的右子节点更新为y
        x.setRight(y);
    }


    /**
     * 静态内部类，红黑树中的节点
     * @param <K>
     * @param <V>
     */
    static class RBNode<K extends Comparable<K>, V>{
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K key;
        private V value;

        public RBNode(){

        }

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}

