package com.miras.javaUtilities.Algebra;

public interface AdditiveGroup<E extends AdditiveGroup<E, T>, T> extends Group<E, T> {

    E sum(E other);
    E zero();

    default E operate(E other){
        return sum(other);
    }

}
