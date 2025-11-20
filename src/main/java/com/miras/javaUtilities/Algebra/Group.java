package com.miras.javaUtilities.Algebra;

public interface Group<E extends Group<E, T>, T> extends AbstractSet<E, T> {

    E operate(E other);

}
