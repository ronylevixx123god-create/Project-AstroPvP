/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.bukkit.Material
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.MetadataValue
 */
package org.arkhamnetwork.enchantments.enchantments.axes;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.LunaSet;
import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class HeroKiller
extends CustomEnchantment {
    public HeroKiller() {
        super("Hero Killer", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(playerHit instanceof Player)) {
            return;
        }
        Player p = (Player)player;
        ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment((ItemStack)used, (String)this.enchantName)) {
            return;
        }
        if(p.hasMetadata("witcher_ability"))
            return;
        if (((Player) playerHit).getPlayer().getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped(((Player) playerHit).getPlayer(), SkinType.VALENTINES_2021_BACKPACK)) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && (long)MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue)p.getMetadata("lastItemHeldChange").get(0)).asLong() <= 5L) {
            return;
        }
        if (p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (KillTrackerAPI.hasGodMode((Player)p) && KillTrackerAPI.getAllSouls((Player)p) > 0) {
            if (!this.isWearingHeroic((Player)playerHit)) {
                return;
            }
            if (LunaSet.isLunarStriked((Player) playerHit)) return;
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSoulRemoveEvent").get(0)).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)4);
            }
            double increase = 0.1 * (double)level;

            if (CosmicArmorSetsAPI.hasCrystalApplied((Player) player, ArmorSet.LUNA)) {
                increase *= 1.1;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                increase += 1.3;
            }
                e.setDamage(e.getDamage() * (1.0 + increase));
        }
    }

    private boolean isWearingHeroic(Player player) {
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            Material type;
            if (armor == null || (type = armor.getType()) != Material.LEATHER_BOOTS && type != Material.LEATHER_LEGGINGS && type != Material.LEATHER_CHESTPLATE && type != Material.LEATHER_HELMET || !new NBTWrapper(armor).getString("cosmicType").equals("HEROIC_ARMOR")) continue;
            return true;
        }
        return false;
    }
}

