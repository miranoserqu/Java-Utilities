/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.TreeMap;

import static com.miras.javaUtilities.ElementalFunctions.*;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class FunctionTree implements ElementalFunction<FunctionTree>, Operator<FunctionTree> {

    protected final TreeMap<Integer, ElementalFunction<?>> repr;

    @Override
    public FunctionTree getTree() {
        return null;
    }

    @Override
    public String toString(){
        return customToString(1, "x");
    }

    @Override
    public String getOperator(){
        return customToString(1, "arg1");
    }

    public TreeMap<Integer, ElementalFunction<?>> getRepr(){
        return this.repr;
    }

    public String customToString(int position, String variable){
        if(this.repr.get(position) == null){
            return variable;
        }
        if(this.repr.get(position).equals(SUM.get())){
            return customToString(position * 2, variable) + " + " + customToString(position * 2 + 1, variable);
        } else if(this.repr.get(position).equals(DIF.get())){
            return customToString(position * 2, variable) + " - " + customToString(position * 2 + 1, variable);
        } else if(this.repr.get(position).equals(MULT.get())){
            ElementalFunction<?> leftChild = this.repr.get(position * 2);
            ElementalFunction<?> rightChild = this.repr.get(position * 2 + 1);

            if(leftChild != null && leftChild.equals(ONE.get())) {
                return customToString(position * 2 + 1, variable);
            } else if(rightChild != null && rightChild.equals(ONE.get())) {
                return customToString(position * 2, variable);
            } else{
                return customToString(position * 2, variable) + " * " + customToString(position * 2 +1, variable);
            }
        } else if(this.repr.get(position).equals(DIV.get())){
            return customToString(position * 2, variable) + " / " + customToString(position * 2 + 1, variable);
        } else if(this.repr.get(position).equals(VARIABLE.get())){
            return variable;
        } else if(this.repr.get(1).equals(ONE.get())){
            return "1";
        }
        String operator = this.repr.get(position).getOperator();
        return operator.substring(0, operator.length() - 1) + customToString(position * 2, variable) + operator.charAt(operator.length() - 1);
    }
    
    public FunctionTree(ElementalFunction<?> function){
        this.repr = new TreeMap<>();
        this.repr.put(1, function);
    }

    public FunctionTree(){
        this.repr = new TreeMap<>();
    }

    protected FunctionTree(TreeMap<Integer, ElementalFunction<?>> treeMap){
        this.repr = treeMap;
    }

    @Override
    public FunctionTree getDerivative() {
        if(this.repr.get(1) == null){
            return ONE.get().getTree();
        }
        if(this.repr.get(1).equals(SUM.get())){
            return this.getSubTree(2).getDerivative().sum(this.getSubTree(3).getDerivative());
        } else if(this.repr.get(1).equals(DIF.get())){
            return this.getSubTree(2).getDerivative().dif(this.getSubTree(3).getDerivative());
        } else if(this.repr.get(1).equals(MULT.get())){
            return this.getSubTree(2).getDerivative().mult(this.getSubTree(3)).sum(this.getSubTree(3).getDerivative().mult(this.getSubTree(2)));
        } else if(this.repr.get(1).equals(DIV.get())){
            return this.getSubTree(2).getDerivative().mult(this.getSubTree(3)).dif(this.getSubTree(3).getDerivative().mult(this.getSubTree(2))).div(this.getSubTree(3).expGen(2.0));
        } else if(this.repr.get(1).equals(ONE.get())){
            return ZERO.get().getTree();
        } else if(this.repr.get(1).equals(ZERO.get())){
            return ZERO.get().getTree();
        } else if(this.repr.get(1).equals(VARIABLE.get())) {
            return ONE.get().getTree();
        } else if(this.repr.get(1).equals(OP.get())) {
            return this.getSubTree(2).getDerivative().op();
        } else{
            if(this.repr.get(2) == null || this.repr.get(2).equals(VARIABLE.get())){
                return this.repr.get(1).getDerivative();
            }
            FunctionTree tree = this.repr.get(1).getDerivative();
            tree.simbolicApply(1, this.getSubTree(2));
            tree = tree.mult(this.getSubTree(2).getDerivative());
            return tree;
        }
    }

    public void insert(int position, FunctionTree tree){
        this.customInsert(position, 1, tree);
    }

    private void customInsert(int position, int thisPosition, FunctionTree tree){
        if(tree.repr.get(thisPosition) == null){
            return;
        } else{
            this.repr.put(position, tree.repr.get(thisPosition));
            customInsert(2 * position, 2 * thisPosition, tree);
            customInsert(2 * position + 1, 2 * thisPosition + 1, tree);
        }
    }

    protected FunctionTree getSubTree(int position){
        if(this.repr.get(position) == null){
            return VARIABLE.get().getTree();
        }
        TreeMap<Integer, ElementalFunction<?>> tree = new TreeMap<>();
        treeExtraction(tree, position, 1);
        return new FunctionTree(tree);
    }

    protected void treeExtraction(TreeMap<Integer, ElementalFunction<?>> tree, int sourcePosition, int destinationPosition){
        if(this.repr.get(sourcePosition) == null){
            return;
        } else{
            tree.put(destinationPosition, this.repr.get(sourcePosition));
            treeExtraction(tree, sourcePosition * 2, destinationPosition * 2);
            treeExtraction(tree, sourcePosition * 2 + 1, destinationPosition * 2 + 1);
        }
    }

    @SafeVarargs
    public final <T extends Field<T>> T apply(T... values){
        return this.customApply(1, values);
    }

    @SafeVarargs
    private <T extends Field<T>> T customApply(int position, T... values){
        if(this.repr.get(position * 2) == null && this.repr.get(position * 2 + 1) == null){
            return this.repr.get(position).apply(values);
        } else if(this.repr.get(position * 2 + 1) == null){
            return this.repr.get(position).apply(customApply(position * 2, values));
        } else {
            return this.repr.get(position).apply(customApply(position * 2, values), customApply(position * 2 + 1, values));
        }
    }

    public void simbolicApply(int position, FunctionTree tree){
        if(this.repr.get(position).equals(SUM.get()) || this.repr.get(position).equals(DIF.get()) || this.repr.get(position).equals(MULT.get()) || this.repr.get(position).equals(DIV.get())) {
            if (this.repr.get(position * 2 + 1) == null || this.repr.get(position * 2 + 1).equals(VARIABLE.get())) {
                this.insert(position * 2 + 1, tree);
            } else{
                simbolicApply(position * 2 + 1, tree);
            }
        }
        if (this.repr.get(position * 2) == null || this.repr.get(position * 2).equals(VARIABLE.get())) {
            this.insert(position * 2, tree);
        } else{
            simbolicApply(position * 2, tree);
        }
    }

    public String customGetLaTex(String variable){
        if(this.repr.get(1) == null || this.repr.get(1).equals(VARIABLE.get())){
            return variable;
        }
        if(getNumberArgs(this.repr.get(1)) == 1){
            return this.repr.get(1).getLaTex().replace("arg1", getSubTree(2).getLaTex());
        }
        return this.repr.get(1).getLaTex().replace("arg1", getSubTree(2).getLaTex()).replace("arg2", getSubTree(3).getLaTex());
    }

    public String getLaTex(){
        return this.customGetLaTex("x");
    }

    protected int getNumberArgs(ElementalFunction<?> elementalFunction){
        String expression = elementalFunction.getLaTex();
        return expression.contains("arg2") ? 2 : 1;
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
    public int compareTo(FunctionTree o) {
        return 0;
    }

    private FunctionTree operate(ElementalFunction<?> elementalFunction){
        FunctionTree function = new FunctionTree(elementalFunction);
        function.insert(2, this);
        return function;
    }

    @Override
    public FunctionTree ln() {
        return operate(ElementalFunctions.LN.get());
    }

    @Override
    public FunctionTree log() {
        return operate(ElementalFunctions.LOG.get());
    }

    @Override
    public FunctionTree exp() {
        return operate(ElementalFunctions.EXP.get());
    }

    @Override
    public FunctionTree expNum(double base) {
        return operate(ElementalFunctions.EXPNUM.apply(base));
    }

    @Override
    public FunctionTree expGen(double exponent) {
        return operate(ElementalFunctions.EXPGEN.apply(exponent));
    }

    @Override
    public FunctionTree sin() {
        return operate(ElementalFunctions.SIN.get());
    }

    @Override
    public FunctionTree cos() {
        return operate(ElementalFunctions.COS.get());
    }

    @Override
    public FunctionTree arcsin() {
        return operate(ElementalFunctions.ARCSIN.get());
    }

    @Override
    public FunctionTree arccos() {
        return operate(ElementalFunctions.ARCCOS.get());
    }

    @Override
    public FunctionTree tan() {
        return operate(ElementalFunctions.TAN.get());
    }

    @Override
    public FunctionTree arctan() {
        return operate(ElementalFunctions.ARCTAN.get());
    }

    @Override
    public FunctionTree sec() {
        // Assuming ElementalFunctions.SEC exists. If not, it would be a composite operation like 1 / cos(x).
        return operate(ElementalFunctions.SEC.get());
    }

    @Override
    public FunctionTree cosec() {
        // Assuming ElementalFunctions.COSEC exists.
        return operate(ElementalFunctions.COSEC.get());
    }

    @Override
    public FunctionTree cotan() {
        // Assuming ElementalFunctions.COTAN exists.
        return operate(ElementalFunctions.COTAN.get());
    }

    @Override
    public FunctionTree sinh() {
        return operate(ElementalFunctions.SINH.get());
    }

    @Override
    public FunctionTree cosh() {
        return operate(ElementalFunctions.COSH.get());
    }

    @Override
    public FunctionTree arcsinh() {
        return operate(ElementalFunctions.ARCSINH.get());
    }

    @Override
    public FunctionTree arccosh() {
        return operate(ElementalFunctions.ARCCOSH.get());
    }

    @Override
    public FunctionTree tanh() {
        return operate(ElementalFunctions.TANH.get());
    }

    @Override
    public FunctionTree cotanh() {
        return operate(ElementalFunctions.COTANH.get());
    }

    @Override
    public FunctionTree arctanh() {
        return operate(ElementalFunctions.ARCTANH.get());
    }

    @Override
    public FunctionTree sech() {
        return operate(ElementalFunctions.SECH.get());
    }

    @Override
    public FunctionTree cosech() {
        return operate(ElementalFunctions.COSECH.get());
    }

    @Override
    public FunctionTree arcsech() {
        return operate(ElementalFunctions.ARCSECH.get());
    }

    @Override
    public FunctionTree arccosech() {
        return operate(ElementalFunctions.ARCCOSECH.get());
    }

    @Override
    public int compareTo(ElementalFunction o) {
        return 0;
    }

    @Override
    public FunctionTree sum(FunctionTree other) {
        FunctionTree function = new FunctionTree(SUM.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public FunctionTree dif(FunctionTree other) {
        FunctionTree function = new FunctionTree(DIF.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public FunctionTree mult(FunctionTree other) {
        FunctionTree function = new FunctionTree(MULT.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public FunctionTree div(FunctionTree other) {
        FunctionTree function = new FunctionTree(DIV.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public FunctionTree sqrt() {
        return operate(SQRT.get());
    }

    @Override
    public FunctionTree op() {
        return operate(OP.get());
    }

    @Override
    public FunctionTree inv() {
        return ONE.get().getTree().div(this);
    }

    @Override
    public FunctionTree one() {
        return operate(ONE.get());
    }

    @Override
    public FunctionTree zero() {
        return operate(ZERO.get());
    }

    @Override
    public FunctionTree clone() {
        return null;
    }

    @Override
    public FunctionTree abs() {
        return operate(ElementalFunctions.ABS.get());
    }

    @Override
    public FunctionTree scale(double factor) {
        return null;
    }

    @Override
    public boolean isEqualTo(FunctionTree other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(FunctionTree other) {
        return false;
    }

    @Override
    public boolean isLessThan(FunctionTree other) {
        return false;
    }

}
