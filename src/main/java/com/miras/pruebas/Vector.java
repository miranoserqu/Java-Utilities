/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class Vector<E extends Number> implements Field<Vector<E>, E> {
    
    private E[] vector;
    
    Vector(E[] vector){
        this.vector = (E[]) new Number[vector.length];
        System.arraycopy(vector, 0, this.vector, 0, vector.length);
    }
    
    Vector(){
        
    }

    public Vector<E> one(int variable) {
        E[] v = (E[]) new Number[variable];
        for(int i = 0; i < variable; i++){
            v[i] = (E) Integer.valueOf(1);
        }
        return new Vector(v);
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
    
    public E dotProduct(Vector w){
        if(this.getDimension() != w.getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E a = (E) Integer.valueOf(0);
        for(int i = 0; i < this.getDimension(); i++){
            a = sumNum(a, multNum(this.get(i), (E) w.get(i)));
        }
        
        return a;
    }
    
    public Matrix toMatrix(char type){
        switch(type){
            case 'r' -> { 
                Double[][] mat = new Double[1][this.getDimension()];
                for(int i = 0; i < this.getDimension(); i++){
                    mat[0][i] = this.get(i).doubleValue();
                }
                return new Matrix(mat, 1, this.getDimension());
            }
            case 'c' -> {
                Double[][] mat = new Double[this.getDimension()][1];
                for(int i = 0; i < this.getDimension(); i++){
                    mat[i][0] = this.get(i).doubleValue();
                }
                return new Matrix(mat, this.getDimension(), 1);
            }
        }
        return null;
    }

    @Override
    public Vector<E> sum(Vector<E> other) {
        
        if(this.getDimension() != other.getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E[] u = (E[]) new Number[this.getDimension()];
        
        for (int i = 0; i < u.length; i++) {
            u[i] = (E) Double.valueOf(this.get(i).doubleValue() + other.get(i).doubleValue());
        }
        
        return new Vector<E>(u);
    }
    
    public double getNorm(){
        double sum = 0;
        for(E a : vector){
            sum += Math.pow(a.doubleValue(), 2);
        }
        return Math.sqrt(sum);
    }
    
    @Override
    public Vector<E> multExt(E k){
        return this.applyInComponents(x -> (E) Double.valueOf(k.doubleValue()*x.doubleValue()));
    }
    
    @Override
    public Vector op(){
        E[] v = (E[]) new Number[this.getDimension()];
        for(int i = 0; i < this.getDimension(); i++){
            v[i] = (E) Double.valueOf(this.vector[i].doubleValue() == 0 ? 0 : -1*this.vector[i].doubleValue());
        }
        return new Vector(v);
    }
    
    public Vector<E> applyInComponents(Function<E, E> func){
        return new Vector(Stream.of(this.vector).map(func).toArray(Number[]::new));
    }
    
    public Vector<E> HadamardProduct(Vector<E> other){
        if(this.getDimension() != other.getDimension()){
            throw new WrongDimensionException("Las dimensiones de los vectores debeb coincidir");
        }
        
        E[] u = (E[]) new Number[this.getDimension()];
        
        for (int i = 0; i < u.length; i++) {
            u[i] = (E) Double.valueOf(this.get(i).doubleValue() * other.get(i).doubleValue());
        }
        
        return new Vector(u);
    }
    
    public Vector zero(int dim){
        E[] zero = (E[]) new Number[dim];
        Arrays.fill(zero, 0);
        return new Vector(zero);
    }
    
    private E sumNum(E a, E b){
        return (E) Double.valueOf(a.doubleValue() + b.doubleValue());
    }
    
    private E multNum(E a, E b){
        return (E) Double.valueOf(a.doubleValue() * b.doubleValue());
    }
    
    private E divNum(E a, E b){
        return (E) Double.valueOf(a.doubleValue() / b.doubleValue());
    }
    
    private E restNum(E a, E b){
        return (E) Double.valueOf(a.doubleValue() - b.doubleValue());
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vector<E> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
}