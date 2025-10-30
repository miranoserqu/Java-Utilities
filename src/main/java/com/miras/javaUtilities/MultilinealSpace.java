/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Field;

import java.util.function.Function;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <T>
 * @param <R>
 */
public interface MultilinealSpace<T extends Field<T>, R> extends Field<T> {
    
    T scale(R factor);
    R mod();
    R dotProduct();
    
    default T applyInComponents(Function<R, R> function){
        throw new UnsupportedOperationException("Not supported yet");
    }
    
}