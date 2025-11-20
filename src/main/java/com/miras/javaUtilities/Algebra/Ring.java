package com.miras.javaUtilities.Algebra;

public interface Ring<E extends Ring<E, T>, T> extends AdditiveGroup<E, T>, MultiplicativeGroup<E, T> {

    @Override
    default E operate(E other) {
        return AdditiveGroup.super.operate(other);
    }

}
