package org.example;

/**
 * This class contains the necessary computations for the Conditions Met Vector (CMV).
 *
 * @author Group 4
 */
public class CMVCalculator {


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
        if (LENGTH1 < 0) {
            return false;
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + 1]) > LENGTH1) {
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
    public static boolean checkLIC1(double[][] points, double RADIUS1) {
        if (RADIUS1 < 0) {
            return false;
        }
        double radius;
        for (int i = 0; i < points.length - 2; i++) {
            radius = MathUtils.calcMinimumEnclosingCircleRadius(points[i], points[i + 1], points[i + 2]);
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
     * @param points  a 2D array indicating the 2D point coordinates
     * @param EPSILON a double value given in the parameters
     * @return whether there exists at least one set of three consecutive data points that satisfy above conditions
     */
    public static boolean checkLIC2(double[][] points, double EPSILON) {
        if (EPSILON >= Math.PI || EPSILON < 0) {
            return false;
        }
        for (int i = 0; i < points.length - 2; i++) {
            if (points[i] == points[i + 1] || points[i + 1] == points[i + 2]) {
                continue;
            }
            // calculate angle using vector doc product
            // note that point[i+1] is the vertex of the angle
            double dotVec = (points[i][0] - points[i + 1][0]) * (points[i + 2][0] - points[i + 1][0]) + (points[i][1] - points[i + 1][1]) * (points[i + 2][1] - points[i + 1][1]);
            double length1 = MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + 1]);
            double length2 = MathUtils.calcDistanceBetweenTwoPoints(points[i + 2], points[i + 1]);
            double cosAngle = dotVec / (length1 * length2);
            double angle = Math.acos(cosAngle);
            if (angle < Math.PI - EPSILON || angle > Math.PI + EPSILON) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that are the vertices of a triangle
     * with area greater than AREA1. If there are less than three poitns, there is no triangle and the
     * result will be false.
     *
     * @param points An array containing any number of points. Each point should consist of two coordinates.
     * @param area   The boundary value that should be smaller or equal to at-least triangle determined by successive
     *               points for the result to be true.
     * @return True if there is atleast one set of three consecutive points which create a triangle with an area
     * larger than the provided area paramter. False if there are less than three points.
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
     * @param dataPoints 2D array of doubles, where each sub-array represents a point in the form of [x, y]
     * @param qPts       an integer representing the number of consecutive points to check in each set
     * @param QUADS      an integer representing the number of quadrants that must be present in a set of qPts points for the method to return true
     * @return true if at least one set of consecutive qPts points in dataPoints fall in more than QUADS quadrants, false otherwise.
     */
    public static boolean checkLIC4(double[][] dataPoints, int qPts, int QUADS) {
        for (int i = 0; i <= dataPoints.length - qPts; i++) {
            int[] quadrantCount = new int[4];
            for (int j = i; j < i + qPts; j++) {
                double x = dataPoints[j][0];
                double y = dataPoints[j][1];
                if (x >= 0 && y >= 0) {
                    quadrantCount[0]++;
                } else if (x < 0 && y >= 0) {
                    quadrantCount[1]++;
                } else if (x <= 0 && y < 0) {
                    quadrantCount[2]++;
                } else if (x > 0 && y < 0) {
                    quadrantCount[3]++;
                }
            }
            int quadrants = 0;
            for (int count : quadrantCount) {
                if (count > 0) {
                    quadrants++;
                }
            }
            if (quadrants > QUADS) {
                return true;
            }
        }
        return false;
    }

    /**
     * There exists at least one set of two consecutive data points, (X[i],Y[i]) and (X[i+1],Y[i+1]), such
     * that X[i+1] - X[i] < 0.
     *
     * @param points An array consisting on points. Each point in the array must have exactly two values.
     *               There must be at least two points in the array.
     * @return Whether there exist two consecutive points such that the second point's x-coordinate is bigger than
     * the first point's x-coordinate.
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
     * Check LIC6
     * <br>
     * There exists at least one set of N_POINTS consecutive data points such that at least one of the
     * points lies a distance greater than DIST from the line joining the first and last of these N PTS
     * points. If the first and last points of these N_POINTS are identical, then the calculated distance
     * to compare with DIST will be the distance from the coincident point to all other points of
     * the N PTS consecutive points. The condition is not met when NUMPOINTS < 3.
     * (3 ≤ N_POINTS ≤ NUMPOINTS), (0 ≤ DIST)
     *
     * @param points   An array of points. Each element should contain exactly two points representing x and y-coordinate.
     * @param N_POINTS The number of consecutive data points for the check above.
     * @param DIST     The distance threshold for the condition.
     * @return False if less than three points are provided. Otherwise true only if there exist N_POINTS consecutive
     * points such that:
     * <ol>
     *     <li>The first and last point are the same and there is atleast one point further
     *     than the distance parameter to these two points</li>
     *     <li>The first and the last points are not the same and there is at least one point further
     *     than away from the line defined by these two points than the provided distance.</li>
     * </ol>either (1) the first and last of these con
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

                double dist = startPoint[0] == endPoint[0] && startPoint[1] == endPoint[1] ? MathUtils.calcDistanceBetweenTwoPoints(startPoint, curPoint) : MathUtils.calcDistanceBetweenPointAndLine(curPoint, startPoint, endPoint);

                if (dist >= DIST) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * There exists at least one set of two data points separated by exactly kPts consecutive intervening points that are a distance greater than the length, LENGTH1, apart. The condition
     * is not met when NUMPOINTS < 3.
     * 1 ≤ K PTS ≤ (NUMPOINTS−2)
     *
     * @param points  a 2D array indicating the 2D point coordinates
     * @param LENGTH1 a double value given in the parameters
     * @param kPts   an int value given in the parameters
     * @return True if there is atleast one set of two points, separated by kPts consecutive intevening points, with a distance greater than LENGTH1. False Otherwise.
     */
    public static boolean checkLIC7(double[][] points, double LENGTH1, int kPts) {

        if (points.length < 3) {
            return false;
        } else if (kPts < 1 || kPts > points.length - 2) {
            return false;
        }


        for (int i = 0; i < points.length - kPts - 1; i++) {
            if (MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + kPts + 1]) > LENGTH1) {
                return true;
            }
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
     * @param points  a 2D array indicating the 2D point coordinates
     * @param RADIUS1 a double value given in the parameters
     * @param aPts   an int value given in the parameters
     * @param bPts   an int value given in the parameters
     * @return True, if there exist a set of three datapoints in points, separated by aPts and bPts respectivly,
     * that cannot be contained withing a circle with radius RADIUS1, False otherwise.
     */

    public static boolean checkLIC8(double[][] points, double RADIUS1, int aPts, int bPts) {
        if (aPts < 1 || bPts < 1) {
            return false;
        } else if (aPts + bPts > points.length - 3) {
            return false;
        } else if (points.length < 5) {
            return false;
        }

        for (int i = 0; i < points.length - aPts - bPts - 2; i++) {
            double min_radius = MathUtils.calcMinimumEnclosingCircleRadius(points[i], points[i + aPts + 1], points[i + aPts + bPts + 2]);
            if (min_radius > RADIUS1) {
                return true;
            }
        }

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
     * @param points  a 2D array indicating the 2D point coordinates
     * @param cPts    first gap size
     * @param dPts    second gap size
     * @param epsilon
     * @param
     * @param
     * @return true if the aforementioned points satisfy the aforementioned conditions.
     * @return
     */
    public static boolean checkLIC9(double[][] points, int cPts, int dPts, double epsilon) {

        if (points.length < 5) return false;

        if (cPts < 1 || dPts < 1 || (cPts + dPts) > points.length - 3)
            throw new IllegalArgumentException("Faulty input.");

        for (int i = 0; i < points.length - cPts - dPts - 2; ++i) {
            if (points[i] == points[i + cPts + 1] || points[i + cPts + 1] == points[i + cPts + dPts + 2]) continue;
            if (MathUtils.deltaIsWithinBounds(points[i], points[i + cPts + 1], points[i + cPts + dPts + 2], epsilon)) {
                return true;
            }
        }
        return false;
    }


    /**
     * There exists at least one set of three data points separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater
     * than AREA1. The condition is not met when NUMPOINTS < 5.
     * 1 ≤ E PTS, 1 ≤ F PTS
     * E PTS+F PTS ≤ NUMPOINTS−3
     *
     * @param ePts       the number of consecutive intervening points between the first and second data points
     * @param fPts       the number of consecutive intervening points between the second and third data points
     * @param AREA1      the area of the triangle
     * @param dataPoints the data points
     * @return true if the condition is met, false otherwise
     */
    public static boolean checkLIC10(int ePts, int fPts, double AREA1, double[][] dataPoints) {
        if (dataPoints.length < 5) {
            return false;
        }
        for (int i = 0; i < dataPoints.length - 3; i++) {
            for (int j = i + ePts + 1; j < dataPoints.length - 2; j++) {
                for (int k = j + fPts + 1; k < dataPoints.length - 1; k++) {
                    if (MathUtils.calcTriangleArea(dataPoints[i], dataPoints[j], dataPoints[k]) > AREA1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * There exists at least one set of two data points, (X[i],Y[i]) and (X[j],Y[j]), separated by
     * exactly G PTS consecutive intervening points, such that X[j] - X[i] < 0. (where i < j ) The
     * condition is not met when NUMPOINTS < 3.
     * 1 ≤ G PTS ≤ NUMPOINTS−2
     *
     * @param points a 2D array indicating the 2D point coordinates
     * @param gPts
     * @return whether there are points that satisfy the abovementioned condition.
     */
    public static boolean checkLIC11(double[][] points, int gPts) {
        if (points.length < 3) return false;

        for (int i = 0; i < (points.length - gPts - 1); ++i) {
            if (points[i + gPts + 1][0] - points[i][0] < 0) return true;
        }
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
     * @param points
     * @param length1
     * @param length2
     * @param kPts
     * @return whether the points satisfy the aforementioned conditions.
     */
    public static boolean checkLIC12(double[][] points, double length1, double length2, int kPts) {

        boolean condition1 = false;
        boolean condition2 = false;

        if (points.length < 3) return false;

        if (length2 < 0) {
            throw new IllegalArgumentException("Faulty input");
        }

        for (int i = 0; i < points.length - kPts - 1; ++i) {
            if (MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + kPts + 1]) > length1) condition1 = true;
            if (MathUtils.calcDistanceBetweenTwoPoints(points[i], points[i + kPts + 1]) < length2) condition2 = true;
        }

        return (condition1 && condition2);
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
     * @param points  An array consisting on points. Each point in the array must have exactly two values. There must be at least two points in the array.
     * @param aPts    first separation size
     * @param bPts    second separation size
     * @param radius1 specified radius of the first circle
     * @param radius2 specified radius of the second circle
     * @return whether the points in the points-array meet the aforementioned conditions.
     */
    public static boolean checkLIC13(double[][] points, int aPts, int bPts, double radius1, double radius2) {

        if (points.length < 5) return false;
        if (radius2 < 0) throw new IllegalArgumentException("Faulty input");

        boolean condition1 = false;
        boolean condition2 = false;
        double radius;

        for (int i = 0; i < points.length - aPts - bPts - 2; ++i) {
            radius = MathUtils.calcMinimumEnclosingCircleRadius(points[i], points[i + aPts + 1], points[i + aPts + bPts + 1]);
            if (radius >= radius1) condition1 = true;
            if (radius <= radius2) condition2 = true;
        }

        return (condition1 && condition2);
    }

    /**
     * There exists at least one set of three data points, separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area greater
     * than AREA1. In addition, there exist three data points (which can be the same or different
     * from the three data points just mentioned) separated by exactly E PTS and F PTS consecutive intervening points, respectively, that are the vertices of a triangle with area less than
     * AREA2. Both parts must be true for the LIC to be true. The condition is not met when
     * NUMPOINTS < 5.
     * 0 ≤ AREA2
     *
     * @param ePts       - The number of consecutive intervening points
     * @param fPts       - The number of consecutive intervening points
     * @param AREA1      - The area of the triangle
     * @param AREA2      - The area of the triangle
     * @param dataPoints - The data points
     * @return true if constaints and both conditions satisfied, false otherwise
     */

    public static boolean checkLIC14(double[][] dataPoints, int ePts, int fPts, double AREA1, double AREA2) {

        if (dataPoints.length < 5) {
            return false;
        }

        if (AREA2 < 0) {
            return false;
        }
        boolean area1requirementIsSatisfied = false;
        for (int i = 0; i < dataPoints.length - 2 - ePts - fPts; i++) {
            double area = MathUtils.calcTriangleArea(dataPoints[i], dataPoints[i + ePts + 1], dataPoints[i + ePts + fPts + 2]);
            if (area > AREA1) {
                area1requirementIsSatisfied = true;
            }
        }
        boolean area2requirementIsSatisfied = false;
        for (int i = 0; i < dataPoints.length - 2 - ePts - fPts; i++) {
            double area = MathUtils.calcTriangleArea(dataPoints[i], dataPoints[i + ePts + 1], dataPoints[i + ePts + fPts + 2]);
            if (area < AREA2) {
                area2requirementIsSatisfied = true;
            }
        }

        return area2requirementIsSatisfied && area1requirementIsSatisfied;
    }

    public static boolean[] calculateCMV(double[][] points, Parameters parameters) {
        boolean[] cmv = new boolean[15];
        cmv[0] = checkLIC0(points, parameters.length1());
        cmv[1] = checkLIC1(points, parameters.radius1());
        cmv[2] = checkLIC2(points, parameters.epsilon());
        cmv[3] = checkLIC3(points, parameters.area1());
        cmv[4] = checkLIC4(points, parameters.qPts(), parameters.quads());
        cmv[5] = checkLIC5(points);
        cmv[6] = checkLIC6(points, parameters.nPts(), parameters.dist());
        cmv[7] = checkLIC7(points, parameters.length1(), parameters.kPts());
        cmv[8] = checkLIC8(points, parameters.radius1(), parameters.aPts(), parameters.bPts());
        cmv[9] = checkLIC9(points, parameters.cPts(), parameters.dPts(), parameters.epsilon());
        cmv[10] = checkLIC0(points, parameters.length1());
        cmv[11] = checkLIC11(points, parameters.gPts());
        cmv[12] = checkLIC12(points, parameters.length1(), parameters.length2(), parameters.kPts());
        cmv[13] = checkLIC13(points, parameters.aPts(), parameters.bPts(), parameters.radius1(), parameters.radius2());
        cmv[14] = checkLIC14(points, parameters.ePts(), parameters.fPts(), parameters.area1(), parameters.area2());
        return cmv;
    }
}
