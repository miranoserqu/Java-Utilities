/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public interface Operator<T extends Operator<T>> extends Field<T> {

    <E extends ElementalFunction<E>> E getDerivative();
    
    <R extends Field<R>> R apply(R... values);

    double apply(double... values);
    int apply(int... values);
    float apply(float... values);
    
}