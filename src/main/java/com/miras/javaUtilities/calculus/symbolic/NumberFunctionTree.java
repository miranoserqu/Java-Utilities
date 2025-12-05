/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;
import com.miras.javaUtilities.calculus.interfaces.Operator;

import java.util.TreeMap;

import static com.miras.javaUtilities.calculus.symbolic.ElementalFunctions.*;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class NumberFunctionTree implements ElementalFunction<NumberFunctionTree>, Operator<NumberFunctionTree> {

    protected TreeMap<Integer, ElementalFunction<?>> repr;

    @Override
    public NumberFunctionTree getTree() {
        return null;
    }

    @Override
    public String toString(){
        return customToString(1, new Variable(0, "x"));
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

    public String customToString(int position, Variable variable){
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
            } else if (ElementalFunction.equals(elementalFunction, VARIABLE.apply(variable))) {
                return elementalFunction.toString();
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
        return variable.getLaTexRepr();
    }
    
    public NumberFunctionTree(ElementalFunction<?> function){
        this.repr = new TreeMap<>();
        this.repr.put(1, function);
    }

    public NumberFunctionTree(){
        this.repr = new TreeMap<>();
    }

    protected NumberFunctionTree(TreeMap<Integer, ElementalFunction<?>> treeMap){
        this.repr = treeMap;
    }

    @Override
    public NumberFunctionTree getDerivative() {
        return new NumberFunctionTree(customGetDerivative(1, new Variable(0, "x")));
    }

    public NumberFunctionTree getPartialDerivative(Variable variable){
        return new NumberFunctionTree(customGetDerivative(1, variable));
    }

    private TreeMap<Integer, ElementalFunction<?>> customGetDerivative(int position, Variable variable){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction) {
            if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                NumberFunctionTree numberFunctionTree = SUM.get().getTree();
                numberFunctionTree.insert(2, new NumberFunctionTree(customGetDerivative(2 * position, variable)));
                numberFunctionTree.insert(3, new NumberFunctionTree(customGetDerivative(2 * position + 1, variable)));
                return numberFunctionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                NumberFunctionTree numberFunctionTree = DIF.get().getTree();
                numberFunctionTree.insert(2, new NumberFunctionTree(customGetDerivative(2 * position, variable)));
                numberFunctionTree.insert(3, new NumberFunctionTree(customGetDerivative(2 * position + 1, variable)));
                return numberFunctionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, MULT.get())) {
                NumberFunctionTree numberFunctionTree = SUM.get().getTree();
                numberFunctionTree.insert(2, MULT.get());
                numberFunctionTree.insert(3, MULT.get());
                numberFunctionTree.insert(4, new NumberFunctionTree(customGetDerivative(2 * position, variable)));
                numberFunctionTree.insert(5, this.getSubTree(2 * position + 1));
                numberFunctionTree.insert(6, new NumberFunctionTree(customGetDerivative(2 * position + 1, variable)));
                numberFunctionTree.insert(7, this.getSubTree(2 * position));
                return numberFunctionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, DIV.get())) {
                NumberFunctionTree numberFunctionTree = DIV.get().getTree();
                numberFunctionTree.insert(2, DIF.get());
                numberFunctionTree.insert(3, EXPGEN.apply(2d));
                numberFunctionTree.insert(4, MULT.get());
                numberFunctionTree.insert(5, MULT.get());
                numberFunctionTree.insert(6, this.getSubTree(position * 2 + 1));
                numberFunctionTree.insert(8, new NumberFunctionTree(customGetDerivative(2 * position, variable)));
                numberFunctionTree.insert(9, this.getSubTree(2 * position + 1));
                numberFunctionTree.insert(10, new NumberFunctionTree(customGetDerivative(2 * position + 1, variable)));
                numberFunctionTree.insert(11, this.getSubTree(2 * position));
                return numberFunctionTree.getRepr();
            } else if (ElementalFunction.equals(elementalFunction, ONE.get())) {
                return ZERO.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, ZERO.get())) {
                return ZERO.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, VARIABLE.apply(variable))) {
                return ONE.get().getTree().getRepr();
            } else if (ElementalFunction.equals(elementalFunction, OP.get())) {
                return this.getSubTree(2 * position).op().getRepr();
            } else if (elementalFunction.getOperator().equals("FunctionTreeBlock")) {
                FunctionTreeBlock functionTreeBlock = (FunctionTreeBlock) elementalFunction;
                if (functionTreeBlock.getVariable() == variable) {
                    return functionTreeBlock.getBlockDerivative().getRepr();
                } else {
                    return ZERO.get().getTree().getRepr();
                }
            } else if (elementalFunction.getOperator().equals("FunctionTree")) {
                NumberFunctionTree tree = (NumberFunctionTree) elementalFunction;
                return tree.getPartialDerivative(variable).getRepr();
            } else {
                if (this.repr.get(2 * position) == null || ElementalFunction.equals(this.repr.get(2 * position), VARIABLE.apply(variable))) {
                    return this.repr.get(position).getDerivative().getRepr();
                }
                NumberFunctionTree tree = this.repr.get(position).getDerivative();
                tree.simbolicApply(1, new NumberFunctionTree(this.getSubTree(2 * position)), new Variable(0, "x"));
                tree = tree.mult(new NumberFunctionTree(customGetDerivative(2 * position, variable)));
                return tree.getRepr();
            }
        }
        return ONE.get().getTree().getRepr();
    }

    public NumberFunctionTree simplify(){
        return customSimplify(1);
    }

    private NumberFunctionTree customSimplify(int position){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction){
            if(ElementalFunction.equals(elementalFunction, MULT.get())){
                NumberFunctionTree left = customSimplify(2 * position);
                NumberFunctionTree right = customSimplify(2 * position + 1);
                if(ElementalFunction.equals(left.repr.get(1), ZERO.get()) || ElementalFunction.equals(right.repr.get(1), ZERO.get())){
                    return new NumberFunctionTree(ZERO.get());
                } else if (ElementalFunction.equals(left.repr.get(1), ONE.get()) && ElementalFunction.equals(right.repr.get(1), ONE.get())){
                    return new NumberFunctionTree(ONE.get());
                } else if (ElementalFunction.equals(left.repr.get(1), ONE.get())){
                    return right;
                } else if (ElementalFunction.equals(right.repr.get(1), ONE.get())){
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), OP.get())){
                    NumberFunctionTree result = new NumberFunctionTree(SUM.get());
                    result.insert(2, left);
                    result.insert(3, right.getSubTree(2));
                    return result;
                }
            } else if (ElementalFunction.equals(elementalFunction, SUM.get())) {
                NumberFunctionTree left = customSimplify(2 * position);
                NumberFunctionTree right = customSimplify(2 * position + 1);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return right;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get())) {
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get()) && ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (ElementalFunction.equals(elementalFunction, DIF.get())) {
                NumberFunctionTree left = customSimplify(2 * position);
                NumberFunctionTree right = customSimplify(2 * position + 1);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return right.op();
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get())) {
                    return left;
                } else if (ElementalFunction.equals(right.repr.get(1), ZERO.get()) && ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (ElementalFunction.equals(elementalFunction, DIV.get())){
                NumberFunctionTree left = customSimplify(2 * position);
                if (ElementalFunction.equals(left.repr.get(1), ZERO.get())){
                    return ZERO.get().getTree();
                }
            } else if (elementalFunction.toString().equals("^1")){
                return customSimplify(2 * position);
            }
        }
        if(this.repr.get(position) == null){
            return VARIABLE.apply(new Variable(0, "x")).getTree();
        }
        NumberFunctionTree result = new NumberFunctionTree(this.repr.get(position));
        result.insert(2, customSimplify(2 * position));
        result.insert(3, customSimplify(2 * position + 1));
        return result;
    }

    private void insert(int position, NumberFunctionTree tree){
        this.customInsert(position, 1, tree);
    }

    private void customInsert(int position, int thisPosition, NumberFunctionTree tree){
        if(tree.repr.get(thisPosition) == null){
            return;
        } else{
            this.repr.put(position, tree.repr.get(thisPosition));
            customInsert(2 * position, 2 * thisPosition, tree);
            customInsert(2 * position + 1, 2 * thisPosition + 1, tree);
        }
    }

    protected NumberFunctionTree getSubTree(int position){
        if(this.repr.get(position) == null){
            return VARIABLE.apply(new Variable(0, "x")).getTree();
        }
        TreeMap<Integer, ElementalFunction<?>> tree = new TreeMap<>();
        treeExtraction(tree, position, 1);
        return new NumberFunctionTree(tree);
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

    void simbolicApply(int position, NumberFunctionTree tree, Variable variable){
        if(this.repr.get(position).equals(SUM.get()) || this.repr.get(position).equals(DIF.get()) || this.repr.get(position).equals(MULT.get()) || this.repr.get(position).equals(DIV.get())) {
            if (this.repr.get(position * 2 + 1) == null || this.repr.get(position * 2 + 1).equals(VARIABLE.apply(variable))) {
                this.insert(position * 2 + 1, tree);
            } else{
                simbolicApply(position * 2 + 1, tree, variable);
            }
        }
        if (this.repr.get(position * 2) == null || this.repr.get(position * 2).equals(VARIABLE.apply(variable))) {
            this.insert(position * 2, tree);
        } else{
            simbolicApply(position * 2, tree, variable);
        }
    }

    public String customGetLaTex(int position, String variable){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction) {
            if(elementalFunction.getOperator().equals("FunctionTree")){
                NumberFunctionTree numberFunctionTree = (NumberFunctionTree) elementalFunction;
                return numberFunctionTree.getLaTex();
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
        return customApply(1, values);
    }

    @Override
    public int apply(int... values) {
        return customApply(1, values);
    }

    @Override
    public float apply(float... values) {
        return customApply(1, values);
    }

    private double customApply(int position, double... values){
        if(this.repr.get(position) == null){
            return values[0];
        }
        if(this.repr.get(position).getID().equals("Variable")){
            return this.repr.get(position).apply(values);
        }
        if(this.repr.get(position).sinType()){
            return this.repr.get(position).apply(customApply(2 * position, values));
        } else {
            return this.repr.get(position).apply(customApply(2 * position, values), customApply(2 * position + 1, values));
        }
    }

    private int customApply(int position, int... values){
        if(this.repr.get(position) == null){
            return values[0];
        }
        if(this.repr.get(position).getID().equals("Variable")){
            return this.repr.get(position).apply(values);
        }
        if(this.repr.get(position).sinType()){
            return this.repr.get(position).apply(customApply(2 * position, values));
        } else {
            return this.repr.get(position).apply(customApply(2 * position, values), customApply(2 * position + 1, values));
        }
    }

    private float customApply(int position, float... values){
        if(this.repr.get(position) == null){
            return values[0];
        }
        if(this.repr.get(position).getID().equals("Variable")){
            return this.repr.get(position).apply(values);
        }
        if(this.repr.get(position).sinType()){
            return this.repr.get(position).apply(customApply(2 * position, values));
        } else {
            return this.repr.get(position).apply(customApply(2 * position, values), customApply(2 * position + 1, values));
        }
    }

    private NumberFunctionTree operate(ElementalFunction<?> elementalFunction){
        NumberFunctionTree function = new NumberFunctionTree(elementalFunction);
        function.insert(2, this);
        return function;
    }

    @Override
    public NumberFunctionTree ln() {
        return operate(ElementalFunctions.LN.get());
    }

    @Override
    public NumberFunctionTree log() {
        return operate(ElementalFunctions.LOG.get());
    }

    @Override
    public NumberFunctionTree exp() {
        return operate(ElementalFunctions.EXP.get());
    }

    @Override
    public NumberFunctionTree expNum(double base) {
        return operate(ElementalFunctions.EXPNUM.apply(base));
    }

    @Override
    public NumberFunctionTree expGen(double exponent) {
        return operate(ElementalFunctions.EXPGEN.apply(exponent));
    }

    @Override
    public NumberFunctionTree sin() {
        return operate(ElementalFunctions.SIN.get());
    }

    @Override
    public NumberFunctionTree cos() {
        return operate(ElementalFunctions.COS.get());
    }

    @Override
    public NumberFunctionTree arcsin() {
        return operate(ElementalFunctions.ARCSIN.get());
    }

    @Override
    public NumberFunctionTree arccos() {
        return operate(ElementalFunctions.ARCCOS.get());
    }

    @Override
    public NumberFunctionTree tan() {
        return operate(ElementalFunctions.TAN.get());
    }

    @Override
    public NumberFunctionTree arctan() {
        return operate(ElementalFunctions.ARCTAN.get());
    }

    @Override
    public NumberFunctionTree sec() {
        // Assuming ElementalFunctions.SEC exists. If not, it would be a composite operation like 1 / cos(x).
        return operate(ElementalFunctions.SEC.get());
    }

    @Override
    public NumberFunctionTree cosec() {
        // Assuming ElementalFunctions.COSEC exists.
        return operate(ElementalFunctions.COSEC.get());
    }

    @Override
    public NumberFunctionTree cotan() {
        // Assuming ElementalFunctions.COTAN exists.
        return operate(ElementalFunctions.COTAN.get());
    }

    @Override
    public NumberFunctionTree sinh() {
        return operate(ElementalFunctions.SINH.get());
    }

    @Override
    public NumberFunctionTree cosh() {
        return operate(ElementalFunctions.COSH.get());
    }

    @Override
    public NumberFunctionTree arcsinh() {
        return operate(ElementalFunctions.ARCSINH.get());
    }

    @Override
    public NumberFunctionTree arccosh() {
        return operate(ElementalFunctions.ARCCOSH.get());
    }

    @Override
    public NumberFunctionTree tanh() {
        return operate(ElementalFunctions.TANH.get());
    }

    @Override
    public NumberFunctionTree cotanh() {
        return operate(ElementalFunctions.COTANH.get());
    }

    @Override
    public NumberFunctionTree arctanh() {
        return operate(ElementalFunctions.ARCTANH.get());
    }

    @Override
    public NumberFunctionTree sech() {
        return operate(ElementalFunctions.SECH.get());
    }

    @Override
    public NumberFunctionTree cosech() {
        return operate(ElementalFunctions.COSECH.get());
    }

    @Override
    public NumberFunctionTree arcsech() {
        return operate(ElementalFunctions.ARCSECH.get());
    }

    @Override
    public NumberFunctionTree arccosech() {
        return operate(ElementalFunctions.ARCCOSECH.get());
    }

    @Override
    public NumberFunctionTree sum(NumberFunctionTree other) {
        NumberFunctionTree function = new NumberFunctionTree(SUM.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public NumberFunctionTree dif(NumberFunctionTree other) {
        NumberFunctionTree function = new NumberFunctionTree(DIF.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public NumberFunctionTree mult(NumberFunctionTree other) {
        NumberFunctionTree function = new NumberFunctionTree(MULT.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public NumberFunctionTree div(NumberFunctionTree other) {
        NumberFunctionTree function = new NumberFunctionTree(DIV.get());
        function.insert(2, this);
        function.insert(3, other);
        return function;
    }

    @Override
    public NumberFunctionTree sqrt() {
        return operate(SQRT.get());
    }

    @Override
    public NumberFunctionTree op() {
        return operate(OP.get());
    }

    @Override
    public NumberFunctionTree inv() {
        return ONE.get().getTree().div(this);
    }

    @Override
    public NumberFunctionTree one() {
        return operate(ONE.get());
    }

    @Override
    public NumberFunctionTree zero() {
        return operate(ZERO.get());
    }

    @Override
    public NumberFunctionTree clone() {
        return new NumberFunctionTree(this.repr);
    }

    @Override
    public NumberFunctionTree abs() {
        return operate(ElementalFunctions.ABS.get());
    }

    @Override
    public NumberFunctionTree scale(double factor) {
        NumberFunctionTree result = new NumberFunctionTree(SCALE.apply(factor));
        result.insert(2, this);
        return result;
    }

    @Override
    public boolean isEqualTo(NumberFunctionTree other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(NumberFunctionTree other) {
        return false;
    }

    @Override
    public boolean isLessThan(NumberFunctionTree other) {
        return false;
    }

}
