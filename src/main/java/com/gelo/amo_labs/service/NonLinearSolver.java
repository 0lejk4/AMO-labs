package com.gelo.amo_labs.service;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import java.lang.reflect.InvocationTargetException;

public class NonLinearSolver {
    private static final ExpressionEvaluator ee = new ExpressionEvaluator();

    public NonLinearSolver(String function) {
        // Now here's where the story begins...

        // The expression will have two "int" parameters: "a" and "b".
        ee.setParameters(new String[]{"x"}, new Class[]{double.class});

        // And the expression (i.e. "result") type is also "int".
        ee.setExpressionType(double.class);
        try {
            ee.cook(function);
        } catch (CompileException e) {
            e.printStackTrace();
        }
    }

    public int setFunction(String function) {
        try {
            ee.cook(function);
        } catch (CompileException e) {
            return 1;
        }
        return 0;
    }

    public double applyFunction(double x) {
        // Eventually we evaluate the expression - and that goes super-fast.
        double result = 0;
        try {
            result = (double) ee.evaluate(new Object[]{x});
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Double solve(Double a, Double b, Double accuracy) {
        if (applyFunction(a) * applyFunction(b) < 0) {
            while (true) {
                Double c = (b + a) / 2;
                if (Math.abs(b - a) < accuracy || applyFunction(c) == 0) {
                    return c;
                } else if (applyFunction(a) * applyFunction(c) < 0) {
                    b = c;
                } else {
                    a = c;
                }
            }
        }
        return null;
    }

}
