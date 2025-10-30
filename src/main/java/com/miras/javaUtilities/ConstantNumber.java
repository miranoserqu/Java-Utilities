package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.function.Function;

public class ConstantNumber<S extends Number> {

    private static abstract class ElementalFunctionInstance implements ElementalFunction<ElementalFunctionInstance>, Comparable<ElementalFunctionInstance> {
        @Override
        public int compareTo(ElementalFunctionInstance other){
            return 0;
        }

        @Override
        public ElementalFunctionInstance clone(){
            return null;
        }

        @Override
        public FunctionTree getTree() {
            return new FunctionTree(this);
        }
    }

    public final Function<S, ElementalFunction<?>> CONSTANTNUMBER = (value) -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return value.toString();
        }

        @Override
        public String getLaTex() {
            return String.valueOf(value.intValue());
        }

        @Override
        public FunctionTree getDerivative() {
            return ElementalFunctions.ZERO.get().getTree();
        }

        @Override
        public <R extends Field<R>> R apply(R... values) {
            return null;
        }

        @Override
        public double apply(double... values) {
            return value.doubleValue();
        }

        @Override
        public int apply(int... values) {
            return value.intValue();
        }

        @Override
        public float apply(float... values) {
            return value.floatValue();
        }
    };

}
