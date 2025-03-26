/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class WrongDimensionException extends RuntimeException {
    
    WrongDimensionException(String errorMessage){
        super(errorMessage);
    }
    
}