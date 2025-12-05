package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;
import com.miras.javaUtilities.calculus.interfaces.Operator;

import java.util.TreeMap;

@Deprecated
public class FunctionTreeBlock implements ElementalFunction<FunctionTreeBlock>, Operator<FunctionTreeBlock> {

    private NumberFunctionTree numberFunctionTree;
    private final Variable variable;

    public FunctionTreeBlock(Variable variable, NumberFunctionTree numberFunctionTree){

        this.numberFunctionTree = numberFunctionTree;
        this.variable = variable;

    }

    public TreeMap<Integer, ElementalFunction<?>> getRepr(){
        return this.numberFunctionTree.getRepr();
    }

    @Override
    public String toString(){
        return this.numberFunctionTree.customToString(1, this.variable);
    }

    @Override
    public String getOperator() {
        return "FunctionTreeBlock";
    }

    @Override
    public int priority(){
        return this.numberFunctionTree.priority();
    }

    @Override
    public boolean single(){
        return this.numberFunctionTree.single();
    }

    @Override
    public boolean sinType(){
        return this.numberFunctionTree.sinType();
    }

    @Override
    public String getLaTex() {
        return this.numberFunctionTree.customGetLaTex(1, variable.getLaTexRepr());
    }

    @Override
    public NumberFunctionTree getDerivative(){
        return null;
    }

    public FunctionTreeBlock getBlockDerivative() {
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.getDerivative());
    }

    @Override
    public double apply(double... values) {
        return this.numberFunctionTree.apply(values[this.variable.getNumber()]);
    }

    @Override
    public int apply(int... values) {
        return this.numberFunctionTree.apply(values[this.variable.getNumber()]);
    }

    @Override
    public float apply(float... values) {
        return this.numberFunctionTree.apply(values[this.variable.getNumber()]);
    }

    public Variable getVariable() {
        return variable;
    }

    private FunctionTreeBlock operate(ElementalFunction elementalFunction){
        NumberFunctionTree tree = elementalFunction.getTree();
        tree.insert(2, this.numberFunctionTree);
        return new FunctionTreeBlock(this.variable, tree);
    }

    @Override
    public FunctionTreeBlock sum(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.sum(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeBlock dif(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.dif(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeBlock mult(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.mult(other.numberFunctionTree));
    }

    @Override
    public FunctionTreeBlock div(FunctionTreeBlock other) {
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.div(other.numberFunctionTree));
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
        return new FunctionTreeBlock(this.variable, this.numberFunctionTree.inv());
    }

    @Override
    public FunctionTreeBlock one() {
        return null;
    }

    @Override
    public FunctionTreeBlock zero() {
        return null;
    }

    @Override
    public FunctionTreeBlock clone() {
        return null;
    }

    @Override
    public boolean isEqualTo(FunctionTreeBlock other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(FunctionTreeBlock other) {
        return false;
    }

    @Override
    public boolean isLessThan(FunctionTreeBlock other) {
        return false;
    }

    @Override
    public FunctionTreeBlock scale(double factor) {
        return null;
    }

    @Override
    public FunctionTreeBlock abs() {
        return null;
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
