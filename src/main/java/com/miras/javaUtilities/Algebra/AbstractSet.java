package com.miras.javaUtilities.Algebra;

public interface AbstractSet<E extends AbstractSet<E, T>, T> {

    boolean isIn(T object);

}
