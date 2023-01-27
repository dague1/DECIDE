package org.example;

import org.junit.Test;

import static org.example.OperationType.*;
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
}
