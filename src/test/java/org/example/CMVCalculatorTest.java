package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CMVCalculatorTest {

    @Test
    public void testBasicTwoPointsLIC0() {
        double[] point1 = new double[]{1.0f, 2.0f};
        double[] point2 = new double[]{1.0f, -5.0f};
        double[][] points = new double[][]{point1, point2};
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
        double[][] points = new double[][]{new double[]{1.5f, 1.5f}, new double[]{1f, 1f}, new double[]{4f, 1f}, new double[]{2f, 10f}, new double[]{2.1f, 10f}};
        assertTrue(CMVCalculator.checkLIC3(points, 10));
        assertTrue(CMVCalculator.checkLIC3(points, 13.5));
        assertFalse(CMVCalculator.checkLIC3(points, 15));
    }

    @Test
    public void testLIC6SmallerThanThreeReturnsFalse() {
        assertFalse(CMVCalculator.checkLIC6(new double[0][2], 1, 10));
        assertFalse(CMVCalculator.checkLIC6(TestUtils.toPoints(10, 20), 1, 10));
        assertFalse(CMVCalculator.checkLIC6(TestUtils.toPoints(10, 20, 30, 40), 1, 10));
    }

    @Test
    public void testLIC6N_POINTSBiggerThanLengthReturnsFalse() {
        assertFalse(CMVCalculator.checkLIC6(TestUtils.toPoints(1, 1, 2, 2, 3, 3, 4, 4), 5, 1));
    }

    @Test
    public void testLIC6WorksWhenTwoPointsAreTheSame() {
        // The distance between the 0th/3rd point to the 2nd point is exactly 5.
        // A distance smaller should return false.
        // A distance equal or bigger should return true
        double[][] points = TestUtils.toPoints(1, 1, 2, 2, 4, 5, 1, 1);
        int N_POINTS = 4;

        assertFalse(CMVCalculator.checkLIC6(points, N_POINTS, 5.1));
        assertTrue(CMVCalculator.checkLIC6(points, N_POINTS, 5));
        assertTrue(CMVCalculator.checkLIC6(points, N_POINTS, 4.9));
    }

    @Test
    public void testLIC6WorksWhenTwoPointsOnLine() {
        // The distance between the 0th/3rd point create the line y = x
        // The distance to the point (0, 5) is 5/2 * sqrt(2)
        // A distance smaller should return false.
        // A distance equal or bigger should return true
        double[][] points = TestUtils.toPoints(1, 1, 2, 2, 0, 5, 10, 10);
        int N_POINTS = 4;

        double DIST_THRESHOLD = (5f / 2f) * Math.sqrt(2);

        assertFalse(CMVCalculator.checkLIC6(points, N_POINTS, DIST_THRESHOLD + 0.1));
        assertTrue(CMVCalculator.checkLIC6(points, N_POINTS, DIST_THRESHOLD - 0.1));
    }

    @Test
    public void testLIC6DealsWithArbitaryArrayLengths() {
        // This test tests various array sizes with various valid N_POINTS values to ensure that there
        // are no weird array index problems.
        for (int pointsSize = 3; pointsSize < 10; pointsSize++) {
            for (int nPointsParamter = 3; nPointsParamter <= pointsSize; nPointsParamter++) {
                double[][] points = new double[pointsSize][2];
                for (int i = 0; i < points.length; i++) {
                    points[i][0] = i;
                    points[i][1] = i;
                }

                try {
                    CMVCalculator.checkLIC6(points, nPointsParamter, 5);
                } catch (Exception e) {
                    fail(String.format("CMVCalculator.checkLIC6 throws an exception when 'points.length=%d' " +
                            "'and N_POINTS=%d'", pointsSize, nPointsParamter));
                }
            }
        }
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
}
