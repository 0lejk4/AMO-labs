package com.gelo.amo_labs.service;


public class MethodGaussZeidel {

    public static String solver(double ESP,
                                double[][] A,
                                double[] B,
                                double[] X) {

        double maxd;
        double maxdp = 0;
        int n = A.length;
        double[] d = new double[n];
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length - 1; j++) {
                result.append("X");
                result.append(j + 1);
                result.append("* (");
                result.append(A[i][j]);
                result.append(") + ");
            }
            result.append("X");
            result.append(A[i].length);
            result.append("* (");
            result.append(A[i][A[i].length - 1]);
            result.append(") = ");
            result.append(B[i]);
            result.append("<br>");
        }

        do {
            for (int i = 0; i < n; ++i) {
                d[i] = X[i];
                X[i] = B[i];
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        X[i] -= A[i][j] * X[j];
                    }
                }
                X[i] = X[i] / A[i][i];
                d[i] = Math.abs(d[i] - X[i]);
            }
            maxd = d[0];
            for (int i = 1; i < n; ++i) {
                if (d[i] > maxd) maxd = d[i];
            }
            if (maxdp == 0) {
                maxdp = maxd;
            } else {
                if (maxdp - maxd < 0) {
                    break;
                } else {
                    maxdp = maxd;
                }
            }
            showX(X, result);
            result.append("<br>");
        }
        while (maxd > ESP);

        result.append("The final result is");
        showX(X, result);

        return result.toString();
    }

    private static void showX(double[] X, StringBuilder result) {
        for (int i = 0; i < X.length; i++) {
            result.append("X");
            result.append(i + 1);
            result.append(" = ");
            result.append(X[i]);
            result.append(" ");
        }
    }


}
