package org.example;

public class MathUtils {
    /**
     * Calculate the area of a triangle as defined by the three provided points.
     * @param p1 Point 1. Should contain two numbers, the first of which is the x-coordinate and the second the y-coordinate.
     * @param p2 Point 2. Same structure as p1.
     * @param p3 Point 3. Same structure as p1.
     * @return The area of the triangle
     */
    public static double calcTriangleArea(double[] p1, double[] p2, double[] p3) {
        if (p1.length != 2 || p2.length != 2 || p3.length != 2) {
            throw new IllegalArgumentException("Each of the provided points must have exactly two elements.");
        }

        return 0.5 * (p1[0] * p2[1] + p2[0] * p3[1] + p3[0] * p1[1] - p1[0] * p3[1] - p2[0] * p1[1] -
                p3[0] * p2[1]);
    }
}
