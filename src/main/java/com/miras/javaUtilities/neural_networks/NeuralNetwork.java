/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.neural_networks;

import com.miras.javaUtilities.algebra.practical.PracticalMatrix;
import com.miras.javaUtilities.algebra.practical.PracticalVector;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class NeuralNetwork {
    
    private PracticalMatrix<Double>[] weights;
    private PracticalVector<Double>[] biases;
    private PracticalVector<Double>[] activation;
    private PracticalVector<Double>[] pseudoActivation;
    private final Integer[] nNeurons;
    private final int nLayers;
    private final double clipGrad = 5.0;
    
    private final Function<Double, Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
    private final Function<Double, Double> dSigmoid = a -> a * (1 - a);

    
    
    NeuralNetwork(Integer[] nNeurons){
        
        this.nNeurons = nNeurons;
        this.nLayers = nNeurons.length;
        Double[][] aux = {{0.0}};

        this.weights = new PracticalMatrix[nLayers];
        this.biases = new PracticalVector[nLayers];

        Random rn = new Random();

        for (int i = 1; i < nLayers; i++) {
            final int rows = nNeurons[i];
            final int cols = nNeurons[i - 1];
            Double[][] w = new Double[rows][cols];
            Double[] b = new Double[rows];

            double limit = Math.sqrt(6.0 / (rows + cols)); // Xavier
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    w[r][c] = (rn.nextDouble() * 2.0 - 1.0) * limit;
                }
                b[r] = 0.0;
            }

            this.weights[i] = new PracticalMatrix<>(w, rows, cols);
            this.biases[i] = new PracticalVector(b);
        }
        
    }
    
    NeuralNetwork(Integer[] nNeurons, List<Double[][]> weights, List<Double[]> biases){
        
        this.nNeurons = nNeurons;
        this.nLayers = nNeurons.length;
        this.weights = new PracticalMatrix[nLayers];
        this.biases = new PracticalVector[nLayers];
        Double[][] aux = {{0.0}};
        
        this.weights[0] = new PracticalMatrix(aux, 1, 1);
        this.biases[0] = new PracticalVector(aux[0]);
        this.activation = new PracticalVector[nLayers];
        this.pseudoActivation = new PracticalVector[nLayers];
        
        for(int i = 1; i < nLayers; i++){
            this.weights[i] = new PracticalMatrix(weights.get(i), weights.get(i).length, weights.get(i)[0].length);
            this.biases[i] = new PracticalVector(biases.get(i));
        }
        
    }
    
    public void feedForward(Function<Double, Double> activationFunction){
        for(int i = 1; i < nLayers; i++){
            this.pseudoActivation[i] = this.weights[i].mult(this.activation[i - 1].toMatrix('c')).toVector().sum(this.biases[i]);
            this.activation[i] = this.pseudoActivation[i].applyInComponents(activationFunction);
        }
    }
    
    public Double[] getResultOf(PracticalVector<Double> input, Function<Double, Double> activationFunction){
        this.activation = new PracticalVector[nLayers];
        this.pseudoActivation = new PracticalVector[nLayers];
        this.activation[0] = input;
        feedForward(activationFunction);
        return this.activation[nLayers - 1].getArray(Double[]::new);
    }
    
    public PracticalVector<Double>[][] getMiniBatch(Double[][] inputs, Double[][] answers, int batchSize){
        
        ArrayList<PracticalVector<Double>[]> selection = new ArrayList<>();
        Random rn = new Random();
        ArrayList<Integer> indexes = new ArrayList<>();
        
        while(selection.size() < batchSize){
            int index = rn.nextInt(0, inputs.length);
            if(!indexes.contains(index)){
                indexes.add(index);
                PracticalVector[] tuple = {new PracticalVector(inputs[index]), new PracticalVector(answers[index])};
                selection.add(tuple);
            }
        }
        
        return selection.toArray(PracticalVector[][]::new);
    }
    
    public void train(Double[][] inputs, Double[][] answers, int batchSize, String activationFunction, double learninRate){
        
        Function<Double, Double> func = sigmoid;
        Function<Double, Double> dFunc = dSigmoid;
        
        switch(activationFunction){
            case "Sigmoid" -> {
                func = this.sigmoid;
                dFunc = this.dSigmoid;
            }
                
        }

        PracticalVector<Double>[][] batch = getMiniBatch(inputs, answers, batchSize);
        PracticalVector<Double>[] Error = new PracticalVector[nLayers];
        PracticalMatrix<Double>[] WeightsError = new PracticalMatrix[nLayers];

        for (int i = 0; i < batch.length; i++) {
            PracticalVector<Double> predict = new PracticalVector(getResultOf(batch[i][0], func));
            PracticalVector<Double>[] error = getError(predict, batch[i][1], dFunc);
            PracticalMatrix<Double>[] weightsError = getWeightsError(error);
            for (int j = 1; j < nLayers; j++) {
                Error[j] = Error[j] == null ? error[j] : Error[j].sum(error[j]);
                WeightsError[j] = WeightsError[j] == null ? weightsError[j] : WeightsError[j].sum(weightsError[j]);
            }
        }

        for (int j = 1; j < nLayers; j++) {
            if (Error[j] != null) Error[j] = Error[j].scale(1.0 / batch.length);
            if (WeightsError[j] != null) WeightsError[j] = WeightsError[j].scale(1.0 / batch.length);
        }

        gradientDescend(Error, WeightsError, learninRate);
        
    }
    
    public PracticalVector<Double>[] getError(PracticalVector<Double> predict, PracticalVector<Double> answer, Function<Double, Double> activationFunctionDerivative){
        PracticalVector<Double>[] error = new PracticalVector[nLayers];
        error[nLayers - 1] = predict.dif(answer)
                .HadamardProduct(this.activation[nLayers - 1]
                        .applyInComponents(dSigmoid));
        for(int i = error.length - 2; i > 0; i--){
            error[i] = this.weights[i + 1].trasponer().mult(error[i + 1].toMatrix('c')).toVector()
                    .HadamardProduct(this.activation[i].applyInComponents(dSigmoid));
        }
        
        return error;
    }
    
    public PracticalMatrix<Double>[] getWeightsError(PracticalVector<Double>[] error){
        PracticalMatrix<Double>[] weightsError = new PracticalMatrix[nLayers];
        for(int k = 1; k < nLayers; k++){
            /*
            Double[][] mat = new Double[nNeurons[k]][nNeurons[k - 1]];
            for(int i = 0; i < mat.length; i++){
                for(int j = 0; j < mat[0].length; j++){
                    mat[i][j] = error[k].get(i) * this.activation[k - 1].get(j);
                }
            }
            weightsError[k] = new Matrix(mat, nNeurons[k], nNeurons[k - 1]);
            */
            weightsError[k] = error[k].toMatrix('c').mult(this.activation[k - 1].toMatrix('r'));
        }
        
        return weightsError;
    }

    public void gradientDescend(PracticalVector<Double>[] error, PracticalMatrix<Double>[] weightsError, double learningRate) {
        for (int layer = 1; layer < nLayers; layer++) {
            if (weightsError[layer] == null) continue;

            double norm = weightsError[layer].mod();
            if (norm > clipGrad) {
                weightsError[layer] = weightsError[layer].scale(clipGrad / norm);
            }

            PracticalMatrix<Double> deltaW = weightsError[layer].scale(learningRate);
            PracticalVector<Double> deltaB = error[layer].scale(learningRate);

            this.weights[layer] = this.weights[layer].dif(deltaW);
            this.biases[layer] = this.biases[layer].dif(deltaB);
        }
    }

    public PracticalMatrix<Double>[] getWeights() {
        return weights;
    }

    public PracticalVector<Double>[] getBiases() {
        return biases;
    }

    public Function<Double, Double> getSigmoid() {
        return sigmoid;
    }

    public Function<Double, Double> getdSigmoid() {
        return dSigmoid;
    }
    
    
    
}