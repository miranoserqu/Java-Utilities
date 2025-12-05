package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;

public class GeneralFunctionTree implements ElementalFunction<GeneralFunctionTree> {

    private int imageDim;

    final NumberFunctionTree[] repr;

    public GeneralFunctionTree(int imageDim){
        this.imageDim = imageDim;

        this.repr = new NumberFunctionTree[imageDim];
    }

    public GeneralFunctionTree(int imageDim, NumberFunctionTree[] repr){
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

    public GeneralFunctionTree getPartialDerivative(Variable variable) {
        NumberFunctionTree[] newRepr = new NumberFunctionTree[this.imageDim];

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
    public NumberFunctionTree getDerivative() {
        return this.getPartialDerivative(new Variable(0, "x")).repr[0];
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

    public GeneralFunctionTree simplify(){
        NumberFunctionTree[] newRepr = new NumberFunctionTree[this.imageDim];

        for(int i = 0; i < this.imageDim; i++){
            newRepr[i] = this.repr[i].simplify();
        }

        return new GeneralFunctionTree(this.imageDim, newRepr);
    }

    @Override
    public GeneralFunctionTree sum(GeneralFunctionTree other) {
        return null;
    }

    @Override
    public GeneralFunctionTree dif(GeneralFunctionTree other) {
        return null;
    }

    @Override
    public GeneralFunctionTree mult(GeneralFunctionTree other) {
        return null;
    }

    @Override
    public GeneralFunctionTree div(GeneralFunctionTree other) {
        return null;
    }

    @Override
    public GeneralFunctionTree sqrt() {
        return null;
    }

    @Override
    public GeneralFunctionTree one() {
        return null;
    }

    @Override
    public GeneralFunctionTree zero() {
        return null;
    }

    @Override
    public GeneralFunctionTree op() {
        return null;
    }

    @Override
    public GeneralFunctionTree inv() {
        return null;
    }

    @Override
    public GeneralFunctionTree clone() {
        return null;
    }

    @Override
    public GeneralFunctionTree abs() {
        return null;
    }

    @Override
    public GeneralFunctionTree scale(double factor) {
        return null;
    }

    @Override
    public boolean isEqualTo(GeneralFunctionTree other) {
        return false;
    }

    @Override
    public boolean isGreaterThan(GeneralFunctionTree other) {
        return false;
    }

    @Override
    public boolean isLessThan(GeneralFunctionTree other) {
        return false;
    }
}
