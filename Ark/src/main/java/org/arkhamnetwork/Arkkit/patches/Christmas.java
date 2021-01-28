package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Christmas
        extends ArkPlugin {
    private ItemStack hat;
    private ItemStack present;

    public Christmas(Plugin p) {
        super(p);
    }

    public static ItemStack getChristmasCandy() {
        return Christmas.getChristmasCandy(1);
    }

    public static ItemStack getChristmasCandy(int amount) {
        ItemStack item = ItemUtils.buildItem(Material.GLOWSTONE_DUST, (short) 0, ChatColor.YELLOW + "Christmas Candy " + ChatColor.GRAY + "(Right Click)", ChatColor.GRAY + "A yummy piece of space candy, only", ChatColor.GRAY + "obtainable during " + ChatColor.UNDERLINE + "Christmas 2016");
        item.setAmount(amount);
        return item;
    }

    public static boolean isChristmasCandy(ItemStack i) {
        return i.isSimilar(Christmas.getChristmasCandy());
    }

    @Override
    public void enable() {
        this.registerListener(new ChristmasListener(this));
        this.registerTask(new BukkitRunnable() {

            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (WorldGuardUtils.isInRegionsFast(pl.getLocation(), "spawn_region")) continue;
                    Christmas.this.spawnEvilElf(pl.getLocation());
                    return;
                }
            }
        }.runTaskTimer(this.getBukkitPlugin(), 2400L, 3200L), TrickOrTreaters.class);
        this.registerCommand(new ArkPluginCommand<Christmas>(this, "spawnelf") {

            @Override
            public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                if (!sender.isOp()) {
                    return true;
                }
                Christmas.this.spawnEvilElf(((Player) sender).getLocation());
                return false;
            }
        });
    }

    public void spawnEvilElf(Location l) {
        Zombie z = (Zombie) l.getWorld().spawnCreature(l, EntityType.ZOMBIE);
        if (z == null || !z.isValid() || z.isDead()) {
            return;
        }
        z.setCustomNameVisible(true);
        z.setMaxHealth(100.0);
        z.setHealth(100.0);
        z.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        z.setCustomName(CC.YellowB + "Evil Christmas Elf");
        z.setMetadata("holidayElf", new FixedMetadataValue(Arkkit.get(), l));
        z.setVillager(false);
        z.setBaby(true);
        EntityEquipment equipment = z.getEquipment();
        equipment.setItemInHandDropChance(0.0f);
        equipment.setHelmetDropChance(0.0f);
        equipment.setChestplateDropChance(0.0f);
        equipment.setLeggingsDropChance(0.0f);
        equipment.setBootsDropChance(0.0f);
        EntityInsentient insentient = (EntityInsentient) ((CraftLivingEntity) z).getHandle();
        EntityCreature creature = (EntityCreature) insentient;
        // TODO: FIX WHEN BOSSES DONE
      /*  BossUtils.clearGoalSelectors((EntityInsentient)insentient);
        BossUtils.addSelector((EntityInsentient)insentient, (int)0, (PathfinderGoal)new PathfinderGoalFloat(insentient));
        BossUtils.addSelector((EntityInsentient)insentient, (int)1, (PathfinderGoal)new PathfinderGoalPanic(creature, 1.4));
        BossUtils.addSelector((EntityInsentient)insentient, (int)5, (PathfinderGoal)new PathfinderGoalLookAtPlayer(insentient, EntityHuman.class, 6.0f));
        BossUtils.addSelector((EntityInsentient)insentient, (int)6, (PathfinderGoal)new PathfinderGoalRandomStroll(creature, 1.0)); */
        l.getWorld().playSound(l, Sound.ZOMBIE_WOODBREAK, 2.0f, 0.25f);
        l.getWorld().playSound(l, Sound.ZOMBIE_IDLE, 2.0f, 0.25f);
        z.getEquipment().setHelmet(this.hat.clone());
        z.getEquipment().setItemInHand(this.present.clone());
    }

    @Override
    public void disable() {
    }

    public boolean isChristmasElf(Entity ent) {
        return ent.hasMetadata("holidayElf");
    }
}

