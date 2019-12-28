package org.noahsrak.jvm;

public class Pecs {

    public static void main(String[] args) {

    }

    public static void extendsTest() {
        Plate<? extends Fruit> p = new Plate<Apple>(new Apple());

        // p.setItem(new Fruit());
        // p.setItem(new Apple());

        Fruit fruit = p.getItem();
        Food food = p.getItem();
        // Apple apple = p.getItem();
    }

    public static void superTest() {
        Plate<? super  Fruit> p = new Plate<Food>(new Food());

        p.setItem(new Fruit());
        p.setItem(new Apple());
        p.setItem(new RedApple());

        Object o = p.getItem();
    }

    static class Plate<T> {
        private T item;

        public Plate() {
        }

        public Plate(T item) {
            this.item = item;
        }

        public void setItem(T item) {
            this.item = item;
        }

        public T getItem() {
            return this.item;
        }
    }

    static class Food {
    }

    static class Fruit extends Food {
    }

    static class Meat extends Food {
    }

    static class Apple extends Fruit {
    }

    static class Banana extends Fruit {
    }

    static class Pork extends Meat {
    }

    static class Beef extends Meat {
    }

    static class RedApple extends Apple {
    }

    static class YellowApple extends Apple {
    }

}


