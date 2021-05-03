package me.astropvp.astroendgrinding.utils;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import io.netty.util.internal.ThreadLocalRandom;
import me.astropvp.astroendgrinding.AstroEndGrindingPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;


public class PlayerUtils {

    public static void killEntity(LivingEntity damaged) {
        if(damaged instanceof Player) {
            if(!((Player)damaged).isOnline()) {
                return;
            }
        }
        if(!(damaged instanceof Player) || WorldGuardUtils.isInRegionsFast(damaged.getLocation(), "spawn_region")) {
            EntityDeathEvent deathEvent = new EntityDeathEvent(damaged, null, DamageCause.CUSTOM);
            deathEvent.setDrops(getDropsOfEntity(damaged));
            deathEvent.setDroppedExp(getEntityExperience(damaged));
            for (ItemStack item : deathEvent.getDrops()) {
                damaged.getLocation().getWorld().dropItem(damaged.getLocation(), item);
            }

            if (damaged instanceof Player) {
                PlayerUtils.clearContents((Player) damaged);
                PlayerUtils.neutralizeKnockback((Player) damaged);
                ((Player) damaged).closeInventory();
                damaged.setHealth(20);
                damaged.setMaxHealth(20);
                damaged.teleport(deathEvent.getSpawnLocation());
                PlayerUtils.subtractExpFromPlayer((Player)damaged, ((Player)damaged).getTotalExperience());
            } else {
                damaged.remove();
            }
            return;
        }
        while(!WorldGuardUtils.isInRegionsFast(damaged.getLocation(), "spawn_region")) {
            PlayerUtils.safelyDamageEntity(damaged, damaged.getHealth(), DamageCause.CUSTOM);
        }
    }

    public static void safelyDamageEntity(LivingEntity damaged, double dmg, DamageCause cause) {
        if(damaged instanceof Player) {
            if(!((Player)damaged).isOnline()) {
                return;
            }
        }
        if (damaged.getHealth() - dmg <= 0) {
            EntityDeathEvent deathEvent = new EntityDeathEvent(damaged, null, cause);
            deathEvent.setDrops(getDropsOfEntity(damaged));
            deathEvent.setDroppedExp(getEntityExperience(damaged));
            Bukkit.getPluginManager().callEvent(deathEvent);
            if (deathEvent.isCancelled()) {
                return;
            }

            for (ItemStack item : deathEvent.getDrops()) {
                damaged.getLocation().getWorld().dropItem(damaged.getLocation(), item);
            }

            if (damaged instanceof Player) {
                clearContents((Player) damaged);
                ((Player) damaged).closeInventory();
                damaged.setHealth(20);
                damaged.teleport(deathEvent.getSpawnLocation());
            } else {
                damaged.remove();
            }
            return;
        }
        damaged.setHealth(damaged.getHealth() - dmg);
        damaged.damage(0.0001);
    }

    public static void subtractExpFromPlayer(Player player, int amount) {
        int xp = player.getTotalExperience();
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
        player.giveExp(xp - amount);
    }

    public static void neutralizeKnockback(LivingEntity entity) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(AstroEndGrindingPlugin.getInstance(), (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                entity.setVelocity(new Vector(0, 0, 0));
            }
        }, 1);
    }

    public static int getEntityExperience(LivingEntity entity) {
        if (entity instanceof Blaze) {
            return 250;
        }
        if (entity instanceof CaveSpider) {
            return 125;
        }
        if (entity instanceof Skeleton || entity instanceof Zombie) {
            return 100;
        }
        if (entity instanceof Spider) {
            return 75;
        }
        if (entity instanceof Pig || entity instanceof Sheep || entity instanceof Wolf) {
            return 25;
        }
        return 0;
    }

    public static void clearContents(Player player) {
        player.setItemOnCursor(null);

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            player.getInventory().setItem(i, null);
        }
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }


    public static List<ItemStack> getDropsOfEntity(LivingEntity entity) {
        if (entity.hasMetadata("drop_no_items")) {
            return Lists.newArrayList();
        }
        if (entity instanceof Player) {
            return getContents((Player) entity);
        }
        if (entity instanceof Blaze) {
            return Arrays.asList(new ItemStack(Material.BLAZE_ROD, ThreadLocalRandom.current().nextInt(1, 4)));
        }
        if (entity instanceof IronGolem) {
            return Arrays.asList(new ItemStack(Material.IRON_BLOCK, ThreadLocalRandom.current().nextInt(1, 3)));
        }
        if (entity instanceof Villager) {
            return Arrays.asList(new ItemStack(Material.EMERALD_BLOCK));
        }
        if(entity instanceof MushroomCow) {
            return Arrays.asList(new ItemStack(Material.GOLD_BLOCK));
        }
        if(entity instanceof Pig) {
            return Arrays.asList(new ItemStack(entity.getFireTicks() > 0 ? Material.GRILLED_PORK :
                    Material.PORK, ThreadLocalRandom.current().nextInt(1, 3)));
        }
        if(entity instanceof Cow) {
            return Arrays.asList(new ItemStack(entity.getFireTicks() > 0 ? Material.COOKED_BEEF :
                    Material.RAW_BEEF, ThreadLocalRandom.current().nextInt(1, 3)));
        }
        if(entity instanceof Ghast) {
            return Arrays.asList(new ItemStack(Material.EMERALD_BLOCK));
        }
        if(entity instanceof MagmaCube) {
            return Arrays.asList(new ItemStack(Material.DIAMOND_BLOCK));
        }
        if(entity instanceof Creeper) {
            return Arrays.asList(new ItemStack(Material.TNT));
        }
        return Lists.newArrayList();
    }

    private static List<ItemStack> getContents(Player player) {
        List<ItemStack> contents = Lists.newArrayList();
        if (player.getItemOnCursor() != null && player.getItemOnCursor().getType() != Material.AIR) {
            contents.add(player.getItemOnCursor());
        }

        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && item.getType() != Material.AIR) {
                contents.add(item);
            }
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                contents.add(item);
            }
        }
        return contents;
    }
}
