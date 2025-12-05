package com.miras.javaUtilities.calculus.symbolic;

public class Variable {

    private final int number;
    private final String LaTexRepr;

    public Variable(int number, String LaTexRepr){

        this.LaTexRepr = LaTexRepr;
        this.number = number;

    }

    public String getLaTexRepr(){
        return LaTexRepr;
    }

    public int getNumber(){
        return number;
    }

    public boolean equals(Variable other){
        return this.number == other.number;
    }

}
