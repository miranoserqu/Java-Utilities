package com.miras.javaUtilities.calculus.numerical;

import com.miras.javaUtilities.calculus.interfaces.Series;
import com.miras.javaUtilities.calculus.symbolic.NumberFunctionTree;

public class SumSeries implements Series<SumSeries> {

    NumberFunctionTree generalTerm;

    public SumSeries(NumberFunctionTree generalTerm){
        this.generalTerm = generalTerm;
    }

    @Override
    public Series<SumSeries> getSumDerivative() {
        return null;
    }

    @Override
    public double compute(double precision) {
        double result = 0;
        for(int i=1; i < precision; i++){
            result += this.generalTerm.apply(Double.valueOf(i));
        }
        return result;
    }

    @Override
    public NumberFunctionTree getDerivative() {
        return null;
    }

    @Override
    public double apply(double... values) {
        return compute(1000);
    }

    @Override
    public int apply(int... values) {
        return Double.valueOf(compute(1000)).intValue();
    }

    @Override
    public float apply(float... values) {
        return Double.valueOf(compute(1000)).floatValue();
    }

    @Override
    public String getOperator() {
        return "";
    }

    @Override
    public String getLaTex() {
        return "\\sum_{n=0}^{\\infty}" + this.generalTerm.getLaTex();
    }

    @Override
    public SumSeries sum(SumSeries other) {
        return new SumSeries(this.generalTerm.sum(other.generalTerm));
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
}
