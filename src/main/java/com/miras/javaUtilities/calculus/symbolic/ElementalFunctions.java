package com.miras.javaUtilities.calculus.symbolic;

import com.miras.javaUtilities.algebra.objects.ConstantNumber;
import com.miras.javaUtilities.calculus.interfaces.ElementalFunction;

import java.util.function.Function;
import java.util.function.Supplier;

public class ElementalFunctions {

    private static abstract class ElementalFunctionInstance implements ElementalFunction<ElementalFunctionInstance>, Comparable<ElementalFunctionInstance> {
        @Override
        public int compareTo(ElementalFunctionInstance other){
            return 0;
        }

        @Override
        public ElementalFunctionInstance clone(){
            return null;
        }

        @Override
        public String toString(){
            return getOperator();
        }

        @Override
        public boolean sinType(){
            return true;
        }

        @Override
        public ElementalFunctionInstance sum(ElementalFunctionInstance other) {
            return null;
        }

        @Override
        public ElementalFunctionInstance dif(ElementalFunctionInstance other) {
            return null;
        }

        @Override
        public ElementalFunctionInstance mult(ElementalFunctionInstance other) {
            return null;
        }

        @Override
        public ElementalFunctionInstance div(ElementalFunctionInstance other) {
            return null;
        }

        @Override
        public ElementalFunctionInstance sqrt() {
            return null;
        }

        @Override
        public ElementalFunctionInstance one() {
            return null;
        }

        @Override
        public ElementalFunctionInstance zero() {
            return null;
        }

        @Override
        public ElementalFunctionInstance op() {
            return null;
        }

        @Override
        public ElementalFunctionInstance abs() {
            return null;
        }

        @Override
        public ElementalFunctionInstance scale(double factor) {
            return null;
        }

        @Override
        public boolean isEqualTo(ElementalFunctionInstance other) {
            return false;
        }

        @Override
        public boolean isGreaterThan(ElementalFunctionInstance other) {
            return false;
        }

        @Override
        public boolean isLessThan(ElementalFunctionInstance other) {
            return false;
        }

        @Override
        public ElementalFunctionInstance inv() {
            return null;
        }
    }

    public static final Supplier<ElementalFunction<?>> LN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "ln()";
        }

        @Override
        public String getLaTex() {
            return "\\ln(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("LN requires at least one value.");
            return Math.log(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("LN requires at least one value.");
            return getIntFun(values[0], Math::log);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("LN requires at least one value.");
            return getFloatFun(values[0], Math::log);
        }

        @Override
        public NumberFunctionTree getDerivative(){
            return OP.get().getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> LOG = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "log10()";
        }

        @Override
        public String getLaTex() {
            return "\\log_{10}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("LOG requires at least one value.");
            return Math.log10(values[0]); // Corrected from Math.log to Math.log10 for base 10 log
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("LOG requires at least one value.");
            return getIntFun(values[0], Math::log10);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("LOG requires at least one value.");
            return getFloatFun(values[0], Math::log10);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return new NumberFunctionTree().scale(10.0).op();
        }
    };

    public static final Supplier<ElementalFunction<?>> EXP = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "e ^ ()";
        }

        @Override
        public String getLaTex() {
            return "e^{arg1}";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("EXP requires at least one value.");
            return Math.exp(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("EXP requires at least one value.");
            return getIntFun(values[0], Math::exp);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("EXP requires at least one value.");
            return getFloatFun(values[0], Math::exp);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return EXP.get().getTree();
        }
    };

    public static final Function<Double, ElementalFunction<?>> EXPNUM = (base) -> new ElementalFunctionInstance() {

        @Override
        public String getOperator() {
            return base.toString() + " ^ ()";
        }

        @Override
        public String getLaTex() {
            return base.intValue() + "^{arg1}";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0 || base == null) {
                throw new IllegalArgumentException("EXPNUM requires at least one value and a base.");
            }
            return Math.pow(base, values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0 || base == null) {
                throw new IllegalArgumentException("EXPNUM requires at least one value and a base.");
            }
            return getIntFun(values[0], (value) -> Math.pow(base, value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0 || base == null) {
                throw new IllegalArgumentException("EXPNUM requires at least one value and a base.");
            }
            return getFloatFun(values[0], (value) -> Math.pow(base, value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return EXPNUM.apply(base).getTree().scale(Math.log(base));
        }
    };

    public static final Function<Double, ElementalFunction<?>> EXPGEN = (exponentValue) -> new ElementalFunctionInstance() {

        @Override
        public String getOperator() {
            return "^";
        }

        @Override
        public int priority(){
            return 1;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public String toString(){
            return this.getOperator() + exponentValue.toString();
        }

        @Override
        public String getLaTex() {
            return "(arg1)^{" + exponentValue.intValue() + "}";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0 || exponentValue == null) {
                throw new IllegalArgumentException("EXPGEN requires at least one value and an exponent.");
            }
            return Math.pow(values[0], exponentValue);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0 || exponentValue == null) {
                throw new IllegalArgumentException("EXPGEN requires at least one value and an exponent.");
            }
            return getIntFun(values[0], (value) -> Math.pow(value, exponentValue));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0 || exponentValue == null) {
                throw new IllegalArgumentException("EXPGEN requires at least one value and an exponent.");
            }
            return getFloatFun(values[0], (value) -> Math.pow(value, exponentValue));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of x^a is a * x^(a-1)
            // This would involve complex tree construction for multiplication and power.
            return EXPGEN.apply(exponentValue -1).getTree().scale(exponentValue);
        }
    };

    public static final Supplier<ElementalFunction<?>> SIN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "sin()";
        }

        @Override
        public String getLaTex() {
            return "\\sin(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("SIN requires at least one value.");
            return Math.sin(values[0]); // Corrected from Math.log to Math.sin
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("SIN requires at least one value.");
            return getIntFun(values[0], Math::sin);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("SIN requires at least one value.");
            return getFloatFun(values[0], Math::sin);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return COS.get().getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> COS = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cos()";
        }

        @Override
        public String getLaTex() {
            return "\\cos(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("COS requires at least one value.");
            return Math.cos(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("COS requires at least one value.");
            return getIntFun(values[0], Math::cos);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("COS requires at least one value.");
            return getFloatFun(values[0], Math::cos);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return SIN.get().getTree().op();
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCSIN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arcsin()";
        }

        @Override
        public String getLaTex() {
            return "\\arcsin(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSIN requires at least one value.");
            return Math.asin(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSIN requires at least one value.");
            return getIntFun(values[0], Math::asin);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSIN requires at least one value.");
            return getFloatFun(values[0], Math::asin);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arcsin(x) is 1 / sqrt(1 - x^2)
            return ONE.get().getTree().dif(EXPGEN.apply(2.0).getTree()).sqrt().inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCCOS = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arccos()";
        }

        @Override
        public String getLaTex() {
            return "\\arccos(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOS requires at least one value.");
            return Math.acos(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOS requires at least one value.");
            return getIntFun(values[0], Math::acos);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOS requires at least one value.");
            return getFloatFun(values[0], Math::acos);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arccos(x) is -1 / sqrt(1 - x^2)
            return ONE.get().getTree().dif(EXPGEN.apply(2.0).getTree()).sqrt().inv().op();
        }
    };

    public static final Supplier<ElementalFunction<?>> TAN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "tan()";
        }

        @Override
        public String getLaTex() {
            return "\\tan(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("TAN requires at least one value.");
            return Math.tan(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("TAN requires at least one value.");
            return getIntFun(values[0], Math::tan);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("TAN requires at least one value.");
            return getFloatFun(values[0], Math::tan);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of tan(x) is sec^2(x) = (sec(x))^2
            return SEC.get().getTree().expGen(2);
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCTAN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arctan()";
        }

        @Override
        public String getLaTex() {
            return "\\arctan(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTAN requires at least one value.");
            return Math.atan(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTAN requires at least one value.");
            return getIntFun(values[0], Math::atan);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTAN requires at least one value.");
            return getFloatFun(values[0], Math::atan);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arctan(x) is 1 / (1 + x^2)
            return ONE.get().getTree().sum(EXPGEN.apply(2.0).getTree()).inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> SEC = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "sec()";
        }

        @Override
        public String getLaTex() {
            return "\\sec(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("SEC requires at least one value.");
            return 1 / Math.cos(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("SEC requires at least one value.");
            return getIntFun(values[0], (value) -> 1 / Math.cos(value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("SEC requires at least one value.");
            return getFloatFun(values[0], (value) -> 1 / Math.cos(value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of sec(x) is sec(x) * tan(x)
            return SEC.get().getTree().mult(TAN.get().getTree());
        }
    };

    public static final Supplier<ElementalFunction<?>> COSEC = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cosec()";
        }

        @Override
        public String getLaTex() {
            return "\\cosec(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSEC requires at least one value.");
            return 1 / Math.sin(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSEC requires at least one value.");
            return getIntFun(values[0], (value) -> 1 / Math.sin(value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSEC requires at least one value.");
            return getFloatFun(values[0], (value) -> 1 / Math.sin(value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of cosec(x) is -cosec(x) * cotan(x)
            return COSEC.get().getTree().op().mult(COTAN.get().getTree());
        }
    };

    public static final Supplier<ElementalFunction<?>> COTAN = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cotan()";
        }

        @Override
        public String getLaTex() {
            return "\\cotan(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("COTAN requires at least one value.");
            return 1 / Math.tan(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("COTAN requires at least one value.");
            return getIntFun(values[0], (value) -> 1 / Math.tan(value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("COTAN requires at least one value.");
            return getFloatFun(values[0], (value) -> 1 / Math.tan(value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of cotan(x) is -cosec^2(x)
            return COSEC.get().getTree().expGen(2).op();
        }
    };

    public static final Supplier<ElementalFunction<?>> SINH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "sinh()";
        }

        @Override
        public String getLaTex() {
            return "\\sinh(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("SINH requires at least one value.");
            return Math.sinh(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("SINH requires at least one value.");
            return getIntFun(values[0], Math::sinh);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("SINH requires at least one value.");
            return getFloatFun(values[0], Math::sinh);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of sinh(x) is cosh(x)
            return COSH.get().getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> COSH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cosh()";
        }

        @Override
        public String getLaTex() {
            return "\\cosh(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSH requires at least one value.");
            return Math.cosh(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSH requires at least one value.");
            return getIntFun(values[0], Math::cosh);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSH requires at least one value.");
            return getFloatFun(values[0], Math::cosh);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of cosh(x) is sinh(x)
            return SINH.get().getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCSINH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arcsinh()";
        }

        @Override
        public String getLaTex() {
            return "\\text{arcsinh}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSINH requires at least one value.");
            return Math.log(values[0] + Math.sqrt(values[0] * values[0] + 1.0));
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSINH requires at least one value.");
            return getIntFun(values[0], (value) -> Math.log(value + Math.sqrt(value * value + 1.0)));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSINH requires at least one value.");
            return getFloatFun(values[0], (value) -> Math.log(value + Math.sqrt(value * value + 1.0)));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arcsinh(x) is 1 / sqrt(x^2 + 1)
            return ONE.get().getTree().sum(EXPGEN.apply(2.0).getTree()).sqrt().inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCCOSH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arccosh()";
        }

        @Override
        public String getLaTex() {
            return "\\text{arccosh}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSH requires at least one value.");
            double val = values[0];
            if (val < 1.0) throw new IllegalArgumentException("arccosh(x) is only defined for x >= 1. Encountered: " + val);
            return Math.log(val + Math.sqrt(val * val - 1.0));
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSH requires at least one value.");
            double val = values[0];
            if (val < 1.0) throw new IllegalArgumentException("arccosh(x) is only defined for x >= 1. Encountered: " + val);
            return getIntFun(values[0], (value) -> Math.log(value + Math.sqrt(value * value - 1.0)));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSH requires at least one value.");
            double val = values[0];
            if (val < 1.0) throw new IllegalArgumentException("arccosh(x) is only defined for x >= 1. Encountered: " + val);
            return getFloatFun(values[0], (value) -> Math.log(value + Math.sqrt(value * value - 1.0)));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arccosh(x) is 1 / sqrt(x^2 - 1)
            return EXPGEN.apply(2.0).getTree().dif(ONE.get().getTree()).sqrt().inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> TANH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "tanh()";
        }

        @Override
        public String getLaTex() {
            return "\\tanh(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("TANH requires at least one value.");
            return Math.tanh(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("TANH requires at least one value.");
            return getIntFun(values[0], Math::tanh);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("TANH requires at least one value.");
            return getFloatFun(values[0], Math::tanh);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of tanh(x) is sech^2(x)
            return SECH.get().getTree().expGen(2);
        }
    };

    public static final Supplier<ElementalFunction<?>> COTANH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cotanh()";
        }

        @Override
        public String getLaTex() {
            return "\\cotanh(arg1)";
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return COSECH.get().getTree().expGen(2.0).op();
        }

        @Override
        public double apply(double... values) {
            return 1 / Math.tanh(values[0]);
        }

        @Override
        public int apply(int... values) {
            return getIntFun(values[0], value -> 1 / Math.tanh(value));
        }

        @Override
        public float apply(float... values) {
            return getFloatFun(values[0], value -> 1 / Math.tanh(value));
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCTANH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arctanh()";
        }

        @Override
        public String getLaTex() {
            return "\\text{arctanh}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTANH requires at least one value.");
            double val = values[0];
            if (val <= -1.0 || val >= 1.0) throw new IllegalArgumentException("arctanh(x) is only defined for -1 < x < 1. Encountered: " + val);
            return 0.5 * Math.log((1.0 + val) / (1.0 - val));
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTANH requires at least one value.");
            double val = values[0];
            if (val <= -1.0 || val >= 1.0) throw new IllegalArgumentException("arctanh(x) is only defined for -1 < x < 1. Encountered: " + val);
            return getIntFun(values[0], (value) -> 0.5 * Math.log((1.0 + value) / (1.0 - value)));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCTANH requires at least one value.");
            double val = values[0];
            if (val <= -1.0 || val >= 1.0) throw new IllegalArgumentException("arctanh(x) is only defined for -1 < x < 1. Encountered: " + val);
            return getFloatFun(values[0], (value) -> 0.5 * Math.log((1.0 + value) / (1.0 - value)));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arctanh(x) is 1 / (1 - x^2)
            return ONE.get().getTree().dif(EXPGEN.apply(2.0).getTree()).inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> SECH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "sech()";
        }

        @Override
        public String getLaTex() {
            return "\\text{sech}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("SECH requires at least one value.");
            return 1 / Math.cosh(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("SECH requires at least one value.");
            return getIntFun(values[0], (value) -> 1 / Math.cosh(value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("SECH requires at least one value.");
            return getFloatFun(values[0], (value) -> 1 / Math.cosh(value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of sech(x) is -sech(x) * tanh(x)
            return SECH.get().getTree().op().mult(TANH.get().getTree());
        }
    };

    public static final Supplier<ElementalFunction<?>> COSECH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "cosech()";
        }

        @Override
        public String getLaTex() {
            return "\\text{cosech(arg1)}";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSECH requires at least one value.");
            return 1 / Math.sinh(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSECH requires at least one value.");
            return getIntFun(values[0], (value) -> 1 / Math.sinh(value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("COSECH requires at least one value.");
            return getFloatFun(values[0], (value) -> 1 / Math.sinh(value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of cosech(x) is -cosech(x) * coth(x)
            return COSECH.get().getTree().op().mult(COTANH.get().getTree());
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCSECH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arcsech()";
        }

        @Override
        public String getLaTex() {
            return "\\text{arcsech(arg1)}";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSECH requires at least one value.");
            double val = values[0];
            if (val <= 0.0 || val > 1.0) throw new IllegalArgumentException("arcsech(x) is only defined for 0 < x <= 1. Encountered: " + val);
            return Math.log((1.0 + Math.sqrt(1.0 - val * val)) / val);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSECH requires at least one value.");
            double val = values[0];
            if (val <= 0.0 || val > 1.0) throw new IllegalArgumentException("arcsech(x) is only defined for 0 < x <= 1. Encountered: " + val);
            return getIntFun(values[0], (value) -> Math.log((1.0 + Math.sqrt(1.0 - value * value)) / value));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCSECH requires at least one value.");
            double val = values[0];
            if (val <= 0.0 || val > 1.0) throw new IllegalArgumentException("arcsech(x) is only defined for 0 < x <= 1. Encountered: " + val);
            return getFloatFun(values[0], (value) -> Math.log((1.0 + Math.sqrt(1.0 - value * value)) / value));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arcsech(x) is -1 / (x * sqrt(1 - x^2))
            return ONE.get().getTree().dif(EXPGEN.apply(2.0).getTree()).sqrt().mult(new NumberFunctionTree()).inv().op();
        }
    };

    public static final Supplier<ElementalFunction<?>> ARCCOSECH = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "arcosech()";
        }

        @Override
        public String getLaTex() {
            return "\\text{arccosech}(arg1)";
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSECH requires at least one value.");
            double val = values[0];
            if (val == 0.0) throw new IllegalArgumentException("arccosech(x) is not defined for x = 0.");
            double invX = 1.0 / val;
            return Math.log(invX + Math.sqrt(invX * invX + 1.0));
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSECH requires at least one value.");
            double val = values[0];
            if (val == 0.0) throw new IllegalArgumentException("arccosech(x) is not defined for x = 0.");
            double invX = 1.0 / val;
            return getIntFun(values[0], (value) -> Math.log(invX + Math.sqrt(invX * invX + 1.0)));
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("ARCCOSECH requires at least one value.");
            double val = values[0];
            if (val == 0.0) throw new IllegalArgumentException("arccosech(x) is not defined for x = 0.");
            double invX = 1.0 / val;
            return getFloatFun(values[0], (value) -> Math.log(invX + Math.sqrt(invX * invX + 1.0)));
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of arccosech(x) is -1 / (|x| * sqrt(x^2 + 1))
            return ONE.get().getTree().sum(EXPGEN.apply(2.0).getTree()).sqrt().mult(ABS.get().getTree()).inv().op();
        }
    };

    public static final Supplier<ElementalFunction<?>> SUM = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "+";
        }

        @Override
        public int priority(){
            return 3;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public String getLaTex() {
            return "(arg1) + (arg2)";
        }

        @Override
        public double apply(double... values) {
            if (values.length < 2) throw new IllegalArgumentException("SUM requires at least two values.");
            return values[0] + values[1];
        }

        @Override
        public int apply(int... values) {
            if (values.length < 2) throw new IllegalArgumentException("SUM requires at least two values.");
            return values[0] + values[1];
        }

        @Override
        public float apply(float... values) {
            if (values.length < 2) throw new IllegalArgumentException("SUM requires at least two values.");
            return values[0] + values[1];
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of f(x)+g(x) is f'(x)+g'(x)
            return this.getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> DIF = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "-";
        }

        @Override
        public int priority(){
            return 3;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public String getLaTex() {
            return "(arg1) - (arg2)";
        }

        @Override
        public double apply(double... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIF requires at least two values.");
            return values[0] - values[1];
        }

        @Override
        public int apply(int... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIF requires at least two values.");
            return values[0] - values[1];
        }

        @Override
        public float apply(float... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIF requires at least two values.");
            return values[0] - values[1];
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of f(x)-g(x) is f'(x)-g'(x)
            return this.getTree();
        }
    };

    public static final Supplier<ElementalFunction<?>> MULT = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "*";
        }

        @Override
        public int priority(){
            return 2;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public String getLaTex() {
            return "arg1 \\cdot arg2";
        }

        @Override
        public double apply(double... values) {
            if (values.length < 2) throw new IllegalArgumentException("MULT requires at least two values.");
            return values[0] * values[1];
        }

        @Override
        public int apply(int... values) {
            if (values.length < 2) throw new IllegalArgumentException("MULT requires at least two values.");
            return values[0] * values[1];
        }

        @Override
        public float apply(float... values) {
            if (values.length < 2) throw new IllegalArgumentException("MULT requires at least two values.");
            return values[0] * values[1];
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return null;
        }
    };

    public static final Supplier<ElementalFunction<?>> DIV = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "/";
        }

        @Override
        public String getLaTex() {
            return "\\frac{arg1}{arg2}";
        }

        @Override
        public int priority(){
            return 2;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public double apply(double... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIV requires at least two values.");
            if (values[1] == 0) throw new ArithmeticException("Division by zero.");
            return values[0] / values[1];
        }

        @Override
        public int apply(int... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIV requires at least two values.");
            if (values[1] == 0) throw new ArithmeticException("Division by zero.");
            return values[0] / values[1];
        }

        @Override
        public float apply(float... values) {
            if (values.length < 2) throw new IllegalArgumentException("DIV requires at least two values.");
            if (values[1] == 0) throw new ArithmeticException("Division by zero.");
            return values[0] / values[1];
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of f(x)/g(x) is (f'(x)g(x) - f(x)g'(x)) / g(x)^2 (Quotient Rule)
            return null;
        }
    };

    public static final Supplier<ElementalFunction<?>> SQRT = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "sqrt()";
        }

        @Override
        public String getLaTex() {
            return "\\sqrt{arg1}";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("SQRT requires at least one value.");
            return Math.sqrt(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("SQRT requires at least one value.");
            return getIntFun(values[0], Math::sqrt);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("SQRT requires at least one value.");
            return getFloatFun(values[0], Math::sqrt);
        }

        @Override
        public NumberFunctionTree getDerivative() {
            // Derivative of sqrt(x) is 1 / (2 * sqrt(x))
            return SQRT.get().getTree().scale(2.0).inv();
        }
    };

    public static final Supplier<ElementalFunction<?>> ZERO = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "0";
        }

        @Override
        public String getLaTex() {
            return "0";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return ZERO.get().getTree();
        }

        @Override
        public double apply(double... values) {
            return 0;
        }

        @Override
        public int apply(int... values) {
            return 0;
        }

        @Override
        public float apply(float... values) {
            return 0;
        }
    };

    public static final Supplier<ElementalFunction<?>> ONE = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "1";
        }

        @Override
        public String getLaTex() {
            return "1";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return ZERO.get().getTree(); // Derivative of a constant is 0
        }

        @Override
        public double apply(double... values) {
            return 1;
        }

        @Override
        public int apply(int... values) {
            return 1;
        }

        @Override
        public float apply(float... values) {
            return 1;
        }
    };

    public static final Supplier<ElementalFunction<?>> OP = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "-()";
        }

        @Override
        public String getLaTex() {
            return "-(arg1)";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return OP.get().getTree();
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) throw new IllegalArgumentException("OP (reciprocal) requires at least one value.");
            if (values[0] == 0) throw new ArithmeticException("Division by zero for reciprocal.");
            return 1 / values[0];
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) throw new IllegalArgumentException("OP (reciprocal) requires at least one value.");
            if (values[0] == 0) throw new ArithmeticException("Division by zero for reciprocal.");
            return Float.valueOf(1f / values[0]).intValue();
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) throw new IllegalArgumentException("OP (reciprocal) requires at least one value.");
            if (values[0] == 0) throw new ArithmeticException("Division by zero for reciprocal.");
            return 1 / values[0];
        }
    };

    public static final Function<Variable, ElementalFunction<?>> VARIABLE = (variable) -> new ElementalFunctionInstance() {

        int index = variable.getNumber();
        String varName = variable.getLaTexRepr();

        @Override
        public String getOperator() {
            return varName;
        }

        @Override
        public String getID(){
            return "Variable";
        }

        @Override
        public String getLaTex() {
            return varName;
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public boolean single(){
            return true;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return ONE.get().getTree();
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) return 0;
            return values[index];
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) return 0;
            return values[index];
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) return 0;
            return values[index];
        }
    };

    public static final Supplier<ElementalFunction<?>> ABS = () -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return "||";
        }

        @Override
        public String getLaTex() {
            return "|arg1|";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return ABS.get().getTree();
        }

        @Override
        public double apply(double... values) {
            if (values.length == 0) return 0;
            return Math.abs(values[0]);
        }

        @Override
        public int apply(int... values) {
            if (values.length == 0) return 0;
            return getIntFun(values[0], Math::abs);
        }

        @Override
        public float apply(float... values) {
            if (values.length == 0) return 0;
            return getFloatFun(values[0], Math::abs);
        }
    };

    public static final Function<Double, ElementalFunction<?>> SCALE = (factor) -> new ElementalFunctionInstance() {
        @Override
        public String getOperator() {
            return factor.intValue() + "*()";
        }

        @Override
        public String getLaTex() {
            return factor.intValue() + "\\cdot (arg1)";
        }

        @Override
        public boolean sinType(){
            return false;
        }

        @Override
        public NumberFunctionTree getDerivative() {
            return new ConstantNumber<Double>().CONSTANTNUMBER.apply(factor).getTree();
        }

        @Override
        public double apply(double... values) {
            return factor * values[0];
        }

        @Override
        public int apply(int... values) {
            return Double.valueOf(factor * values[0]).intValue();
        }

        @Override
        public float apply(float... values) {
            return Double.valueOf(factor * values[0]).floatValue();
        }
    };

    private static int getIntFun(int value, Function<Double, Double> function){
        return function.apply(Integer.valueOf(value).doubleValue()).intValue();
    }

    private static int getFloatFun(float value, Function<Double, Double> function){
        return function.apply(Float.valueOf(value).doubleValue()).intValue();
    }

}
