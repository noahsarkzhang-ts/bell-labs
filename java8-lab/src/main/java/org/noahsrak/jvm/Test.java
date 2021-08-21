package org.noahsrak.jvm;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/10
 */
public class Test {

    public int foo(int a) {
        Object obj = new Object();
        while (a > 0) {
            a--;
        }
        return a;
    }

    public static void main(String[] args) {
        new Test().foo(10);
    }
}
