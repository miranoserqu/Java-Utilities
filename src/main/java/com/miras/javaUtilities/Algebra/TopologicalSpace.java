package com.miras.javaUtilities.Algebra;

public interface TopologicalSpace<E extends TopologicalSpace<E, T>, T> extends AbstractSet<E, T> {

    abstract boolean isOpen(Set<E> set);

}
