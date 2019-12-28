package org.noahsrak.jvm;

public class TypeInferenceTest {

    static abstract class ReferencePipeline<P_IN, P_OUT> implements Stream<P_OUT> {

        public abstract P_OUT accept(P_IN parmams, P_OUT out);
    }

    static class Head<P_IN, P_OUT> extends ReferencePipeline<P_IN, P_OUT> {

        public Head() {
            super();
        }

        public P_OUT process(P_OUT out) {
            System.out.println("haha");
            System.out.println("P_IN of class:" + out.getClass().toGenericString());
            return null;
        }

        @Override
        public P_OUT accept(P_IN parmams, P_OUT out) {
            System.out.println("P_IN of class:" + parmams.getClass().toGenericString());

            return out;
        }
    }

    public static <T> Stream<T> stream() {

        Stream<T> result = new TypeInferenceTest.Head<>();

        return result;
    }

    public static void of() {
        Stream<Integer> stream = stream();
        ReferencePipeline<String, Integer> pipeline = (ReferencePipeline<String, Integer>)stream;

        pipeline.accept("fdsdf",14);

        stream.process(4);
    }

    public static void main(String[] args) {
        of();
    }
}
