/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.calculus.interfaces;

import com.miras.javaUtilities.algebra.interfaces.AbstractField;
import com.miras.javaUtilities.calculus.symbolic.NumberFunctionTree;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public interface Operator<T extends Operator<T>> extends AbstractField<T, T> {

    NumberFunctionTree getDerivative();

    default int priority(){
        return 0;
    }

    default boolean single(){
        return false;
    }

    default boolean sinType(){
        return false;
    }

}