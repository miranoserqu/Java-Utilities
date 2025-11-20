package com.miras.javaUtilities.Algebra;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.geometry.partitioning.utilities.OrderedTuple;

import java.util.function.Function;

public class Set<E> implements AbstractSet<Set<E>, E> {

    private Function<E, Boolean> inclusionRule;

    public Set(Function<E, Boolean> inclusionRule){
        this.inclusionRule = inclusionRule;
    }

    @Override
    public boolean isIn(E object) {
        return this.inclusionRule.apply(object);
    }

    Set<E> intersection(Set<E> other){
        return new Set<>((object) -> this.inclusionRule.apply(object) && other.inclusionRule.apply(object));
    }

    Set<E> union(Set<E> other){
        return new Set<>((object) -> this.inclusionRule.apply(object) || other.inclusionRule.apply(object));
    }

    Set<E> getComplementary(){
        return new Set<>((object) -> !this.inclusionRule.apply(object));
    }

    <R> Set<Pair<E, R>> cartesianProduct(Set<R> other){
        return new Set<>(objects -> this.isIn(objects.getLeft()) && other.isIn(objects.getRight()));
    }

    <R, S> Set<Triple<E, R, S>> cartesianProduct(Set<R> other, Set<S> otherr){
        return new Set<>(objects -> this.isIn(objects.getLeft()) && other.isIn(objects.getMiddle()) && otherr.isIn(objects.getRight()));
    }

    Set<E[]> cartesianExponent(int exponent){
        return new Set<>(objects -> {
            for(E object : objects){
                if(!this.isIn(object)){
                    return false;
                }
            }
            return true;
        });
    }

}
