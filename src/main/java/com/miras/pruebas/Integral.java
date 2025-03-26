/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.ArrayList;
import java.util.function.Function;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class Integral<E extends Number>{
    
    E limitInf;
    E limitSup;
    float finura;
    
    Integral(E limitInf, E limitSup, float finura){
        this.limitInf = limitInf;
        this.limitSup = limitSup;
        this.finura = finura;
    }
    
    private Double[] getPartition(){
        ArrayList<Double> L = new ArrayList<>();
        double i = this.limitInf.doubleValue();
        while(i <= this.limitSup.doubleValue()){
            L.add(i);
            i += this.finura;
        }
        return (Double[]) L.toArray();
    }
    
    private Double[] getInfs(Function<Double, Double> f){
        Double[] P = this.getPartition();
        ArrayList<Double> L = new ArrayList<>();
        for(int i = 0; i < P.length - 1; i++){
            L.add(f.apply(P[i]) < f.apply(P[i + 1]) ? f.apply(P[i]) : f.apply(P[i + 1]));
        }
        return (Double[]) L.toArray();
    }
    
    private Double[] getMaxs(Function f){
        Double[] P = this.getPartition();
        ArrayList<Double> L = new ArrayList<>();
        for(int i = 0; i < P.length - 1; i++){
            L.add(P[i] > P[i + 1] ? P[i] : P[i + 1]);
        }
        return (Double[]) L.toArray();
    }
    
    public double getRiemmanInf(Function f){
        double sum = 0;
        for(double x : this.getInfs(f)){
            sum += x * this.finura;
        }
        return sum;
    }
}
