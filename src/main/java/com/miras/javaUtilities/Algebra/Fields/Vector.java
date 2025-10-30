/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra.Fields;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.ElementalFunction;
import com.miras.javaUtilities.MultilinealSpace;
import com.miras.javaUtilities.WrongDimensionException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class Vector<E extends Number> implements MultilinealSpace<Vector<E>, E>, Field<Vector<E>> {
    
    private E[] vector;
    private E prototype;
    final int DIM = 1;
    
    public Vector(E[] vector){
        this.prototype = vector[0];
        this.vector = (E[]) Array.newInstance(this.prototype.getClass(), vector.length);
        System.arraycopy(vector, 0, this.vector, 0, vector.length);
    }

    public Vector(List<E> vector){
        this.prototype = vector.getFirst();
        this.vector = (E[]) Array.newInstance(this.prototype.getClass(), vector.size());
        for(int i = 0; i < vector.size(); i++){
            this.vector[i] = vector.get(i);
        }
    }
    
    Vector(){
        
    }
    
    public E[] getArray(){
        return this.vector;
    }
    
    public E[] getArray(IntFunction<E[]> generator){
        return (E[]) Arrays.copyOf(vector, vector.length, generator.apply(vector.length).getClass());
    }
    
    public int getDimension(){
        return this.vector.length;
    }
    
    public E get(int i){
        return this.vector[i];
    }
    
    @Override
    public E dotProduct(){
        if(this.getDimension() != getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E a = initialize(1.0);
        for(int i = 0; i < this.getDimension(); i++){
            a = sumNum(a, multNum(this.get(i), (E) get(i)));
        }
        
        return a;
    }
    
    public Matrix<E> toMatrix(char type){
        switch(type){
            case 'r' -> { 
                E[][] mat = (E[][]) Array.newInstance(this.prototype.getClass(), 1, this.getDimension());
                for(int i = 0; i < this.getDimension(); i++){
                    mat[0][i] = initialize(this.get(i).doubleValue());
                }
                return new Matrix<>(mat, 1, this.getDimension());
            }
            case 'c' -> {
                E[][] mat = (E[][]) Array.newInstance(this.prototype.getClass(), this.getDimension(), 1);
                for(int i = 0; i < this.getDimension(); i++){
                    mat[i][0] = initialize(this.get(i).doubleValue());
                }
                return new Matrix<>(mat, this.getDimension(), 1);
            }
        }
        return null;
    }

    @Override
    public Vector<E> sum(Vector<E> other) {
        
        if(this.getDimension() != other.getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E[] u = (E[]) Array.newInstance(this.prototype.getClass(), this.getDimension());
        
        for (int i = 0; i < u.length; i++) {
            u[i] = initialize(this.get(i).doubleValue() + other.get(i).doubleValue());
        }
        
        return new Vector<E>(u);
    }
    
    public E mod(){
        double sum = 0;
        for(E a : vector){
            sum += Math.pow(a.doubleValue(), 2);
        }
        return initialize(Math.sqrt(sum));
    }
    
    @Override
    public Vector<E> scale(E k){
        return this.applyInComponents(x -> initialize(k.doubleValue()*x.doubleValue()));
    }
    
    @Override
    public Vector<E> op(){
        E[] v = (E[]) Array.newInstance(this.prototype.getClass(), this.getDimension());
        for(int i = 0; i < this.getDimension(); i++){
            v[i] = initialize(this.vector[i].doubleValue() == 0 ? 0 : -1*this.vector[i].doubleValue());
        }
        return new Vector<>(v);
    }
    
    @Override
    public Vector<E> applyInComponents(Function<E, E> func){
        return new Vector<E>(Stream.of(this.vector).map(func).toList());
    }
    
    public Vector<E> HadamardProduct(Vector<E> other){
        if(this.getDimension() != other.getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E[] u = (E[]) Array.newInstance(this.prototype.getClass(), this.getDimension());
        
        for (int i = 0; i < u.length; i++) {
            u[i] = initialize(this.get(i).doubleValue() * other.get(i).doubleValue());
        }
        
        return new Vector<>(u);
    }

    public Vector<E> zero(int dim){
        E[] zero = (E[]) Array.newInstance(this.prototype.getClass(), dim);
        Arrays.fill(zero, 0);
        return new Vector<>(zero);
    }
    
    private E sumNum(E a, E b){
        return initialize(a.doubleValue() + b.doubleValue());
    }
    
    private E multNum(E a, E b){
        return initialize(a.doubleValue() * b.doubleValue());
    }
    
    private E divNum(E a, E b){
        return initialize(a.doubleValue() / b.doubleValue());
    }
    
    private E restNum(E a, E b){
        return initialize(a.doubleValue() - b.doubleValue());
    }

    @Override
    public Vector<E> dif(Vector<E> other) {
        return this.sum(other.op());
    }

    @Override
    public Vector<E> mult(Vector<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> div(Vector<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> inv() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> one() {
        return this.applyInComponents(x -> initialize(1.0));
    }

    @Override
    public Vector<E> zero() {
        return this.applyInComponents(x -> initialize(0.0));
    }

    @Override
    public Vector<E> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> abs() {
        return this.applyInComponents(x -> initialize(Math.abs(x.doubleValue())));
    }

    @Override
    public Vector<E> scale(double factor) {
        return this.scale(initialize(factor));
    }

    @Override
    public boolean isEqualTo(Vector<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(Vector<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(Vector<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(Vector<E> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> ln() {
        return this.applyInComponents(x -> initialize(Math.log(x.doubleValue())));
    }

    @Override
    public Vector<E> log() {
        return this.applyInComponents(x -> initialize(Math.log10(x.doubleValue())));
    }

    @Override
    public Vector<E> exp() {
        return this.applyInComponents(x -> initialize(Math.exp(x.doubleValue())));
    }

    @Override
    public Vector<E> expNum(double base) {
        return this.applyInComponents(x -> initialize(Math.pow(base, x.doubleValue())));
    }

    @Override
    public Vector<E> expGen(double exponent) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Vector<E> sin() {
        return this.applyInComponents(x -> initialize(Math.sin(x.doubleValue())));
    }

    @Override
    public Vector<E> cos() {
        return this.applyInComponents(x -> initialize(Math.cos(x.doubleValue())));
    }

    @Override
    public Vector<E> arcsin() {
        return this.applyInComponents(x -> initialize(Math.asin(x.doubleValue())));
    }

    @Override
    public Vector<E> arccos() {
        // Math.acos is the correct method for arccos (inverse cosine)
        return this.applyInComponents(x -> initialize(Math.acos(x.doubleValue())));
    }

    @Override
    public Vector<E> tan() {
        // Math.tan is the correct method for tan (tangent)
        return this.applyInComponents(x -> initialize(Math.tan(x.doubleValue())));
    }

    @Override
    public Vector<E> arctan() {
        // Math.atan is the correct method for arctan (inverse tangent)
        return this.applyInComponents(x -> initialize(Math.atan(x.doubleValue())));
    }

    @Override
    public Vector<E> sec() {
        // sec(x) = 1 / cos(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.cos(x.doubleValue())));
    }

    @Override
    public Vector<E> cosec() {
        // cosec(x) = 1 / sin(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.sin(x.doubleValue())));
    }

    @Override
    public Vector<E> cotan() {
        // cotan(x) = 1 / tan(x) = cos(x) / sin(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.tan(x.doubleValue())));
    }

    @Override
    public Vector<E> sinh() {
        // Math.sinh is the correct method for hyperbolic sine
        return this.applyInComponents(x -> initialize(Math.sinh(x.doubleValue())));
    }

    @Override
    public Vector<E> cosh() {
        // Math.cosh is the correct method for hyperbolic cosine
        return this.applyInComponents(x -> initialize(Math.cosh(x.doubleValue())));
    }

    @Override
    public Vector<E> arcsinh() {
        // arcsinh(x) = ln(x + sqrt(x^2 + 1))
        return this.applyInComponents(x -> {
            double val = x.doubleValue();
            return initialize(Math.log(val + Math.sqrt(val * val + 1.0)));
        });
    }

    @Override
    public Vector<E> arccosh() {
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
    public Vector<E> tanh() {
        // Math.tanh is the correct method for hyperbolic tangent
        return this.applyInComponents(x -> initialize(Math.tanh(x.doubleValue())));
    }

    @Override
    public Vector<E> cotanh() {
        return this.applyInComponents(x -> initialize(1 / Math.tanh(x.doubleValue())));
    }

    @Override
    public Vector<E> arctanh() {
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
    public Vector<E> sech() {
        // sech(x) = 1 / cosh(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.cosh(x.doubleValue())));
    }

    @Override
    public Vector<E> cosech() {
        // cosech(x) = 1 / sinh(x)
        return this.applyInComponents(x -> initialize(1.0 / Math.sinh(x.doubleValue())));
    }

    @Override
    public Vector<E> arcsech() {
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
    public Vector<E> arccosech() {
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

    private E initialize(Double num){
        if(this.prototype instanceof Double){
            return (E) Double.valueOf(num.doubleValue());
        } else if(this.prototype instanceof Integer){
            return (E) Integer.valueOf(num.intValue());
        } else{
            return (E) Float.valueOf(num.floatValue());
        }
    }
    
}