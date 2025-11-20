/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <E> The Field in which the Structure is defined
 */
public interface Field<E extends Field<E, T>, T> extends Ring<E, T> {

    E dif(E other);
    E div(E other);
    E sqrt();
    E op();
    E inv();
    E clone();
    E abs();
    E scale(double factor);
    
    boolean isEqualTo(E other);
    boolean isGreaterThan(E other);
    boolean isLessThan(E other);
    
    default E ln(){
        throw new UnsupportedOperationException("No procede");
    }
    default E log(){
        throw new UnsupportedOperationException("No procede");
    }
    default E exp(){
        throw new UnsupportedOperationException("No procede");
    }
    default E expNum(double base){
        throw new UnsupportedOperationException("No procede");
    }
    default E expGen(double exponent){
        throw new UnsupportedOperationException("No procede");
    }
    default E sin(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cos(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arcsin(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arccos(){
        throw new UnsupportedOperationException("No procede");
    }
    default E tan(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arctan(){
        throw new UnsupportedOperationException("No procede");
    }
    default E sec(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cosec(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cotan(){
        throw new UnsupportedOperationException("No procede");
    }
    default E sinh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cosh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arcsinh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arccosh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E tanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cotanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arctanh(){
        throw new UnsupportedOperationException("No procede");
    }
    default E sech(){
        throw new UnsupportedOperationException("No procede");
    }
    default E cosech(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arcsech(){
        throw new UnsupportedOperationException("No procede");
    }
    default E arccosech(){
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
