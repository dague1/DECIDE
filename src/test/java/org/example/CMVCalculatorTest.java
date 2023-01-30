package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CMVCalculatorTest {

    @Test
    public void testLIC0BasicTwoPoints() {
        double[] point1 = new double[]{1.0f, 2.0f};
        double[] point2 = new double[]{1.0f, -5.0f};
        double[][] points = new double[][]{point1, point2};
        assertTrue(CMVCalculator.checkLIC0(points, 6.0d));
        assertFalse(CMVCalculator.checkLIC0(points, 10.0d));
        assertFalse(CMVCalculator.checkLIC0(points, -1.0d));
    }

    @Test
    public void TestLIC1BasicThreePoints() {
        // check three identical points
        double[] point1 = new double[]{1.0f, 1.0f};
        double[][] points = new double[][]{point1, point1, point1};
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        // check invalid RADIUS1
        assertFalse(CMVCalculator.checkLIC1(points, -1.0d));

        // check three collinear points
        double[] point2 = new double[]{1.0f, 0.0f};
        double[] point3 = new double[]{1.0f, 2.0f};
        points = new double[][]{point1, point2, point3};
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));

        // check 2 points (on boundary) circle
        point1 = new double[]{0.2f, 1.0f};
        points[0] = point1;
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));

        // check 3 points (on boundary) circle
        point1 = new double[]{0.0f, 1.0f};
        points[0] = point1;
        assertFalse(CMVCalculator.checkLIC1(points, 1.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 1.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 0.8d));
    }

    @Test
    public void testLIC1FourPoints() {
        double[] point1 = new double[]{1.5f, 3.0f};
        double[] point2 = new double[]{1.5f, 0.0f};
        double[] point3 = new double[]{0.0f, 1.5f};
        double[] point4 = new double[]{5.5f, 3.0f};
        double[][] points = new double[][]{point1, point2, point3, point4};

        assertTrue(CMVCalculator.checkLIC1(points, 0.5d));
        assertTrue(CMVCalculator.checkLIC1(points, 1.0d));
        assertTrue(CMVCalculator.checkLIC1(points, 1.5d));
        // edge case should be RADIUS = 5.0
        assertFalse(CMVCalculator.checkLIC1(points, 5.0d));
        assertFalse(CMVCalculator.checkLIC1(points, 5.1d));
    }

    @Test
    public void testLIC2ThreeAndFourPoints() {
        double[] point1 = new double[]{0.0f, 1.0f};
        double[] point2 = new double[]{0.0f, 0.0f};
        double[] point3 = new double[]{1.0f, 0.0f};
        double[][] points = new double[][]{point1, point2, point3};
        // Epsilon invalid
        assertFalse(CMVCalculator.checkLIC2(points, -1.0d));
        // 0.5*pi < pi - 0.25*pi
        assertTrue(CMVCalculator.checkLIC2(points, 0.25d * Math.PI));
        // 0.5*pi = pi - 0.5*pi
        assertFalse(CMVCalculator.checkLIC2(points, 0.5d * Math.PI));
        // 0.5*pi > pi - 0.75*pi
        assertFalse(CMVCalculator.checkLIC2(points, 0.75d * Math.PI));

        // angle do not exist
        points[2] = point2;
        assertFalse(CMVCalculator.checkLIC2(points, 0.5d * Math.PI));

        // angle123 = pi, angle234 = 0.25 * pi
        point3 = new double[]{0.0f, -1.0f};
        double[] point4 = new double[]{1.0f, 0.0f};
        points = new double[][]{point1, point2, point3, point4};
        assertTrue(CMVCalculator.checkLIC2(points, 0));
        // angle123 = pi, angle234 = 0.5 * pi
        point4[1] = -1.0f;
        assertFalse(CMVCalculator.checkLIC2(points, 0.5 * Math.PI));
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
    public void testLIC4True() {
        double[][] dataPoints = {{1, 2}, {-3, 4}, {5, -6}, {-7, -8}, {9, 10}, {-11, 12}, {13, -14}, {-15, -16}, {17, 18}, {-19, 20}};
        int QUADS = 3;
        int QPTS = 4;
        //is true because the first 4 and the next 4 elements are in their own separate quadrants which is > than QUADS
        assertTrue(CMVCalculator.checkLIC4(dataPoints, QPTS, QUADS));
    }

    @Test
    public void testLIC4False() {
        double[][] dataPoints = {{1, 2}, {-3, 4}, {5, -6}, {-7, -8}, {9, 10}, {-11, 12}, {13, -14}, {-15, -16}, {17, 18}, {-19, 20}};
        int QPTS = 3;
        int QUADS = 3;
        //is false because there are 3 QPTS and 3 QUADS so can't be larger due to the condition requirement
        assertFalse(CMVCalculator.checkLIC4(dataPoints, QPTS, QUADS));
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
                    fail(String.format("CMVCalculator.checkLIC6 throws an exception when 'points.length=%d' " + "'and N_POINTS=%d'", pointsSize, nPointsParamter));
                }
            }
        }
    }

    @Test
    public void testLIC6SmallerThanThreeReturnsFalse() {
        assertFalse(CMVCalculator.checkLIC6(new double[0][2], 1, 10));
        assertFalse(CMVCalculator.checkLIC6(TestUtils.toPoints(10, 20), 1, 10));
        assertFalse(CMVCalculator.checkLIC6(TestUtils.toPoints(10, 20, 30, 40), 1, 10));
    }

    @Test
    public void testLIC7() {
        double[] point0 = new double[]{0.0f, 0.0f};
        double[] point1 = new double[]{0.0f, 4.0f};
        double[] point2 = new double[]{0.0f, 4.0f};
        double[] point3 = new double[]{0.0f, 4.0f};

        double[][] points = new double[][]{point0, point1, point2, point3};

        assertTrue(CMVCalculator.checkLIC7(points, 3.9f, 1));
        assertTrue(CMVCalculator.checkLIC7(points, 3.9f, 2));
        assertFalse(CMVCalculator.checkLIC7(points, 4.0f, 1));
        assertFalse(CMVCalculator.checkLIC7(points, 3.0f, 3));
    }

    @Test
    public void testLIC8ForCorrectInterveningDistanceCalculation() {
        double[] point0 = new double[]{0.0f, 0.0f};
        double[] point1 = new double[]{10.0f, 0.0f};
        double[] point2 = new double[]{10.0f, 0.0f};
        double[] point3 = new double[]{10.0f, 0.0f};
        double[] point4 = new double[]{0.0f, 0.0f};
        double[] point5 = new double[]{10.0f, 0.0f};
        double[] point6 = new double[]{10.0f, 0.0f};
        double[] point7 = new double[]{10.0f, 0.0f};
        double[] point8 = new double[]{10.0f, 0.0f};
        double[] point9 = new double[]{0.0f, 0.0f};
        double[][] points = new double[][]{point0, point1, point2, point3, point4, point5, point6, point7, point8, point9};


        assertFalse(CMVCalculator.checkLIC8(points, 2, 3, 4));
        assertTrue(CMVCalculator.checkLIC8(points, 2, 4, 3));
        assertTrue(CMVCalculator.checkLIC8(points, 2, 1, 1));
    }

    @Test
    public void testLIC8ForRadiusEdgeCase() {
        double[] point0 = new double[]{0.0f, 0.0f};
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{0.0f, 2.0f};
        double[] point3 = new double[]{0.0f, 0.0f};
        double[] point4 = new double[]{0.0f, 4.0f};
        double[][] points = new double[][]{point0, point1, point2, point3, point4};

        assertFalse(CMVCalculator.checkLIC8(points, 2, 1, 1));
        assertTrue(CMVCalculator.checkLIC8(points, 1.9, 1, 1));
    }

    @Test
    public void testLIC9TooFewPoints() {
        double[] point1 = new double[]{0.0f, 1.0f};
        double[] point2 = new double[]{0.0f, 0.0f};
        double[] point3 = new double[]{1.0f, 0.0f};

        double[][] points = new double[][]{point1, point2, point3};

        int C_PTS = 1;
        int D_PTS = 1;

        double epsilon = 0.5d * Math.PI;

        assertFalse(CMVCalculator.checkLIC9(points, C_PTS, D_PTS, epsilon));
    }

    @Test
    public void testLIC9ConditionNotMet() {
        double[] point1 = new double[]{0.0f, 1.0f};
        double[] point2 = new double[]{0.0f, 0.0f};
        double[] point3 = new double[]{0.0f, 0.0f};
        double[] point4 = new double[]{0.0f, 1.0f};
        double[] point5 = new double[]{0.0f, 0.0f};
        double[] point6 = new double[]{1.0f, 0.0f};

        double[][] points = new double[][]{point1, point2, point3, point4, point5, point6};

        int C_PTS = 1;
        int D_PTS = 2;

        double epsilon = 0.5d * Math.PI;

        assertFalse(CMVCalculator.checkLIC9(points, C_PTS, D_PTS, epsilon));
    }

    @Test
    public void testLIC9ConditionMet1() { // delta is less than pi - epsilon  (pi/2)
        double[] point1 = new double[]{0.0f, 3.0f};
        double[] point2 = new double[]{1.0f, 0.0f};
        double[] point3 = new double[]{2.0f, 0.0f};
        double[] point4 = new double[]{0.0f, 0.0f};
        double[] point5 = new double[]{4.0f, 0.0f};
        double[] point6 = new double[]{5.0f, 0.0f};
        double[] point7 = new double[]{3.0f, 0.0f};

        double[][] points = new double[][]{point1, point2, point3, point4, point5, point6, point7};

        int C_PTS = 2;
        int D_PTS = 2;

        double epsilon = 1;

        assertTrue(CMVCalculator.checkLIC9(points, C_PTS, D_PTS, epsilon));
    }

    @Test
    public void testLIC9ConditionMet2() { // delta is greater than pi + epsilon (2*pi)
        double[] point1 = new double[]{0.0f, 3.0f};
        double[] point2 = new double[]{0.0f, 0.0f};
        double[] point3 = new double[]{0.0f, 0.0f};
        double[] point4 = new double[]{0.0f, 0.0f};
        double[] point5 = new double[]{4.0f, 0.0f};
        double[] point6 = new double[]{4.0f, 0.0f};
        double[] point7 = new double[]{0.0f, 3.0f};

        double[][] points = new double[][]{point1, point2, point3, point4, point5, point6, point7};

        int C_PTS = 2;
        int D_PTS = 2;

        double epsilon = 1;

        assertTrue(CMVCalculator.checkLIC9(points, C_PTS, D_PTS, epsilon));
    }

    @Test
    public void testLIC10True() {
        double[][] dataPoints = {{0, 4}, {4, 0}, {0, 0}, {7, 8}, {9, 10}, {11, 12}, {13, 14}};
        int EPTS = 1;
        int FPTS = 1;
        double AREA1 = 0.5;
        // the points {0, 4}, {4, 0}, {0, 0} form a triangle with area greater than 0.5 (the value of AREA1).
        // These points are separated by 1 (the value of EPTS) and 1 (the value of FPTS) consecutive intervening points, respectively
        assertTrue(CMVCalculator.checkLIC10(EPTS, FPTS, AREA1, dataPoints));
    }

    @Test
    public void testLIC10False() {
        double[][] dataPoints = {{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}};
        int EPTS = 2;
        int FPTS = 2;
        double AREA1 = 0.5;
        //here the only option is (1,2), (5, 6) and (9, 10) but the points are on a straight line so the area is 0
        assertFalse(CMVCalculator.checkLIC10(EPTS, FPTS, AREA1, dataPoints));
    }

    @Test
    public void testLIC11TooFewPoints() {
        double[] point1 = new double[]{1.5f, 3.0f};
        double[] point2 = new double[]{1.5f, 0.0f};
        double[][] points = new double[][]{point1, point2};
        int G_PTS = 0;
        assertFalse(CMVCalculator.checkLIC11(points, G_PTS));
    }

    @Test
    public void testLIC11WhenLICNotMet() {
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{0.0f, 1.0f};
        double[] point3 = new double[]{1.0f, 3.0f};
        double[][] points = new double[][]{point1, point2, point3};
        int G_PTS = 1;
        assertFalse(CMVCalculator.checkLIC11(points, G_PTS));
    }

    @Test
    public void testLIC11WhenLICMet() {
        double[] point1 = new double[]{3.0f, 0.0f};
        double[] point2 = new double[]{0.0f, 1.0f};
        double[] point3 = new double[]{1.0f, 3.0f};
        double[][] points = new double[][]{point1, point2, point3};
        int G_PTS = 1;
        assertTrue(CMVCalculator.checkLIC11(points, G_PTS));

    }

    @Test
    public void testLIC12TooFewPoints() {
        double[] point1 = new double[]{1.5f, 3.0f};
        double[] point2 = new double[]{1.5f, 0.0f};
        double length1 = 1;
        double length2 = 1;
        int K_PTS = 0;
        double[][] points = new double[][]{point1, point2};
        assertFalse(CMVCalculator.checkLIC12(points, length1, length2, K_PTS));
    }

    @Test
    public void testLIC12BothConditionsMet() {
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{2.0f, 2.0f};
        double[] point3 = new double[]{2.1f, 2.1f};
        double[] point4 = new double[]{2.1f, 2.1f};
        double length1 = 1;
        double length2 = 1;
        int K_PTS = 1;
        double[][] points = new double[][]{point1, point2, point3, point4};
        assertTrue(CMVCalculator.checkLIC12(points, length1, length2, K_PTS));
    }

    @Test
    public void testLIC12OnlyOneConditionMet() {
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{2.0f, 2.0f};
        double[] point3 = new double[]{2.1f, 2.1f};
        double[] point4 = new double[]{5.1f, 5.1f};
        double length1 = 1;
        double length2 = 1;
        int K_PTS = 1;
        double[][] points = new double[][]{point1, point2, point3, point4};
        assertFalse(CMVCalculator.checkLIC12(points, length1, length2, K_PTS));
    }

    @Test
    public void testLIC13TooFewPoints() {
        double[] point1 = new double[]{1.5f, 3.0f};
        double[] point2 = new double[]{1.5f, 0.0f};
        double[] point3 = new double[]{1.5f, 3.0f};
        double[] point4 = new double[]{1.5f, 0.0f};
        double radius1 = 1;
        double radius2 = 1;
        int A_PTS = 1;
        int B_PTS = 1;
        double[][] points = new double[][]{point1, point2, point3, point4};
        assertFalse(CMVCalculator.checkLIC13(points, A_PTS, B_PTS, radius1, radius2));
    }

    @Test
    public void testLIC13OnlyOneConditionMet() {
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{2.0f, 0.0f};
        double[] point3 = new double[]{2.0f, 0.0f};
        double[] point4 = new double[]{4.0f, 0.0f};
        double[] point5 = new double[]{4.0f, 0.0f};
        double radius1 = 1;
        double radius2 = 1;
        int A_PTS = 1;
        int B_PTS = 1;
        double[][] points = new double[][]{point1, point2, point3, point4, point5};
        assertFalse(CMVCalculator.checkLIC13(points, A_PTS, B_PTS, radius1, radius2));
    }

    @Test
    public void testLIC13BothConditionMet() {
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{2.0f, 0.0f};
        double[] point3 = new double[]{2.0f, 0.0f};
        double[] point4 = new double[]{4.0f, 0.0f};
        double[] point5 = new double[]{4.0f, 0.0f};

        double radius1 = 1;
        double radius2 = 5;

        int A_PTS = 1;
        int B_PTS = 1;

        double[][] points = new double[][]{point1, point2, point3, point4, point5};

        assertTrue(CMVCalculator.checkLIC13(points, A_PTS, B_PTS, radius1, radius2));
    }

    @Test
    public void testLIC14ReturnsTrue() {
        int E_PTS = 2;
        int F_PTS = 1;
        double AREA1 = 10;
        double AREA2 = 20;
        double[][] dataPoints = new double[][]{{1, 2}, {1, 1}, {3, 1}, {22, 8}, {9, 10}, {11, 12}, {13, 14}, {15, 16}};
        //returns true because the area of the triangle formed by the first, third, and fourth data points is 16.5

        assertTrue(CMVCalculator.checkLIC14(dataPoints, E_PTS, F_PTS, AREA1, AREA2));
    }

    @Test
    public void testLIC14ReturnsFalse() {
        int E_PTS = 2;
        int F_PTS = 1;
        double AREA1 = 30;
        double AREA2 = 40;
        double[][] dataPoints = new double[][]{{1, 2}, {3, 4}, {5, 6}, {7, 8}, {9, 10}, {11, 12}, {13, 14}, {15, 16}};

        //returns false because all of these points form a line with area of 0 between any 3 of them
        assertFalse(CMVCalculator.checkLIC14(dataPoints, E_PTS, F_PTS, AREA1, AREA2));
    }
}
