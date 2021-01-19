package me.ifamasssxd.cosmicbosses.dungeonboss.planetnul;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CloneWarAbility extends DungeonAbility {
    public CloneWarAbility(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
        this.boss.getMinions().forEach(minion -> {
            Skeleton skeleton = (Skeleton)minion;
            if (skeleton.getTarget() == null || !(skeleton.getTarget() instanceof Player)) {
                skeleton.getNearbyEntities(10.0, 10.0, 10.0).stream().filter(en -> en instanceof Player).findAny().ifPresent(e -> skeleton.setTarget((LivingEntity)e));
            }
        });
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(20L);
    }

    @Override
    public String getAbilityName() {
        return "Clone War";
    }

    @Override
    public boolean shouldStopTicking() {
        if (!this.boss.hasMinions() || this.boss.getMinions().isEmpty()) return true;

        return System.currentTimeMillis() - this.initTime >= this.getCooldown() || (System.currentTimeMillis() - this.initTime > 1500 && !this.boss.hasMinions());
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();

        ((CorruptCosmonaut)this.boss).setDuplicateMinions(true);

        this.attemptSpawn(this.boss.getEntity().getLocation().clone().add(0, 0, 4));
        this.attemptSpawn(this.boss.getEntity().getLocation().clone().add(4, 0, 0));
        this.attemptSpawn(this.boss.getEntity().getLocation().clone().add(-4, 0, 0));
    }

    private void attemptSpawn(Location loc) {
        if (loc.getBlock().getType() == Material.AIR && loc.clone().add(0, 1, 0).getBlock().getType() == Material.AIR && loc.clone().add(0, -1, 0).getBlock().getType() != Material.AIR) {
            this.boss.spawnMinion(EntityType.SKELETON, loc);
        } else {
            this.boss.spawnMinion(EntityType.SKELETON, this.boss.getEntity().getLocation());
        }
    }

    @Override
    public void onAbilityDisable() {
        this.boss.removeMinions();
        ((CorruptCosmonaut)this.boss).setDuplicateMinions(false);
        ((CorruptCosmonaut)this.boss).doTrickster();
    }

    @Override
    public void onAbilityEvent(Event p0) {

    }
}
