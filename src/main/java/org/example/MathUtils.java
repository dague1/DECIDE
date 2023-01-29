package org.example;

public class MathUtils {
    /**
     * Calculate the area of a triangle as defined by the three provided points.
     *
     * @param p1 Point 1. Should contain two numbers, the first of which is the x-coordinate and the second the y-coordinate.
     * @param p2 Point 2. Same structure as p1.
     * @param p3 Point 3. Same structure as p1.
     * @return The area of the triangle
     */
    public static double calcTriangleArea(double[] p1, double[] p2, double[] p3) {
        if (p1.length != 2 || p2.length != 2 || p3.length != 2) {
            throw new IllegalArgumentException("Each of the provided points must have exactly two elements.");
        }

        return 0.5 * (p1[0] * p2[1] + p2[0] * p3[1] + p3[0] * p1[1] - p1[0] * p3[1] - p2[0] * p1[1] - p3[0] * p2[1]);
    }

    /**
     * Used for calculate distance between two 2D points
     *
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @return the distance between point1 and point2
     */
    public static double calcDistanceBetweenTwoPoints(double[] point1, double[] point2) {
        double diffX = point1[0] - point2[0];
        double diffY = point1[1] - point2[1];
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double calcDistanceBetweenPointAndLine(double[] curPoint, double[] startPoint, double[] endPoint) {
        // Firslty we convert the line points to a formula in the form of ax + bx + c = 0
        // We do this using: https://math.stackexchange.com/questions/422602/convert-two-points-to-line-eq-ax-by-c-0
        double a = startPoint[1] - endPoint[1];
        double b = endPoint[0] - startPoint[0];
        double c = startPoint[0] * endPoint[1] - endPoint[0] * startPoint[1];

        // Then we compute the distance between the line and the point using the following equation:
        // |a * x + b * y + c| / sqrt(a^2 + b^2)
        // See: https://www.chilimath.com/lessons/advanced-algebra/distance-between-point-and-line-formula/
        return Math.abs(a * curPoint[0] + b * curPoint[1] + c) / Math.sqrt(a * a + b * b);
    }
}
