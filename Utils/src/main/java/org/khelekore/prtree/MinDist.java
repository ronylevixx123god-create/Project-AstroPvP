/*
 * Decompiled with CFR 0.150.
 */
package org.khelekore.prtree;

import org.khelekore.prtree.MBR;
import org.khelekore.prtree.PointND;

public class MinDist {
    private MinDist() {
    }

    public static double get(MBR mbr, PointND p) {
        double res = 0.0;
        for (int i = 0; i < p.getDimensions(); ++i) {
            double o = p.getOrd(i);
            double rv = MinDist.r(o, mbr.getMin(i), mbr.getMax(i));
            double dr = o - rv;
            res += dr * dr;
        }
        return res;
    }

    private static double r(double x, double min, double max) {
        double r = x;
        if (x < min) {
            r = min;
        }
        if (x > max) {
            r = max;
        }
        return r;
    }
}

