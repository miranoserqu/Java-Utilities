/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class Matrix<E extends Number> implements Field<Matrix<E>, E> {
    
    private E[][] matrix;
    private E[][] Tmatrix;
    private int n;
    private int m;
    
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
    
    Matrix(int n, int m){
        this.matrix = (E[][]) new Number[n][m];
        this.Tmatrix = this.trasponerArr();
        this.n = n;
        this.m = m;
    }
    
    Matrix(E[][] mat, int n, int m){
        this.matrix = (E[][]) new Number[n][m];
        this.setMatrix(mat);
        this.Tmatrix = this.trasponerArr();
        this.n = n;
        this.m = m;
    }
    
    Matrix(List<List<E>> mat, int n, int m){
        this.matrix = (E[][]) new Number[n][m];
        for(int i = 0; i < mat.size(); i++){
            for(int j = 0; j < mat.get(i).size(); j++){
                this.matrix[i][j] = mat.get(i).get(j);
            }
        }
    }
    
    Matrix(){
        
    }
    
    private E[][] trasponerArr(){
        E[][] TMatrix = (E[][]) new Number[this.m][this.n];
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = (E) this.matrix[i][j];
            }
        }
        return TMatrix;
    }
    
    public Matrix<E> trasponer(){
        E[][] TMatrix = (E[][]) new Number[this.m][this.n];
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = (E) this.matrix[i][j];
            }
        }
        return new Matrix((E[][]) TMatrix, m, n);
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
        return (E) Double.valueOf(det);
    }

    public void setMatrix(E[][] matrix) {
        for(int i = 0; i < matrix.length; i++){
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, matrix[i].length);
        }
    }

    public Matrix<E> one(int n){
        
        E[][] I = (E[][]) new Number[n][n];
        for(E[] x: I){
            Arrays.fill(x, (E) Integer.valueOf(0));
        }
        
        for(int i = 0; i < n; i++){
            I[i][i] = (E) Integer.valueOf(1);
        }
        
        return new Matrix<>(I, n, n);
    }
    
    @Override
    public Matrix<E> sum(Matrix<E> other){
        if(this.n != other.n || this.m != other.m){
            throw new WrongDimensionException("Las dimensiones de las matrices deben coincidir");
        }
        
        E[][] C = (E[][]) new Number[this.n][this.m];
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.m; j++){
                C[i][j] = Field.sumNum(this.getMatrix()[i][j], other.getMatrix()[i][j]);
            }
        }
        
        return new Matrix(C, this.n, this.m);
    }
    
    public Matrix<E> rest(Matrix<E> other){
        
        int maxN = this.n > other.n ? this.n : other.n;
        int maxM = this.m > other.m ? this.m : other.m;
        
        E[][] a = (E[][]) new Number[maxN][maxM];
        E[][] b = (E[][]) new Number[maxN][maxM];
        
        for(int i = 0; i < maxN; i++){
            System.arraycopy(this.getMatrix()[i], 0, a[i], 0, n);
            System.arraycopy(other.getMatrix()[i], 0, b[i], 0, n);
        }
        
        for(int i = 0; i < maxN; i++){
            for(int j = 0; j < maxM; j++){
                if(a[i][j] == null){
                    a[i][j] = b[i][j];
                } else if(b[i][j] != null){
                    a[i][j] = Field.restNum(a[i][j], b[i][j]);
                }
            }
        }
        return new Matrix<E>(a, maxN, maxM);
    }
    
    public boolean IsEqualTo(Matrix<E> other){
        
        if(other.n != this.n || other.m != this.m){
            return false;
        }
        
        for(int i = 0; i < other.n; i++){
            for(int j = 0; j < other.n; j++){
                if(this.matrix[i][j] != other.matrix[i][j]){
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
        
        E[][] c = (E[][]) new Number[this.n][other.m];
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < other.m; j++){
                E element = (E) Integer.valueOf(0);
                for(int k = 0; k < other.n; k++){
                    element = Field.sumNum(element, Field.multNum(this.matrix[i][k], other.matrix[k][j])) ;
                }
                c[i][j] = element;
            }
        }
        
        return new Matrix<E>(c, this.n, other.m);
    }
    
    @Override
    public Matrix<E> multExt(E n){
        
        E[][] a = (E[][]) new Number[this.getN()][this.getM()];
        
        for(int k = 0; k < this.n; k++){
            System.arraycopy(this.matrix[k], 0, a[k], 0, this.m);
        }
        
        for(E[] e : a){
            for(E ep : e){
                ep =Field. multNum(ep, n);
            }
        }
        
        return new Matrix<E>(a, this.n, this.m);
    }
    
    public Matrix<E> Adj(boolean traspuesta){
        
        E[][] a = (E[][]) new Number[this.n][this.m];
        E[][] b = (E[][]) new Number[this.n][this.m];
        
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
                    a[ip][j] = (E) Integer.valueOf(0);
                }
                for(int jp = 0; jp < this.m; jp++){
                    a[i][jp] = (E) Integer.valueOf(0);
                }
                a[i][j] = (E) Integer.valueOf(1);
                Matrix<E> menor = new Matrix<>(a, this.n, this.m);
                b[i][j] = (i+j)%2 == 0 ? menor.getDet() : Field.restNum((E) Integer.valueOf(0), menor.getDet());
            }
        }
        return new Matrix<>(b, this.n, this.m);
    }
    
    @Override
    public Matrix<E> inv(){
        return this.Adj(true).multExt(Field.divNum((E) Integer.valueOf(1), this.getDet()));
    }
    
    @Override
    public Matrix<E> div(Matrix<E> B){
        return mult(B.inv());
    }
    
    public Matrix<E> zero(int n, int m){
        
        E[][] c = (E[][]) new Number[n][m];
        
        for(int k = 0; k < n; k++){
            Arrays.fill(c[k], (E) Integer.valueOf(0));
        }
        
        return new Matrix<>(c, n, m);
    }
    
    @Override
    public String toString(){
        return Arrays.toString(Arrays.asList(this.matrix).stream().map(arr -> Arrays.toString(arr)).toArray());
    }
    
    public Matrix applyInComponents(Function<E, E> func){
        E[][] A = (E[][]) new Number[this.n][this.m];
        for(int i = 0; i < A.length; i++){
            A[i] = (E[]) Arrays.asList(this.matrix[i]).stream().map(func).toArray(Number[]::new);
        }
        return new Matrix(A, this.n, this.m);
    }
    
    public Vector<E> toVector(){
        if(this.n == 1){
            return new Vector<E>(this.matrix[0]);
        } else if(this.m == 1){
            E[] v = (E[]) new Number[this.n];
            for(int j = 0; j < this.n; j++){
                v[j] = this.matrix[j][0];
            }
            return new Vector<E>(v);
        }
        throw new WrongDimensionException("La matriz solo puede tener una fila o una columna");
    }
    
    public double getNorm(){
        double sum = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                sum += Math.pow(matrix[i][j].doubleValue(), 2);
            }
        }
        return Math.sqrt(sum);
    }

    @Override
    public Matrix<E> dif(Matrix<E> other) {
        return this.sum(other.op());
    }

    @Override
    public Matrix<E> op() {
        return this.multExt((E) Integer.valueOf(-1));
    }

    @Override
    public Matrix<E> one() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Matrix<E> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Matrix<E> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
}