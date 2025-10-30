/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities.Algebra.Fields;

import com.miras.javaUtilities.Algebra.Field;

/**
 *(([-]?\d*([.](\d*)+)?)(([a-z])\^((\d*))+)?)?
 * @author Samir Lyaoui Vidal
 * @param <E> the type of element that the coeficients are
 * @param <T>
 */
public class PolinomioNVariable<E extends Field<E>> implements Field<PolinomioNVariable<E>> {
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
    public PolinomioNVariable<E> sqrt() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> op() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> inv() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> one() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> zero() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> clone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> abs() {
        return null;
    }

    @Override
    public PolinomioNVariable<E> scale(double factor) {
        return null;
    }

    @Override
    public PolinomioNVariable<E> sum(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> dif(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PolinomioNVariable<E> mult(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public PolinomioNVariable<E> div(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isEqualTo(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isGreaterThan(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isLessThan(PolinomioNVariable<E> other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int compareTo(PolinomioNVariable<E> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
