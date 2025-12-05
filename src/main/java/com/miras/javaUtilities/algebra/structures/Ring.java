package com.miras.javaUtilities.algebra.structures;

public abstract class Ring<A> extends Group<A> {

    public abstract A sum(A one, A other);
    public abstract A dif(A one, A other);
    public abstract A op(A one);
    public abstract A zero(A one);
    public abstract A mult(A one, A other);
    public abstract A div(A one, A other);
    public abstract A inv(A one);
    public abstract A one(A one);

    @Override
    public A operate(A one, A other){
        return this.sum(one, other);
    }

    @Override
    public A inverseOperate(A one, A other) {
        return this.dif(one, other);
    }

    @Override
    public A getInverse(A one) {
        return this.op(one);
    }

    @Override
    public A getNeutralObject(A one) {
        return this.zero(one);
    }

}
