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

    /**
     * Used for judge whether three 2D points are on the same line
     * This is calculated by the equation (y1-y2) * (x1-x3) = (y1-y3) * (x1-x2)
     * If the three points are on the same line, the above equation should be true
     *
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @param point3 an array containing x coordinate and y coordinate of point 2
     * @return whether the three points are on the same line
     */
    public static boolean checkCollinear(double[] point1, double[] point2, double[] point3) {
        return (point1[1] - point2[1]) * (point1[0] - point3[0]) == (point1[1] - point3[1]) * (point1[0] - point2[0]);
    }

    /**
     * Used for calculate the center point (or middle point) between two 2D points
     *
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @return the center point between point1 and point2
     */
    public static double[] getMiddlePoint(double[] point1, double[] point2) {
        double center_x = (point1[0] + point2[0]) / 2.0f;
        double center_y = (point1[1] + point2[1]) / 2.0f;
        return new double[]{center_x, center_y};
    }

    /**
     * Used for calculate the circumcenter of a triangle
     * Formula according to https://en.wikipedia.org/wiki/Circumscribed_circle#Circumcircle_equations
     *
     * @param point1 an array containing x coordinate and y coordinate of triangle vertex 1
     * @param point2 an array containing x coordinate and y coordinate of triangle vertex 2
     * @param point3 an array containing x coordinate and y coordinate of triangle vertex 3
     * @return the circumcenter of the input triangle
     */
    public static double[] getCircumcenter(double[] point1, double[] point2, double[] point3) {
        double D = 2.0f * (point1[0] * (point2[1] - point3[1]) + point2[0] * (point3[1] - point1[1]) + point3[0] * (point1[1] - point2[1]));
        double Ax2 = point1[0] * point1[0];
        double Ay2 = point1[1] * point1[1];
        double Bx2 = point2[0] * point2[0];
        double By2 = point2[1] * point2[1];
        double Cx2 = point3[0] * point3[0];
        double Cy2 = point3[1] * point3[1];
        double U_x = 1.0f / D * ((Ax2 + Ay2) * (point2[1] - point3[1]) + (Bx2 + By2) * (point3[1] - point1[1]) + (Cx2 + Cy2) * (point1[1] - point2[1]));
        double U_y = 1.0f / D * ((Ax2 + Ay2) * (point3[0] - point2[0]) + (Bx2 + By2) * (point1[0] - point3[0]) + (Cx2 + Cy2) * (point2[0] - point1[0]));
        return new double[]{U_x, U_y};
    }

    /**
     * Helper function that returns true if the angle delta created between the three points is < (Math.PI - epsilon) or > (Math.PI + epsilon)
     *
     * @param point1 an array containing x coordinate and y coordinate point1
     * @param point2 an array containing x coordinate and y coordinate point2
     * @param point3 an array containing x coordinate and y coordinate point3
     * @return returns true if the angle delta created between the three points is < (Math.PI - epsilon) or > (Math.PI + epsilon)
     */
    public static boolean deltaIsWithinBounds(double[] point1, double[] point2, double[] point3, double epsilon) {
        if (point1 == point2 || point2 == point3 || Math.PI <= epsilon) return false;

        double dist12 = Math.hypot(point2[0] - point2[1], point2[1] - point1[1]);
        double dist23 = Math.hypot(point2[0] - point3[0], point2[1] - point3[1]);
        double dist13 = Math.hypot(point1[0] - point3[0], point1[1] - point3[1]);

        double delta = Math.acos((Math.pow(dist12, 2) + Math.pow(dist23, 2) - Math.pow(dist13, 2)) // the angle in radians
                / (2 * dist12 * dist23));
        return delta < (Math.PI - epsilon) || delta > (Math.PI + epsilon);
    }

    /**
     * Used for calculate the minimum enclosing circle radius for the given three 2D points
     * First check if the three points are collinear (apply also to three identical points)
     * Then check if the three points satisfy that two points on the circle boundary and the other inside the circle
     * If above two check failed, then compute the circumcenter O of the triangle and the radius would be |OA|
     * A could be any point from the three points
     *
     * @param point1 an array containing x coordinate and y coordinate point1
     * @param point2 an array containing x coordinate and y coordinate point2
     * @param point3 an array containing x coordinate and y coordinate point3
     * @return the radius of the minimum enclosing circle of the three input points
     */
    public static double calcMinimumEnclosingCircleRadius(double[] point1, double[] point2, double[] point3) {
        double radius;
        // first check if the three points are collinear (this also apply if three points are identical)
        if (checkCollinear(point1, point2, point3)) {
            // the minimum enclosing circle radius should be
            // half of the maximum length between L1_2, L1_3, L2_3
            radius = Math.max(calcDistanceBetweenTwoPoints(point1, point2), calcDistanceBetweenTwoPoints(point1, point3));
            radius = Math.max(radius, calcDistanceBetweenTwoPoints(point2, point3)) / 2.0d;
            return radius;
        }

        // try all 2 points (on boundary) circles
        // in this case, we assume the line between the 2 points is the circle's diameter
        // if we could find the enclosing circle, compare the radius with RADIUS1
        double[][] points = new double[][]{point1, point2, point3};
        for (int m = 0; m < 3; m++) {
            for (int n = m + 1; n < 3; n++) {
                double[] center = getMiddlePoint(points[m], points[n]);
                radius = calcDistanceBetweenTwoPoints(points[m], points[n]) / 2.0d;
                // check if the third point is enclosed in the circle
                // if so, we found the minimum enclosing circle (which is unique)
                if (calcDistanceBetweenTwoPoints(center, points[3 - m - n]) < radius) {
                    return radius;
                }
            }
        }

        // if above conditions are not met, then
        // the 3 points (all on boundary) circle would be the minimum enclosing circle
        // get the radius and compare it with RADIUS1
        double[] center = getCircumcenter(point1, point2, point3);
        radius = calcDistanceBetweenTwoPoints(center, point1);
        return radius;
    }
}
