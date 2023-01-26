package org.example;

import org.junit.Test;

import static org.example.OperationType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PUMCalculatorTest {
    @Test
    public void testBasicTwoByTwoANDD() {
        boolean[] cmv1 = new boolean[]{true, false};
        boolean[] cmv2 = new boolean[]{true, true};

        OperationType[][] lcm = new OperationType[][]{
                new OperationType[]{NOT_USED, OperationType.ANDD},
                new OperationType[]{OperationType.ANDD, NOT_USED}
        };

        boolean[][] pum1 = PUMCalculator.calculatePUM(cmv1, lcm);
        assertFalse(pum1[0][1]);
        assertFalse(pum1[1][0]);

        boolean[][] pum2 = PUMCalculator.calculatePUM(cmv2, lcm);
        assertTrue(pum2[0][1]);
        assertTrue(pum2[1][0]);
    }

    @Test
    public void testBasicTwoByTwoORR() {
        boolean[] cmv1 = new boolean[]{true, false};
        boolean[] cmv2 = new boolean[]{true, true};
        boolean[] cmv3 = new boolean[]{false, false};


        OperationType[][] lcm = new OperationType[][]{
                new OperationType[]{NOT_USED, ORR},
                new OperationType[]{ORR, NOT_USED}
        };

        boolean[][] pum1 = PUMCalculator.calculatePUM(cmv1, lcm);
        assertTrue(pum1[0][1]);
        assertTrue(pum1[1][0]);

        boolean[][] pum2 = PUMCalculator.calculatePUM(cmv2, lcm);
        assertTrue(pum2[0][1]);
        assertTrue(pum2[1][0]);

        boolean[][] pum3 = PUMCalculator.calculatePUM(cmv3, lcm);
        assertFalse(pum3[0][1]);
        assertFalse(pum3[1][0]);
    }

    @Test
    public void testBasicTwoByTwoNOT_USED() {
        boolean[] cmv1 = new boolean[]{true, false};
        boolean[] cmv2 = new boolean[]{true, true};
        boolean[] cmv3 = new boolean[]{false, false};


        OperationType[][] lcm = new OperationType[][]{
                new OperationType[]{NOT_USED, NOT_USED},
                new OperationType[]{NOT_USED, NOT_USED}
        };

        boolean[][] pum1 = PUMCalculator.calculatePUM(cmv1, lcm);
        assertTrue(pum1[0][1]);
        assertTrue(pum1[1][0]);

        boolean[][] pum2 = PUMCalculator.calculatePUM(cmv2, lcm);
        assertTrue(pum2[0][1]);
        assertTrue(pum2[1][0]);

        boolean[][] pum3 = PUMCalculator.calculatePUM(cmv3, lcm);
        assertTrue(pum3[0][1]);
        assertTrue(pum3[1][0]);
    }

    @Test
    public void testFourByFour() {
        boolean[] cmv = new boolean[]{true, false, false, true};
        OperationType[][] lcm = new OperationType[][]{
                new OperationType[]{NOT_USED, ORR, NOT_USED, ANDD},
                new OperationType[]{ORR, NOT_USED, NOT_USED, NOT_USED},
                new OperationType[]{NOT_USED, NOT_USED, NOT_USED, ORR},
                new OperationType[]{ANDD, NOT_USED, ORR, NOT_USED}
        };

        boolean[][] actualPUM = PUMCalculator.calculatePUM(cmv, lcm);

        assertArrayEquals(new boolean[][]{
                new boolean[]{false, true, true, true},
                new boolean[]{true, false, true, true},
                new boolean[]{true, true, false, true},
                new boolean[]{true, true, true, false}
        }, actualPUM);
    }

    @Test
    public void testIncompatibleDimensionsThrowExpection() {
        boolean[] cmv = new boolean[3];

        OperationType[][] lcmTwoByThree = new OperationType[2][3];
        OperationType[][] lcmThreeByTwo = new OperationType[3][2];

        try {
            PUMCalculator.calculatePUM(cmv, lcmTwoByThree);
            fail();
        } catch (IllegalArgumentException e) {
            // The test passes as the exception should have been thrown.
        }

        try {
            PUMCalculator.calculatePUM(cmv, lcmThreeByTwo);
            fail();
        } catch (IllegalArgumentException e) {
            // The test passes as the exception should have been thrown.
        }
    }
}