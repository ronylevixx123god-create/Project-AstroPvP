/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection.regions;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.khelekore.prtree.MBRConverter;

public class ProtectedRegionMBRConverter
implements MBRConverter<ProtectedRegion> {
    @Override
    public int getDimensions() {
        return 3;
    }

    @Override
    public double getMax(int dimension, ProtectedRegion region) {
        switch (dimension) {
            case 0: {
                return region.getMaximumPoint().getBlockX();
            }
            case 1: {
                return region.getMaximumPoint().getBlockY();
            }
            case 2: {
                return region.getMaximumPoint().getBlockZ();
            }
        }
        return 0.0;
    }

    @Override
    public double getMin(int dimension, ProtectedRegion region) {
        switch (dimension) {
            case 0: {
                return region.getMinimumPoint().getBlockX();
            }
            case 1: {
                return region.getMinimumPoint().getBlockY();
            }
            case 2: {
                return region.getMinimumPoint().getBlockZ();
            }
        }
        return 0.0;
    }
}

