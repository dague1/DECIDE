package org.example;

public class FUVCalculator {
    public static boolean[] calculateFUV(boolean[][] pum, boolean[] puv) {
        int numConditions = pum.length;
        boolean[] fuv = new boolean[numConditions];

        for (int i = 0; i < numConditions; i++) {
            fuv[i] = true;

            if (puv[i]) {
                for (int j = 0; j < numConditions; j++) {
                    if (i != j && !pum[i][j]) {
                        fuv[i] = false;
                        break;
                    }
                }
            }
        }

        return fuv;
    }

    public static boolean evaluateFUV(boolean[] fuv) {
        for (boolean val : fuv) {
            if(!val) {
                return false;
            }
        }

        return true;
    }
}
