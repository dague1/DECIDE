package org.example;

import org.junit.Test;

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
    public void testLIC5() {
        // For the following points, the x-coordinates are increasing thus the conidition is not met.
        // Note how the y-coordinate can be anything.
        assertFalse(CMVCalculator.checkLIC5(TestUtils.toPoints(1f, 0f, 2f, -10f, 10f, 100f)));

        // When the x-coordinate is constant between consecutive points, the condition is also not met.
        assertFalse(CMVCalculator.checkLIC5(TestUtils.toPoints(1f, 0f, 1f, -10f, 10f, 100f)));

        // When the x-coordinate does decrease, the condition is met.
        assertTrue(CMVCalculator.checkLIC5(TestUtils.toPoints(2f, 0f, 1f, -10f, 10f, 100f)));
    }


    @Test
    public void TestBasicThreePointsLIC1() {
        // check three identical points
        float[] point1 = new float[]{1.0f, 1.0f};
        float[][] points = new float[][]{point1, point1, point1};
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        // check invalid RADIUS1
        assertFalse(CMVCalculator.checkLIC1(points, -1.0d));

        // check three collinear points
        float[] point2 = new float[]{1.0f, 0.0f};
        float[] point3 = new float[]{1.0f, 2.0f};
        points = new float[][]{point1, point2, point3};
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));

        // check 2 points (on boundary) circle
        point1 = new float[]{0.2f, 1.0f};
        points[0] = point1;
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));

        // check 3 points (on boundary) circle
        point1 = new float[]{0.0f, 1.0f};
        points[0] = point1;
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));
    }

    @Test
    public void testFourPointsLIC1() {
        float[] point1 = new float[]{1.5f, 3.0f};
        float[] point2 = new float[]{1.5f, 0.0f};
        float[] point3 = new float[]{0.0f, 1.5f};
        float[] point4 = new float[]{5.5f, 3.0f};
        float[][] points = new float[][]{point1, point2, point3, point4};

        assertTrue(CMVCalculator.checkLIC1(points, 0.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 1.0d));
        assertTrue(CMVCalculator.checkLIC1(points, 1.5d));
        // edge case should be RADIUS = 5.0
        assertFalse(CMVCalculator.checkLIC1(points, 5.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 5.1d));
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

    @Test
    public void testLIC7() {
        float[] point0 = new float[]{0.0f, 0.0f};
        float[] point1 = new float[]{0.0f, 4.0f};
        float[] point2 = new float[]{0.0f, 4.0f};
        float[] point3 = new float[]{0.0f, 4.0f};
        float[][] points = new float[][]{point0, point1, point2, point3};
     
        assertTrue(CMVCalculator.checkLIC7(points, 3.9f, 1));
        assertTrue(CMVCalculator.checkLIC7(points, 3.9f, 2));
        assertFalse(CMVCalculator.checkLIC7(points, 4.0f, 1));
        assertFalse(CMVCalculator.checkLIC7(points, 3.0f, 3));
    }

    @Test
    public void testLIC8ForRadiusEdgeCase(){
        float[] point0 = new float[]{0.0f, 0.0f};
        float[] point1 = new float[]{0.0f, 0.0f};
        float[] point2 = new float[]{0.0f, 2.0f};
        float[] point3 = new float[]{0.0f, 0.0f};
        float[] point4 = new float[]{0.0f, 4.0f};
        float[][] points = new float[][]{point0, point1, point2, point3, point4};

        assertFalse(CMVCalculator.checkLIC8(points, 2, 1, 1));
        assertTrue(CMVCalculator.checkLIC8(points, 1.9, 1, 1));
    }

    @Test
    public void testLIC8ForCorrectInterveningDistanceCalculation(){
        float[] point0 = new float[]{0.0f, 0.0f};
        float[] point1 = new float[]{10.0f, 0.0f};
        float[] point2 = new float[]{10.0f, 0.0f};
        float[] point3 = new float[]{10.0f, 0.0f};
        float[] point4 = new float[]{0.0f, 0.0f};
        float[] point5 = new float[]{10.0f, 0.0f};
        float[] point6 = new float[]{10.0f, 0.0f};
        float[] point7 = new float[]{10.0f, 0.0f};
        float[] point8 = new float[]{10.0f, 0.0f};
        float[] point9 = new float[]{0.0f, 0.0f};
        float[][] points = new float[][]{point0, point1, point2, point3, point4, point5, point6, point7, point8, point9};

      
        assertFalse(CMVCalculator.checkLIC8(points, 2, 3, 4));
        assertTrue(CMVCalculator.checkLIC8(points, 2, 4, 3));
        assertTrue(CMVCalculator.checkLIC8(points, 2, 1, 1));
    }
}
