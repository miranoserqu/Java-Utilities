package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.Algebra.Fields.Vector;

import java.util.TreeMap;
import java.util.function.Function;

import static com.miras.javaUtilities.ElementalFunctions.*;

public class GeneralFunctionTree implements ElementalFunction<GeneralFunctionTree>, Operator<GeneralFunctionTree> {

    private int imageDim;

    final FunctionTree[] repr;

    public GeneralFunctionTree(int imageDim){
        this.imageDim = imageDim;

        this.repr = new FunctionTree[imageDim];
    }

    public GeneralFunctionTree(int imageDim, FunctionTree[] repr){
        this.imageDim = imageDim;

        this.repr = repr;
    }

    @Override
    public String toString() {
        String str = "[";

        for(int i = 0; i < this.imageDim; i++){
            str = str.concat(this.repr[i].toString());
            if(i != (this.imageDim - 1)){
                str = str.concat(", ");
            }
        }

        return str.concat("]");
    }

    @Override
    public String getOperator() {
        return null;
    }

    public GeneralFunctionTree getPartialDerivative(int variable) {
        FunctionTree[] newRepr = new FunctionTree[this.imageDim];

        for(int i = 0; i < this.imageDim; i++){
            newRepr[i] = this.repr[i].getPartialDerivative(variable);
        }

        return new GeneralFunctionTree(this.imageDim, newRepr);
    }

    @Override
    public String getLaTex() {
        String str = "[";

        for(int i = 0; i < this.imageDim; i++){
            str = str.concat(this.repr[i].getLaTex());
            if(i != (this.imageDim - 1)){
                str = str.concat(", ");
            }
        }

        return str.concat("]");
    }

    @Override
    public FunctionTree getDerivative() {
        return this.getPartialDerivative(0).repr[0];
    }

    @Override
    public <R extends Field<R>> R apply(R... values) {
        return null;
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

    public Float[] applyVector(float... values){
        Float[] floats = new Float[this.imageDim];
        for(int  i = 0; i < this.imageDim; i++){
            floats[i] = this.repr[i].apply(values);
        }
        return floats;
    }

    public Integer[] applyVector(int... values){
        Integer[] integers = new Integer[this.imageDim];
        for(int  i = 0; i < this.imageDim; i++){
            integers[i] = this.repr[i].apply(values);
        }
        return integers;
    }

    public Double[] applyVector(double... values){
        Double[] doubles = new Double[this.imageDim];
        for(int  i = 0; i < this.imageDim; i++){
            doubles[i] = this.repr[i].apply(values);
        }
        return doubles;
    }

    public <R extends Field<R>> R[] applyVector(R... values){
        Field[] fields = new Field[this.imageDim];
        for(int  i = 0; i < this.imageDim; i++){
            fields[i] = this.repr[i].apply(values);
        }
        return (R[]) fields;
    }

    public GeneralFunctionTree simplify(){
        FunctionTree[] newRepr = new FunctionTree[this.imageDim];

        for(int i = 0; i < this.imageDim; i++){
            newRepr[i] = this.repr[i].simplify();
        }

        return new GeneralFunctionTree(this.imageDim, newRepr);
    }

    @Override
    public int compareTo(GeneralFunctionTree o) {
        return 0;
    }

    @Override
    public GeneralFunctionTree clone() {
        return ElementalFunction.super.clone();
    }
}
