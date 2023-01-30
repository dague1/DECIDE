package org.example;

public class FUVCalculator {
    /**
     * Calculate the FUV based upon the PUM and the PUV.
     * <br>
     * The Final Unlocking Vector (FUV) is generated from the Preliminary Unlocking Matrix. The
     * input PUV indicates whether the corresponding LIC is to be considered as a factor in signaling
     * interceptor launch. <code>FUV[i]</code> should be set to true if <code>PUV[i]</code> is false (indicating that the associated
     * LIC should not hold back launch) or if all elements in PUM row i are true.
     *
     * @param pum Preliminary Unlocking Matrix
     * @param puv Preliminary Unlocking Vector
     * @return The calculated Final Unlocking Vector
     */
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

    /**
     * Evaluate the FUV to reach the final launch decision.
     * <br>
     * The final launch/no launch decision is based on the FUV. The decision to launch requires that all
     * elements in the FUV be true.
     *
     * @param fuv The Final Unlocking Vector (FUV)
     * @return The launch/no launch decision. The result is true if all elements of the fuv are true.
     */
    public static boolean evaluateFUV(boolean[] fuv) {
        for (boolean val : fuv) {
            if (!val) {
                return false;
            }
        }

        return true;
    }
}
