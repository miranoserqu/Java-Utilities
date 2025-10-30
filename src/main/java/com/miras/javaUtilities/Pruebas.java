package com.miras.javaUtilities;

import com.miras.javaUtilities.Algebra.Fields.Vector;

import java.util.TreeMap;

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

        GeneralFunctionTree generalFunctionTree = new GeneralFunctionTree(2, 1, new TreeMap[]{new FunctionTreeBlock(new Variable(0, "x"), ElementalFunctions.VARIABLE.get().getTree()).mult(new FunctionTreeBlock(new Variable(1, "y"), ElementalFunctions.VARIABLE.get().getTree())).getRepr()});

        GeneralFunctionTree derivative = (GeneralFunctionTree) generalFunctionTree.getDerivative(new Variable(0, "x"));

        System.out.println(generalFunctionTree.toString());
    }
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int mult(int a, int b){
        return a*b;
    }
    
}