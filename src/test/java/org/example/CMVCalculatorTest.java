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
    public void testThreeAndFourPointsLIC2(){
        float[] point1 = new float[]{0.0f, 1.0f};
        float[] point2 = new float[]{0.0f, 0.0f};
        float[] point3 = new float[]{1.0f, 0.0f};
        float[][] points = new float[][]{point1, point2, point3};
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
        point3 = new float[]{0.0f, -1.0f};
        float[] point4 = new float[]{1.0f, 0.0f};
        points = new float[][]{point1, point2, point3, point4};
        assertTrue(CMVCalculator.checkLIC2(points, 0));
        // angle123 = pi, angle234 = 0.5 * pi
        point4[1] = -1.0f;
        assertFalse(CMVCalculator.checkLIC2(points, 0.5*Math.PI));
    }
}
