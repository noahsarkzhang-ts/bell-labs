package org.noahsrak.jvm;

/**
 * @author: noahsark
 * @version:
 * @date: 2020/8/10
 */
public class DisAssemblyTest {

    public void invoke() {
        String name = "test";
        String newName = name.intern();
        System.out.println(name == newName);
    }

    public static void main(String[] args) {
        new DisAssemblyTest().invoke();
    }
}
