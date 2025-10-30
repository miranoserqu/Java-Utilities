/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra.Fields;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.MultilinealSpace;
import com.miras.javaUtilities.WrongDimensionException;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <E>
 */
public class MatrixExt<E extends Field<E>> implements MultilinealSpace<MatrixExt<E>, E>, Field<MatrixExt<E>> {
        
    private E[][] matrix;
    private E[][] Tmatrix;
    private int n;
    private int m;
    final int DIM = 2;
    
    public E[][] getMatrix(){
        return matrix;
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
    
    MatrixExt(int n, int m){
        this.matrix = (E[][]) new Field[n][m];
        this.Tmatrix = this.trasponerArr();
        this.n = n;
        this.m = m;
    }
    
    MatrixExt(E[][] mat, int n, int m){
        this.matrix = (E[][]) new Field[n][m];
        this.setMatrix(mat);
        this.Tmatrix = this.trasponerArr();
        this.n = n;
        this.m = m;
    }
    
    MatrixExt(){
        
    }
    
    private E[][] trasponerArr(){
        E[][] TMatrix = (E[][]) new Field[this.m][this.n];
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = (E) this.matrix[i][j];
            }
        }
        return TMatrix;
    }
    
    public MatrixExt<E> trasponer(){
        E[][] TMatrix = (E[][]) new Field[this.m][this.n];
        for (int i = 0; i < this.n; i++){
            for (int j = 0; j < this.m; j++){
                TMatrix[j][i] = (E) this.matrix[i][j];
            }
        }
        return new MatrixExt((E[][]) TMatrix, m, n);
    }
    
    public MatrixExt<E> Triangular(){
        E zero = this.matrix[0][0].zero();
        MatrixExt<E> Triangular = new MatrixExt<>(this.matrix, this.n, this.m);
        for(int j = 0; j < Triangular.m; j++){
            for(int i = j + 1; i < Triangular.n; i++){
                if(Triangular.matrix[i][j].equals(zero)){
                    int currenRow = j;
                    E maxRow = Triangular.matrix[j][j].clone();
                    for(int ip = 0; ip < Triangular.n; ip++){
                        if(Triangular.matrix[ip][j].isGreaterThan(maxRow)){
                            currenRow = ip;
                        }
                    }
                    E factor = Triangular.matrix[i][j].div(Triangular.matrix[j][j]);
                    for(int jp = 0; jp < m; jp++){
                        Triangular.matrix[i][jp] = (Triangular.matrix[i][jp].dif(Triangular.matrix[j][jp].mult(factor)));
                    }
                }
            }
        }
        return Triangular;
    }
    
    public E getDet(){
        E det = this.matrix[0][0].one();
        MatrixExt<E> Triangular = this.Triangular();
        for(int j = 0; j < this.m; j++){
            det = det.mult(Triangular.matrix[j][j]);
        }
        return det;
    }

    public void setMatrix(E[][] matrix) {
        for(int i = 0; i < matrix.length; i++){
            System.arraycopy(matrix[i], 0, this.matrix[i], 0, matrix[i].length);
        }
    }

    public MatrixExt<E> one(int n){
        
        E[][] I = (E[][]) new Field[n][n];
        for(E[] x: I){
            Arrays.fill(x, this.matrix[0][0].zero());
        }
        
        for(int i = 0; i < n; i++){
            I[i][i] = this.matrix[0][0].one();
        }
        
        return new MatrixExt<>(I, n, n);
    }
    
    @Override
    public MatrixExt<E> sum(MatrixExt<E> other){
        if(this.n != other.n || this.m != other.m){
            throw new WrongDimensionException("Las dimensiones de las matrices deben coincidir");
        }
        
        E[][] C = (E[][]) new Field[this.n][other.m];
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < this.m; j++){
                C[i][j] = this.getMatrix()[i][j].sum(other.getMatrix()[i][j]);
            }
        }
        
        return new MatrixExt<>(C, this.n, this.m);
    }
    
    @Override
    public MatrixExt<E> dif(MatrixExt<E> other){
        
        int maxN = Math.max(this.n, other.n);
        int maxM = Math.max(this.m, other.m);
        
        E[][] a = (E[][]) new Field[maxN][maxM];
        E[][] b = (E[][]) new Field[maxN][maxM];
        
        for(int i = 0; i < maxN; i++){
            System.arraycopy(this.getMatrix()[i], 0, a[i], 0, n);
            System.arraycopy(other.getMatrix()[i], 0, b[i], 0, n);
        }
        
        for(int i = 0; i < maxN; i++){
            for(int j = 0; j < maxM; j++){
                if(a[i][j] == null){
                    a[i][j] = b[i][j];
                } else if(b[i][j] != null){
                    a[i][j] = a[i][j].dif(b[i][j]);
                }
            }
        }
        return new MatrixExt<>(a, maxN, maxM);
    }
    
    @Override
    public boolean isEqualTo(MatrixExt<E> other){
        
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
    public MatrixExt<E> mult(MatrixExt<E> other){
        
        if(this.m != other.n){
            throw new WrongDimensionException("Las dimensiones de las matrices no son las adecuadas");
        }
        
        E[][] c = (E[][]) new Field[this.n][other.m];
        
        for(int i = 0; i < this.n; i++){
            for(int j = 0; j < other.m; j++){
                E element = this.matrix[0][0].zero();
                for(int k = 0; k < this.m; k++){
                    element = element.sum(this.matrix[i][k].mult(other.matrix[k][j])) ;
                }
                c[i][j] = element;
            }
        }
        
        return new MatrixExt<>(c, this.n, other.m);
    }

    @Override
    public MatrixExt<E> scale(E factor){
        
        E[][] a = (E[][]) new Field[this.getN()][this.getM()];
        
        for(int k = 0; k < this.n; k++){
            System.arraycopy(this.matrix[k], 0, a[k], 0, this.m);
        }
        
        for(E[] e : a){
            for(E ep : e){
                ep = ep.mult(factor);
            }
        }
        
        return new MatrixExt<>(a, this.n, this.m);
    }
    
    public MatrixExt<E> Adj(boolean traspuesta){
        
        E zero = this.matrix[0][0].zero();
        E one = this.matrix[0][0].one();
        E[][] a = (E[][]) new Field[this.n][this.m];
        E[][] b = (E[][]) new Field[this.n][this.m];
        
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
                    a[ip][j] = zero;
                }
                for(int jp = 0; jp < this.m; jp++){
                    a[i][jp] = zero;
                }
                a[i][j] = one;
                MatrixExt<E> menor = new MatrixExt<>(a, this.n, this.m);
                b[i][j] = (i+j)%2 == 0 ? menor.getDet() : menor.getDet().op();
            }
        }
        return new MatrixExt<>(b, this.n, this.m);
    }
    
    @Override
    public MatrixExt<E> inv(){
        return this.Adj(true).scale(this.getDet().inv());
    }
    
    @Override
    public MatrixExt<E> div(MatrixExt<E> B){
        return mult(B.inv());
    }
    
    public MatrixExt<E> zero(int n, int m){
        
        E[][] c = (E[][]) new Field[n][m];
        
        for(int k = 0; k < n; k++){
            Arrays.fill(c[k], this.matrix[0][0].zero());
        }
        
        return new MatrixExt<>(c, n, m);
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("");
        for(E[] row : matrix){
            str.append(Arrays.toString(Stream.of(row).map(x -> x.toString()).toArray()));
            str.append("\n");
        }
        str.deleteCharAt(str.length() - 1);
        
        return str.toString();
    }
    
    @Override
    public MatrixExt<E> applyInComponents(Function<E, E> func){
        E[][] A = (E[][]) new Field[this.n][this.m];
        for(int i = 0; i < A.length; i++){
            A[i] = (E[]) Arrays.asList(this.matrix[i]).stream().map(func).toArray(Field[]::new);
        }
        return new MatrixExt(A, this.n, this.m);
    }
    
    /*
    public Vector<E> toVector(){
        if(this.n == 1){
            return new Vector<>(this.matrix[0]);
        } else if(this.m == 1){
            E[] v = (E[]) new VectorialSpace[this.n];
            for(int j = 0; j < this.n; j++){
                v[j] = this.matrix[j][0];
            }
            return new Vector(v);
        }
        throw new WrongDimensionException("La matriz solo puede tener una fila o una columna");
    }
    
    public PolinomioExt<E> getCharacteristicPolynomial(){
        
        E aux = this.get(0, 0);
        Field[] aux2 = {aux.zero(), aux.one()};
        MatrixExt<PolinomioExt<E>, E> X = new MatrixExt();
        X = X.one().mult(new PolinomioExt(aux2, 1, 'x'));
        MatrixExt<PolinomioExt<E>, E> A = new MatrixExt((PolinomioExt<E>[][]) Stream.of(this.matrix).map(row -> Stream.of(row).map(x -> {
            Field[] a = {x};
            return new PolinomioExt(a, 0, 'x');
        }).toArray(Field[]::new)).toArray(Field[][]::new), this.n, this.m);
        
        return X.dif(A).getDet();
    }
*/

    @Override
    public MatrixExt<E> op() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MatrixExt<E> one() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MatrixExt<E> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MatrixExt<E> clone() {
        return new MatrixExt<>(matrix, n, m);
    }

    @Override
    public MatrixExt<E> abs() {
        return this.applyInComponents(Field::abs);
    }

    @Override
    public MatrixExt<E> scale(double factor) {
        return this.applyInComponents(x -> x.scale(factor));
    }

    @Override
    public MatrixExt<E> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(MatrixExt<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(MatrixExt<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(MatrixExt<E> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E mod() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E dotProduct() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MatrixExt<E> ln() {
        return this.applyInComponents(Field::ln);
    }

    @Override
    public MatrixExt<E> log() {
        return this.applyInComponents(Field::log);
    }

    @Override
    public MatrixExt<E> exp() {
        return this.applyInComponents(Field::exp);
    }

    @Override
    public MatrixExt<E> expNum(double base) {
        return this.applyInComponents((entry) -> entry.expNum(base));
    }

    @Override
    public MatrixExt<E> expGen(double exponent) {
        MatrixExt<E> matrix = this.clone();
        for(int i = 0; i < exponent; i++){
            matrix = matrix.mult(this);
        }
        return matrix;
    }

    @Override
    public MatrixExt<E> sin() {
        return this.applyInComponents(Field::sin);
    }

    @Override
    public MatrixExt<E> cos() {
        return this.applyInComponents(Field::cos);
    }

    @Override
    public MatrixExt<E> arcsin() {
        return this.applyInComponents(Field::arcsin);
    }

    @Override
    public MatrixExt<E> arccos() {
        return this.applyInComponents(Field::arccos);
    }

    @Override
    public MatrixExt<E> tan() {
        return this.applyInComponents(Field::tan);
    }

    @Override
    public MatrixExt<E> arctan() {
        return this.applyInComponents(Field::arctan);
    }

    @Override
    public MatrixExt<E> sec() {
        return this.applyInComponents(Field::sec);
    }

    @Override
    public MatrixExt<E> cosec() {
        return this.applyInComponents(Field::cosec);
    }

    @Override
    public MatrixExt<E> cotan() {
        return this.applyInComponents(Field::cotan);
    }

    @Override
    public MatrixExt<E> sinh() {
        return this.applyInComponents(Field::sinh);
    }

    @Override
    public MatrixExt<E> cosh() {
        return this.applyInComponents(Field::cosh);
    }

    @Override
    public MatrixExt<E> arcsinh() {
        return this.applyInComponents(Field::arcsinh);
    }

    @Override
    public MatrixExt<E> arccosh() {
        return this.applyInComponents(Field::arccosh);
    }

    @Override
    public MatrixExt<E> tanh() {
        return this.applyInComponents(Field::tanh);
    }

    @Override
    public MatrixExt<E> cotanh() {
        return this.applyInComponents(Field::cotanh);
    }

    @Override
    public MatrixExt<E> arctanh() {
        return this.applyInComponents(Field::arctanh);
    }

    @Override
    public MatrixExt<E> sech() {
        return this.applyInComponents(Field::sech);
    }

    @Override
    public MatrixExt<E> cosech() {
        return this.applyInComponents(Field::cosech);
    }

    @Override
    public MatrixExt<E> arcsech() {
        return this.applyInComponents(Field::arcsech);
    }

    @Override
    public MatrixExt<E> arccosech() {
        return this.applyInComponents(Field::arccosech);
    }
    
}
