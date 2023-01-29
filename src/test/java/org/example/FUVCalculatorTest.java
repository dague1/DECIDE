package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FUVCalculatorTest {
    @Test
    public void testPUVAllFalseGivesFUVAllTrue() {
        boolean[][] pum = new boolean[3][3];
        boolean[] puv = new boolean[3];

        boolean[] result = FUVCalculator.calculateFUV(pum, puv);
        for (boolean resultValue : result) {
            assertTrue(resultValue);
        }
    }

    @Test
    public void testFUVIsSetToFalseIfPUMRowContainsFalse() {
        boolean[][] pum = new boolean[3][3];
        pum[0][1] = false;
        pum[0][2] = true;
        // With these settings, the 0th element in the FUV should become false because pum[0,2] is false.

        // We set the PUV in such a way that we are only concerned about the first row of the PUM.
        boolean[] puv = new boolean[]{true, false, false};

        boolean[] fuv = FUVCalculator.calculateFUV(pum, puv);

        assertFalse(fuv[0]);
        assertTrue(fuv[1]);
        assertTrue(fuv[2]);
    }

    @Test
    public void testFUVCalculationIgnoresPUMDiagonal() {
        // The PUM diagonal is meaningless.
        // This is because pum[i][j] gives information about the combination condition i and condition j.
        // However, if i == j, you can see this information is meaningless.

        // In this test, we check that the FUV calculation does not consider elements along the diagonal.
        // In other words, if the puv enables all rows of the pum and all elements in the pum are true except for those
        // along the diagonal, the FUV should still be all true.
        boolean[][] pum = new boolean[3][3];
        for (int i = 0; i < pum.length; i++) {
            for (int j = 0; j < pum.length; j++) {
                pum[i][j] = i != j;
            }
        }
        // We set the PUV in such a way that we are only concerned about the first row of the PUM.
        boolean[] puv = new boolean[]{true, true, true};

        boolean[] fuv = FUVCalculator.calculateFUV(pum, puv);

        assertTrue(fuv[0]);
        assertTrue(fuv[1]);
        assertTrue(fuv[2]);
    }

    @Test
    public void testAllFalseFUVEvaluatesToFalse() {
        assertFalse(FUVCalculator.evaluateFUV(new boolean[]{false, false, false, false, false}));
    }

    @Test
    public void testAllTrueFUVEvaluatesToTrue() {
        assertTrue(FUVCalculator.evaluateFUV(new boolean[]{true, true, true}));
    }

    @Test
    public void testAllTrueButOneFalseFUVEvaluatesToFalse() {
        assertFalse(FUVCalculator.evaluateFUV(new boolean[]{true, true, true, false, true, true, true}));
    }
}