/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Tameable;

public interface Ocelot
extends Animals,
Tameable {
    public Type getCatType();

    public void setCatType(Type var1);

    public boolean isSitting();

    public void setSitting(boolean var1);

    public static enum Type {
        WILD_OCELOT(0),
        BLACK_CAT(1),
        RED_CAT(2),
        SIAMESE_CAT(3);

        private static final Type[] types;
        private final int id;

        private Type(int id) {
            this.id = id;
        }

        @Deprecated
        public int getId() {
            return this.id;
        }

        @Deprecated
        public static Type getType(int id) {
            return id >= types.length ? null : types[id];
        }

        static {
            types = new Type[Type.values().length];
            Type[] arr$ = Type.values();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                Type type;
                Type.types[type.getId()] = type = arr$[i$];
            }
        }
    }
}

