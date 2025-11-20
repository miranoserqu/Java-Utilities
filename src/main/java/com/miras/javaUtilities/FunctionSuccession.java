package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.TreeMap;
import java.util.function.Function;

public class FunctionSuccession extends FunctionTree {

    private final Variable indexVariable;

    public FunctionSuccession(){
        this(new Variable(0, "n"));
    }

    public FunctionSuccession(Variable indexVariable){
        super();
        this.indexVariable = indexVariable;
    }

    public FunctionSuccession(TreeMap<Integer, ElementalFunction<?>> repr, Variable indexVariable){
        super(repr);
        this.indexVariable = indexVariable;
    }

    public FunctionSuccession(TreeMap<Integer, ElementalFunction<?>> repr){
        super(repr);
        this.indexVariable = new Variable(0, "n");
    }

    public int apply(int term, int... values){
        int[] finalArray = new int[values.length + 1];
        System.arraycopy(values, 0, finalArray, 1, values.length);
        finalArray[0] = term;
        return this.apply(finalArray);
    }

    public double apply(int term, double... values){
        double[] finalArray = new double[values.length + 1];
        System.arraycopy(values, 0, finalArray, 1, values.length);
        finalArray[0] = term;
        return this.apply(finalArray);
    }

    public float apply(int term, float... values){
        float[] finalArray = new float[values.length + 1];
        System.arraycopy(values, 0, finalArray, 1, values.length);
        finalArray[0] = term;
        return this.apply(finalArray);
    }

    public FunctionTree compute(int n){

    }

    private FunctionTree customCompute(int n, int position){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction && !elementalFunction.getOperator().equals(this.indexVariable.getLaTexRepr())){
            FunctionTree nextCompute = customCompute(n, 2 * position);
            FunctionTree nnextCompute = customCompute(n, 2 * position + 1);
            if(nextCompute.repr.get(1).getOperator().equals("constant number")){

            }
        }
        return new ConstantNumber<Double>().CONSTANTNUMBER.apply(Integer.valueOf(n).doubleValue()).getTree();
    }


}
