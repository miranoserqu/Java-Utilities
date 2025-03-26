package com.miras.pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import org.apache.commons.math3.complex.Complex;

public class Pruebas {

    public static void main(String[] args) {
        
        /*
        Double[][] AP = {
            {1.0, 2.0},
            {3.0, 4.0}};
        
        NumberField<Double>[][] AQ = new Double[100][100];
        
        for(Double[] aq : AQ){
            for(Double a : aq){
                a = 100*Math.random();
            }
        }
        
        Matrix A = new Matrix<>(AQ, 2, 2);
        
        for(int i = 0; i < 100; i++){
            System.out.println(Arrays.toString(AQ[i]));
        }
        
        for(int i = 0; i < 100; i++){
            System.out.println(Arrays.toString(A.getMatrix()[i]));
        }
        
        Polinomio<NumberField<Float>> p1 = new Polinomio(new Double[] {1.0, 2.0, 3.0, 43.56, 65.75, 9.6}, 5, 'x');
        Polinomio<NumberField<Float>> p2 = new Polinomio(new Double[] {4.0, 5.0, 6.0}, 2, 'x');
        
        PolinomioNVariable<NumberField<Float>> P = new PolinomioNVariable("3.45x^4-4s^5+574.574f^2", 4.0f, 5);
        
        Polinomio Q = new Polinomio("3x^3-5.46x^2+5x", 48, 3, 'x');
        
        System.out.println(P.values.get('f').getValues()[2]);
        System.out.println(p1.mult(p1, p2).toString());
        System.out.println(p1.derivate(p1.mult(p1, p2)).toString());
*/
        
        Integer[][] a = {
            {1, 0, 0},
            {4, 6, 3},
            {6, 2, 3}
        };
        
        Integer[] b = {1, 2, 3};
        Integer[] c = {1, 2, 3};
        
        Vector<Integer> vect = new Vector(b);
        Vector<Integer> vect2 = new Vector(c);
        
        Double[][] w = new Double[3][5];
        
        System.out.println(vect.toMatrix('c').mult(vect2.toMatrix('r')).toString());
        
    }
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int mult(int a, int b){
        return a*b;
    }
    
}