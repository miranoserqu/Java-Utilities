/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.miras.javaUtilities.neural_networks;

import com.miras.javaUtilities.Algebra.Fields.Vector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        Double[][] a = {
            {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
        };
        
        File MNIST_train = new File("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\MNIST_train.large");
        Scanner input = new Scanner(MNIST_train);
        
        Double[][] inputs = new Double[60000][784];
        Double[][] answers = new Double[60000][10];
        
        for(int i = 0; i < inputs.length; i++){
            System.out.println(i);
            String in = input.nextLine();
            char ans = in.charAt(0);
            inputs[i] = Arrays.asList(in.substring(2, in.length()).split(",")).stream().map(s -> Double.parseDouble(s) / 255f).toArray(Double[]::new);
            System.arraycopy(a[Integer.parseInt(String.valueOf(ans))], 0, answers[i], 0, 10);
            System.out.println(ans);
            System.out.println(Arrays.toString(answers[i]));
        }
        
        System.out.println(Arrays.toString(answers[0]));
        System.out.println(Arrays.toString(answers[1]));
        System.out.println(Arrays.toString(answers[2]));
        System.out.println(Arrays.toString(answers[3]));
        
        /*
        System.out.println("Guardando inputs");
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("train_inputs.arr"))) {
            out1.writeObject(inputs);
            out1.flush();
        }
        
        System.out.println("Guardando answers");
        try (ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("train_answers.arr"))) {
            out2.writeObject(answers);
            out2.flush();
        }
        
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\Samir\\Documents\\NetBeansProjects\\pruebas\\src\\main\\java\\com\\miras\\pruebas\\train_inputs.arr"));
        
        System.out.println("leyendo inputs");
        Float[][] inputs = (Float[][]) in.readObject();
        
        ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("C:\\Users\\Samir\\Documents\\NetBeansProjects\\pruebas\\src\\main\\java\\com\\miras\\pruebas\\train_answers.arr"));
        
        System.out.println("leyendo answers");
        Float[][] answers = (Float[][]) in2.readObject();
        */
        
        System.out.println("Importando weights y biases");
        ObjectInputStream in2 = new ObjectInputStream(new FileInputStream("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\weights.net"));
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\biases.net"));
        
        List<Number[]> bia = (List<Number[]>) in.readObject();
        List<Number[][]> weig = (List<Number[][]>) in2.readObject();
        
        List<Double[]> Bia = new ArrayList();
        List<Double[][]> Weig = new ArrayList();
        
        for(Number[] bia1 : bia){
            if(bia1 != null){
                int l = bia1.length;
                Double[] b1 = new Double[l];
                for(int j = 0; j < b1.length; j++){
                    b1[j] = bia1[j].doubleValue();
                }
                Bia.add(b1);
            }
            
        }
        
        for(Number[][] weig1 : weig){
            if(weig1 != null){
                Double[][] w1 = new Double[weig1.length][];
                for(int j = 0; j < w1.length; j++){
                    Double[] w2 = new Double[weig1[j].length];
                    for(int k = 0; k < w2.length; k++){
                        w2[k] = weig1[j][k].doubleValue();
                    }
                    w1[j] = w2;
                }
                Weig.add(w1);
            }
            
        }
        
        Integer[] nNeurons = {784, 15, 10};
        NeuralNetwork MNIST = new NeuralNetwork(nNeurons, Weig, Bia);
        
        for(int i = 0; i < 100; i++){
            System.out.println("Entrenando");
            System.out.println(i);
            MNIST.train(inputs, answers, 128, "Sigmoid", 10.0);
        }
        
        System.out.println("Guardando weights");
        List<Number[][]> w = Stream.of(MNIST.getWeights())
                .map(x -> x == null ? null : (Number[][]) x.getMatrix())
                .toList();
        try (ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\weights.net"))) {
            out1.writeObject(w);
        }
        
        System.out.println("Guardando biases");
        List<Double[]>b = Stream.of(MNIST.getBiases())
                .map(x -> x == null ? null : x.getArray(Double[]::new))
                .toList();
        try (ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\biases.net"))) {
            out2.writeObject(b);
        }
        
        File MNIST_test = new File("C:\\Users\\samir\\Documents\\NetBeansProjects\\Files\\Java Utilities\\MNIST_test.large");
        Scanner inputT = new Scanner(MNIST_test);
        
        Double[][] inputsT = new Double[10000][784];
        Double[][] answersT = new Double[10000][10];
        
        for(int i = 0; i < inputsT.length; i++){
            System.out.println(i);
            String inT = inputT.nextLine();
            char ans = inT.charAt(0);
            inputsT[i] = Arrays.asList(inT.substring(2, inT.length()).split(",")).stream().map(s -> Double.parseDouble(s) / 255.0).toArray(Double[]::new);
            System.arraycopy(a[Integer.parseInt(String.valueOf(ans))], 0, answersT[i], 0, 10);
        }
        
        int correct = 0;
        for(int i = 0; i < inputsT.length; i++){
            Double[] r = MNIST.getResultOf(new Vector<>(inputsT[i]), MNIST.getSigmoid());
            int maxi = 0;
            float max = 0;
            for(int j = 0; j < r.length; j++){
                max = max > r[j].floatValue() ? max : r[j].floatValue();
                maxi = max > r[j].floatValue() ? maxi : j;
            }
            if(a[maxi] == answersT[i]){
                correct ++;
            }
            System.out.println(maxi);
            System.out.println(Arrays.toString(r));
            System.out.println(Arrays.toString(answersT[i]));
        }
        
        System.out.println(correct / inputsT.length);
    }
    
}
