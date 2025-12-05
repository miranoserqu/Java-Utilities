package com.miras.javaUtilities;

import com.miras.javaUtilities.calculus.numerical.DummyNumberFunction;
import com.miras.javaUtilities.calculus.numerical.SumSeries;
import com.miras.javaUtilities.calculus.symbolic.ElementalFunctions;
import com.miras.javaUtilities.calculus.symbolic.GeneralFunctionTree;
import com.miras.javaUtilities.calculus.symbolic.NumberFunctionTree;
import com.miras.javaUtilities.calculus.symbolic.Variable;

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

        NumberFunctionTree numberFunctionTree = ElementalFunctions.MULT.get().getTree();
        numberFunctionTree.insert(2, ElementalFunctions.VARIABLE.apply(new Variable(0, "x")).getTree());
        numberFunctionTree.insert(3, ElementalFunctions.VARIABLE.apply(new Variable(1, "y")).getTree());

        GeneralFunctionTree generalFunctionTree = new GeneralFunctionTree(2, new NumberFunctionTree[]{numberFunctionTree, numberFunctionTree.sin()});

        System.out.println(numberFunctionTree);
        System.out.println(numberFunctionTree.getPartialDerivative(new Variable(0, "x")).simplify());
        System.out.println(numberFunctionTree.getPartialDerivative(new Variable(0, "x")).getPartialDerivative(new Variable(0, "x")).simplify());

        System.out.println(numberFunctionTree.sin().arctan().ln().getPartialDerivative(new Variable(0, "x")));
        System.out.println(numberFunctionTree.sin().expGen(2).getLaTex());
        System.out.println(numberFunctionTree.sin().expGen(2).getPartialDerivative(new Variable(0, "x")).simplify().getLaTex());

        NumberFunctionTree serie = new NumberFunctionTree();
        serie.repr.put(1, ElementalFunctions.DIV.get());
        serie.repr.put(2, ElementalFunctions.ONE.get());
        serie.repr.put(3, ElementalFunctions.EXPGEN.apply(4d));
        serie.repr.put(6, ElementalFunctions.VARIABLE.apply(new Variable(0, "n")));

        System.out.println(new SumSeries(serie).compute(100000));

        NumberFunctionTree functionTree = ElementalFunctions.SIN.get().getTree();
        functionTree.insert(2, ElementalFunctions.VARIABLE.apply(new Variable(0, "x")).getTree());

        System.out.println(DummyNumberFunction.dummyfier.apply(functionTree).apply(Math.PI));
        System.out.println(DummyNumberFunction.dummyfier.apply(functionTree).getDerivative(0).apply(Math.PI));

    }
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int mult(int a, int b){
        return a*b;
    }
    
}