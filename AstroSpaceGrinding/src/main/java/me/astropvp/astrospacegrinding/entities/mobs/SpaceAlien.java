package me.astropvp.astrospacegrinding.entities.mobs;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingPlugin;
import me.astropvp.astrospacegrinding.data.ForceFieldData;
import me.astropvp.astrospacegrinding.entities.SpaceMonster;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.astrospacegrinding.utils.ActionBarMessageUtils;
import me.astropvp.astrospacegrinding.utils.config.ConfigManager;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astrospacegrinding.data.SpacePlayerData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class SpaceAlien extends SpaceMonster {

    @Override
    public LivingEntity spawn(Location location) {

        Zombie entity = (Zombie) AstroSpaceGrindingAPI.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        entity.setCustomName(CC.GreenB + "Space Alien");
        AstroSpaceGrindingPlugin.activeMonsters.remove(entity.getUniqueId());

        entity.setRemoveWhenFarAway(false);
        entity.setCustomNameVisible(true);
        entity.setCanPickupItems(false);

        entity.getEquipment().setHelmet(new ItemBuilder(new ItemStack(Material.GLASS, 1, (short) 95))
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .enchant(Enchantment.DURABILITY, 3).build());
        entity.setMaxHealth(12.5);
        entity.setHealth(entity.getMaxHealth());
        entity.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .enchant(Enchantment.DURABILITY, 3).build());
        entity.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .enchant(Enchantment.DURABILITY, 3).build());
        entity.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS)
                .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                .enchant(Enchantment.DURABILITY, 3).build());
        entity.getEquipment().setItemInHand(new ItemBuilder(Material.DIAMOND_SWORD)
                .enchant(Enchantment.DAMAGE_ALL, 5)
                .enchant(Enchantment.DURABILITY, 3).build());
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 3));
        if (entity.getEquipment() != null) {
            entity.getEquipment().setItemInHandDropChance(0.0F);
            entity.getEquipment().setHelmetDropChance(0.0F);
            entity.getEquipment().setChestplateDropChance(0.0F);
            entity.getEquipment().setLeggingsDropChance(0.0F);
            entity.getEquipment().setBootsDropChance(0.0F);
        }

        AstroSpaceGrindingPlugin.activeMonsters.put(entity.getUniqueId(), MonsterType.SPACE_ALIEN);

        return entity;
    }


    @Override
    public List<ItemStack> getRewards(Player player) {
        List<ItemStack> rewards = Lists.newArrayList();
        SpacePlayerData data = SpacePlayerData.findPlayer(player);
        Random random = new Random();


        if (Math.random() < 0.5) {
            int maxMoney = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMaxMoney");
            int minMoney = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMinMoney");
            int randomNumberMoney = random.nextInt(maxMoney - minMoney + 1) + minMoney;
            if (ForceFieldData.findPlayer(player).isActive()) {
                randomNumberMoney *= 0.7;
            }
            player.sendMessage(CC.translate("&a&l+ $" + NumberUtils.formatMoney(randomNumberMoney)));
            EconomyUtils.depositBalance(player, randomNumberMoney);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
            data.addMoney(randomNumberMoney);

        } else {
            int maxXP = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMaxXP");
            int minXP = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMinXP");
            int randomNumberXP = random.nextInt(maxXP - minXP + 1) + minXP;
            if (ForceFieldData.findPlayer(player).isActive()) {
                randomNumberXP *= 0.70;
            }
            player.sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(randomNumberXP)) + " xp");
            player.giveExp(randomNumberXP);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
            data.addXp(randomNumberXP);

        }


        Map<ItemStack, Double> loot = AstroSpaceGrindingPlugin.cachedCrateLoot.get(MonsterType.SPACE_ALIEN);
        double chance = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienRewardChance");
        if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.SUMMER_2021_BELT)) {
            chance *= 1.30;
        }

        if (ForceFieldData.findPlayer(player).isActive()) {
            chance *= 0.70;
        }

        if (Math.random() < chance / 100) {
            rewards.add(AstroSpaceGrindingAPI.getRandomReward(loot));
            if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.NYE_AMULET)) {
                rewards.add(AstroSpaceGrindingAPI.getRandomReward(loot));
            }
        }
        return rewards;
    }

    @Override
    public void onDeath(EntityDeathEvent event, SpacePlayerData killer) {
        AstroSpaceGrindingPlugin.activeMonsters.remove(event.getEntity().getUniqueId());

    }

    @Override
    public void onPlayerDamageMob(EntityDamageByEntityEvent event, Player attacker) {
        ActionBarMessageUtils.sendMessage(attacker, CC.translate("&a&lSpace Alien &7(&f" + Math.round(((LivingEntity) event.getEntity()).getHealth()) + "/" + Math.round(((LivingEntity) event.getEntity()).getMaxHealth()) + " HP&7) "));
    }


}
