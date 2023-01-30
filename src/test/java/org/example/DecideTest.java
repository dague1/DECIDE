package org.example;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DecideTest {

    @Test
    public void testDecideWhenLCMIsAllNotUsed() {
        //points
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{1.0f, 0.0f};
        double[] point3 = new double[]{0.0f, 1.0f};
        double[][] points = new double[][]{point1, point2, point3};

        //parameters
        double length1 = 1;
        double radius1 = 1;
        double radius2 = 1;
        double epsilon = 1;
        double area1 = 2;
        int qPts = 2;
        int quads = 1; // quadrants
        double dist = 1;
        int nPts = 3;
        int kPts = 1;
        int aPts = 1;
        int bPts = 1;
        int cPts = 1;
        int dPts = 1;
        int ePts = 1;
        int fPts = 1;
        int gPts = 1;
        double length2 = 1;
        double area2 = 2;

        Parameters parameters = new Parameters(length1, radius1, radius2, epsilon, area1, qPts, quads, dist, nPts, kPts, aPts, bPts, cPts, dPts, ePts, fPts, gPts, length2, area2);


        //lcm
        OperationType ANDD = OperationType.ANDD;
        OperationType ORR = OperationType.ORR;
        OperationType NOT_USED = OperationType.NOT_USED;

        OperationType[][] lcm = new OperationType[][]{
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD},
        };

      //puv
        boolean[] puv = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

        String res = Decide.decide(points, parameters, lcm, puv);
        assertEquals(res, "YES");
    }



    @Test
    public void testDecideWhenPUVIsAllFalse() {
        //points
        double[] point1 = new double[]{0.0f, 0.0f};
        double[] point2 = new double[]{1.0f, 0.0f};
        double[] point3 = new double[]{0.0f, 1.0f};
        double[][] points = new double[][]{point1, point2, point3};

        //parameters
        double length1 = 1;
        double radius1 = 1;
        double radius2 = 1;
        double epsilon = 1;
        double area1 = 2;
        int qPts = 2;
        int quads = 1; // quadrants
        double dist = 1;
        int nPts = 3;
        int kPts = 1;
        int aPts = 1;
        int bPts = 1;
        int cPts = 1;
        int dPts = 1;
        int ePts = 1;
        int fPts = 1;
        int gPts = 1;
        double length2 = 1;
        double area2 = 2;

        Parameters parameters = new Parameters(length1, radius1, radius2, epsilon, area1, qPts, quads, dist, nPts, kPts, aPts, bPts, cPts, dPts, ePts, fPts, gPts, length2, area2);


        //lcm
        OperationType ANDD = OperationType.ANDD;
        OperationType ORR = OperationType.ORR;
        OperationType NOT_USED = OperationType.NOT_USED;

        OperationType[][] lcm = new OperationType[][]{
                {ANDD, NOT_USED, ANDD, NOT_USED, ANDD, NOT_USED, ANDD, NOT_USED, ANDD, NOT_USED, ANDD, NOT_USED, ANDD, NOT_USED, ANDD},
                {NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED, NOT_USED},
                {NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD, NOT_USED},
                {ANDD, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, NOT_USED, ANDD},
        };

        //puv
        boolean[] puv = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};

        String res = Decide.decide(points, parameters, lcm, puv);
        assertEquals(res, "YES");
    }

    @Test
    public void testDecideWhenThreeLICsTrue() {

    }
}
