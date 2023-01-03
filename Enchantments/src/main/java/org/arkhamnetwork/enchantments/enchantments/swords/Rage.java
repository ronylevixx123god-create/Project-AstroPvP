package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.google.common.collect.Maps;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.weaponbonuses.api.WeaponBonusesAPI;
import net.astropvp.weaponbonuses.bonus.BonusType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.objects.RageObject;
import org.arkhamnetwork.enchantments.util.CacheUtils;
import org.arkhamnetwork.enchantments.util.RageStacksCache;
import org.arkhamnetwork.enchantments.util.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;


public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {

            if (!(target instanceof Player)) {
                return;
            }
            if (target.hasMetadata("doubledStriked") && System.currentTimeMillis() - ((MetadataValue) target.getMetadata("doubleStriked").get(0)).asLong() < 500L) {
                return;
            }


            RageStacksCache rageStacksCache = CacheUtils.getRageStackCache((Player) user);
            if (AstroItemSkinsAPI.hasSkinEquipped(((Player) user).getPlayer(), SkinType.VALENTINES_2022_SWORD) || AstroItemSkinsAPI.hasSkinEquipped(((Player) user).getPlayer(), SkinType.VALENTINES_2022_MUTATED_SWORD)) {
                rageStacksCache.setMaxRageStack(rageStacksCache.getMaxRageStack() + 1);
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(((Player) target).getPlayer(), SkinType.VALENTINES_2022_SWORD) && Math.random() < 0.15) {
                target.sendMessage(CC.translate("&a&l** PRICKLE PEAR &7Reset Rage Stacks &a&l**"));
                rageStacksCache.resetRageStack();
            }


            if (rageStacksCache.getLastRageStackTime() + 5000 < System.currentTimeMillis() || !rageStacksCache.isSameVictim((Player) target)) {
                rageStacksCache.updateLastRageStackTime();
                rageStacksCache.resetRageStack();
                rageStacksCache.updateVictim((Player) target);
                return;
            }


            if (rageStacksCache.getRageStack() >= 1) {
                double baseRageMath = rageStacksCache.getRageStack() * Enchantments.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("rageMultiplier");
                double rageMath;

                if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VALENTINES_2021_AMULET)) {
                    rageMath = 1 + (baseRageMath * 0.5);
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                    if (Math.random() < 0.25) {
                        target.sendMessage(CC.translate("&c&l* ROSE AMULET &7[-50% Rage DMG] &c&l*"));
                    }
                } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_SWORD) || AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CHRISTMAS_2021_MUTATED_SWORD)) {
                    rageMath = 1 + (baseRageMath * 1.1);
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                    if (Math.random() < 0.25) {
                        user.sendMessage(CC.translate("&4&l* SEASON'S BEATING &7[+10% Rage DMG] &4&l*"));
                    }
                } else if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.BACK_TO_SCHOOL_2021_BOOTS) && Math.random() < 0.1) {
                    target.sendMessage(CC.translate("&8&l* BIG TIMBS &7[Cancelled Rage Stack DMG] &8&l*"));
                } else if (WeaponBonusesAPI.hasWeaponBonuses(((Player) user).getItemInHand()) && WeaponBonusesAPI.hasBonus(((Player) user).getItemInHand(), BonusType.BONUS_RAGE_STACK_DMG)) {
                    rageMath = 1 + (baseRageMath * 1.1);
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                } else if ((AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_AMULET) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_MUTATED_AMULET)) && Math.random() < 0.3) {
                    rageMath = 1 + (baseRageMath * 0.65);
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                    if (Math.random() < 0.25) {
                        target.sendMessage(CC.translate("&e&l* CHESHIRE GRIN &7[-35% Rage DMG] &e&l*"));
                    } else if (AstroItemSkinsAPI.hasSkinEquipped(((Player) user).getPlayer(), SkinType.VALENTINES_2022_SWORD) || AstroItemSkinsAPI.hasSkinEquipped(((Player) user).getPlayer(), SkinType.VALENTINES_2022_MUTATED_SWORD)) {
                        rageMath = 1 + (baseRageMath * 1.1);
                        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                        if (Math.random() < 0.25) {
                            target.sendMessage(CC.translate("&c&l* SKELETON KEY &7[+10% Rage DMG] &c&l*"));
                        }
                    }
                } else {
                    rageMath = 1 + (baseRageMath);
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * rageMath);
                }
                this.playRageEffect(target.getLocation());

            }

            rageStacksCache.incrementRageStack();
            rageStacksCache.updateLastRageStackTime();
            rageStacksCache.updateVictim((Player) target);

            target.setMetadata("effectedByRage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis()));


        }

    }

    private void playRageEffect(Location loc) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}

