package org.example;

/**
 * This class contains the necessary computations for the Conditions Met Vector (CMV).
 * @author Group 4
 */
public class CMVCalculator {

    /**
     * Used for calculate distance between two 2D points
     * @param point1 an array containing x coordinate and y coordinate of point 1
     * @param point2 an array containing x coordinate and y coordinate of point 2
     * @return the distance between point1 and point2
     */
    private static double calcDistanceBetweenTwoPoints(float[] point1, float[] point2)
    {
        float del_x = point1[0] - point2[0];
        float del_y = point1[1] - point2[1];
        return Math.sqrt(del_x*del_x + del_y * del_y);
    }

    /**
     * There exists at least one set of two consecutive data points that are a distance greater than
     * the length, LENGTH1, apart.
     * (0 ≤ LENGTH1)
     * @param points a 2D array indicating the 2D point coordinates
     * @param LENGTH1 a double value given in the parameters
     * @return whether there are two consecutive data points for which the distance is bigger than the provided length parameter
     */
    public static boolean checkLIC0(float[][] points, double LENGTH1) {
        if(LENGTH1 < 0) {return false;}  // length is invalid
        for (int i=0; i<points.length -1; i++){
            if(calcDistanceBetweenTwoPoints(points[i], points[i+1]) > LENGTH1){
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that cannot all be contained
     * within or on a circle of radius RADIUS1.
     * (0 ≤ RADIUS1)
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC1() {
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
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC2() {
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that are the vertices of a triangle
     * with area greater than AREA1.
     * (0 ≤ AREA1)
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC3() {
        return false;
    }

    /**
     * There exists at least one set of Q PTS consecutive data points that lie in more than QUADS
     * quadrants. Where there is ambiguity as to which quadrant contains a given point, priority
     * of decision will be by quadrant number, i.e., I, II, III, IV. For example, the data point (0,0)
     * is in quadrant I, the point (-l,0) is in quadrant II, the point (0,-l) is in quadrant III, the point
     * (0,1) is in quadrant I and the point (1,0) is in quadrant I.
     * (2 ≤ Q PTS ≤ NUMPOINTS), (1 ≤ QUADS ≤ 3)
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC4() {
        return false;
    }

    /**
     * There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[i+1],Y[i+1]), such
     * that X[i+1] - X[i] < 0. (where i = j-1)
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
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC6() {
        return false;
    }

    /**
     * There exists at least one set of two data points separated by exactly K PTS consecutive intervening points that are a distance greater than the length, LENGTH1, apart. The condition
     * is not met when NUMPOINTS < 3.
     * 1 ≤ K PTS ≤ (NUMPOINTS−2)
     * @param
     * @param
     * @return
     */
    public static boolean checkLIC7() {
        return false;
    }

    /**
     * There exists at least one set of three data points separated by exactly A PTS and B PTS
     * consecutive intervening points, respectively, that cannot be contained within or on a circle of
     * radius RADIUS1. The condition is not met when NUMPOINTS < 5.
     * 1 ≤ A PTS, 1 ≤ B PTS
     * A PTS+B PTS ≤ (NUMPOINTS−3)
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
