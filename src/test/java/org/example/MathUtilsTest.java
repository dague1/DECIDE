package org.example;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathUtilsTest {
    @Test
    public void calcTriangleArea() {
        assertEquals(13.5, MathUtils.calcTriangleArea(new double[]{1f, 1f}, new double[]{4f, 1f}, new double[]{2f, 10f}));

        assertEquals(25, MathUtils.calcTriangleArea(new double[]{1f, 8f}, new double[]{2f, 1f}, new double[]{8f, 9f}));
    }

}