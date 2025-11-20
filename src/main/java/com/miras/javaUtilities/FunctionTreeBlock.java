package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.TreeMap;

public class FunctionTreeBlock implements ElementalFunction<FunctionTreeBlock>, Operator<FunctionTreeBlock> {

    private FunctionTree functionTree;
    private final Variable variable;

    public FunctionTreeBlock(Variable variable, FunctionTree functionTree){

        this.functionTree = functionTree;
        this.variable = variable;

    }

    public TreeMap<Integer, ElementalFunction<?>> getRepr(){
        return this.functionTree.getRepr();
    }

    @Override
    public String toString(){
        return this.functionTree.customToString(1, this.variable.getLaTexRepr());
    }

    @Override
    public String getOperator() {
        return "FunctionTreeBlock";
    }

    @Override
    public int priority(){
        return this.functionTree.priority();
    }

    @Override
    public boolean single(){
        return this.functionTree.single();
    }

    @Override
    public boolean sinType(){
        return this.functionTree.sinType();
    }

    @Override
    public String getLaTex() {
        return this.functionTree.customGetLaTex(1, variable.getLaTexRepr());
    }

    @Override
    public FunctionTree getDerivative(){
        return null;
    }

    public FunctionTreeBlock getBlockDerivative() {
        return new FunctionTreeBlock(this.variable, this.functionTree.getDerivative());
    }

    @Override
    public <R extends Field<R>> R apply(R... values) {
        return this.functionTree.apply(values);
    }

    @Override
    public double apply(double... values) {
        return this.functionTree.apply(values[this.variable.getNumber()]);
    }

    @Override
    public int apply(int... values) {
        return this.functionTree.apply(values[this.variable.getNumber()]);
    }

    @Override
    public float apply(float... values) {
        return this.functionTree.apply(values[this.variable.getNumber()]);
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public int compareTo(FunctionTreeBlock o) {
        return 0;
    }

    private FunctionTreeBlock operate(ElementalFunction elementalFunction){
        FunctionTree tree = elementalFunction.getTree();
        tree.insert(2, this.functionTree);
        return new FunctionTreeBlock(this.variable, tree);
    }

    @Override
    public FunctionTreeBlock sum(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.functionTree.sum(other.functionTree));
    }

    @Override
    public FunctionTreeBlock dif(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.functionTree.dif(other.functionTree));
    }

    @Override
    public FunctionTreeBlock mult(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.functionTree.mult(other.functionTree));
    }

    @Override
    public FunctionTreeBlock div(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.functionTree.div(other.functionTree));
    }

    @Override
    public FunctionTreeBlock sqrt() {
        return operate(ElementalFunctions.SQRT.get());
    }

    @Override
    public FunctionTreeBlock op() {
        return operate(ElementalFunctions.OP.get());
    }

    @Override
    public FunctionTreeBlock inv() {
        return new FunctionTreeBlock(this.variable, this.functionTree.inv());
    }

    @Override
    public FunctionTreeBlock one() {
        return ElementalFunction.super.one();
    }

    @Override
    public FunctionTreeBlock zero() {
        return ElementalFunction.super.zero();
    }

    @Override
    public FunctionTreeBlock clone() {
        return ElementalFunction.super.clone();
    }

    @Override
    public boolean isEqualTo(FunctionTreeBlock other) {
        return ElementalFunction.super.isEqualTo(other);
    }

    @Override
    public boolean isGreaterThan(FunctionTreeBlock other) {
        return ElementalFunction.super.isGreaterThan(other);
    }

    @Override
    public boolean isLessThan(FunctionTreeBlock other) {
        return ElementalFunction.super.isLessThan(other);
    }

    @Override
    public FunctionTreeBlock scale(double factor) {
        return ElementalFunction.super.scale(factor);
    }

    @Override
    public FunctionTreeBlock abs() {
        return ElementalFunction.super.abs();
    }

    @Override
    public FunctionTreeBlock ln() {
        return ElementalFunction.super.ln();
    }

    @Override
    public FunctionTreeBlock log() {
        return ElementalFunction.super.log();
    }

    @Override
    public FunctionTreeBlock exp() {
        return ElementalFunction.super.exp();
    }

    @Override
    public FunctionTreeBlock expNum(double base) {
        return ElementalFunction.super.expNum(base);
    }

    @Override
    public FunctionTreeBlock expGen(double exponent) {
        return ElementalFunction.super.expGen(exponent);
    }

    @Override
    public FunctionTreeBlock sin() {
        return ElementalFunction.super.sin();
    }

    @Override
    public FunctionTreeBlock cos() {
        return ElementalFunction.super.cos();
    }

    @Override
    public FunctionTreeBlock arcsin() {
        return ElementalFunction.super.arcsin();
    }

    @Override
    public FunctionTreeBlock arccos() {
        return ElementalFunction.super.arccos();
    }

    @Override
    public FunctionTreeBlock tan() {
        return ElementalFunction.super.tan();
    }

    @Override
    public FunctionTreeBlock arctan() {
        return ElementalFunction.super.arctan();
    }

    @Override
    public FunctionTreeBlock sec() {
        return ElementalFunction.super.sec();
    }

    @Override
    public FunctionTreeBlock cosec() {
        return ElementalFunction.super.cosec();
    }

    @Override
    public FunctionTreeBlock cotan() {
        return ElementalFunction.super.cotan();
    }

    @Override
    public FunctionTreeBlock sinh() {
        return ElementalFunction.super.sinh();
    }

    @Override
    public FunctionTreeBlock cosh() {
        return ElementalFunction.super.cosh();
    }

    @Override
    public FunctionTreeBlock arcsinh() {
        return ElementalFunction.super.arcsinh();
    }

    @Override
    public FunctionTreeBlock arccosh() {
        return ElementalFunction.super.arccosh();
    }

    @Override
    public FunctionTreeBlock tanh() {
        return ElementalFunction.super.tanh();
    }

    @Override
    public FunctionTreeBlock cotanh() {
        return ElementalFunction.super.cotanh();
    }

    @Override
    public FunctionTreeBlock arctanh() {
        return ElementalFunction.super.arctanh();
    }

    @Override
    public FunctionTreeBlock sech() {
        return ElementalFunction.super.sech();
    }

    @Override
    public FunctionTreeBlock cosech() {
        return ElementalFunction.super.cosech();
    }

    @Override
    public FunctionTreeBlock arcsech() {
        return ElementalFunction.super.arcsech();
    }

    @Override
    public FunctionTreeBlock arccosech() {
        return ElementalFunction.super.arccosech();
    }

}
