/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.rit.sucy.EnchantmentAPI
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package me.astropvp.astropvpskins.objects.skins.belts;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class CorruptPowerBeltSkin
extends ItemSkin {
    public CorruptPowerBeltSkin() {
        super(SkinType.CORRUPT_BELT_POWER);
    }

    @Override
    public void onEquip(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            return;
        }
        EnchantmentAPI.getEnchantment((String)"Godly Overload").applyEquipEffect(player, 5);
    }

    @Override
    public void onUpdate(Player player) {
        OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
        if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
            return;
        }
    }

    @Override
    public void onUnequip(Player player) {
        EnchantmentAPI.getEnchantment((String)"Godly Overload").applyUnequipEffect(player, 5);
    }
}

