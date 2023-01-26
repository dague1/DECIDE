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
    public void addTestThatFailsToTestCICD() {
        assertTrue(false);
    }

}