package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

public class FunctionTreeInterface implements ElementalFunction<FunctionTreeInterface> {

    private final FunctionTree functionTree;
    private final String variable;
    private final int variableNumber;

    public FunctionTreeInterface(FunctionTree functionTree, String variable, int variableNumber){
        this.functionTree = functionTree;
        this.variable = variable;
        this.variableNumber = variableNumber;
    }

    @Override
    public FunctionTree getTree() {
        return this.functionTree;
    }

    @Override
    public String getOperator() {
        return this.functionTree.customToString(1, "arg" + this.variableNumber);
    }

    @Override
    public String getLaTex() {
        return this.functionTree.customGetLaTex(1, this.variable);
    }

    FunctionTreeInterface get(FunctionTree functionTree){
        return new FunctionTreeInterface(functionTree, this.variable, this.variableNumber);
    }

    @Override
    public FunctionTreeInterface sum(FunctionTreeInterface other) {
        return get(this.functionTree.sum(other.functionTree));
    }

    @Override
    public FunctionTreeInterface dif(FunctionTreeInterface other) {
        return get(this.functionTree.dif(other.functionTree));
    }

    @Override
    public FunctionTreeInterface mult(FunctionTreeInterface other) {
        return get(this.functionTree.mult(other.functionTree));
    }

    @Override
    public FunctionTreeInterface div(FunctionTreeInterface other) {
        return get(this.functionTree.div(other.functionTree));
    }

    @Override
    public FunctionTreeInterface sqrt() {
        return get(this.functionTree.sqrt());
    }

    @Override
    public FunctionTreeInterface op() {
        return get(this.functionTree.op());
    }

    @Override
    public FunctionTreeInterface inv() {
        return get(this.functionTree.inv());
    }

    @Override
    public FunctionTreeInterface one() {
        return get(this.functionTree.one());
    }

    @Override
    public FunctionTreeInterface zero() {
        return get(this.functionTree.zero());
    }

    @Override
    public FunctionTreeInterface clone() {
        return new FunctionTreeInterface(this.functionTree, this.variable, this.variableNumber);
    }

    @Override
    public boolean isEqualTo(FunctionTreeInterface other) {
        return ElementalFunction.super.isEqualTo(other);
    }

    @Override
    public boolean isGreaterThan(FunctionTreeInterface other) {
        return ElementalFunction.super.isGreaterThan(other);
    }

    @Override
    public boolean isLessThan(FunctionTreeInterface other) {
        return ElementalFunction.super.isLessThan(other);
    }

    @Override
    public FunctionTreeInterface scale(double factor) {
        return get(this.functionTree.scale(factor));
    }

    @Override
    public FunctionTreeInterface abs() {
        return get(this.functionTree.abs());
    }

    @Override
    public FunctionTreeInterface ln() {
        return get(this.functionTree.ln());
    }

    @Override
    public FunctionTreeInterface log() {
        return get(this.functionTree.log());
    }

    @Override
    public FunctionTreeInterface exp() {
        return get(this.functionTree.exp());
    }

    @Override
    public FunctionTreeInterface expNum(double base) {
        return get(this.functionTree.expNum(base));
    }

    @Override
    public FunctionTreeInterface expGen(double exponent) {
        return get(this.functionTree.expGen(exponent));
    }

    @Override
    public FunctionTreeInterface sin() {
        return get(this.functionTree.sin());
    }

    @Override
    public FunctionTreeInterface cos() {
        return get(this.functionTree.cos());
    }

    @Override
    public FunctionTreeInterface arcsin() {
        return get(this.functionTree.arcsin());
    }

    @Override
    public FunctionTreeInterface arccos() {
        return get(this.functionTree.arccos());
    }

    @Override
    public FunctionTreeInterface tan() {
        return get(this.functionTree.tan());
    }

    @Override
    public FunctionTreeInterface arctan() {
        return get(this.functionTree.arctan());
    }

    @Override
    public FunctionTreeInterface sec() {
        return get(this.functionTree.sec());
    }

    @Override
    public FunctionTreeInterface cosec() {
        return get(this.functionTree.cosec());
    }

    @Override
    public FunctionTreeInterface cotan() {
        return get(this.functionTree.cotan());
    }

    @Override
    public FunctionTreeInterface sinh() {
        return get(this.functionTree.sinh());
    }

    @Override
    public FunctionTreeInterface cosh() {
        return get(this.functionTree.cosh());
    }

    @Override
    public FunctionTreeInterface arcsinh() {
        return get(this.functionTree.arcsinh());
    }

    @Override
    public FunctionTreeInterface arccosh() {
        return get(this.functionTree.arccosh());
    }

    @Override
    public FunctionTreeInterface tanh() {
        return get(this.functionTree.tanh());
    }

    @Override
    public FunctionTreeInterface cotanh() {
        return get(this.functionTree.cotanh());
    }

    @Override
    public FunctionTreeInterface arctanh() {
        return get(this.functionTree.arctanh());
    }

    @Override
    public FunctionTreeInterface sech() {
        return get(this.functionTree.sech());
    }

    @Override
    public FunctionTreeInterface cosech() {
        return get(this.functionTree.cosech());
    }

    @Override
    public FunctionTreeInterface arcsech() {
        return get(this.functionTree.arcsech());
    }

    @Override
    public FunctionTreeInterface arccosech() {
        return get(this.functionTree.arccosech());
    }

    @Override
    public FunctionTree getDerivative() {
        return this.functionTree.getDerivative();
    }

    @SafeVarargs
    @Override
    public final <R extends Field<R>> R apply(R... values) {
        return this.functionTree.apply(values);
    }

    @Override
    public double apply(double... values) {
        return this.functionTree.apply(values);
    }

    @Override
    public int apply(int... values) {
        return this.functionTree.apply(values);
    }

    @Override
    public float apply(float... values) {
        return this.functionTree.apply(values);
    }

    @Override
    public int compareTo(FunctionTreeInterface o) {
        return 0;
    }
}
