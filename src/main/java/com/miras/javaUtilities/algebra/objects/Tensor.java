package com.miras.javaUtilities.algebra.objects;

import com.miras.javaUtilities.algebra.interfaces.AbstractField;
import com.miras.javaUtilities.algebra.interfaces.AbstractVectorialSpace;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Tensor<K extends AbstractField<K, ?>> implements AbstractVectorialSpace<Tensor<K>, K> {

    private final K[][] coeficients;
    private Vector<K>[][] basis;
    private Covector<K>[][] covectorBasis;
    private int N;
    private int M;
    private int[] vectorDims;
    private int[] covectorDims;

    public Tensor(Function<Vector<K>[], K>[][] functions, int[] covectorDims, Vector<K>[][] basis){

        int[] vectorDims = new int[basis.length];
        for(int i = 0; i < basis.length; i++) vectorDims[i] = basis[i].length;

        this.N = 1;
        for(int dim : vectorDims) this.N *= dim;
        this.M = 1;
        for(int dim : covectorDims) this.M *= dim;
        this.vectorDims = vectorDims;
        this.covectorDims = covectorDims;
        this.basis = basis;
        this.covectorBasis = covectorBasis;

        this.coeficients = (K[][]) Array.newInstance(basis[0][0].repr[0].getClass(), N, M);

        ArrayList<Integer[]> vectorIndexes = getIndexes(vectorDims);
        ArrayList<Integer[]> covectorIndexes = getIndexes(covectorDims);

        for(Integer[] indexes : vectorIndexes){
            for(Integer[] otherIndexes : covectorIndexes){
                int li = getLinealIndex(indexes, vectorDims);
                int lj = getLinealIndex(otherIndexes, covectorDims);
                Vector<K>[] vectors = (Vector<K>[]) new Vector[indexes.length];
                for(int k = 0; k < indexes.length; k++){
                    vectors[k] = basis[k][indexes[k]];
                }
                this.coeficients[li][lj] = functions[li][lj].apply(vectors);
            }
        }
    }

    public Tensor(K[][] coeficients, Vector<K>[][] basis, Covector<K>[][] covectorBasis){
        this.coeficients = coeficients;
        this.N = this.coeficients.length;
        this.M = this.coeficients[0].length;
        this.basis = basis;
        this.covectorBasis = covectorBasis;

        int[] vectorDims = new int[basis.length];
        for(int i = 0; i < basis.length; i++) vectorDims[i] = basis[i].length;

        int[] covectorDims = new int[covectorBasis.length];
        for(int i = 0; i < covectorBasis.length; i++) covectorDims[i] = covectorBasis[i].length;

        this.vectorDims = vectorDims;
        this.covectorDims = covectorDims;
    }

    public Vector<K> tensorialProd(Vector<K> a, Vector<K> b){
        K[] result = (K[]) Array.newInstance(a.repr[0].getClass(), a.repr.length * b.repr.length);

        for(int i = 0; i < a.repr.length; i++){
            for(int j = 0; j < b.repr.length; j++){
                result[i * b.repr.length + j] = a.repr[i].mult(b.repr[j]);
            }
        }

        return new Vector<>(result);
    }

    public Covector<K> tensorialProd(Covector<K> a, Covector<K> b){
        K[] result = (K[]) Array.newInstance(a.repr[0].getClass(), a.repr.length * b.repr.length);

        for(int i = 0; i < a.repr.length; i++){
            for(int j = 0; j < b.repr.length; j++){
                result[i * b.repr.length + j] = a.repr[i].mult(b.repr[j]);
            }
        }

        return new Covector<>(result);
    }

    private ArrayList<Integer[]> getIndexes(int[] limits){
        int n = limits.length;
        Integer[] tuple = new Integer[n];
        Arrays.fill(tuple, 0);

        ArrayList<Integer[]> tuples = new ArrayList<>();
        tuples.add(tuple.clone());
        int total = 1;
        for (int lim : limits) total *= lim;

        while(tuples.size() < total){
            Integer[] newTuple = tuple.clone();

            newTuple[n - 1] ++;

            for(int i = n - 1; i > 0; i--){
                if(newTuple[i] == limits[i]){
                    newTuple[i] = 0;
                    newTuple[i - 1] ++;
                }
            }
            tuples.add(newTuple.clone());
            tuple = newTuple.clone();
        }
        return tuples;
    }

    private int getLinealIndex(Integer[] multiIndex, int[] dims){
        int index = 0;
        int mul = 1;
        for (int i = dims.length - 1; i >= 0; i--) {
            index += multiIndex[i] * mul;
            mul *= dims[i];
        }
        return index;
    }

    public Matrix<K> getMatrix(){
        return new Matrix<>(this.coeficients, this.N, this.M);
    }

    public Vector<K>[] apply(Vector<K>[] vectors){
        Vector<K> tensoredVector = vectors[0];
        for(int i = 1; i < vectors.length; i++){
            tensoredVector = tensorialProd(tensoredVector, vectors[i]);
        }

        Vector<K> tensoredResult = tensoredVector.toMatrix().mult(this.getMatrix()).toVector();
        Vector<K>[] result = new Vector[this.covectorDims.length];

        for(int i = 0; i < this.covectorDims.length; i++){
            K[] vector = (K[]) Array.newInstance(this.basis[0][0].repr[0].getClass(), this.covectorDims[i]);
            for(int j = 0; j < this.covectorDims[i]; j++){
                int offset = 0;
                for(int k = 0; k < i; k++){
                    offset += this.covectorDims[k];
                }
                vector[j] = tensoredResult.repr[offset + j];
            }
            result[i] = new Vector<>(vector);
        }

        return result;
    }

    @Override
    public Tensor<K> scale(K factor) {
        return new Tensor<>(getMatrix().scale(factor).getMatrix(), this.basis, this.covectorBasis);
    }

    @Override
    public Tensor<K> zero() {
        return new Tensor<>(getMatrix().zero().getMatrix(), this.basis, this.covectorBasis);
    }

    @Override
    public Tensor<K> sum(Tensor<K> other) {
        return new Tensor<>(getMatrix().sum(other.getMatrix()).getMatrix(), this.basis, this.covectorBasis);
    }

    @Override
    public Tensor<K> dif(Tensor<K> other) {
        return new Tensor<>(getMatrix().dif(other.getMatrix()).getMatrix(), this.basis, this.covectorBasis);
    }

    @Override
    public Tensor<K> op() {
        return new Tensor<>(getMatrix().op().getMatrix(), this.basis, this.covectorBasis);
    }

    @Override
    public boolean isIn(Tensor<K> object) {
        return false;
    }
}
