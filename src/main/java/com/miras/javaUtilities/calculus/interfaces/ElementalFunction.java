/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.calculus.interfaces;

import com.miras.javaUtilities.calculus.symbolic.NumberFunctionTree;
import com.miras.javaUtilities.calculus.symbolic.Variable;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public interface ElementalFunction<T extends ElementalFunction<T>> extends Operator<T> {

    default NumberFunctionTree getTree(){
        return new NumberFunctionTree(this);
    }

    double apply(double... values);
    int apply(int... values);
    float apply(float... values);

    String getOperator();
    default String getID(){
        return getOperator();
    }
    String getLaTex();

    default NumberFunctionTree getDerivative(Variable variable) {
        return null;
    }

    public static boolean equals(ElementalFunction one, ElementalFunction other){
        return one.getOperator().equals(other.getOperator());
    }

}
