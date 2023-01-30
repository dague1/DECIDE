package org.example;

import java.util.Arrays;

public class PUMCalculator {
    /**
     * Calculate the Preliminary Unlock Matrix (PUM) based upon the Conditions Met Vector and Logical Connector Matrix.
     * <br>
     * The entries in the LCM represent
     * the logical connectors to be used between pairs of LICs to determine the corresponding entry in
     * the PUM, i.e. LCM[i,j] represents the boolean operator to be applied to CMV[i] and CMV[j].
     * PUM[i,j] is set according to the result of this operation. If LCM[i,j] is NOTUSED, then PUM[i,j]
     * should be set to true. If LCM[i,j] is ANDD, PUM[i,j] should be set to true only if (CMV[i] AND
     * CMV[j]) is true. If LCM[i,j] is ORR, PUM[i,j] should be set to true if (CMV[i] OR CMV[j]) is
     * true. (Note that the LCM is symmetric, i.e. LCM[i,j]=LCM[j,i] for all i and j.)
     *
     * @param cmv Conditions Met Vector. Should not be null.
     * @param lcm Logical Connection Matrix. Should be a square matrix with where the width/length is equal to the length
     *            of the cmv.
     * @return The Preliminary Unlock Matrix (PUM). Is never null.
     * @throws IllegalArgumentException An IllegalArgumentException is thrown if the dimensions of the lcm and cmv are
     *                                  incompatible.
     */
    public static boolean[][] calculatePUM(boolean[] cmv, OperationType[][] lcm) {
        int numConditions = cmv.length;

        // The lcm should be a square matrix with the same height and width as the cmv's length.
        // If this is not the case, an illegal argument has been provided.
        if (lcm.length != numConditions || Arrays.stream(lcm).map(row -> row.length).anyMatch(len -> len != numConditions)) {
            throw new IllegalArgumentException("The provided cmv and lcm have incompatible dimensions. The lcm should" + "be a square, symmetrical matrix with the same height/width as the cmv's length");
        }

        boolean[][] pum = new boolean[numConditions][numConditions];

        for (int i = 0; i < numConditions; i++) {
            for (int j = 0; j < numConditions; j++) {
                // Conditions do not combine with themselves. Therefore, we do not compute a PUM value when i == j.
                if (i != j) {
                    pum[i][j] = calculateValue(cmv[i], cmv[j], lcm[i][j]);
                }
            }
        }

        return pum;
    }

    private static boolean calculateValue(boolean val1, boolean val2, OperationType operationType) {
        switch (operationType) {
            case NOT_USED -> {
                return true;
            }
            case ANDD -> {
                return val1 && val2;
            }
            case ORR -> {
                return val1 || val2;
            }
            default ->
                    throw new IllegalStateException(String.format("OperationType '%s' is not a valid operation type!", operationType));
        }
    }
}
