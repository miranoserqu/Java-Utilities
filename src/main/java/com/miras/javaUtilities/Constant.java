package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.function.Function;

public class Constant<S extends Field<S>> {

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
        public FunctionTree getTree(){
            return new FunctionTree(this);
        }
    }

    public final Function<S, ElementalFunction> CONSTANT = (value) -> new ElementalFunctionInstance(){

        @Override
        public String getOperator() {
            return value.toString();
        }

        @Override
        public String getLaTex() {
            return value.toString();
        }

        @Override
        public FunctionTree getDerivative() {
            return ElementalFunctions.ZERO.get().getTree();
        }

        @Override
        public <R extends Field<R>> R apply(R... values) {
            return (R) value;
        }

        @Override
        public double apply(double... values) {
            return 0;
        }

        @Override
        public int apply(int... values) {
            return 0;
        }

        @Override
        public float apply(float... values) {
            return 0;
        }
    };

}
