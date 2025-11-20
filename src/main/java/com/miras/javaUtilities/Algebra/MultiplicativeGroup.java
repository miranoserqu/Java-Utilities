package com.miras.javaUtilities.Algebra;

public interface MultiplicativeGroup<E extends MultiplicativeGroup<E, T>, T> extends Group<E, T> {

    E mult(E other);
    E one();

    default E operate(E other){
        return mult(other);
    }

}
