package org.noahsrak.jvm;

/**
 * @author: noahsark
 * @version:
 * @date: 2021/6/23
 */
public class GenericityTest {

    public static void main(String[] args) {
        Plate<? extends Fruit> plate = new Plate<Apple>(new Apple());

    }


    public static class Fruit {
    }

    public static class Apple extends Fruit {
    }

    public static class Plate<T> {
        private T item;

        public Plate(T item) {
            this.item = item;
        }

        public T getItem() {
            return item;
        }

        public void setItem(T item) {
            this.item = item;
        }
    }
}
