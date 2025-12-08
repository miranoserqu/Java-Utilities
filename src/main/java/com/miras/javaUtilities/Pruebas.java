package com.miras.javaUtilities;

import com.miras.javaUtilities.algebra.objects.*;
import com.miras.javaUtilities.calculus.fourier.DFT;
import com.miras.javaUtilities.calculus.numerical.DummyNumberFunction;
import com.miras.javaUtilities.calculus.numerical.SumSeries;
import com.miras.javaUtilities.calculus.symbolic.ElementalFunctions;
import com.miras.javaUtilities.calculus.symbolic.GeneralFunctionTree;
import com.miras.javaUtilities.calculus.symbolic.NumberFunctionTree;
import com.miras.javaUtilities.calculus.symbolic.Variable;

import java.util.Arrays;
import java.util.function.Function;

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
        serie.getRepr().put(1, ElementalFunctions.DIV.get());
        serie.getRepr().put(2, ElementalFunctions.ONE.get());
        serie.getRepr().put(3, ElementalFunctions.EXPGEN.apply(4d));
        serie.getRepr().put(6, ElementalFunctions.VARIABLE.apply(new Variable(0, "n")));

        // --- Ejemplo con K = RealNumber ---
        // Vectores de entrada: 2 vectores, de dimensiones 2 y 3
        Vector<RealNumber> v1 = new Vector<>(new RealNumber[]{ new RealNumber(1), new RealNumber(2) });
        Vector<RealNumber> v2 = new Vector<>(new RealNumber[]{ new RealNumber(3), new RealNumber(4), new RealNumber(5) });
        Vector<RealNumber>[] inputs = new Vector[]{v1, v2};

        // Bases de covectores de salida: 2 vectores, dimensiones 2 y 3
        Vector<RealNumber>[][] covectorBasis = new Vector[2][];
        covectorBasis[0] = new Vector[2];
        covectorBasis[1] = new Vector[3];

        for(int i = 0; i < 2; i++) {
            RealNumber[] coords = new RealNumber[2];
            for(int j = 0; j < 2; j++) coords[j] = new RealNumber((i+1)*(j+1)); // valores distintos
            covectorBasis[0][i] = new Vector<>(coords);
        }

        for(int i = 0; i < 3; i++) {
            RealNumber[] coords = new RealNumber[3];
            for(int j = 0; j < 3; j++) coords[j] = new RealNumber((i+2)*(j+2));
            covectorBasis[1][i] = new Vector<>(coords);
        }

        // Funciones del tensor: devuelve suma ponderada de coordenadas de entrada
        int totalVectors = v1.getRepr().length * v2.getRepr().length; // para indexación lineal
        Function<Vector<RealNumber>[], RealNumber>[][] functions = new Function[totalVectors][totalVectors];

        for(int li=0; li<totalVectors; li++){
            for(int lj=0; lj<totalVectors; lj++){
                final int indexSum = li + lj; // hacemos que cada función dependa de los índices
                functions[li][lj] = vecs -> {
                    RealNumber sum = new RealNumber(0);
                    int factor = 1;
                    for(Vector<RealNumber> vec : vecs){
                        for(RealNumber x : vec.getRepr()){
                            sum = sum.sum(x.scale(factor));
                            factor++;
                        }
                    }
                    sum = sum.sum(new RealNumber(indexSum)); // añadir un valor dependiente de (li, lj)
                    return sum;
                };
            }
        }

        // Dimensiones de covectores
        int[] covectorDims = {2, 3};

        // Creamos el tensor
        Tensor<RealNumber> tensor = new Tensor<>(functions, covectorDims, covectorBasis);

        // Aplicamos
        Vector<RealNumber>[] output = tensor.apply(inputs);

        // Imprimimos resultados
        for(int i = 0; i < output.length; i++){
            System.out.print("Vector de salida " + i + ": ");
            for(RealNumber x : output[i].getRepr()){
                System.out.print(x.getValue() + " ");
            }
            System.out.println();
        }

    }
    
    public static void print(String string) {
        System.out.println(string);
    }
    
    public static int mult(int a, int b){
        return a*b;
    }
    
}