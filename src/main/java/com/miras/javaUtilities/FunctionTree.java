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

    protected TreeMap<Integer, ElementalFunction<?>> repr;

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
        return "FunctionTree";
    }

    @Override
    public int priority(){
        return this.repr.get(1) == null ? 0 : this.repr.get(1).priority();
    }

    @Override
    public boolean single(){
        return this.repr.get(1).single();
    }

    @Override
    public boolean sinType(){
        return this.repr.get(1) != null && this.repr.get(1).sinType();
    }

    public TreeMap<Integer, ElementalFunction<?>> getRepr(){
        return this.repr;
    }

    public String customToString(int position, String variable){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction) {
            if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                return customToString(position * 2, variable) + " + " + customToString(position * 2 + 1, variable);
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                return customToString(position * 2, variable) + " - " + customToString(position * 2 + 1, variable);
            } else if (ElementalFunction.equals(elementalFunction, MULT.get())) {
                ElementalFunction<?> leftChild = this.repr.get(position * 2);
                ElementalFunction<?> rightChild = this.repr.get(position * 2 + 1);

                if (leftChild != null && leftChild.equals(ONE.get())) {
                    return customToString(position * 2 + 1, variable);
                } else if (rightChild != null && rightChild.equals(ONE.get())) {
                    return customToString(position * 2, variable);
                } else {
                    return customToString(position * 2, variable) + " * " + customToString(position * 2 + 1, variable);
                }
            } else if (ElementalFunction.equals(elementalFunction, DIV.get())) {
                return "(" + customToString(position * 2, variable) + " / " + customToString(position * 2 + 1, variable) + ")";
            } else if (ElementalFunction.equals(elementalFunction, VARIABLE.get())) {
                return variable;
            } else if(ElementalFunction.equals(elementalFunction, ONE.get())){
                return "1";
            } else if (ElementalFunction.equals(elementalFunction, ZERO.get())) {
                return "0";
            } else if(elementalFunction.getOperator().equals("FunctionTreeBlock")) {
                return elementalFunction.toString();
            } else if(elementalFunction.getOperator().equals("FunctionTree")){
                return elementalFunction.toString();
            }
            String operator = elementalFunction.getOperator();
            return operator.substring(0, operator.length() - 1) + customToString(position * 2, variable) + operator.charAt(operator.length() - 1);
        }
        return variable;
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
        return new FunctionTree(customGetDerivative(1, 0));
    }

    public FunctionTree getPartialDerivative(int variable){
        return new FunctionTree(customGetDerivative(1, variable));
    }

    private TreeMap<Integer, ElementalFunction<?>> customGetDerivative(int position, int variable){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction) {
            if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                FunctionTree functionTree = SUM.get().getTree();
                functionTree.insert(2, new FunctionTree(customGetDerivative(2 * position, variable)));
                functionTree.insert(3, new FunctionTree(customGetDerivative(2 * position + 1, variable)));
                return functionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                FunctionTree functionTree = DIF.get().getTree();
                functionTree.insert(2, new FunctionTree(customGetDerivative(2 * position, variable)));
                functionTree.insert(3, new FunctionTree(customGetDerivative(2 * position + 1, variable)));
                return functionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, MULT.get())) {
                FunctionTree functionTree = SUM.get().getTree();
                functionTree.insert(2, MULT.get());
                functionTree.insert(3, MULT.get());
                functionTree.insert(4, new FunctionTree(customGetDerivative(2 * position, variable)));
                functionTree.insert(5, this.getSubTree(2 * position + 1));
                functionTree.insert(6, new FunctionTree(customGetDerivative(2 * position + 1, variable)));
                functionTree.insert(7, this.getSubTree(2 * position));
                return functionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, DIV.get())) {
                FunctionTree functionTree = DIV.get().getTree();
                functionTree.insert(2, DIF.get());
                functionTree.insert(3, EXPGEN.apply(2d));
                functionTree.insert(4, MULT.get());
                functionTree.insert(5, MULT.get());
                functionTree.insert(6, this.getSubTree(position * 2 + 1));
                functionTree.insert(8, new FunctionTree(customGetDerivative(2 * position, variable)));
                functionTree.insert(9, this.getSubTree(2 * position + 1));
                functionTree.insert(10, new FunctionTree(customGetDerivative(2 * position + 1, variable)));
                functionTree.insert(11, this.getSubTree(2 * position));
                return functionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, ONE.get())) {
                return ZERO.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, ZERO.get())) {
                return ZERO.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, VARIABLE.get())) {
                return ONE.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, OP.get())) {
                return this.getSubTree(2 * position).op().getRepr();
            } else if (elementalFunction.getOperator().equals("FunctionTreeBlock")) {
                FunctionTreeBlock functionTreeBlock = (FunctionTreeBlock) elementalFunction;
                if (functionTreeBlock.getVariable().getNumber() == variable) {
                    return functionTreeBlock.getBlockDerivative().getRepr();
                } else {
                    return ZERO.get().getTree().getRepr();
                }
            } else if (elementalFunction.getOperator().equals("FunctionTree")) {
                FunctionTree tree = (FunctionTree) elementalFunction;
                return tree.getPartialDerivative(variable).getRepr();
            } else {
                if (this.repr.get(2 * position) == null || ElementalFunction.equals(this.repr.get(2 * position), VARIABLE.get())) {
                    return this.repr.get(position).getDerivative().getRepr();
                }
                FunctionTree tree = this.repr.get(position).getDerivative();
                tree.simbolicApply(1, new FunctionTree(this.getSubTree(2 * position)));
                tree = tree.mult(new FunctionTree(customGetDerivative(2 * position, variable)));
                return tree.getRepr();
            }
        }
        return ONE.get().getTree().getRepr();
    }

    public FunctionTree simplify(){
        return customSimplify(1);
    }

    private FunctionTree customSimplify(int position){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction){
            if(ElementalFunction.equals(elementalFunction, MULT.get())){
                FunctionTree left = customSimplify(2 * position);
                FunctionTree right = customSimplify(2 * position + 1);
                if(ElementalFunction.equals(left.repr.get(1), ZERO.get()) || ElementalFunction.equals(right.repr.get(1), ZERO.get())){
                    return new FunctionTree(ZERO.get());
                } else if (ElementalFunction.equals(left.repr.get(1), ONE.get()) && ElementalFunction.equals(right.repr.get(1), ONE.get())){
                    return new FunctionTree(ONE.get());
                } else if (ElementalFunction.equals(left.repr.get(1), ONE.get())){
                    return right;
                } else if (ElementalFunction.equals(right.repr.get(1), ONE.get())){
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), OP.get())){
                    FunctionTree result = new FunctionTree(SUM.get());
                    result.insert(2, left);
                    result.insert(3, right.getSubTree(2));
                    return result;
                }
            } else if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                FunctionTree left = customSimplify(2 * position);
                FunctionTree right = customSimplify(2 * position + 1);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return right;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get())) {
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get()) && ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                FunctionTree left = customSimplify(2 * position);
                FunctionTree right = customSimplify(2 * position + 1);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return right.op();
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get())) {
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get()) && ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (ElementalFunction.equals(elementalFunction, DIV.get())){
                FunctionTree left = customSimplify(2 * position);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (elementalFunction.toString().equals("^1")){
                return customSimplify(2 * position);
            }
        }
        if(this.repr.get(position) == null){
            return VARIABLE.get().getTree();
        }
        FunctionTree result = new FunctionTree(this.repr.get(position));
        result.insert(2, customSimplify(2 * position));
        result.insert(3, customSimplify(2 * position + 1));
        return result;
    }

    private void insert(int position, FunctionTree tree){
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

    void simbolicApply(int position, FunctionTree tree){
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

    public String customGetLaTex(int position, String variable){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction) {
            if(elementalFunction.getOperator().equals("FunctionTree")){
                FunctionTree functionTree = (FunctionTree) elementalFunction;
                return functionTree.getLaTex();
            } else if (elementalFunction.getOperator().equals("FunctionTree")){
                FunctionTreeBlock functionTreeBlock = (FunctionTreeBlock) elementalFunction;
                return functionTreeBlock.getLaTex();
            } else if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                return ((elementalFunction.priority() < this.repr.get(2 * position).priority() ? "(arg1) + " : "arg1 + ") +
                        (elementalFunction.priority() < this.repr.get(2 * position + 1).priority() ? "(arg2)" : "arg2"))
                        .replace("arg1", customGetLaTex(2 * position, variable))
                        .replace("arg2", customGetLaTex(2 * position + 1, variable));
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                return ((elementalFunction.priority() < this.repr.get(2 * position).priority() ? "(arg1) - " : "arg1 - ") +
                        (elementalFunction.priority() < this.repr.get(2 * position + 1).priority() ? "(arg2)" : "arg2"))
                        .replace("arg1", customGetLaTex(2 * position, variable))
                        .replace("arg2", customGetLaTex(2 * position + 1, variable));
            } else if (ElementalFunction.equals(elementalFunction, MULT.get())) {
                return ((elementalFunction.priority() < this.repr.get(2 * position).priority() ? "(arg1) \\cdot " : "arg1 \\cdot ") +
                        (elementalFunction.priority() < this.repr.get(2 * position + 1).priority() ? "(arg2)" : "arg2"))
                        .replace("arg1", customGetLaTex(2 * position, variable))
                        .replace("arg2", customGetLaTex(2 * position + 1, variable));
            } else if (elementalFunction.getOperator().equals("^")) {
                if (!this.repr.get(2 * position).sinType()) {
                    return (this.repr.get(2 * position).single() || this.repr.get(2 * position) == null ? elementalFunction.getLaTex().replace("(arg1)", "{arg1}") : elementalFunction.getLaTex())
                            .replace("arg1", customGetLaTex(2 * position, variable));
                } else {
                    return this.repr.get(2 * position).getLaTex().replace("(arg1)", "^{" + elementalFunction.toString().substring(1) + "}(" + customGetLaTex(4 * position, variable) + ")");
                }
            } else if (getNumberArgs(elementalFunction) == 1) {
                return elementalFunction.getLaTex().replace("arg1", customGetLaTex(2 * position, variable));
            }
            return elementalFunction.getLaTex().replace("arg1", customGetLaTex(2 * position, variable)).replace("arg2", customGetLaTex(2 * position + 1, variable));
        }
        return variable;
    }

    public FunctionTree semiApply(double value, int variable){
        return customSemiApply(value, variable, 1);
    }

    private FunctionTree customSemiApply(double value, int variable, int position){
        FunctionTree result = new FunctionTree(this.repr.get(position));
        if(this.repr.get(position) == null || ElementalFunction.equals(this.repr.get(position), VARIABLE.get())){
            return new ConstantNumber<Double>().CONSTANTNUMBER.apply(value).getTree();
        } else {
            result.insert(2, customSemiApply(value, variable, 2 * position));
            result.insert(3, customSemiApply(value, variable, 2 * position + 1));
        }
        return result;
    }

    public String getLaTex(){
        return this.customGetLaTex(1, "x");
    }

    protected int getNumberArgs(ElementalFunction<?> elementalFunction){
        String expression = elementalFunction.getLaTex();
        return expression.contains("arg2") ? 2 : 1;
    }

    public void insert(int position, ElementalFunction elementalFunction){
        this.repr.put(position, elementalFunction);
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

    public

    public abstract FunctionSuccession sum(FunctionSuccession other);

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
        return new FunctionTree(this.repr);
    }

    @Override
    public FunctionTree abs() {
        return operate(ElementalFunctions.ABS.get());
    }

    @Override
    public FunctionTree scale(double factor) {
        FunctionTree result = new FunctionTree(SCALE.apply(factor));
        result.insert(2, this);
        return result;
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
