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
        double LENGTH1 = 6.0f;
        boolean result = CMVCalculator.checkLIC0(points, LENGTH1);
        assertTrue(result);

        LENGTH1 = 10.0f;
        result = CMVCalculator.checkLIC0(points, LENGTH1);
        assertFalse(result);

        LENGTH1 = -1.0f;
        result = CMVCalculator.checkLIC0(points, LENGTH1);
        assertFalse(result);
    }
}
