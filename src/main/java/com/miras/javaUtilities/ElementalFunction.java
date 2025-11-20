/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miras.javaUtilities;

import java.util.Objects;

/**
 *
 * @author Samir Lyaoui Vidal
 */
public interface ElementalFunction<T extends ElementalFunction<T>> extends Operator<T> {

    default FunctionTree getTree(){
        return new FunctionTree(this);
    }

    String getOperator();
    String getLaTex();

    default FunctionTree getDerivative(Variable variable) {
        return null;
    }

    @Override
    default T sum(T other){
        return null;
    }

    @Override
    default T dif(T other){
        return null;
    }

    @Override
    default T mult(T other){
        return null;
    }

    @Override
    default T div(T other){
        return null;
    }

    @Override
    default T sqrt() {
        return null;
    }

    @Override
    default T op() {
        return null;
    }

    @Override
    default T inv() {
        return null;
    }

    @Override
    default T one() {
        return null;
    }

    @Override
    default T zero() {
        return null;
    }

    @Override
    default T clone() {
        return null;
    }

    @Override
    default boolean isEqualTo(T other) {
        return false;
    }

    @Override
    default boolean isGreaterThan(T other) {
        return false;
    }

    @Override
    default boolean isLessThan(T other) {
        return false;
    }

    @Override
    default T scale(double factor) {
        return null;
    }

    @Override
    default T abs(){
        return null;
    }

    @Override
    default T ln() {
        return Operator.super.ln();
    }

    @Override
    default T log() {
        return Operator.super.log();
    }

    @Override
    default T exp() {
        return Operator.super.exp();
    }

    @Override
    default T expNum(double base) {
        return Operator.super.expNum(base);
    }

    @Override
    default T expGen(double exponent) {
        return Operator.super.expGen(exponent);
    }

    @Override
    default T sin() {
        return Operator.super.sin();
    }

    @Override
    default T cos() {
        return Operator.super.cos();
    }

    @Override
    default T arcsin() {
        return Operator.super.arcsin();
    }

    @Override
    default T arccos() {
        return Operator.super.arccos();
    }

    @Override
    default T tan() {
        return Operator.super.tan();
    }

    @Override
    default T arctan() {
        return Operator.super.arctan();
    }

    @Override
    default T sec() {
        return Operator.super.sec();
    }

    @Override
    default T cosec() {
        return Operator.super.cosec();
    }

    @Override
    default T cotan() {
        return Operator.super.cotan();
    }

    @Override
    default T sinh() {
        return Operator.super.sinh();
    }

    @Override
    default T cosh() {
        return Operator.super.cosh();
    }

    @Override
    default T arcsinh() {
        return Operator.super.arcsinh();
    }

    @Override
    default T arccosh() {
        return Operator.super.arccosh();
    }

    @Override
    default T tanh() {
        return Operator.super.tanh();
    }

    @Override
    default T cotanh() {
        return Operator.super.cotanh();
    }

    @Override
    default T arctanh() {
        return Operator.super.arctanh();
    }

    @Override
    default T sech() {
        return Operator.super.sech();
    }

    @Override
    default T cosech() {
        return Operator.super.cosech();
    }

    @Override
    default T arcsech() {
        return Operator.super.arcsech();
    }

    @Override
    default T arccosech() {
        return Operator.super.arccosech();
    }

    @Override
    default int compareTo(ElementalFunction o) {
        return 0;
    }

    public static boolean equals(ElementalFunction one, ElementalFunction other){
        return one.getOperator().equals(other.getOperator());
    }

}
