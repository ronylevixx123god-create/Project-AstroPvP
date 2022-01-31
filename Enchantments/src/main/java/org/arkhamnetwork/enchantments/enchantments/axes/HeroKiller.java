/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.NBTWrapper
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


import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.sets.LunaArmorSet;
import net.astropvp.core.killtracker.KillTrackerAPI;
import net.astropvp.damagehandler.AstroDamageHandler;
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
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.JULY_2021_BACKPACK)) {
            return;
        }
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
            if (LunaArmorSet.isLunaStriked((Player) playerHit)) return;
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSoulRemoveEvent").get(0)).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)4);
            }
            double increase = level * 5;
            if (AstroArmorSetsAPI.getBonusCrystalsEquipped((Player) player).containsKey(ArmorSetType.LUNA)) {
                increase *= 1.1;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.THANKSGIVING_SWORD_HARVESTER) && !AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VALENTINES_2021_HELMET)) {
                increase += 1.3;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.JULY_2021_BACKPACK) && player.getHealth() <= player.getMaxHealth()/2) {
                increase *=1.15;
            }
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(increase, e, "&c&lHero Killer");
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

