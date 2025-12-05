package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.algebra.objects.ConstantNumber;
import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;

import java.util.TreeMap;

public class FunctionSuccession extends NumberFunctionTree {

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

    private NumberFunctionTree customCompute(int n, int position){
        if(this.repr.get(position) instanceof ElementalFunction<?> elementalFunction && !elementalFunction.getOperator().equals(this.indexVariable.getLaTexRepr())){
            NumberFunctionTree nextCompute = customCompute(n, 2 * position);
            NumberFunctionTree nnextCompute = customCompute(n, 2 * position + 1);
            if(nextCompute.repr.get(1).getOperator().equals("constant number")){

            }
        }
        return new ConstantNumber<Double>().CONSTANTNUMBER.apply(Integer.valueOf(n).doubleValue()).getTree();
    }


}
