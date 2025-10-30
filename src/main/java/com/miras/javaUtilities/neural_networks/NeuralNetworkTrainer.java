/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.neural_networks;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public class NeuralNetworkTrainer {
    /*
    private Float[][] rawOutputs;
    private Float[][] answers;
    private Vector<Float>[][] error;
    private Matrix<Float>[][] weightsGradients;
    NeuralNetwork NN;

    public Vector<Float>[][] getError() {
        return error;
    }

    public Matrix<Float>[][] getWeightsGradients() {
        return weightsGradients;
    }
    
    public Float[][] getRawOutputs() {
        return rawOutputs;
    }
    
    public void setNewOutput(int iteration, Float[] output){
        System.arraycopy(output, 0, rawOutputs[iteration], 0, output.length);
        
        Number[] v = NN.getPseudoActivation()[NN.getnLayers() - 1].getArray();
        Float[] vp = new Float[v.length];
        for(int i = 0; i < v.length; i++){
            vp[i] = NeuralNetwork.dSwish.apply(v[i].floatValue());
        }
        Vector<Float> ansVect = new Vector(answers[iteration]);
        
        error[iteration][NN.getnLayers() - 1] = NN.getActivation()[NN.getnLayers() - 1]
                .sum(ansVect.op())
                .HadamardProduct(new Vector(vp));
        
        for(int i = NN.getnLayers() - 2; i > 0; i--){
            Number[] vi = NN.getPseudoActivation()[i].getArray();
            Float[] vpi = new Float[vi.length];
            for(int j = 0; j < vi.length; j++){
                vpi[j] = NeuralNetwork.dSwish.apply(vi[j].floatValue());
            }
            error[iteration][i] = NN.getWeights()[i + 1].trasponer()
                    .mult(error[iteration][i + 1].toMatrix('c')).toVector()
                    .HadamardProduct(new Vector(vpi));
        }
        
    }
    
    NeuralNetworkTrainer(NeuralNetwork NN, Float[][] rawOutputs, Float[][] answers){
        this.NN = NN;
        this.rawOutputs = rawOutputs;
        this.answers = answers;
        this.error = new Vector[rawOutputs.length][NN.getnLayers()];
        this.weightsGradients = new Matrix[rawOutputs.length][NN.getnLayers()];   
    }
    
    /**
     *
     * This method will return the corresponding cost evaluated 
     * to the Neural Network this instance is related to. The 
     * <code>function</code> paragram indicated the type of cost 
     * function used in this procces. This are the possible values:
     * 
     * <ul>
     * <li>{@code "MSE"} (The mean squared error cost function)
     * </ul>
     * 
     * @param function
     * @return The cost of the Neural Network
     */
    /*
    public float getCost(String function){
        switch(function){
            case "MSE" -> {
                return getMSE();
            }
        }
        return 0;
    }
    
    private float getMSE(){
        return (float) (1/(2*rawOutputs.length) * IntStream.range(0, rawOutputs.length)
                .mapToDouble(i -> Math.pow(new Vector(answers[i]).sum(new Vector(rawOutputs[i]).multExt(-1f)).getNorm(), 2)).sum());
    }
    
    public void computeGradients(int iteration){
        
        ArrayList<ArrayList<Float>>[] WeightsGradients = new ArrayList[NN.getnLayers()];
        
        for(int i = 1; i < WeightsGradients.length; i++){
            WeightsGradients[i] = new ArrayList<>(NN.getnNeurons()[i]);
            for(int j = 0; j < NN.getnNeurons()[i]; j++){
                WeightsGradients[i].add(new ArrayList(NN.getnNeurons()[i - 1]));
                for(int k = 0; k < NN.getnNeurons()[i - 1]; k++){
                    float value = NN.getActivation()[i - 1].get(k);
                    float value2 = Arrays.asList(Arrays.toString(error[iteration][i].getArray()).substring(1, Arrays.toString(error[iteration][i].getArray()).length() - 1).split(",")).stream().map(s -> Float.valueOf(s)).toArray(Float[]::new)[j];
                    WeightsGradients[i].get(j).add(value * value2);
                }
            }
        }
        
        this.weightsGradients[iteration] = new Matrix[NN.getnLayers()];
        
        for(int i = 1; i < this.weightsGradients[iteration].length; i++){
            this.weightsGradients[iteration][i] = getWeightsGradientsMatrix(WeightsGradients, i);
        }
        
    }
    
    private Matrix getWeightsGradientsMatrix(ArrayList<ArrayList<Float>>[] Weights, int layer){
        ArrayList<Float[]> mat = new ArrayList<>();
        Weights[layer].stream().map(row -> row.toArray(Float[]::new)).forEach(mat::add);
        return new Matrix<>(mat.toArray(Float[][]::new), mat.size(), mat.get(0).length);
    }
    
    public void gradientDescend(){
        
    }
    */
    
}
