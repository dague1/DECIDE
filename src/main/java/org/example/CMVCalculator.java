package org.example;

/**
 * This class contains the necessary computations for the Conditions Met Vector (CMV).
 *
 * @author Group 4
 */
public class CMVCalculator {

    /**
     * Used for calculate distance between two 2D points
     *
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @return the distance between point1 and point2
     */
    private static double calcDistanceBetweenTwoPoints(float[] point1, float[] point2) {
        float del_x = point1[0] - point2[0];
        float del_y = point1[1] - point2[1];
        return Math.sqrt(del_x * del_x + del_y * del_y);
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
    private static boolean checkCollinear(float[] point1, float[] point2, float[] point3) {
        return (point1[1] - point2[1]) * (point1[0] - point3[0]) == (point1[1] - point3[1]) * (point1[0] - point2[0]);
    }

    /**
     * Used for calculate the center point (or middle point) between two 2D points
     *
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @return the center point between point1 and point2
     */
    private static float[] getMiddlePoint(float[] point1, float[] point2) {
        float center_x = (point1[0] + point2[0]) / 2.0f;
        float center_y = (point1[1] + point2[1]) / 2.0f;
        return new float[]{center_x, center_y};
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
    private static float[] getCircumcenter(float[] point1, float[] point2, float[] point3) {
        float D = 2.0f * (point1[0] * (point2[1] - point3[1]) + point2[0] * (point3[1] - point1[1]) + point3[0] * (point1[1] - point2[1]));
        float Ax2 = point1[0] * point1[0];
        float Ay2 = point1[1] * point1[1];
        float Bx2 = point2[0] * point2[0];
        float By2 = point2[1] * point2[1];
        float Cx2 = point3[0] * point3[0];
        float Cy2 = point3[1] * point3[1];
        float U_x = 1.0f / D * ((Ax2 + Ay2) * (point2[1] - point3[1]) + (Bx2 + By2) * (point3[1] - point1[1]) + (Cx2 + Cy2) * (point1[1] - point2[1]));
        float U_y = 1.0f / D * ((Ax2 + Ay2) * (point3[0] - point2[0]) + (Bx2 + By2) * (point1[0] - point3[0]) + (Cx2 + Cy2) * (point2[0] - point1[0]));
        return new float[]{U_x, U_y};
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
    private static double calcMinimumEnclosingCircleRadius(float[] point1, float[] point2, float[] point3) {
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
        float[][] points = new float[][]{point1, point2, point3};
        for (int m = 0; m < 3; m++) {
            for (int n = m + 1; n < 3; n++) {
                float[] center = getMiddlePoint(points[m], points[n]);
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
        float[] center = getCircumcenter(point1, point2, point3);
        radius = calcDistanceBetweenTwoPoints(center, point1);
        return radius;
    }

    /**
     * There exists at least one set of two consecutive data points that are a distance greater than
     * the length, LENGTH1, apart.
     * (0 ≤ LENGTH1)
     *
     * @param points  a 2D array indicating the 2D point coordinates
     * @param LENGTH1 a double value given in the parameters
     * @return whether there are two consecutive data points for which the distance is bigger than the provided length parameter
     */
    public static boolean checkLIC0(double[][] points, double LENGTH1) {
        if(LENGTH1 < 0) {
            return false;
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + 1]) > LENGTH1){
                return true;
            }
        }

        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that cannot all be contained
     * within or on a circle of radius RADIUS1.
     * (0 ≤ RADIUS1)
     *
     * @param points  a 2D array indicating the 2D point coordinates
     * @param RADIUS1 a double value given in the parameters
     * @return whether there are three consecutive data points that cannot all be contained within or on a circle of radius RADIUS1.
     */
    public static boolean checkLIC1(float[][] points, double RADIUS1) {
        if (RADIUS1 < 0) {
            return false;
        }
        double radius;
        for (int i = 0; i < points.length - 2; i++) {
            radius = calcMinimumEnclosingCircleRadius(points[i], points[i + 1], points[i + 2]);
            if (radius > RADIUS1) return true;
        }
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points which form an angle such that:
     * angle < (PI−EPSILON)
     * or
     * angle > (PI+EPSILON)
     * The second of the three consecutive points is always the vertex of the angle. If either the first
     * point or the last point (or both) coincides with the vertex, the angle is undefined and the LIC
     * is not satisfied by those three points.
     * (0 ≤ EPSILON < PI)
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC2() {
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that are the vertices of a triangle
     * with area greater than AREA1. If there are less than three poitns, there is no triangle and the
     * result will be false.
     *
     * @param points An array containing any number of points. Each point should consist of two coordinates.
     * @param area The boundary value that should be smaller or equal to at-least triangle determined by successive
     *             points for the result to be true.
     * @return True if there is atleast one set of three consecutive points which create a triangle with an area
     *         larger than the provided area paramter. False if there are less than three points.
     */
    public static boolean checkLIC3(double[][] points, double area) {
        // There need to be atleast three consecutive data points for the condition to be true.
        if (points.length < 3) {
            return false;
        }

        for (int i = 0; i < points.length - 2; i++) {
            if (MathUtils.calcTriangleArea(points[i], points[i + 1], points[i + 2]) >= area) {
                return true;
            }
        }

        return false;
    }

    /**
     * There exists at least one set of Q PTS consecutive data points that lie in more than QUADS
     * quadrants. Where there is ambiguity as to which quadrant contains a given point, priority
     * of decision will be by quadrant number, i.e., I, II, III, IV. For example, the data point (0,0)
     * is in quadrant I, the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the point
     * (0,1) is in quadrant I and the point (1,0) is in quadrant I.
     * (2 ≤ Q PTS ≤ NUMPOINTS), (1 ≤ QUADS ≤ 3)
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC4() {
        return false;
    }

    /**
     * There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[i+1],Y[i+1]), such
     * that X[i+1] - X[i] < 0.
     * @param points An array consisting on points. Each point in the array must have exactly two values.
     *               There must be at least two points in the array.
     * @return Whether there exist two consecutive points such that the second point's x-coordinate is bigger than
     *         the first point's x-coordinate.
     */
    public static boolean checkLIC5(double[][] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i + 1][0] - points[i][0] < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * There exists at least one set of N PTS consecutive data points such that at least one of the
     * points lies a distance greater than DIST from the line joining the first and last of these N PTS
     * points. If the first and last points of these N PTS are identical, then the calculated distance
     * to compare with DIST will be the distance from the coincident point to all other points of
     * the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
     * (3 ≤ N PTS ≤ NUMPOINTS), (0 ≤ DIST)
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC6(double[][] points, final int N_POINTS, final double DIST) {
        // The condition can not be met if there are less than three points.
        if (points.length < 3) {
            return false;
        }

        for (int i = 0; i <= points.length - N_POINTS; i++) {
            double[] startPoint = points[i];
            double[] endPoint = points[i + N_POINTS - 1];

            for (int j = i + 1; j < i + N_POINTS; j++) {
                double[] curPoint = points[j];

                double dist = startPoint[0] == endPoint[0] && startPoint[1] == endPoint[1]
                        ? MathUtils.calcDistanceBetweenTwoPoints(startPoint, curPoint)
                        : MathUtils.calcDistanceBetweenPointAndLine(curPoint, startPoint, endPoint);

                if (dist >= DIST) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * There exists at least one set of two data points separated by exactly K_PTS consecutive intervening points that are a distance greater than the length, LENGTH1, apart. The condition
     * is not met when NUMPOINTS < 3.
     * 1 ≤ K PTS ≤ (NUMPOINTS−2)
     *
     * @param points  a 2D array indicating the 2D point coordinates
     * @param LENGTH1 a double value given in the parameters
     * @param K_PTS an int value given in the parameters
     * @return True if there is atleast one set of two points, separated by K_PTS consecutive intevening points, with a distance greater than LENGTH1. False Otherwise.
     */
    public static boolean checkLIC7(float[][] points, double LENGTH1, int K_PTS) {

        if (points.length < 3) { 
            return false; 
        } else if (K_PTS < 1 || K_PTS > points.length-2){ 
            return false; 
        }


        for (int i = 0; i < points.length - K_PTS - 1; i++) {
            if (calcDistanceBetweenTwoPoints(points[i], points[i + K_PTS]) > LENGTH1){ return true; }
        }

        return false;
    }

    /**
     * There exists at least one set of three data points separated by exactly A PTS and B PTS
     * consecutive intervening points, respectively, that cannot be contained within or on a circle of
     * radius RADIUS1. The condition is not met when NUMPOINTS < 5.
     * 1 ≤ A PTS, 1 ≤ B PTS
     * A PTS+B PTS ≤ (NUMPOINTS−3)
     *
     * @param
     * @param
     * @return
     */

    public static boolean checkLIC8() {
        return false;
    }

    /**
     * There exists at least one set of three data points separated by exactly C PTS and D PTS
     * consecutive intervening points, respectively, that form an angle such that:
     * angle < (PI−EPSILON)
     * or
     * angle > (PI+EPSILON)
     * The second point of the set of three points is always the vertex of the angle. If either the first
     * point or the last point (or both) coincide with the vertex, the angle is undefined and the LIC
     * is not satisfied by those three points. When NUMPOINTS < 5, the condition is not met.
     * 1 ≤ C PTS, 1 ≤ D PTS
     * C PTS+D PTS ≤ NUMPOINTS−3
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC9() {
        return false;
    }


    /**
     * There exists at least one set of three data points separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater
     * than AREA1. The condition is not met when NUMPOINTS < 5.
     * 1 ≤ E PTS, 1 ≤ F PTS
     * E PTS+F PTS ≤ NUMPOINTS−3
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC10() {
        return false;
    }


    /**
     * There exists at least one set of two data points, (X[i],Y[i]) and (X[j],Y[j]), separated by
     * exactly G PTS consecutive intervening points, such that X[j] - X[i] < 0. (where i < j ) The
     * condition is not met when NUMPOINTS < 3.
     * 1 ≤ G PTS ≤ NUMPOINTS−2
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC11() {
        return false;
    }

    /**
     * There exists at least one set of two data points, separated by exactly K PTS consecutive
     * intervening points, which are a distance greater than the length, LENGTH1, apart. In addition, there exists at least one set of two data points (which can be the same or different from
     * the two data points just mentioned), separated by exactly K PTS consecutive intervening
     * points, that are a distance less than the length, LENGTH2, apart. Both parts must be true
     * for the LIC to be true. The condition is not met when NUMPOINTS < 3.
     * 0 ≤ LENGTH2
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC12() {
        return false;
    }

    /**
     * There exists at least one set of three data points, separated by exactly A PTS and B PTS
     * consecutive intervening points, respectively, that cannot be contained within or on a circle of
     * radius RADIUS1. In addition, there exists at least one set of three data points (which can be
     * the same or different from the three data points just mentioned) separated by exactly A PTS
     * and B PTS consecutive intervening points, respectively, that can be contained in or on a
     * circle of radius RADIUS2. Both parts must be true for the LIC to be true. The condition is
     * not met when NUMPOINTS < 5.
     * 0 ≤ RADIUS2
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC13() {
        return false;
    }

    /**
     * There exists at least one set of three data points, separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater
     * than AREA1. In addition, there exist three data points (which can be the same or different
     * from the three data points just mentioned) separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area less than
     * AREA2. Both parts must be true for the LIC to be true. The condition is not met when
     * NUMPOINTS < 5.
     * 0 ≤ AREA2
     *
     * @param
     * @param
     * @return
     */

    public static boolean checkLIC14() {
        return false;
    }

    public static boolean[] calculateCMV(float[][] points, Parameter parameter) {

        return null;
    }
}
