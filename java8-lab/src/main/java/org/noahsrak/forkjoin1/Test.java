package org.noahsrak.forkjoin1;

public class Test {
    public static void main(String[] args) {

        final int DONE_MASK = 0xf0000000;  // mask out non-completion bits
        final int NORMAL = 0xf0000000;  // must be negative
        final int CANCELLED = 0xc0000000;  // must be < NORMAL
        final int EXCEPTIONAL = 0x80000000;  // must be < CANCELLED
        final int SIGNAL = 0x00010000;  // must be >= 1 << 16
        final int SMASK = 0x0000ffff;  // short bits for tags
        final int NEGATIVE = 0xffffffff;

        System.out.println("DONE_MASK = " + DONE_MASK);
        System.out.println("NORMAL = " + NORMAL);
        System.out.println("CANCELLED = " + CANCELLED);
        System.out.println("EXCEPTIONAL = " + EXCEPTIONAL);
        System.out.println("SIGNAL = " + SIGNAL);
        System.out.println("SMASK = " + SMASK);
        System.out.println("NEGATIVE = " + NEGATIVE);

        System.out.println("CANCELLED < NORMAL = " + (CANCELLED < NORMAL));



    }
}
