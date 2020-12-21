/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.hanging;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreakByEntityEvent
extends HangingBreakEvent {
    private final Entity remover;

    public HangingBreakByEntityEvent(Hanging hanging, Entity remover) {
        super(hanging, HangingBreakEvent.RemoveCause.ENTITY);
        this.remover = remover;
    }

    public Entity getRemover() {
        return this.remover;
    }
}

