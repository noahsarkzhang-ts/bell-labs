package org.noahsark.rbt;

public class RBTree<T extends Comparable<T>> {
    private RBNode<T> root; // 根结点
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree() {
    }

    public RBTree(RBNode<T> root) {
        this.root = root;
    }

    public RBNode<T> parentOf(RBNode<T> node) {
        return node != null ? node.parent : null;
    }

    public void setParent(RBNode<T> node, RBNode<T> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    public boolean colorOf(RBNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    public boolean isRed(RBNode<T> node) {
        return colorOf(node) == RED;
    }

    public boolean isBlack(RBNode<T> node) {
        return colorOf(node) == BLACK;
    }

    public void setRed(RBNode<T> node) {
        if (node != null) {
            node.color = RED;
        }
    }

    public void setBlack(RBNode<T> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    public void setColor(RBNode<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    /**
     * 前序编历
     */
    public void preOrder() {
        preOrder(this.root);
    }

    private void preOrder(RBNode<T> node) {

        if (node != null) {
            System.out.println(node.key + ",");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序
     */
    public void inOrder() {
        inOrder(this.root);
    }

    private void inOrder(RBNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.key + ",");
            inOrder(node.right);
        }
    }

    /**
     * 后序
     */
    public void postOrder() {
        postOrder(this.root);
    }

    private void postOrder(RBNode<T> node) {

        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.key + ",");
        }
    }

    public RBNode<T> search(T key) {
        return search(this.root, key);
    }

    private RBNode<T> search(RBNode<T> node, T key) {
        int cmp = 0;

        while (node != null) {
            cmp = key.compareTo(node.key);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return node;
    }

    /**
     * 最小值
     *
     * @return T
     */
    public T minValue() {
        RBNode<T> node = minNode(this.root);

        if (node != null) {
            return node.key;
        }

        return null;
    }

    private RBNode<T> minNode(RBNode<T> tree) {

        if (tree == null) {
            return null;
        }

        while (tree.left != null) {
            tree = tree.left;
        }

        return tree;
    }

    public T maxValue() {
        RBNode<T> node = maxNode(this.root);

        if (node != null) {
            return node.key;
        }

        return null;
    }

    private RBNode<T> maxNode(RBNode<T> tree) {

        if (tree == null) {
            return null;
        }

        while (tree.right != null) {
            tree = tree.right;
        }

        return tree;
    }

    /**
     * 寻找后继结点
     *
     * @param node 结点
     * @return 后继结点
     */
    public RBNode<T> successor(RBNode<T> node) {
        // 如果x有右子节点，那么后继节点为“以右子节点为根的子树的最小节点”
        if (node.right != null) {
            return minNode(node.right);
        }

        // 如果x没有右子节点，会出现以下两种情况：
        // 1. x是其父节点的左子节点，则x的后继节点为它的父节点
        // 2. x是其父节点的右子节点，则先查找x的父节点p，然后对p再次进行这两个条件的判断
        RBNode<T> parent = node.parent;
        while (parent != null && (node == parent.right)) {
            node = parent;
            parent = node.parent;
        }

        return parent;
    }

    /**
     * 寻找前驱结点
     *
     * @param node 结点
     * @return 前驱结点
     */
    public RBNode<T> predecessor(RBNode<T> node) {
        // 如果x有左子节点，那么前驱结点为“左子节点为根的子树的最大节点”
        if (node.left != null) {
            return maxNode(node.left);
        }

        // 如果x没有左子节点，会出现以下两种情况：
        // 1. x是其父节点的右子节点，则x的前驱节点是它的父节点
        // 2. x是其父节点的左子节点，则先查找x的父节点p，然后对p再次进行这两个条件的判断
        RBNode<T> parent = node.parent;
        while (parent != null && (node == parent.left)) {
            node = parent;
            parent = node.parent;
        }

        return parent;
    }


    /**
     * 插入结点
     *
     * @param key 键值
     */
    public void insert(T key) {
        RBNode<T> node = new RBNode<T>(RED, key, null, null, null);
        insert(node);
    }

    private void insert(RBNode<T> node) {
        RBNode<T> parent = null;
        RBNode<T> tmp = this.root;

        // 1.找到插入的位置
        while (tmp != null) {
            parent = tmp;

            if (node.key.compareTo(parent.key) < 0) {
                tmp = tmp.left;
            } else {
                tmp = tmp.right;
            }
        }

        node.parent = parent;

        // 2. 判断node是插在左子节点还是右子节点
        if (parent != null) {
            if (node.key.compareTo(parent.key) < 0) {
                parent.left = node;
            } else {
                parent.right = node;
            }
        } else {
            this.root = node;
        }

        //3. 将它重新修整为一颗红黑树
        insertFixUp(node);
    }

    /**
     * 调整红黑树
     *
     * @param node
     */
    private void insertFixUp(RBNode<T> node) {
        // 定义父节点和祖父节点
        RBNode<T> parent = null;
        RBNode<T> grandParent = null;
        RBNode<T> uncle = null;

        // 需要修整的条件：父节点存在，且父节点的颜色是红色
        while (((parent = parentOf(node)) != null) && isRed(parent)) {

            // 获得祖父节点
            grandParent = parentOf(parent);

            // 若父节点是祖父节点的左子节点，下面else与其相反
            if (parent == grandParent.left) {
                // 获得叔叔节点
                uncle = grandParent.right;

                // case1: 叔叔节点也是红色
                if (uncle != null && isRed(uncle)) {
                    // 把父节点和叔叔节点涂黑
                    setBlack(parent);
                    setBlack(uncle);

                    // 把祖父节点涂红
                    setRed(grandParent);

                    //将位置放到祖父节点处
                    node = grandParent;

                    continue;
                }

                // case2: 叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.right) {
                    // 从父节点处左旋
                    leftRotate(parent);
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // case3: 叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(grandParent);
                rightRotate(grandParent);
            } else { // 若父节点是祖父节点的右子节点,与上面的完全相反，本质一样的
                uncle = grandParent.left;

                // case1: 叔叔节点也是红色
                if (uncle != null && isRed(uncle)) {
                    // 把父节点和叔叔节点涂黑
                    setBlack(parent);
                    setBlack(uncle);

                    // 把祖父节点涂红
                    setRed(grandParent);

                    //将位置放到祖父节点处
                    node = grandParent;

                    continue;
                }

                // case2: 叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.left) {
                    // 从父节点处左旋
                    rightRotate(parent);
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // case3: 叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(grandParent);
                leftRotate(grandParent);
            }
        }

        //将根节点设置为黑色
        setBlack(this.root);
    }

    public void remove(T key) {
        RBNode<T> node;
        if ((node = search(root, key)) != null)
            remove(node);
    }


    /**
     *  删除分3种情况：
     *  1）没有子结点；
     *  2）只有一个子结点（左/右结点）；
     *  3）有两个子结点；
     *  1）2）两种情况删除的是结点本身，
     *  3）情况转换为删除其右子结点的后继结点（即右子树的最小结点），
     *  在这种情况下，其后继结点左子结点为空，右子结点可不为空。
     *
     * @param node
     */
    public void remove(RBNode<T> node) {
        RBNode<T> candidate = null;  // 表示删除的结点
        RBNode child = null;
        RBNode parent = null;
        boolean color;

        // 1. 寻找要删除的结点
        // 1.1 没有子结点或只有一个结点，删除结点本身
        if (node.left == null || node.right == null) {
            candidate = node;
        } else {  // 1.2 有两个子结点，则删除右子树最小结点
            candidate = node.right;

            while (candidate.left != null) {
                candidate = candidate.left;
            }
        }

        // 2. 赋值删除结点的父结点及子结点
        parent = candidate.parent;

        if (candidate.left != null) {
            child = candidate.left;
        } else {
            child = candidate.right;
        }

        // 3. 将子结点的父结点设置为删除结点的父结点
        if (child != null) {
            child.parent = parent;
        }

        // 4. 将父结点的子结点设置为删除结点的子结点
        if (parent == null) {
            this.root = child;
        } else {
            if (candidate == parent.left) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }

        // 5. 如果删除结点有两个子结点，删除其右子树最小结点之后，
        // 需要将其最小结点的值赋值给删除结点，从而达到删除结点的目的。
        if (candidate != node) {
            node.key = candidate.key;
        }

        // 如果删除结点是黑色，则需要修复红黑树。
        if (candidate.color == BLACK) {
            removeFixUp(child, parent);
        }
        node = null;

    }


    /**
     *  修复删除结点后的红黑树
     * @param node 调整的结点（删除结点的子结点）
     * @param parent 删除结点的父结点
     */
    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
        RBNode<T> sibling;

        while ((node == null || isBlack(node)) && (node != this.root)) {
            if (parent.left == node) { //node是左子节点，
                sibling = parent.right; //node的兄弟节点
                if (isRed(sibling)) { //case1: node的兄弟节点other是红色的
                    setBlack(sibling);
                    setRed(parent);
                    leftRotate(parent);
                    sibling = parent.right;
                }

                // case2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                if ((sibling.left == null || isBlack(sibling.left)) &&
                        (sibling.right == null || isBlack(sibling.right))) {
                    setRed(sibling);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    // case3: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点是黑色
                    if (sibling.right == null || isBlack(sibling.right)) {
                        setBlack(sibling.left);
                        setRed(sibling);
                        rightRotate(sibling);
                        sibling = parent.right;
                    }

                    // case4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点任意颜色
                    setColor(sibling, colorOf(parent));
                    setBlack(parent);
                    setBlack(sibling.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else { //与上面的对称
                sibling = parent.left;

                if (isRed(sibling)) {
                    // Case 1: node的兄弟other是红色的
                    setBlack(sibling);
                    setRed(parent);
                    rightRotate(parent);
                    sibling = parent.left;
                }

                if ((sibling.left == null || isBlack(sibling.left)) &&
                        (sibling.right == null || isBlack(sibling.right))) {
                    // Case 2: node的兄弟other是黑色，且other的俩个子节点都是黑色的
                    setRed(sibling);
                    node = parent;
                    parent = parentOf(node);
                } else {

                    if (sibling.left == null || isBlack(sibling.left)) {
                        // Case 3: node的兄弟other是黑色的，并且other的左子节点是红色，右子节点为黑色。
                        setBlack(sibling.right);
                        setRed(sibling);
                        leftRotate(sibling);
                        sibling = parent.left;
                    }

                    // Case 4: node的兄弟other是黑色的；并且other的左子节点是红色的，右子节点任意颜色
                    setColor(sibling, colorOf(parent));
                    setBlack(parent);
                    setBlack(sibling.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if (node != null)
            setBlack(node);
    }

    /*
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */
    private void leftRotate(RBNode<T> x) {

        RBNode<T> y = x.right;
        RBNode<T> parent = x.parent;

        // 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }

        // 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        y.parent = parent;
        if (parent == null) {
            this.root = y;
        } else {
            if (parent.left == x) {
                parent.left = y;
            } else {
                parent.right = y;
            }
        }

        // 3.将y的左子节点设为x，将x的父节点设为y
        y.left = x;
        x.parent = y;
    }


    /*
     * 右旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
    private void rightRotate(RBNode<T> y) {

        RBNode<T> x = y.left;
        RBNode<T> parent = y.parent;

        // 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
        y.left = x.right;
        if (x.right != null) {
            x.right.parent = y;
        }

        // 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
        x.parent = parent;
        if (parent == null) {
            this.root = x;
        } else {
            if (parent.left == y) {
                parent.left = x;
            } else {
                parent.right = x;
            }
        }

        // 3. 将x的右子节点设为y，将y的父节点设为x
        x.right = y;
        y.parent = x;
    }

    private class RBNode<T extends Comparable<T>> {
        boolean color; // 颜色
        T key; // 键值
        RBNode<T> left; // 左结点
        RBNode<T> right; // 右结点
        RBNode<T> parent; // 父结点

        public RBNode() {
        }

        public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "RBNode{" +
                    "color=" + color +
                    ", key=" + key +
                    ", left=" + left +
                    ", right=" + right +
                    ", parent=" + parent +
                    '}';
        }
    }

    public void clear() {
        destroy(root);
        root = null;
    }

    private void destroy(RBNode<T> tree) {
        if (tree == null)
            return;
        if (tree.left != null)
            destroy(tree.left);
        if (tree.right != null)
            destroy(tree.right);
        tree = null;
    }


    public void print() {
        if (root != null) {
            print(root, root.key, 0);
        }
    }

    /*
     * key---节点的键值
     * direction--- 0:表示该节点是根节点
     *              -1:表示该节点是它的父节点的左子节点
     *              1:表示该节点是它的父节点的右子节点
     */
    private void print(RBNode<T> tree, T key, int direction) {
        if (tree != null) {
            if (0 == direction)
                System.out.printf("%2d(B) is root\n", tree.key);
            else
                System.out.printf("%2d(%s) is %2d's %6s child\n",
                        tree.key, isRed(tree) ? "R" : "B", key, direction == 1 ? "right" : "left");
            print(tree.left, tree.key, -1);
            print(tree.right, tree.key, 1);
        }
    }

}
