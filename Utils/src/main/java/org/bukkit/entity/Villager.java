/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.NPC;

public interface Villager
extends Ageable,
NPC {
    public Profession getProfession();

    public void setProfession(Profession var1);

    public static enum Profession {
        FARMER(0),
        LIBRARIAN(1),
        PRIEST(2),
        BLACKSMITH(3),
        BUTCHER(4);

        private static final Profession[] professions;
        private final int id;

        private Profession(int id) {
            this.id = id;
        }

        @Deprecated
        public int getId() {
            return this.id;
        }

        @Deprecated
        public static Profession getProfession(int id) {
            return id >= professions.length ? null : professions[id];
        }

        static {
            professions = new Profession[Profession.values().length];
            Profession[] arr$ = Profession.values();
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                Profession type;
                Profession.professions[type.getId()] = type = arr$[i$];
            }
        }
    }
}

