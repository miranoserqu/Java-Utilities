package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.function.Function;

public class SumSeries implements Series<SumSeries> {

    Function<Integer, FunctionTree> generalTerm;

    public SumSeries(Function<Integer, FunctionTree> generalTerm){
        this.generalTerm = generalTerm;
    }

    @Override
    public Series<SumSeries> getSumDerivative() {
        return null;
    }

    @Override
    public double compute(double precision) {
        return 0;
    }

    @Override
    public <R extends Field<R>> R computeField(double precision) {
        return null;
    }

    @Override
    public FunctionTree getDerivative() {
        return null;
    }

    @Override
    public <R extends Field<R>> R apply(R... values) {
        return null;
    }

    @Override
    public double apply(double... values) {
        return 0;
    }

    @Override
    public int apply(int... values) {
        return 0;
    }

    @Override
    public float apply(float... values) {
        return 0;
    }

    @Override
    public SumSeries sum(SumSeries other) {
        return new SumSeries((term) -> );
    }

    @Override
    public SumSeries dif(SumSeries other) {
        return null;
    }

    @Override
    public SumSeries mult(SumSeries other) {
        return null;
    }

    @Override
    public SumSeries div(SumSeries other) {
        return null;
    }

    @Override
    public SumSeries sqrt() {
        return null;
    }

    @Override
    public SumSeries op() {
        return null;
    }

    @Override
    public SumSeries inv() {
        return null;
    }

    @Override
    public SumSeries one() {
        return null;
    }

    @Override
    public SumSeries zero() {
        return null;
    }

    @Override
    public SumSeries clone() {
        return null;
    }

    @Override
    public SumSeries abs() {
        return null;
    }

    @Override
    public SumSeries scale(double factor) {
        return null;
    }

    @Override
    public boolean isEqualTo(SumSeries other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(SumSeries other) {
        return false;
    }

    @Override
    public boolean isLessThan(SumSeries other) {
        return false;
    }

    @Override
    public int compareTo(SumSeries o) {
        return 0;
    }
}
