package org.example;

import org.junit.Test;

import static org.example.OperationType.*;
import static org.junit.jupiter.api.Assertions.*;

public class CMVCalculatorTest {

    @Test
    public void testBasicTwoPointsLIC0() {
        float[] point1 = new float[]{1.0f, 2.0f};
        float[] point2 = new float[]{1.0f, -5.0f};
        float[][] points = new float[][]{point1, point2};
        assertTrue(CMVCalculator.checkLIC0(points, 6.0d));
        assertFalse(CMVCalculator.checkLIC0(points, 10.0d));
        assertFalse(CMVCalculator.checkLIC0(points, -1.0d));
    }

    @Test
    public void testLIC3FalseForLessThanThreePoints() {
        for (int i = 0; i <= 2; i++) {
            double[][] points = new double[i][2];
            assertFalse(CMVCalculator.checkLIC3(points, 10));
        }
    }

    @Test
    public void testLIC3ForExactlyThreePoints() {
        // The area of this triangle is 13.5.
        double[][] points = new double[][]{new double[]{1f, 1f}, new double[]{4f, 1f}, new double[]{2f, 10f}};
        assertTrue(CMVCalculator.checkLIC3(points, 10));
        assertTrue(CMVCalculator.checkLIC3(points, 13.5));
        assertFalse(CMVCalculator.checkLIC3(points, 15));

    }

    @Test
    public void testLIC3ForMoreThanThreePoints() {
        // The second, third and fourth point make a triangle of size 13.5. This is the biggest triangle in the array.
        double[][] points = new double[][]{new double[]{1.5f, 1.5f}, new double[]{1f, 1f}, new double[]{4f, 1f},
                new double[]{2f, 10f}, new double[]{2.1f, 10f}};
        assertTrue(CMVCalculator.checkLIC3(points, 10));
        assertTrue(CMVCalculator.checkLIC3(points, 13.5));
        assertFalse(CMVCalculator.checkLIC3(points, 15));
    }
}
