/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicCrate.api.CCAPI
 *  com.compilex.CosmicCrate.libs.Version
 *  com.compilex.CosmicCrate.listeners.HeroicMysteryCrateListener
 */
package com.cosmicpvp.cosmicutils.itembuilder;

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.compilex.CosmicCrate.listeners.HeroicMysteryCrateListener;
import com.cosmicpvp.cosmicutils.itembuilder.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.CustomItemUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CosmicCrateBuilder
extends ItemBuilder {
    private boolean random;
    private String crateName;
    private String signer;

    public CosmicCrateBuilder(boolean random, String signer) {
        super(Material.CHEST);
        this.random = random;
        this.signer = signer;
    }

    public CosmicCrateBuilder(String crateName, String signer) {
        super(Material.CHEST);
        this.crateName = crateName;
        this.signer = signer;
    }

    @Override
    public ItemStack build() {
        if (this.crateName != null) {
            try {
                return CustomItemUtils.createCosmicCrate(this.crateName, this.signer);
            }
            catch (Exception exception) {
                return null;
            }
        }
        String targetCrates = "versions_2017";
        if (Math.random() <= 0.05) {
            targetCrates = "rare_versions_2017";
        } else if (Math.random() <= 0.1) {
            targetCrates = "godly_versions_2017";
        } else if (Math.random() <= 0.4) {
            targetCrates = "versions2018";
        }
        List version = null;
        try {
            version = (List) ReflectionUtils.getObject(HeroicMysteryCrateListener.class, null, targetCrates);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (version == null || version.isEmpty()) {
            return null;
        }
        return CCAPI.getCosmicCrate((Version)((Version)version.get(ThreadLocalRandom.current().nextInt(version.size()))), (String)this.signer);
    }
}

