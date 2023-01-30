package org.example;

public class Decide {
    public static String decide(double[][] points, Parameters parameters, OperationType[][] lcm, boolean[] puv) {
        var cmv = CMVCalculator.calculateCMV(points, parameters);
        var pum = PUMCalculator.calculatePUM(cmv, lcm);
        var fuv = FUVCalculator.calculateFUV(pum, puv);
        return FUVCalculator.evaluateFUV(fuv) ? "YES" : "NO";
    }
}
