/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Monster;

public interface Skeleton
extends Monster {
    public SkeletonType getSkeletonType();

    public void setSkeletonType(SkeletonType var1);

    public static enum SkeletonType {
        NORMAL(0),
        WITHER(1);

        private static final SkeletonType[] types;
        private final int id;

        private SkeletonType(int id) {
            this.id = id;
        }

        @Deprecated
        public int getId() {
            return this.id;
        }

        @Deprecated
        public static SkeletonType getType(int id) {
            return id >= types.length ? null : types[id];
        }

        static {
            types = new SkeletonType[SkeletonType.values().length];
            SkeletonType[] arr$ = SkeletonType.values();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                SkeletonType type;
                SkeletonType.types[type.getId()] = type = arr$[i$];
            }
        }
    }
}

