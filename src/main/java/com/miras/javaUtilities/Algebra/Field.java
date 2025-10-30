/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra;

import java.util.function.Function;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <T> The Field in which the Structure is defined
 */
public interface Field<T extends Field<T>> extends Comparable<T> {
    
    T sum(T other);
    T dif(T other);
    T mult(T other);
    T div(T other);
    T sqrt();
    T op();
    T inv();
    T one();
    T zero();
    T clone();
    T abs();
    T scale(double factor);
    
    boolean isEqualTo(T other);
    boolean isGreaterThan(T other);
    boolean isLessThan(T other);
    
    default T ln(){
        throw new UnsupportedOperationException("No procede");
    }
    default T log(){
        throw new UnsupportedOperationException("No procede");
    }
    default T exp(){
        throw new UnsupportedOperationException("No procede");
    }
    default T expNum(double base){
        throw new UnsupportedOperationException("No procede");
    }
    default T expGen(double exponent){
        throw new UnsupportedOperationException("No procede");
    }
    default T sin(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cos(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arcsin(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arccos(){
        throw new UnsupportedOperationException("No procede");
    }
    default T tan(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arctan(){
        throw new UnsupportedOperationException("No procede");
    }
    default T sec(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cosec(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cotan(){
        throw new UnsupportedOperationException("No procede");
    }
    default T sinh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cosh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arcsinh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arccosh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T tanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cotanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arctanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default T sech(){
        throw new UnsupportedOperationException("No procede");
    }
    default T cosech(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arcsech(){
        throw new UnsupportedOperationException("No procede");
    }
    default T arccosech(){
        throw new UnsupportedOperationException("No procede");
    }
        
    public static <R extends Number> R sumNum(R a, R b){
        return (R) Double.valueOf(a.doubleValue() + b.doubleValue());
    }
    
    public static <R extends Number> R multNum(R a, R b){
        return (R) Double.valueOf(a.doubleValue() * b.doubleValue());
    }
    
    public static <R extends Number> R divNum(R a, R b){
        return (R) Double.valueOf(a.doubleValue() / b.doubleValue());
    }
    
    public static <R extends Number> R restNum(R a, R b){
        return (R) Double.valueOf(a.doubleValue() - b.doubleValue());
    }

}
