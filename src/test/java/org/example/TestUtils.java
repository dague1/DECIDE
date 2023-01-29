package org.example;

public class TestUtils {
    /**
     * Method for creating a points array more easily.
     * <br>
     * coordinateToPointsArray(1f, 2f, 3f, 4f) <==> new double[][]{new double{1f, 2f}, new double[]{3f, 4f}}
     * @param points The coordinates of the points. The first is the first x-coordinate, the second is the first
     *               y-coordinate, the third the second x-coordinate etc. There should be an even number of points.
     * @return An array of points (arrays with two elements) specified by the parameter.
     */
    public static double[][] toPoints(double... points) {
        if (points.length % 2 != 0) {
            throw new IllegalArgumentException("You must provide an even number of points");
        }

        int n = points.length / 2;
        double[][] result = new double[n][2];

        for (int i = 0; i < n; i++) {
            result[i][0] = points[2 * i];
            result[i][1] = points[2 * i + 1];
        }

        return result;
    }
}
