package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;

public class FunctionTreeInterface implements ElementalFunction<FunctionTreeInterface> {

    private final NumberFunctionTree numberFunctionTree;
    private final String variable;
    private final int variableNumber;

    public FunctionTreeInterface(NumberFunctionTree numberFunctionTree, String variable, int variableNumber){
        this.numberFunctionTree = numberFunctionTree;
        this.variable = variable;
        this.variableNumber = variableNumber;
    }

    @Override
    public NumberFunctionTree getTree() {
        return this.numberFunctionTree;
    }

    @Override
    public String getOperator() {
        return this.numberFunctionTree.customToString(1, new Variable(variableNumber, variable));
    }

    @Override
    public String getLaTex() {
        return this.numberFunctionTree.customGetLaTex(1, this.variable);
    }

    FunctionTreeInterface get(NumberFunctionTree numberFunctionTree){
        return new FunctionTreeInterface(numberFunctionTree, this.variable, this.variableNumber);
    }

    @Override
    public FunctionTreeInterface sum(FunctionTreeInterface other) {
        return get(this.numberFunctionTree.sum(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeInterface dif(FunctionTreeInterface other) {
        return get(this.numberFunctionTree.dif(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeInterface mult(FunctionTreeInterface other) {
        return get(this.numberFunctionTree.mult(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeInterface div(FunctionTreeInterface other) {
        return get(this.numberFunctionTree.div(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeInterface sqrt() {
        return get(this.numberFunctionTree.sqrt());
    }

    @Override
    public FunctionTreeInterface op() {
        return get(this.numberFunctionTree.op());
    }

    @Override
    public FunctionTreeInterface inv() {
        return get(this.numberFunctionTree.inv());
    }

    @Override
    public FunctionTreeInterface one() {
        return get(this.numberFunctionTree.one());
    }

    @Override
    public FunctionTreeInterface zero() {
        return get(this.numberFunctionTree.zero());
    }

    @Override
    public FunctionTreeInterface clone() {
        return new FunctionTreeInterface(this.numberFunctionTree, this.variable, this.variableNumber);
    }

    @Override
    public boolean isEqualTo(FunctionTreeInterface other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(FunctionTreeInterface other) {
        return false;
    }

    @Override
    public boolean isLessThan(FunctionTreeInterface other) {
        return false;
    }

    @Override
    public FunctionTreeInterface scale(double factor) {
        return get(this.numberFunctionTree.scale(factor));
    }

    @Override
    public FunctionTreeInterface abs() {
        return get(this.numberFunctionTree.abs());
    }

    @Override
    public FunctionTreeInterface ln() {
        return get(this.numberFunctionTree.ln());
    }

    @Override
    public FunctionTreeInterface log() {
        return get(this.numberFunctionTree.log());
    }

    @Override
    public FunctionTreeInterface exp() {
        return get(this.numberFunctionTree.exp());
    }

    @Override
    public FunctionTreeInterface expNum(double base) {
        return get(this.numberFunctionTree.expNum(base));
    }

    @Override
    public FunctionTreeInterface expGen(double exponent) {
        return get(this.numberFunctionTree.expGen(exponent));
    }

    @Override
    public FunctionTreeInterface sin() {
        return get(this.numberFunctionTree.sin());
    }

    @Override
    public FunctionTreeInterface cos() {
        return get(this.numberFunctionTree.cos());
    }

    @Override
    public FunctionTreeInterface arcsin() {
        return get(this.numberFunctionTree.arcsin());
    }

    @Override
    public FunctionTreeInterface arccos() {
        return get(this.numberFunctionTree.arccos());
    }

    @Override
    public FunctionTreeInterface tan() {
        return get(this.numberFunctionTree.tan());
    }

    @Override
    public FunctionTreeInterface arctan() {
        return get(this.numberFunctionTree.arctan());
    }

    @Override
    public FunctionTreeInterface sec() {
        return get(this.numberFunctionTree.sec());
    }

    @Override
    public FunctionTreeInterface cosec() {
        return get(this.numberFunctionTree.cosec());
    }

    @Override
    public FunctionTreeInterface cotan() {
        return get(this.numberFunctionTree.cotan());
    }

    @Override
    public FunctionTreeInterface sinh() {
        return get(this.numberFunctionTree.sinh());
    }

    @Override
    public FunctionTreeInterface cosh() {
        return get(this.numberFunctionTree.cosh());
    }

    @Override
    public FunctionTreeInterface arcsinh() {
        return get(this.numberFunctionTree.arcsinh());
    }

    @Override
    public FunctionTreeInterface arccosh() {
        return get(this.numberFunctionTree.arccosh());
    }

    @Override
    public FunctionTreeInterface tanh() {
        return get(this.numberFunctionTree.tanh());
    }

    @Override
    public FunctionTreeInterface cotanh() {
        return get(this.numberFunctionTree.cotanh());
    }

    @Override
    public FunctionTreeInterface arctanh() {
        return get(this.numberFunctionTree.arctanh());
    }

    @Override
    public FunctionTreeInterface sech() {
        return get(this.numberFunctionTree.sech());
    }

    @Override
    public FunctionTreeInterface cosech() {
        return get(this.numberFunctionTree.cosech());
    }

    @Override
    public FunctionTreeInterface arcsech() {
        return get(this.numberFunctionTree.arcsech());
    }

    @Override
    public FunctionTreeInterface arccosech() {
        return get(this.numberFunctionTree.arccosech());
    }

    @Override
    public NumberFunctionTree getDerivative() {
        return this.numberFunctionTree.getDerivative();
    }

    @Override
    public double apply(double... values) {
        return this.numberFunctionTree.apply(values);
    }

    @Override
    public int apply(int... values) {
        return this.numberFunctionTree.apply(values);
    }

    @Override
    public float apply(float... values) {
        return this.numberFunctionTree.apply(values);
    }
}
