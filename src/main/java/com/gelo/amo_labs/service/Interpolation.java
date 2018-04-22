package com.gelo.amo_labs.service;


import java.util.function.Function;

public class Interpolation {
    public static final int Lagrange = 0;
    public static final int Newton = 1;
    public static final int Eitken = 2;
    private double a;
    private double b;
    private double step;
    private int count;
    private double[] xNodes;
    private double[] yNodes;
    private double[] zNodes;
    private Function<Double, Double> function;

    public Interpolation(double a, double b, int count) {
        this.a = a;
        this.b = b;
        this.count = count;
        this.step = (b - a) / this.count;
        this.xNodes = new double[count + 1];
        this.yNodes = new double[count + 1];
        this.zNodes = new double[80];
        for (int i = 0; i <= count; i++)
            this.xNodes[i] = this.a + i * this.step;
    }

    public void setY(Function<Double, Double> function) {
        this.function = function;
        for (int i = 0; i <= count; i++) {
            yNodes[i] = function.apply(xNodes[i]);
        }
    }

    public double[] realArray() {
        double value = 1;
        for (int i = 0; i < zNodes.length; i++) {
            zNodes[i] = function.apply(value);
            value += 0.1;
        }
        return zNodes;
    }

    public double[] interpolateArray(int method) {
        double value = 1;
        for (int i = 0; i < zNodes.length; i++) {
            zNodes[i] = interpolation(method, value);
            value += 0.1;
        }
        return zNodes;
    }

    public double interpolation(int method, double xx) {
        double result = 0;
        if (method == Lagrange) {
            result = this.lagrange(xx);
        }
        if (method == Newton) {
            result = this.newton(xx);
        }
        if (method == Eitken) {
            result = this.eitken(xx, 0, count);
        }
        return result;
    }

    public double lagrange(double xx) {
        double result = 0;
        for (int i = 0; i <= count; i++) {
            double temp = 1;
            for (int j = 0; j < i; j++) {
                temp = temp * (xx - xNodes[j]) / (xNodes[i] - xNodes[j]);
            }
            for (int j = i + 1; j <= count; j++) {
                temp = temp * (xx - xNodes[j]) / (xNodes[i] - xNodes[j]);
            }

            result = result + temp * yNodes[i];

        }

        return result;
    }

    public double eitken(double xx, int start, int end) {
        double x_i = xNodes[start];
        double x_j = xNodes[end];
        return start == end
                ? yNodes[start]
                : ((xx - x_i) * eitken(xx, start + 1, end) - (xx - x_j)
                * eitken(xx, start, end - 1)) / (x_j - x_i);
    }

    public double newton(double xx) {
        double result = yNodes[0];
        double buf = 1;
        for (int k = 1; k <= count; k++) {
            double tempSum = 0;
            for (int i = 0; i <= k; i++) {
                double temp = 1;
                for (int j = 0; j < i; j++)
                    temp = temp * (xNodes[i] - xNodes[j]);
                for (int j = i + 1; j <= k; j++)
                    temp = temp * (xNodes[i] - xNodes[j]);
                temp = yNodes[i] / temp;
                tempSum += temp;
            }
            buf = buf * (xx - xNodes[k - 1]);
            result = result + tempSum * buf;
        }

        return result;
    }

    public double getError(Function<Double, Double> function, int method, double xx) {
        Interpolation interp1 = new Interpolation(a, b, count + 1);
        interp1.setY(function);
        return (interp1.interpolation(method, xx) - this.interpolation(method, xx));
    }

    public double getErrorOfError(Function<Double, Double> function, int method, double xx) {
        Interpolation interp1 = new Interpolation(a, b, count + 1);
        interp1.setY(function);
        return interp1.getError(function, method, xx);
    }

    public double getValueBluriness(Function<Double, Double> function, int method, double xx) {
        return getErrorOfError(function, method, xx) / getError(function, method, xx);
    }

    public double[] getBluriness(Function<Double, Double> function, int method) {
        double value = 1;
        for (int i = 0; i < zNodes.length; i++) {
            zNodes[i] = getValueBluriness(function, method, value);
            value += 0.4;
        }
        return zNodes;
    }


}