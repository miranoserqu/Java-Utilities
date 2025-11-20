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

        FunctionTree functionTree = ElementalFunctions.MULT.get().getTree();
        functionTree.insert(2, new FunctionTreeBlock(new Variable(0, "x"), new FunctionTree()));
        functionTree.insert(3, new FunctionTreeBlock(new Variable(1, "y"), new FunctionTree()));

        GeneralFunctionTree generalFunctionTree = new GeneralFunctionTree(2, new FunctionTree[]{functionTree, functionTree.sin()});

        System.out.println(functionTree);
        System.out.println(functionTree.getPartialDerivative(0).simplify());
        System.out.println(functionTree.getPartialDerivative(0).getPartialDerivative(0).simplify());

        System.out.println(functionTree.sin().arctan().ln().getPartialDerivative(0));
        System.out.println(functionTree.sin().expGen(2).getLaTex());
        System.out.println(functionTree.sin().expGen(2).getPartialDerivative(0).simplify().getLaTex());
        }
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int mult(int a, int b){
        return a*b;
    }
    
}