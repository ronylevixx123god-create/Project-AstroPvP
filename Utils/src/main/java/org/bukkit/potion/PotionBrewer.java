/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.potion;

import java.util.Collection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface PotionBrewer {
    public PotionEffect createEffect(PotionEffectType var1, int var2, int var3);

    @Deprecated
    public Collection<PotionEffect> getEffectsFromDamage(int var1);
}

