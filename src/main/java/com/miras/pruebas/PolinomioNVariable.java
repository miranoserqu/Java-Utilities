/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *(([-]?\d*([.](\d*)+)?)(([a-z])\^((\d*))+)?)?
 * @author Samir Lyaoui Vidal
 * @param <E> the type of element that the coeficients are
 * @param <T>
 */
public class PolinomioNVariable<E extends Field<E, T>, T> implements Field<PolinomioNVariable<E, T>, E> {
    /*
    
    Dictionary<Character, Polinomio> values;
    E indepT;
    
    PolinomioNVariable(String polinomio, E terminoIndependiente, int maxGrado){
        Pattern pattern = Pattern.compile("([-]?\\d+([.](\\d+)+)?)(([a-z])(\\^)((\\d*)+))?");
        Matcher matcher = pattern.matcher(polinomio);
        values = new Hashtable();
        while(matcher.find()){
            if(!"".equals(matcher.group())){
                try{
                    this.values.get(matcher.group(5).charAt(0))..getValues()[Integer.parseInt(matcher.group(7))] = (E) Double.valueOf(matcher.group(1));
                } catch(Exception E){
                    E[] aux = (E[]) new NumberField[maxGrado + 1];
                    E zero = this.indepT.zero();
                    Arrays.fill(aux, (E) zero);
                    aux[Integer.parseInt(matcher.group(7))] = (E) new NumberField(Double.valueOf(matcher.group(1)));
                    System.out.println(Arrays.toString(aux));
                    this.values.put(matcher.group(5).charAt(0), new Polinomio<>(aux, maxGrado, matcher.group(5).charAt(0)));
                }
            }
        }
        this.indepT = terminoIndependiente;
    }
    
    PolinomioNVariable(Dictionary<Character, E>[] polinomio, E terminoIndependiente, int maxGrado){
        System.arraycopy(polinomio, 0, this.values, 0, maxGrado);
        this.indepT = terminoIndependiente;
    }
    
    @Override
    public String toString(){
        String str = "";
        try{
            Iterator<Character> keyIterator = this.values.keys().asIterator();
            while(keyIterator.hasNext()){
                char currentKey = keyIterator.next();
                for(int i = this.values.get(currentKey).getValues().length - 1; i > 0; i--){
                    E zero = (E) this.values.get(currentKey).getValues()[i].zero();
                    if((this.values.get(currentKey).getValues()[i].isGreaterThan(zero) || this.values.get(currentKey).getValues()[i].isEqualTo(zero))
                            && !str.equals("") && !this.values.get(currentKey).getValues()[i].isEqualTo(zero)){
                        str += "+" + this.values.get(currentKey).getValues()[i].toString() + currentKey + "^" + i;
                    } else if(!this.values.get(currentKey).getValues()[i].isEqualTo(zero)){
                        str += this.values.get(currentKey).getValues()[i].toString() + currentKey + "^" + i;
                    }
                }
            }
        } catch(Exception E){

        }
        
        if(this.indepT.isGreaterThan(this.indepT.zero()) || this.indepT.isEqualTo(this.indepT.zero())){
            str += "+" + this.indepT.toString();
        } else{
            str += this.indepT.toString();
        }
        return str;
    }
    */

    @Override
    public PolinomioNVariable<E, T> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> op() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> inv() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> one() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> sum(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> dif(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> mult(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> multExt(E k) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E, T> div(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEqualTo(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(PolinomioNVariable<E, T> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(PolinomioNVariable<E, T> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
