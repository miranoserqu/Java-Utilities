package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.Algebra.Fields.Vector;

import java.util.TreeMap;

import static com.miras.javaUtilities.ElementalFunctions.*;
import static com.miras.javaUtilities.ElementalFunctions.DIV;
import static com.miras.javaUtilities.ElementalFunctions.MULT;
import static com.miras.javaUtilities.ElementalFunctions.ONE;
import static com.miras.javaUtilities.ElementalFunctions.OP;
import static com.miras.javaUtilities.ElementalFunctions.VARIABLE;
import static com.miras.javaUtilities.ElementalFunctions.ZERO;

public class GeneralFunctionTree implements ElementalFunction<GeneralFunctionTree>, Operator<GeneralFunctionTree> {

    private int preimageDim;
    private int imageDim;

    final TreeMap<Integer, ElementalFunction<?>>[] repr;

    public GeneralFunctionTree(int preimageDim, int imageDim){
        this.preimageDim = preimageDim;
        this.imageDim = imageDim;

        this.repr = new TreeMap[imageDim];
    }

    public GeneralFunctionTree(int preimageDim, int imageDim, TreeMap<Integer, ElementalFunction<?>>[] repr){
        this.preimageDim = preimageDim;
        this.imageDim = imageDim;

        this.repr = repr;
    }

    @Override
    public String toString() {
        String str = "[";

        for(int i = 0; i < this.imageDim; i++){
            str = str.concat(customToString(1, i, "x"));
        }

        return str.concat("]");
    }

    @Override
    public String getOperator() {
        return super.getOperator();
    }

    public String customToString(int position, int coord, String variable) {
        if(this.repr[coord].get(position) == null){
            return variable;
        }
        if(this.repr[coord].get(position).equals(SUM.get())){
            return customToString(position * 2, variable) + " + " + customToString(position * 2 + 1, variable);
        } else if(this.repr[coord].get(position).equals(DIF.get())){
            return customToString(position * 2, variable) + " - " + customToString(position * 2 + 1, variable);
        } else if(this.repr[coord].get(position).equals(MULT.get())){
            ElementalFunction<?> leftChild = this.repr[coord].get(position * 2);
            ElementalFunction<?> rightChild = this.repr[coord].get(position * 2 + 1);

            if(leftChild != null && leftChild.equals(ONE.get())) {
                return customToString(position * 2 + 1, variable);
            } else if(rightChild != null && rightChild.equals(ONE.get())) {
                return customToString(position * 2, variable);
            } else{
                return customToString(position * 2, variable) + " * " + customToString(position * 2 +1, variable);
            }
        } else if(this.repr[coord].get(position).equals(DIV.get())){
            return customToString(position * 2, variable) + " / " + customToString(position * 2 + 1, variable);
        } else if(this.repr[coord].get(position).equals(VARIABLE.get())){
            return variable;
        } else if(this.repr[coord].get(1).equals(ONE.get())){
            return "1";
        } else if(this.repr[coord].get(1) instanceof FunctionTreeBlock functionTreeBlock){
            return functionTreeBlock.toString();
        }
        String operator = this.repr[coord].get(position).getOperator();
        return operator.substring(0, operator.length() - 1) + customToString(position * 2, variable) + operator.charAt(operator.length() - 1);
    }

    @Override
    public GeneralFunctionTree getDerivative(Variable variable) {
        TreeMap[] newRepr = new TreeMap[this.imageDim];

        for(int i = 0; i < this.imageDim; i++){
            newRepr[i] = customGetDerivative(i, variable);
        }

        return new GeneralFunctionTree(this.preimageDim, this.imageDim, newRepr);
    }

    protected TreeMap<Integer, ElementalFunction<?>> getSubTree(int position, int coord) {
        if(this.repr[coord].get(position) == null){
            return VARIABLE.get().getTree().getRepr();
        }
        TreeMap<Integer, ElementalFunction<?>> tree = new TreeMap<>();
        treeExtraction(tree, position, 1, coord);
        return tree;
    }

    protected void treeExtraction(TreeMap<Integer, ElementalFunction<?>> tree, int sourcePosition, int destinationPosition, int coord){
        if(this.repr[coord].get(sourcePosition) == null){
            return;
        } else{
            tree.put(destinationPosition, this.repr[coord].get(sourcePosition));
            treeExtraction(tree, sourcePosition * 2, destinationPosition * 2);
            treeExtraction(tree, sourcePosition * 2 + 1, destinationPosition * 2 + 1);
        }
    }

    private TreeMap<Integer, ElementalFunction<?>> customGetDerivative(int coord, Variable variable){
        if(this.repr[coord].get(1) == null){
            return ONE.get().getTree().getRepr();
        }
        if(this.repr[coord].get(1).equals(SUM.get())){
            return new FunctionTree(this.getSubTree(2, coord)).getDerivative().sum(new FunctionTree(this.getSubTree(3, coord)).getDerivative()).getRepr();
        } else if(this.repr[coord].get(1).equals(DIF.get())){
            return new FunctionTree(this.getSubTree(2, coord)).getDerivative().dif(new FunctionTree(this.getSubTree(3, coord)).getDerivative()).getRepr();
        } else if(this.repr[coord].get(1).equals(MULT.get())){
            return new FunctionTree(this.getSubTree(2, coord)).getDerivative().mult(new FunctionTree(this.getSubTree(3, coord))).sum(this.getSubTree(3).getDerivative().mult(new FunctionTree(this.getSubTree(2, coord)))).getRepr();
        } else if(this.repr[coord].get(1).equals(DIV.get())){
            return new FunctionTree(this.getSubTree(2, coord)).getDerivative().mult(new FunctionTree(this.getSubTree(3, coord))).dif(this.getSubTree(3).getDerivative().mult(new FunctionTree(this.getSubTree(2, coord)))).div(new FunctionTree(this.getSubTree(3, coord)).expGen(2.0)).getRepr();
        } else if(this.repr[coord].get(1).equals(ONE.get())){
            return ZERO.get().getTree().getRepr();
        } else if(this.repr[coord].get(1).equals(ZERO.get())){
            return ZERO.get().getTree().getRepr();
        } else if(this.repr[coord].get(1).equals(VARIABLE.get())) {
            return ONE.get().getTree().getRepr();
        } else if(this.repr[coord].get(1).equals(OP.get())) {
            return new FunctionTree(this.getSubTree(2, coord)).getDerivative().op().getRepr();
        } else if(this.repr[coord].get(1) instanceof FunctionTreeBlock functionTreeBlock){
            if(functionTreeBlock.getVariable().equals(variable)){
                if (this.repr[coord].get(2) == null || this.repr[coord].get(2).equals(VARIABLE.get())) {
                    return functionTreeBlock.getDerivative().getRepr();
                }
                FunctionTree tree = this.repr[coord].get(1).getDerivative();
                tree.simbolicApply(1, new FunctionTree(this.getSubTree(2, coord)));
                tree = tree.mult(new FunctionTree(this.getSubTree(2, coord)).getDerivative());
                return tree.getRepr();
            } else{
                return ZERO.get().getTree().getRepr();
            }
        }else{
            if(this.repr[coord].get(2) == null || this.repr[coord].get(2).equals(VARIABLE.get())){
                return this.repr[coord].get(1).getDerivative().getRepr();
            }
            FunctionTree tree = this.repr[coord].get(1).getDerivative();
            tree.simbolicApply(1, new FunctionTree(this.getSubTree(2, coord)));
            tree = tree.mult(new FunctionTree(this.getSubTree(2, coord)).getDerivative());
            return tree.getRepr();
        }
    }

    @Override
    public void insert(int position, FunctionTree tree) {
        super.insert(position, tree);
    }

    @Override
    public void simbolicApply(int position, FunctionTree tree) {
        super.simbolicApply(position, tree);
    }

    public String customGetLaTex(String variable, int coord) {
        if(this.repr[coord].get(1) == null || this.repr[coord].get(1).equals(VARIABLE.get())){
            return variable;
        }
        if(getNumberArgs(this.repr[coord].get(1)) == 1){
            return this.repr[coord].get(1).getLaTex().replace("arg1", getSubTree(2).getLaTex());
        }
        return this.repr[coord].get(1).getLaTex().replace("arg1", getSubTree(2).getLaTex()).replace("arg2", getSubTree(3).getLaTex());
    }

    @Override
    public String getLaTex() {
        String str = "[";

        for(int i = 0; i < this.imageDim; i++){
            str = str.concat(customGetLaTex("x", i));
        }

        return str.concat("]");
    }

    @Override
    public GeneralFunctionTree getDerivative() {
        return this.getDerivative(new Variable(0, "x"));
    }

    @Override
    public <R extends Field<R>> R apply(R... values) {
        return null;
    }

    @Override
    public double apply(double... values) {
        return super.apply(values);
    }

    @Override
    public int apply(int... values) {
        return super.apply(values);
    }

    @Override
    public float apply(float... values) {
        return super.apply(values);
    }

    @Override
    public int compareTo(GeneralFunctionTree o) {
        return 0;
    }
}
