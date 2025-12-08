package com.miras.javaUtilities.calculus.fourier;

import com.miras.javaUtilities.algebra.objects.ComplexNumber;

import javax.swing.*;
import java.util.Arrays;

public class DFT {

    public static ComplexNumber[] getDFT(ComplexNumber[] secuence){

        ComplexNumber[] transformedSecuence = new ComplexNumber[secuence.length];

        for(int i = 0; i < transformedSecuence.length; i++){
            ComplexNumber complexNumber = new ComplexNumber(0, 0, ComplexNumber.Coordinates.CARTESIAN);
            for(int j = 0; j < secuence.length; j++){
                complexNumber = complexNumber.sum(secuence[j].mult(new ComplexNumber(1, -(2 * Math.PI * i * j)/secuence.length, ComplexNumber.Coordinates.POLAR)));
            }
            transformedSecuence[i] = complexNumber;
        }
        return transformedSecuence;
    }

    public static ComplexNumber[] getIDFT(ComplexNumber[] transformedSecuence){

        ComplexNumber[] secuence = new ComplexNumber[transformedSecuence.length];

        for(int i = 0; i < secuence.length; i++){
            ComplexNumber complexNumber = new ComplexNumber(0, 0, ComplexNumber.Coordinates.CARTESIAN);
            for(int j = 0; j < transformedSecuence.length; j++){
                complexNumber = complexNumber.sum(transformedSecuence[j].mult(new ComplexNumber(1, (2 * Math.PI * i * j)/transformedSecuence.length, ComplexNumber.Coordinates.POLAR)).scale(1/transformedSecuence.length));
            }
            secuence[i] = complexNumber;
        }
        return secuence;
    }

    public static ComplexNumber[] getDFFT(ComplexNumber[] secuence){

        int N = secuence.length;
        if(N == 1) return new ComplexNumber[]{secuence[0]};

        ComplexNumber[] even = new ComplexNumber[N/2];
        ComplexNumber[] odd = new ComplexNumber[N/2];

        for(int i = 0; i < N; i++){
            if(i % 2 == 0) even[i/2] = secuence[i];
            if(i % 2 == 1){
                int index = Double.valueOf(Math.floor(i/2)).intValue();
                odd[index] = secuence[i];
            }
        }

        ComplexNumber[] transformedEven = getDFFT(even);
        ComplexNumber[] transformedOdd = getDFFT(odd);

        ComplexNumber[] transformedSecuence = new ComplexNumber[N];

        for(int i = 0; i < N/2; i++){
            double v = -(2 * Math.PI * i) / N;
            transformedSecuence[i] = transformedEven[i].sum(new ComplexNumber(1, v, ComplexNumber.Coordinates.POLAR).mult(transformedOdd[i]));
            transformedSecuence[i + N/2] = transformedEven[i].dif(new ComplexNumber(1, v, ComplexNumber.Coordinates.POLAR).mult(transformedOdd[i]));
        }

        return transformedSecuence;
    }

    public static ComplexNumber[] getIDFFT(ComplexNumber[] transformedSecuence){

        int N = transformedSecuence.length;
        if(N == 1) return new ComplexNumber[]{transformedSecuence[0]};

        ComplexNumber[] even = new ComplexNumber[N/2];
        ComplexNumber[] odd = new ComplexNumber[N/2];

        for(int i = 0; i < N; i++){
            if(i % 2 == 0) even[i/2] = transformedSecuence[i];
            if(i % 2 == 1){
                int index = Double.valueOf(Math.floor(i/2)).intValue();
                odd[index] = transformedSecuence[i];
            }
        }

        ComplexNumber[] transformedEven = getIDFFT(even);
        ComplexNumber[] transformedOdd = getIDFFT(odd);

        ComplexNumber[] secuence = new ComplexNumber[N];

        for(int i = 0; i < N/2; i++){
            double v = Double.valueOf((2 * Math.PI * i) / N);
            secuence[i] = transformedEven[i].sum(new ComplexNumber(1, v, ComplexNumber.Coordinates.POLAR).mult(transformedOdd[i])).scale(1/2);
            secuence[i + N/2] = transformedEven[i].dif(new ComplexNumber(1, v, ComplexNumber.Coordinates.POLAR).mult(transformedOdd[i])).scale(1/2);
        }

        return secuence;
    }

    public static ComplexNumber[] convolve(ComplexNumber[] a, ComplexNumber[] b){

        int n = a.length + b.length - 1;
        int N = 1;
        while(N < n) N <<= 1;

        ComplexNumber[] A = new ComplexNumber[N];
        ComplexNumber[] B = new ComplexNumber[N];

        for(int i = 0; i < N; i++){
            A[i] = i < a.length ? a[i] : new ComplexNumber(0, 0, ComplexNumber.Coordinates.CARTESIAN);
            B[i] = i < b.length ? b[i] : new ComplexNumber(0, 0, ComplexNumber.Coordinates.CARTESIAN);
        }

        ComplexNumber[] TA = getDFFT(A);
        ComplexNumber[] TB = getDFFT(B);
        ComplexNumber[] TR = new ComplexNumber[N];

        for(int i = 0; i < N; i++){
            TR[i] = TA[i].mult(TB[i]);
        }

        return getIDFFT(TR);

    }

}
