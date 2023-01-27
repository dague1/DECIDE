package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CMVCalculatorTest {

    @Test
    public void testBasicTwoPointsLIC0(){
        float[] point1 = new float[]{ 1.0f, 2.0f};
        float[] point2 = new float[]{ 1.0f, -5.0f};
        float[][] points = new float[][]{ point1, point2};
        assertTrue(CMVCalculator.checkLIC0(points, 6.0d));
        assertFalse(CMVCalculator.checkLIC0(points, 10.0d));
        assertFalse(CMVCalculator.checkLIC0(points, -1.0d));
    }

    @Test
    public void testLIC5() {
        // For the following points, the x-coordinates are increasing thus the conidition is not met.
        // Note how the y-coordinate can be anything.
        assertFalse(CMVCalculator.checkLIC5(TestUtils.toPoints(1f, 0f, 2f, -10f, 10f, 100f)));

        // When the x-coordinate is constant between consecutive points, the condition is also not met.
        assertFalse(CMVCalculator.checkLIC5(TestUtils.toPoints(1f, 0f, 1f, -10f, 10f, 100f)));

        // When the x-coordinate does decrease, the condition is met.
        assertTrue(CMVCalculator.checkLIC5(TestUtils.toPoints(2f, 0f, 1f, -10f, 10f, 100f)));
    }

}
