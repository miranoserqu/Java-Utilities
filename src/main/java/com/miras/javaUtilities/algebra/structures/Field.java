package com.miras.javaUtilities.algebra.structures;

public abstract class Field<A> extends Ring<A> {

    public abstract A ln(A one);
    public abstract A log(A one);
    public abstract A exp(A one);
    public abstract A expNum(A one, double base);
    public abstract A expGen(A one, double exponent);
    public abstract A sin(A one);
    public abstract A cos(A one);
    public abstract A arcsin(A one);
    public abstract A arccos(A one);
    public abstract A tan(A one);
    public abstract A arctan(A one);
    public abstract A sec(A one);
    public abstract A cosec(A one);
    public abstract A cotan(A one);
    public abstract A sinh(A one);
    public abstract A cosh(A one);
    public abstract A arcsinh(A one);
    public abstract A arccosh(A one);
    public abstract A tanh(A one);
    public abstract A cotanh(A one);
    public abstract A arctanh(A one);
    public abstract A sech(A one);
    public abstract A cosech(A one);
    public abstract A arcsech(A one);
    public abstract A arccosech(A one);
    
}
