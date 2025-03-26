/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <E> the type of element that the coeficients are
 * @param <T>
 */

/*
public class PolinomioExt<E extends Field<E, T>, T> implements Field<PolinomioExt<E, T>, E> {
    
    private final char variable;
    private E[] values;
    private int maxGrado;
    
    PolinomioExt(E[] lista, int maxGrado, char variable){
        values = (E[]) new Field[maxGrado + 1];
        this.variable = variable;
        System.arraycopy(lista, 0, this.values, 0, maxGrado + 1);
    }
    
    public E[] getValues(){
        return values;
    }
    
    public PolinomioNVariable<E, T> sumExt(Polinomio<E> other){
        
        int MaxGrado = this.values.length > other.values.length ? this.values.length : other.values.length;
        
        E[] Values = (E[]) new Field[MaxGrado];

        Dictionary<Character, E>[] val = new Dictionary[MaxGrado];
        for(int i = 0; i < MaxGrado; i++){
            Dictionary<Character, E> dic = new Hashtable<>();
            dic.put(this.variable, (E) this.values[i]);
            dic.put(other.variable, (E) other.values[i]);
            val[i] = dic;
        }

        return new PolinomioNVariable<>(val, this.values[0].sum(other.values[0]), MaxGrado);
    }
    
    @Override
    public PolinomioExt<E, T> dif(PolinomioExt<E, T> other){
        
        int MaxGrado = this.values.length > other.values.length ? this.values.length : other.values.length;
        
        E[] Values = (E[]) new Field[MaxGrado];
        
        for(int i = 0; i < MaxGrado; i++){
            try{
                Values[i] = (E) this.values[i].dif(other.values[i]);
            } catch(Exception Ex){
                try{
                   Values[i] = (E) this.values[i];
                } catch(Exception Ex2){
                    Values[i] = (E) other.values[i].op();
                }
            }
        }
        
        return new Polinomio(Values, MaxGrado, this.variable);
    }
        
    public static <T extends Number> Polinomio<NumberField<T>> mult(Polinomio<NumberField<T>> p, Polinomio<NumberField<T>> q){
        
        if(p.variable != q.variable){
            throw new DiferentTypeOfVariableException("Las variables de los polinomios no son las mismas, utilice el método multExt()");
        }
        
        int maxLen = p.values.length > q.values.length ? p.values.length : q.values.length;
        int paddedSize = Integer.highestOneBit(maxLen - 1) << 1;
        
        FastFourierTransformer FFT = new FastFourierTransformer(DftNormalization.STANDARD);
        
        double[] PF = new double[paddedSize]; Arrays.fill(PF, 0.0);
        double[] QF = new double[paddedSize]; Arrays.fill(QF, 0.0);
        
        for(int i = 0; i < q.values.length; i++){
            PF[i] = p.values[i].getValue().doubleValue();
        }
        
        for(int i = 0; i < q.values.length; i++){
            QF[i] = q.values[i].getValue().doubleValue();
        }
        
        Complex[] PT = FFT.transform(PF, TransformType.FORWARD);
        Complex[] QT = FFT.transform(QF, TransformType.FORWARD);
        
        System.out.println(PT.length);
        
        Complex[] TM = new Complex[PT.length];
        
        for(int i = 0; i < PT.length; i++){
            TM[i] = PT[i].multiply(QT[i]);
        }
        
        TM = FFT.transform(TM, TransformType.INVERSE);
        
        for(int i = 0; i < PT.length; i++){
            TM[i] = TM[i].divide(paddedSize);
        }
        
        NumberField<T>[] FinalP = (NumberField<T>[]) new Field[TM.length];
        for(int i = 0; i < PT.length; i++){
            FinalP[i] = new NumberField((T) Double.valueOf(TM[i].getReal()));
        }
        
        return new Polinomio<NumberField<T>>(FinalP, TM.length - 1, p.variable);
    }
    
    public Polinomio<E> multExt(Polinomio P, Polinomio Q){
        throw new UnsupportedOperationException("Esta operación no ha sido diseñada todavía");
    }
    
    @Override
    public PolinomioExt<E, T> sum(PolinomioExt<E, T> other){
        
        if(this.variable != other.variable){
            throw new DiferentTypeOfVariableException("Las variables de los polinomios no son las mismas, utilice el método sumExt()");
        }
        
        int maxGrad = this.maxGrado > other.maxGrado ? this.maxGrado : other.maxGrado;
        
        E[] list = (E[]) new Field[maxGrad];
        
        for(int i = 0; i < maxGrad; i++){
            list[i] = (E) this.values[i].sum(other.values[i]);
        }
        return new Polinomio<>(list, maxGrad, this.variable);
    }
    
    public PolinomioExt<E, T> derivate(){
        E[] L = null;
        System.arraycopy(this.getValues(), 1, L, 0, this.maxGrado - 1);
        return new PolinomioExt(L, this.maxGrado - 1, this.variable);
    }
    
    @Override
    public String toString(){
        String str = "";
        for(int i = this.values.length-1; i > 0; i--){
            if(!str.equals("")){
                str += (!(!this.values[i].isEqualTo(this.values[0].zero())) ? "" : ((this.values[i].isGreaterThan(this.values[0].zero()) || this.values[i].isEqualTo(this.values[0].zero()) ? "+" : "") +
                        this.values[i].toString() + this.variable + (i == 0 ? "" : "^" + (i == 1 ? "" : i))));
            }
        }
        if(this.values[0].isEqualTo(this.values[0].zero()) || this.values[0].isGreaterThan(this.values[0].zero())){
            str += "+" + this.values[0].toString();
        } else{
            str += this.values[0].toString();
        }
        return str;
    }
    
    @Override
    public PolinomioExt<E, T> mult(PolinomioExt<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioExt<E, T> div(PolinomioExt<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEqualTo(PolinomioExt<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(PolinomioExt<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(PolinomioExt<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioExt<E, T> multExt(T k) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioExt<E, T> divExt(T k) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(PolinomioExt<E, T> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public PolinomioExt<E, T> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
*/