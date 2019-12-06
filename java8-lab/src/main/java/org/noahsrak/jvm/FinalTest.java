package org.noahsrak.jvm;

public class FinalTest {

    public static void main(String[] args) {
        System.out.println("x = " + testFinal());
    }

    public static int testFinal() {
        int x;

        try {
            x = 1 / 0;

            //throw new NullPointerException();

            return x;

        } catch (Exception ex) {
            x = 2;

            return x;

        } finally {
            x = 3;

            System.out.println("finally block: x = " + x);
        }

    }
}
