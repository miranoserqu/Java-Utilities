/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class Cuerpo {
    
    float mass;
    float size;

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
    
    Cuerpo(){
        size = 10f;
        mass = 10f;
    }
    
}
