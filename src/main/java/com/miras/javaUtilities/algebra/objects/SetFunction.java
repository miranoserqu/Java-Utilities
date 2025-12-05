package com.miras.javaUtilities.algebra.objects;

import com.miras.javaUtilities.algebra.interfaces.AbstractSet;
import com.miras.javaUtilities.algebra.structures.Set;

public abstract class SetFunction<R extends AbstractSet<R, ?>, T extends AbstractSet<T, ?>> implements java.util.function.Function<R, T> {

    @Override
    public abstract T apply(R r);

    Set<R> getInverseImage(Set<T> set){
        return new Set<>((r) -> set.isIn(this.apply(r))){};
    }

    Set<T> getImage(Set<R> set){
        return null;
    }

}
