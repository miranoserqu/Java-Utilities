/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <T> The Field in which the Structure is defined
 * @param <R>
 */
public interface Field<T, R> extends Comparable<T> {
    
    T sum(T other);
    T dif(T other);
    T mult(T other);
    T multExt(R k);
    T div(T other);
    T sqrt();
    T op();
    T inv();
    T one();
    T zero();
    T clone();
    boolean isEqualTo(T other);
    boolean isGreaterThan(T other);
    boolean isLessThan(T other);
    
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
