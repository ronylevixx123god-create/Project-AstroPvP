/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmiccrate.cosmichcfcrate.CosmicHCFCrate
 *  me.ifamasssxd.cosmiccrate.cosmichcfcrate.struct.Crate
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import me.ifamasssxd.cosmiccrate.cosmichcfcrate.CosmicHCFCrate;
import me.ifamasssxd.cosmiccrate.cosmichcfcrate.struct.Crate;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public enum CustomCrateType {
    ELITE("elite_crate"),
    ULTIMATE("ultimate_crate"),
    LEGENDARY("legendary_crate"),
    KILL_MESSAGES("kill_message_crate");

    private String crateName;

    public ItemStack getCrateItem() {
        if (!CosmicUtils.get().isHcfCrateEnabled()) {
            return null;
        }
        Crate crate = (Crate)CosmicHCFCrate.get().getCrateManager().getCrates().get(this.crateName);
        if (crate == null) {
            Bukkit.getLogger().info("Unable to find crate " + this.name() + "!");
            return null;
        }
        return CosmicHCFCrate.get().getCrateManager().createCrateKey(crate);
    }

    private CustomCrateType(String crateName) {
        this.crateName = crateName;
    }

    public String getCrateName() {
        return this.crateName;
    }
}

