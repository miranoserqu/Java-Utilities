/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

/**
 *
 * @author Samir Lyaoui Vidal
 * @param <E>
 */
public class NumberField<E extends Number> implements Field<NumberField<E>, E> {
    
    private final E value;
    
    NumberField(E value){
        this.value = value;
    }
    
    @Override
    public NumberField<E> sum(NumberField<E> other){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(value.intValue() + other.value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(value.floatValue() + other.value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(value.doubleValue() + other.value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> mult(NumberField<E> other){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(value.intValue() * other.value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(value.floatValue() * other.value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(value.doubleValue() * other.value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> dif(NumberField<E> other){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(value.intValue() - other.value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(value.floatValue() - other.value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(value.doubleValue() - other.value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> div(NumberField<E> other){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(value.intValue() * other.inv().value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(value.floatValue() * other.inv().value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(value.doubleValue() * other.inv().value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> op(){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(-value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(-value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(-value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> inv(){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(1 / value.intValue()));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(1f / value.floatValue()));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(1 / value.doubleValue()));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> zero(){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(0));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(0));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(0));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public NumberField<E> one(){
        if(value instanceof Integer){
            return new NumberField<E>((E) Integer.valueOf(1));
        } else if(value instanceof Float){
            return new NumberField<E>((E) Float.valueOf(1));
        } else if(value instanceof Double){
            return new NumberField<E>((E) Double.valueOf(1));
        }
        
        throw new UnsupportedOperationException("Tipo de dato no soportado");
    }
    
    @Override
    public String toString(){
        return value.toString();
    }
    
    @Override
    public boolean isEqualTo(NumberField<E> other){
        return compareTo(other) == 0;
    }
    
    @Override
    public boolean isGreaterThan(NumberField<E> other){
        return compareTo(other) > 0;
    }
    
    @Override
    public boolean isLessThan(NumberField<E> other){
        return compareTo(other) < 0;
    }
    
    @Override
    public NumberField<E> clone(){
        return new NumberField<>(value);
    }
    
    @Override
    public int compareTo(NumberField<E> other){
        return Double.compare(value.doubleValue(), other.value.doubleValue());
    }

    public E getValue() {
        return value;
    }

    @Override
    public NumberField<E> sqrt() {
        return new NumberField((E) Double.valueOf(Math.sqrt(value.doubleValue())));
    }

    @Override
    public NumberField<E> multExt(E k) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
