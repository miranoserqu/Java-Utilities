/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.pruebas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Ciclo<E> {
    
    private final ArrayList<E> elements;
    private final int numberOfElements;
    
    Ciclo(ArrayList<E> elements){
        this.numberOfElements = elements.size();
        this.elements = elements;
    }
    
    public ArrayList<E> getElements(){
        return elements;
    }
    
    public E get(E e){
        if(elements.contains(e))
            try{
                return elements.get(Arrays.binarySearch(elements.toArray(), e) + 1);
            } catch(Exception a){
                return elements.get(0);
            }
        return e;
    }
    
    public int sig(){
        return (int) Math.pow(-1, this.numberOfElements - 1);
    }
    
    public <E> List<List<E>> getCyclePermutations(){
        List<List<E>> cycles = new ArrayList<>();
        getCycles((ArrayList<E>) this.getElements(), new boolean[numberOfElements], cycles, new ArrayList<>(), 0);
        return cycles;
    }
    
    private <E> void getCycles(List<E> elements, boolean[] visited, List<List<E>> cycles, List<E> currentCycle, int start){
        if(allVisited(visited)){
            cycles.add(currentCycle);
            return;
        }
        for(int i = start; i < numberOfElements; i++){
            if(!visited[i]){
                visited[i] = true;
                currentCycle.add((E) elements.get(i));
                boolean[] newVisited = new boolean[numberOfElements];
                System.arraycopy(visited, 0, newVisited, 0, visited.length);
                getCycles(elements, newVisited, cycles, new ArrayList<>(currentCycle), (i + 1));
                currentCycle.removeLast();
            }
        }
    }
    
    private static boolean allVisited(boolean[] visited){
        for (boolean v : visited){
            if (!v){
                return false;
            }
        }
        return true;
    }
    
}
