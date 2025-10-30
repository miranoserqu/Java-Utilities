/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra.Fields;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.miras.javaUtilities.Algebra.Field;
import com.miras.javaUtilities.DiferentTypeOfVariableException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <E> the type of element that the coeficients are
 */
public class Polinomio<E extends Number> implements Field<Polinomio<E>> {
    
    private final char variable;
    private E[] values;
    private int maxGrado;
    
    Polinomio(String polinomio, E terminoIndependiente, int maxGrado, char variable){
        this.variable = variable;
        this.maxGrado = maxGrado;
        Pattern pattern = Pattern.compile("([-]?\\d+([.](\\d+)+)?)(([a-z])(\\^)((\\d*)+))?");
        Matcher matcher = pattern.matcher(polinomio);
        values = (E[]) new Number[maxGrado + 1];
        while(matcher.find()){
            if(!"".equals(matcher.group())){
                try{
                    this.values[Integer.parseInt(matcher.group(7))] = (E) Double.valueOf(matcher.group(1));
                } catch(NumberFormatException E){
                    this.values[1] = (E) Double.valueOf(matcher.group(1));
                }
            }
        }
        this.values[0] = terminoIndependiente;
    }
    
    Polinomio(E[] lista, int maxGrado, char variable){
        values = (E[]) new Field[maxGrado + 1];
        this.variable = variable;
        System.arraycopy(lista, 0, this.values, 0, maxGrado + 1);
    }
    
    public E[] getValues(){
        return values;
    }
    
    /*
    public PolinomioNVariable<E, > sumExt(Polinomio<E> other){
        
        int MaxGrado = this.values.length > other.values.length ? this.values.length : other.values.length;
        
        E[] Values = (E[]) new Field[MaxGrado];

        Dictionary<Character, E>[] val = new Dictionary[MaxGrado];
        for(int i = 0; i < MaxGrado; i++){
            Dictionary<Character, E> dic = new Hashtable<>();
            dic.put(this.variable, (E) this.values[i]);
            dic.put(other.variable, (E) other.values[i]);
            val[i] = dic;
        }

        return new PolinomioNVariable<>(val, Field.sumNum(this.values[0], other.values[0]), MaxGrado);
    }
*/
    
    @Override
    public Polinomio<E> dif(Polinomio<E> other){
        
        int MaxGrado = this.values.length > other.values.length ? this.values.length : other.values.length;
        
        E[] Values = (E[]) new Field[MaxGrado];
        
        for(int i = 0; i < MaxGrado; i++){
            try{
                Values[i] = Field.restNum(this.values[i], other.values[i]);
            } catch(Exception Ex){
                try{
                   Values[i] = (E) this.values[i];
                } catch(Exception Ex2){
                    Values[i] = (E) Field.restNum(0, other.values[i]);
                }
            }
        }
        
        return new Polinomio(Values, MaxGrado, this.variable);
    }
        
    @Override
    public Polinomio<E> mult(Polinomio<E> other){
        
        if(this.variable != other.variable){
            throw new DiferentTypeOfVariableException("Las variables de los polinomios no son las mismas, utilice el método multExt()");
        }
        
        int maxLen = this.values.length > other.values.length ? this.values.length : other.values.length;
        int paddedSize = Integer.highestOneBit(maxLen - 1) << 1;
        
        FastFourierTransformer FFT = new FastFourierTransformer(DftNormalization.STANDARD);
        
        double[] PF = new double[paddedSize]; Arrays.fill(PF, 0.0);
        double[] QF = new double[paddedSize]; Arrays.fill(QF, 0.0);
        
        for(int i = 0; i < other.values.length; i++){
            PF[i] = this.values[i].doubleValue();
        }
        
        for(int i = 0; i < other.values.length; i++){
            QF[i] = other.values[i].doubleValue();
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
        
        E[] FinalP = (E[]) new Number[TM.length];
        for(int i = 0; i < PT.length; i++){
            FinalP[i] = (E) Double.valueOf(TM[i].getReal());
        }
        
        return new Polinomio<>(FinalP, TM.length - 1, this.variable);
    }
    
    public Polinomio<E> multExt(Polinomio P, Polinomio Q){
        throw new UnsupportedOperationException("Esta operación no ha sido diseñada todavía");
    }
    
    @Override
    public Polinomio<E> sum(Polinomio<E> other){
        
        if(this.variable != other.variable){
            throw new DiferentTypeOfVariableException("Las variables de los polinomios no son las mismas, utilice el método sumExt()");
        }
        
        int maxGrad = this.maxGrado > other.maxGrado ? this.maxGrado : other.maxGrado;
        
        E[] list = (E[]) new Field[maxGrad];
        
        for(int i = 0; i < maxGrad; i++){
            list[i] = (E) Field.sumNum(this.values[i], other.values[i]);
        }
        return new Polinomio<>(list, maxGrad, this.variable);
    }
    
    public Polinomio<E> derivate(Polinomio P){
        E[] L = null;
        System.arraycopy(P.getValues(), 1, L, 0, P.maxGrado - 1);
        return new Polinomio(L, P.maxGrado - 1, P.variable);
    }
    
    @Override
    public String toString(){
        String str = "";
        for(int i = this.values.length-1; i > 0; i--){
            if(!str.equals("")){
                str += (this.values[i].doubleValue() == 0 ? "" : ((this.values[i].doubleValue() >= 0 ? "+" : "") +
                        this.values[i].toString() + this.variable + (i == 0 ? "" : "^" + (i == 1 ? "" : i))));
            }
        }
        if(this.values[0].doubleValue() >= 0){
            str += "+" + this.values[0].toString();
        } else{
            str += this.values[0].toString();
        }
        return str;
    }

    @Override
    public Polinomio div(Polinomio other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEqualTo(Polinomio other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(Polinomio other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(Polinomio other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(Polinomio o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Polinomio<E> op() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Polinomio<E> inv() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Polinomio<E> one() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Polinomio<E> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Polinomio<E> clone() {
        return new Polinomio(this.values, this.maxGrado, this.variable);
    }

    @Override
    public Polinomio<E> abs() {
        return null;
    }

    @Override
    public Polinomio<E> scale(double factor) {
        return null;
    }

    @Override
    public Polinomio<E> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
