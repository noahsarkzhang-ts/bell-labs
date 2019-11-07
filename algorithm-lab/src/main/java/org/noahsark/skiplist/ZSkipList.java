package org.noahsark.skiplist;

import com.google.common.base.Preconditions;

import java.util.Random;
import java.util.Scanner;

public class ZSkipList<T extends Comparable<? super T>> {

    // 最大层数
    private static final int ZSKIPLIST_MAXLEVEL = 32;

    // P，概率
    private static final double ZSKIPLIST_P = 0.25;

    // 头结点
    private ZSkipListNode<T> header;

    // 节点总数
    private int length;

    // 层数
    private int level;

    // 随机数
    private Random random;


    public ZSkipList() {

        this.length = 0;
        this.level = 1;

        ZSkipListNode<T> nullNode = new ZSkipListNode<>();
        this.header = new ZSkipListNode<>(null, ZSKIPLIST_MAXLEVEL);

        random = new Random();
    }

    /**
     *  向跳跃表中插入结点
     * @param key 插入元素
     */
    public void insert(T key) {

        Preconditions.checkNotNull(key);

        // update存放插入结点每一层的前驱结点，
        ZSkipListNode<T>[] update = new ZSkipListNode[ZSKIPLIST_MAXLEVEL];

        // rank存放结点在每一层上的排名
        int [] rank = new int[ZSKIPLIST_MAXLEVEL];

        // 用于遍历插入结点的位置
        ZSkipListNode<T> creeper = header;

        /*
        *   1、找到每一个层次的前驱结点及每一个层次的排名。
         *  从最顶层开始遍历,从上往下查的插入结点的位置,如果在该层中结点大于插入结点的值,
         *  则移向该结点的下一层开始查找,同时记录插入结点的排名,用于计算插入结点与前一个
         *  结点之间的跨度(span).
         */
        for (int i = level - 1; i >= 0; i--) {

            // 用于插入结点在某一个层次的排名,在最高层排名默认值为0;
            // 非最高层默认值上一个层次的值,会在下面的循环中依次增加排名.
            rank[i] = i == level-1 ? 0 : rank[i+1];

            // 如果当前结点小于等于插入结点,则继续查找.
            while (creeper.levels[i].next != null && key.compareTo(creeper.levels[i].next.key) > 0) {

                // 排名数加上当前结点到一个结点之间的跨度
                rank[i] += creeper.levels[i].span;

                // 指向后继结点
                creeper = creeper.levels[i].next;
            }

            // 找到当前层次中后继结点大于等于插入结点的结点,作为插入结点的前驱结点
            update[i] = creeper;
        }

        // 2、随机生成插入结点的层次
        // 用冥次定律,生成一个随机的层次
        int newLevel = zslRandomLevel();

        // 如果生成的层次大于当前跳跃表的最大层次,则初始化大于的层次.
        if (newLevel > level) {
            for (int i = level ; i < newLevel ; i++) {
                rank[i] = 0;
                update[i] = header;
                update[i].levels[i].span = this.length;
            }
            this.level = newLevel;
        }

        // 新建插入的结点
        ZSkipListNode<T> node = new ZSkipListNode<>(key, level);

        /*
        *  3、插入结点及更新跨度
        *  1）在每一个层次中插入一个结点，该操作就是链表的插入操作，比较简单；
        *  2）更新产驱结点及当前结点的跨度。
        */
        for (int i = 0; i < newLevel; i++) {

            // 3.1 插入结点的后继结点指向前驱结点的后继结点；
            // 3.2 前驱结点的后继结点指向插入结点。
            node.levels[i].next = update[i].levels[i].next;
            update[i].levels[i].next = node;

            /* 插入一个新结点，等于在前驱结点与其后继结点之间插入一个结点，之前的
            *  span等于前驱结点与其后继结点之间的跨度，现在一分为二：前驱结点与插入结点的跨度和
            *  插入结点与后继结点的跨度。前一个值即为前驱结点的跨度，等于上下两个层次排名的差值，
            *  后一个值即为插入结点的跨度，等于前驱结点原先的span减去前一个值。
            */
            node.levels[i].span = update[i].levels[i].span - (rank[0] - rank[i]);
            update[i].levels[i].span = (rank[0] - rank[i]) + 1;
        }

        // 如果插入结点的层次小于当前最大的层数，则更新高层的spsn。
        for (int i = newLevel; i < this.level; i++) {
            update[i].levels[i].span++;
        }

        // 4. 结点数加1.
        this.length++;

        System.out.printf("\nnew node = %d, level = %d \n" , key, newLevel);
    }

    /**
     *  获取每一层的前驱结点
     * @param key
     * @param update
     */
    public void predecessorLevels(T key, ZSkipListNode<T>[] update) {

        ZSkipListNode<T> creeper = header;

        for (int i = level - 1; i >= 0; i--) {

            while (creeper.levels[i].next != null && key.compareTo(creeper.levels[i].next.key) > 0) {
                creeper = creeper.levels[i].next;
            }

            update[i] = creeper;
        }

    }

    /**
     *  删除一个已有结点
     * @param key
     */
    public void delete(T key) {

        // 1、找到key对应的结点
        Preconditions.checkNotNull(key);

        ZSkipListNode<T>[] update = new ZSkipListNode[ZSKIPLIST_MAXLEVEL];
        // 获取前驱结点
        predecessorLevels(key, update);

        // 第一层的后继结点有可能是删除的结点(有可能删除一个不存在的结点)
        ZSkipListNode<T> node = update[0].levels[0].next;

        // 判断是否是删除的结点
        if (key.compareTo(node.key) == 0) {
            for (int i = 0; i < this.level; i++) {
                if (update[i].levels[i].next == node) {

                    // 前驱结点的跨度等于之前的跨度再加上删除结点的跨度,再减去删除结点.
                    update[i].levels[i].span += node.levels[i].span -1;
                    update[i].levels[i].next = node.levels[i].next;
                } else {
                    update[i].levels[i].span -= 1;
                }
            }

            // 清理空的层
            while (this.level > 1 && this.header.levels[this.level-1].next == null) {
                this.level--;
            }

            // 结点数减一
            this.length--;
        }

    }

    public ZSkipListNode<T> search(T key) {
        ZSkipListNode<T> creeper;

        creeper = header;

        int curLevel = level -1;

        while (curLevel >= 0){

            while (creeper.levels[curLevel].next != null && key.compareTo(creeper.levels[curLevel].next.key) < 0) {
                creeper = creeper.levels[curLevel].next;
            }

            if (creeper.levels[curLevel].next != null && key.compareTo(creeper.levels[curLevel].next.key) == 0) {
                return creeper.levels[curLevel].next;
            } else {
                curLevel--;
            }
        }

        return null;
    }

    /* 按幂次定律生成小于32的随机数的函数
    * 宏 ZSKIPLIST_MAXLEVEL 的定义为32, 宏 ZSKIPLIST_P 被设定为 0.25
    * 即
    *  level == 1的概率为 75%
    *  level == 2的概率为 75% * 25%
    *  level == 3的概率为 75% * 25% * 25%
    *  ...
    *  level == 31的概率为 0.75 * 0.25^30
    *  而
    *  level == 32的概率为 0.75 * 0.25^31
    */
    private int zslRandomLevel() {
        int level = 1;

        while ((random.nextInt() & 0xFFFF) < (ZSKIPLIST_P * 0xFFFF)) {
            level += 1;
        }

        return (level < ZSKIPLIST_MAXLEVEL) ? level : ZSKIPLIST_MAXLEVEL;
    }

    public void print() {

        ZSkipListNode<T> creeper;
        creeper = header;
        int nodes = 0;
        int span = 0;
        int nextSpan = 0;

        for (int i = level -1; i >= 0; i--) {

            creeper = header.levels[i].next;
            span = header.levels[i].span;
            System.out.printf("head[%02d](%02d)", i ,span);

            while (creeper != null) {

                nextSpan = creeper.levels[i].span;
                for (int j = 0; j < span - 1; j++) {
                    System.out.printf("---------");
                }

                System.out.printf("-->%02d(%02d)", creeper.key,nextSpan);

                creeper = creeper.levels[i].next;
                nodes++;
                span = nextSpan;
            }

            System.out.printf( "\n" );
            nodes = 0;
        }
    }

    private static class ZSkipListNode<T extends Comparable<? super T>> {
        // 键值
        T key;

        // 层次
        ZSkipListLevel<T>[] levels;

        public ZSkipListNode() {
        }

        public ZSkipListNode(T key, int level) {
            this.key = key;
            this.levels = new ZSkipListLevel[level];

            ZSkipListLevel<T> listLevel = null;

            for (int i = 0 ; i < level ; i++) {
                listLevel = new ZSkipListLevel<>();
                this.levels[i] = listLevel;
            }

        }

    }

    /**
     *  层
     * @param <T> 模板
     */
    private static class ZSkipListLevel<T extends Comparable<? super T>> {

        // 下一个结点
        ZSkipListNode<T> next;

        // 跨度，当前结点与后继结点蹭相隔几个结点
        int span;

        public ZSkipListLevel() {
            this.next = null;
            this.span = 0;
        }
    }

    public static void main(String[] args) {

        ZSkipList<Integer> skipList = new ZSkipList<>();
        Random random = new Random();

        Integer [] elements = {12, 45, 63, 21, 99, 87, 23, 47, 30, 50};

        int length = random.nextInt(20) + 1;
        //System.out.println("length = " + length);

        Integer item = null;

        for (int i = 0; i < elements.length; i++) {

            skipList.insert(elements[i]);

            skipList.print();
        }
       //skipList.print();

        System.out.print("\n输入删除的元素：");
        Scanner sc = new Scanner(System.in);
        String exit = "exit";

        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                skipList.delete(sc.nextInt());
                skipList.print();
            } else if (sc.next().contentEquals(exit)) {
                System.out.println("退出!!!");
                break;
            }

            System.out.print("\n输入删除的元素：");
        }

    }

}
