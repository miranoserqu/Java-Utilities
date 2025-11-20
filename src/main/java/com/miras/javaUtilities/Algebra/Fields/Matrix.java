/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra.Fields;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.MultilinealSpace;
import com.miras.javaUtilities.WrongDimensionException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

public class Matrix<E extends Number> implements MultilinealSpace<Matrix<E>, E>, Field<Matrix<E>> {
    
    private E[][] matrix;
    private E[][] Tmatrix;
    private E prototype;
    private int n;
    private int m;
    
    public Matrix getPrototype(){
        return new Matrix();
    }
    
    public E[][] getMatrix(){
        return matrix;
    }
    
    public E[][] getMatrix(IntFunction<E[][]> generator){
        return (E[][]) Arrays.copyOf(matrix, n, generator.apply(n).getClass());
    }

    public E[][] getTmatrix() {
        return Tmatrix;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }
    
    public E get(int i, int j){
        return this.matrix[i][j];
    }
    
    public Matrix(int n, int m){
        this.n = n;
        this.m = m;
    }
    
    public Matrix(E[][] mat, int n, int m){
        this.prototype = mat[0][0];
        this.matrix = mat; //(E[][]) Array.newInstance(prototype.getClass(), n, m);
        this.Tmatrix = this.trasponerArr();
        this.n = n;
        this.m = m;
    }
    
    Matrix(List<List<E>> mat, int n, int m){
        this.prototype = mat.getFirst().getFirst();
        this.matrix = (E[][]) Array.newInstance(prototype.getClass(), n, m);
        for(int i = 0; i < mat.size(); i++){
            for(int j = 0; j < mat.get(i).size(); j++){
                this.matrix[i][j] = mat.get(i).get(j);
            }
        }
    }
    
    private Matrix(){}

    private <T extends Number> E getNumber(T num){
        if(num instanceof double doubleNum){
            return (E) Double.valueOf(doubleNum);
        } else if(num instanceof int intNum){
            return (E) Integer.valueOf(intNum);
        } else if(num instanceof float floatNum){
            return (E) Float.valueOf(floatNum);
        }
        return (E) num;
    }
    
    private E[][] trasponerArr(){
        E[][] TMatrix = (E[][]) Array.newInstance(this.prototype.getClass(), m, n);
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = (E) this.matrix[i][j];
            }
        }
        return TMatrix;
    }
    
    public Matrix<E> trasponer(){
        E[][] TMatrix = (E[][]) Array.newInstance(this.prototype.getClass(), m, n);
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = this.matrix[i][j];
            }
        }
        return new Matrix<>(TMatrix, m, n);
    }
    
    public Matrix<E> Triangular(){
        Matrix<E> Triangular = new Matrix<>(this.matrix, this.n, this.m);
        for(int j = 0; j < Triangular.m; j++){
            for(int i = j + 1; i < Triangular.n; i++){
                if(Triangular.matrix[i][j].doubleValue() == 0){
                    int currenRow = j;
                    E maxRow = Triangular.matrix[j][j];
                    for(int ip = 0; ip < Triangular.n; ip++){
                        if(Triangular.matrix[ip][j].doubleValue() > maxRow.doubleValue()){
                            currenRow = ip;
                        }
                    }
                    E factor = Field.divNum(Triangular.matrix[i][j], Triangular.matrix[j][j]);
                    for(int jp = 0; jp < m; jp++){
                        Triangular.matrix[i][jp] = Field.restNum(Triangular.matrix[i][jp], Field.multNum(Triangular.matrix[j][jp], factor));
                    }
                }
            }
        }
        return Triangular;
    }
    
    public E getDet(){
        double det = 1;
        Matrix<E> Triangular = this.Triangular();
        for(int j = 0; j < this.m; j++){
            det *= Triangular.matrix[j][j].doubleValue();
        }
        return getNumber(det);
    }

    public void setMatrix(E[][] matrix) {
        for(int i = 0; i < matrix.length; i++){
            this.matrix[i] = Arrays.copyOf(matrix[i], m);
        }
    }

    public Matrix<E> one(int n){
        
        E[][] I = (E[][]) Array.newInstance(this.prototype.getClass(), n, n);

        for(E[] x: I){
            Arrays.fill(x, initialize(0.0));
        }
        
        for(int i = 0; i < n; i++){
            I[i][i] = initialize(1.0);
        }
        
        return new Matrix<>(I, n, n);
    }
    
    @Override
    public Matrix<E> sum(Matrix<E> other){
        if(this.n != other.n || this.m != other.m){
            throw new WrongDimensionException("Las dimensiones de las matrices deben coincidir");
        }
        
        E[][] C = (E[][]) Array.newInstance(this.prototype.getClass(), n, m);
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.m; j++){
                C[i][j] = Field.sumNum(this.getMatrix()[i][j], other.getMatrix()[i][j]);
            }
        }
        
        return new Matrix<>(C, this.n, this.m);
    }
    
    public boolean IsEqualTo(Matrix<E> other){
        
        if(other.n != this.n || other.m != this.m){
            return false;
        }
        
        for(int i = 0; i < other.n; i++){
            for(int j = 0; j < other.n; j++){
                if(!Objects.equals(this.matrix[i][j], other.matrix[i][j])){
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public Matrix<E> mult(Matrix<E> other){
        
        if(this.m != other.n){
            throw new WrongDimensionException("Las dimensiones de las matrices no son las adecuadas");
        }
        
        E[][] c = (E[][]) Array.newInstance(this.prototype.getClass(), this.n, other.m);
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < other.m; j++){
                E element;
                element = initialize((double) 0);
                for(int k = 0; k < other.n; k++){
                    element = Field.sumNum(element, Field.multNum(this.matrix[i][k], other.matrix[k][j])) ;
                }
                c[i][j] = element;
            }
        }
        
        return new Matrix<E>(c, this.n, other.m);
    }

    private E initialize(Double num){
        if(this.prototype instanceof Double){
            return (E) Double.valueOf(num.doubleValue());
        } else if(this.prototype instanceof Integer){
            return (E) Integer.valueOf(num.intValue());
        } else{
            return (E) Float.valueOf(num.floatValue());
        }
    }
    
    @Override
    public Matrix<E> scale(E factor){
        
        E[][] a = (E[][]) Array.newInstance(this.prototype.getClass(), this.n, this.m);
        
        for(int k = 0; k < this.n; k++){
            System.arraycopy(this.matrix[k], 0, a[k], 0, this.m);
        }
        
        for(E[] e : a){
            for(E ep : e){
                ep = Field.multNum(ep, factor);
            }
        }
        
        return new Matrix<>(a, this.n, this.m);
    }
    
    public Matrix<E> Adj(boolean traspuesta){
        
        E[][] a = (E[][]) Array.newInstance(this.prototype.getClass(), this.n, this.m);
        E[][] b = (E[][]) Array.newInstance(this.prototype.getClass(), this.n, this.m);
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.m; j++){
                for(int k = 0; k < this.n; k++){
                    if(traspuesta){
                        System.arraycopy(this.Tmatrix[k], 0, a[k], 0, this.n);
                    } else{
                        System.arraycopy(this.matrix[k], 0, a[k], 0, this.n);
                    }
                }
                for(int ip = 0; ip < this.n; ip++){
                    a[ip][j] = initialize((double) 0);
                }
                for(int jp = 0; jp < this.m; jp++){
                    a[i][jp] = initialize((double) 0);
                }
                a[i][j] = initialize(1.0);
                Matrix<E> menor = new Matrix<>(a, this.n, this.m);
                b[i][j] = (i+j)%2 == 0 ? menor.getDet() : Field.restNum(initialize((double) 0), menor.getDet());
            }
        }
        return new Matrix<>(b, this.n, this.m);
    }
    
    @Override
    public Matrix<E> inv(){
        return this.Adj(true).scale(Field.divNum(initialize(1.0), this.getDet()));
    }
    
    @Override
    public Matrix<E> div(Matrix<E> B){
        return mult(B.inv());
    }


    public Matrix<E> zero(int n, int m){
        
        E[][] c = (E[][]) Array.newInstance(this.prototype.getClass(), this.n, this.m);
        
        for(int k = 0; k < n; k++){
            Arrays.fill(c[k], initialize((double) 0));
        }
        
        return new Matrix<>(c, n, m);
    }
    
    @Override
    public String toString(){
        return Arrays.toString(Arrays.stream(this.matrix).map(Arrays::toString).toArray());
    }
    
    @Override
    public Matrix<E> applyInComponents(Function<E, E> func){
        List<List<E>> A = new ArrayList<>(n);
        for(int i = 0; i < n; i++){
            A.add(Arrays.stream(this.matrix[i]).map(func).toList());
        }
        return new Matrix<>(A, this.n, this.m);
    }
    
    public Vector<E> toVector(){
        if(this.n == 1){
            return new Vector<>(this.matrix[0]);
        } else if(this.m == 1){
            E[] v = (E[]) Array.newInstance(this.prototype.getClass(), n);
            for(int j = 0; j < this.n; j++){
                v[j] = this.matrix[j][0];
            }
            return new Vector<>(v);
        }
        throw new WrongDimensionException("La matriz solo puede tener una fila o una columna");
    }
    
    @Override
    public E mod(){
        E sum = initialize((double) 0);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                sum = Field.sumNum(sum, initialize(Math.pow(matrix[i][j].doubleValue(), 2)));
            }
        }
        return getNumber(initialize(Math.sqrt(sum.doubleValue())));
    }

    @Override
    public Matrix<E> dif(Matrix<E> other) {
        return this.sum(other.op());
    }

    @Override
    public Matrix<E> op() {
        return this.scale(initialize(-1.0));
    }

    @Override
    public Matrix<E> one() {
        return this.one(this.n);
    }

    @Override
    public Matrix<E> zero() {
        return this.zero(this.n, this.m);
    }

    @Override
    public Matrix<E> clone() {
        return new Matrix<>(this.matrix, this.n, this.m);
    }

    @Override
    public Matrix<E> abs() {
        return this.applyInComponents(x -> initialize(Math.abs(x.doubleValue())));
    }

    @Override
    public Matrix<E> scale(double factor) {
        return this.scale(initialize(factor));
    }

    @Override
    public boolean isEqualTo(Matrix<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(Matrix<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(Matrix<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(Matrix<E> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Matrix<E> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public E dotProduct() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Matrix<E> ln() {
        return this.applyInComponents(x -> initialize(Math.log(x.doubleValue())));
    }

    @Override
    public Matrix<E> log() {
        return this.applyInComponents(x -> initialize(Math.log10(x.doubleValue())));
    }

    @Override
    public Matrix<E> exp() {
        return this.applyInComponents(x -> initialize(Math.exp(x.doubleValue())));
    }

    @Override
    public Matrix<E> expNum(double base) {
        return this.applyInComponents(x -> initialize(Math.pow(base, x.doubleValue())));
    }

    @Override
    public Matrix<E> expGen(double exponent) {
        Matrix<E> matrix = this.clone();
        for(int i = 0; i < exponent; i++){
            matrix = matrix.mult(this);
        }
        return matrix;
    }

    @Override
    public Matrix<E> sin() {
        return this.applyInComponents(x -> initialize(Math.sin(x.doubleValue())));
    }

    @Override
    public Matrix<E> cos() {
        return this.applyInComponents(x -> initialize(Math.cos(x.doubleValue())));
    }

    @Override
    public Matrix<E> arcsin() {
        return this.applyInComponents(x -> initialize(Math.asin(x.doubleValue())));
    }

    @Override
    public Matrix<E> arccos() {
        // Math.acos is the correct method for arccos (inverse cosine)
        return this.applyInComponents(x -> initialize(Math.acos(x.doubleValue())));
    }

    @Override
    public Matrix<E> tan() {
        // Math.tan is the correct method for tan (tangent)
        return this.applyInComponents(x -> initialize(Math.tan(x.doubleValue())));
    }

    @Override
    public Matrix<E> arctan() {
        // Math.atan is the correct method for arctan (inverse tangent)
        return this.applyInComponents(x -> initialize(Math.atan(x.doubleValue())));
    }

    @Override
    public Matrix<E> sec() {
        // sec(x) = 1 / cos(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.cos(x.doubleValue())));
    }

    @Override
    public Matrix<E> cosec() {
        // cosec(x) = 1 / sin(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.sin(x.doubleValue())));
    }

    @Override
    public Matrix<E> cotan() {
        // cotan(x) = 1 / tan(x) = cos(x) / sin(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.tan(x.doubleValue())));
    }

    @Override
    public Matrix<E> sinh() {
        // Math.sinh is the correct method for hyperbolic sine
        return this.applyInComponents(x -> initialize(Math.sinh(x.doubleValue())));
    }

    @Override
    public Matrix<E> cosh() {
        // Math.cosh is the correct method for hyperbolic cosine
        return this.applyInComponents(x -> initialize(Math.cosh(x.doubleValue())));
    }

    @Override
    public Matrix<E> arcsinh() {
        // arcsinh(x) = ln(x + sqrt(x^2 + 1))
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            return initialize(Math.log(val + Math.sqrt(val * val + 1.0)));
        });
    }

    @Override
    public Matrix<E> arccosh() {
        // arccosh(x) = ln(x + sqrt(x^2 - 1))
        // Domain: x >= 1
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            if (val < 1.0) {
                throw new IllegalArgumentException("arccosh(x) is only defined for x >= 1. Encountered: " + val);
            }
            return initialize(Math.log(val + Math.sqrt(val * val - 1.0)));
        });
    }

    @Override
    public Matrix<E> tanh() {
        // Math.tanh is the correct method for hyperbolic tangent
        return this.applyInComponents(x -> initialize(Math.tanh(x.doubleValue())));
    }

    @Override
    public Matrix<E> cotanh() {
        return this.applyInComponents(x -> initialize(1 / Math.tanh(x.doubleValue())));
    }

    @Override
    public Matrix<E> arctanh() {
        // arctanh(x) = 0.5 * ln((1 + x) / (1 - x))
        // Domain: -1 < x < 1
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            if (val <= -1.0 || val >= 1.0) {
                throw new IllegalArgumentException("arctanh(x) is only defined for -1 < x < 1. Encountered: " + val);
            }
            return initialize(0.5 * Math.log((1.0 + val) / (1.0 - val)));
        });
    }

    @Override
    public Matrix<E> sech() {
        // sech(x) = 1 / cosh(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.cosh(x.doubleValue())));
    }

    @Override
    public Matrix<E> cosech() {
        // cosech(x) = 1 / sinh(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.sinh(x.doubleValue())));
    }

    @Override
    public Matrix<E> arcsech() {
        // arcsech(x) = arccosh(1/x) = ln((1 + sqrt(1 - x^2)) / x)
        // Domain: 0 < x <= 1
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            if (val <= 0.0 || val > 1.0) {
                throw new IllegalArgumentException("arcsech(x) is only defined for 0 < x <= 1. Encountered: " + val);
            }
            return initialize(Math.log((1.0 + Math.sqrt(1.0 - val * val)) / val));
        });
    }

    @Override
    public Matrix<E> arccosech() {
        // arccosech(x) = arcsinh(1/x) = ln((1 + sqrt(1 + x^2)) / x) for x != 0
        // Simplified: ln(1/x + sqrt(1/x^2 + 1))
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            if (val == 0.0) {
                throw new IllegalArgumentException("arccosech(x) is not defined for x = 0.");
            }
            double invX = 1.0 / val;
            return initialize(Math.log(invX + Math.sqrt(invX * invX + 1.0)));
        });
    }

}