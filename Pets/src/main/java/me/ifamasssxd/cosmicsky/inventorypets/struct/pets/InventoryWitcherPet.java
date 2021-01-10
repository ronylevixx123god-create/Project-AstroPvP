package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class InventoryWitcherPet extends InventoryPet {
    @Override
    public InventoryPets getOwner() {
        return InventoryPets.WITCHER;
    }

    @Override
    public List<String> getPetDescription(final int level) {
        final String tag = CC.DarkRedB + "* ";
        final int duration = 4 + level;
        return Lists.newArrayList(CC.Gray + "Subject to intense physical and mental", CC.Gray + "training, it's sure to pay off in battle.", "", CC.YellowB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[Witcher]:", tag + CC.Gray + "Immune to (almost) all Knockback (10s)", tag + CC.Gray + "Teleblock enemies in an 8x8 radius (10s)", tag + CC.Gray + "Freeze enemies in an 8x8 radius (1s)", tag + CC.Gray + "Negates enemy Ragdoll in 8x8 (5s)", tag + CC.Gray + "Silence enemy Soul Enchants in a 8x8 radius (5s)", CC.Gray + "  Includes Soul Enchants on /skins", "", CC.YellowB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(this.getCooldown(level)));
    }

    @Override
    public int getCooldown(final int level) {
        return 600 - 60 * (level - 1);
    }

    @Override
    public int getExpForLevel(final int level) {
        return level * 1250;
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }

    @Override
    public boolean onPetItemInteract(final Player player, final ItemStack item, final InventoryPetItem petItem, final PlayerInteractEvent event) throws PetException {
        final long lastUse = petItem.getLastUsed();
        final long delay = TimeUnit.SECONDS.toMillis(this.getCooldown(petItem.getLevel()));
        final long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking())) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        }
        final Faction faction = FactionUtils.getFaction(player);
        if (faction == null) {
            throw new PrefixedPetException("You must be in a faction to use this pet!");
        }
        if (!WorldGuardUtils.isPvPEnabled(player.getLocation())) {
            throw new PrefixedPetException("You cannot use this pet in non pvp-enabled territory.");
        }
        final List<Player> nearbyEnemies = Lists.newArrayList();
        for (final Entity other : player.getNearbyEntities(8.0, 8.0, 8.0)) {
            if (other instanceof Player) {
                final Player nearPlayer = (Player) other;
                final Faction nearFac = FactionUtils.getFaction(nearPlayer);
                if (!player.canSee(nearPlayer)) {
                    continue;
                }
                // TODO: ADD NEW FREEZE HERE
//                if (StaffPlus.get().freeze.isActive(nearPlayer.getName())) {
//                    continue;
//                }
                if (CosmicArmorSetsAPI.getArmorSet(player) == ArmorSet.KOTH) {
                    continue;
                }
                if (nearFac.getRelationTo(faction) != Relation.ENEMY && nearFac.getRelationTo(faction) != Relation.NEUTRAL) {
                    continue;
                }
                nearbyEnemies.add(nearPlayer);
            }
        }
        final CosmicSkyInventoryPets plugin = CosmicSkyInventoryPets.get();
        if (nearbyEnemies.isEmpty()) {
            throw new PrefixedPetException("Could not find a valid target!");
        }
        CombatLog.flagCombat(player);
        final int duration = 4 + petItem.getLevel();
        player.sendMessage("");
        player.sendMessage(CC.translate("&4&lPET: &4&l** WITCHER [&c10s Knockback Immunity&4&l] **"));
        player.sendMessage("");
        for (final Player enemy : nearbyEnemies) {
            enemy.sendMessage(CC.translate(String.format("&4&l** %s WITCHER PET **",
                    player.getName()
                            + (player.getName().endsWith("s") ? "'" : "'s"))));
            enemy.sendMessage(CC.translate("&c(10s Teleblock, 1s Freeze, 5s Ragdoll Negation, 5s Soul Enchant Silence)"));
            enemy.setMetadata("teleblock", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis() + (long) (10 * 3000)));
            freezePlayer(enemy, 2);
            enemy.setMetadata("noRagdollProcs", new FixedMetadataValue(plugin, System.currentTimeMillis() + 5 * 1000));
            enemy.setMetadata("noSoulProcsUntil", new FixedMetadataValue(plugin, System.currentTimeMillis() + 5 * 1000));
            enemy.setMetadata("witchered", new FixedMetadataValue(plugin, System.currentTimeMillis() + 5 * 1000));
        }
        player.setMetadata("noKnockbackUntil", new FixedMetadataValue(plugin, System.currentTimeMillis() + 10 * 1000));
        new BukkitRunnable() {
            public void run() {
                for (final Player other : nearbyEnemies) {
                    other.removeMetadata("noRagdollProcs", plugin);
                    other.removeMetadata("noSoulProcsUntil", plugin);
                    other.removeMetadata("witchered", plugin);
                }
                player.removeMetadata("noKnockbackUntil", plugin);
            }
        }.runTaskLater(plugin, duration * 20);
        petItem.setLastUsed(System.currentTimeMillis());
        final int xp = 80 + new Random().nextInt(20 + petItem.getLevel());
        player.playSound(player.getLocation(), Sound.LAVA_POP, 1.1f, 1.0f);
        final NBTTagCompoundBuilder cosmicData = petItem.getWrapper().getconaxData();
        boolean skipped = false;
        if (cosmicData.hasKey("lastExpGained")) {
            final long gained = cosmicData.getLong("lastExpGained");
            final long timeSince = System.currentTimeMillis() - gained;
            if (timeSince < TimeUnit.DAYS.toMillis(1L)) {
                CosmicSkyInventoryPets.get().getLogger().info("Skipping exp gain since: " + timeSince);
                skipped = true;
            }
        }
        if (!skipped) {
            cosmicData.setLong("lastExpGained", System.currentTimeMillis());
            petItem.getWrapper().setNBTBase("conaxData", cosmicData.get());
            petItem.addExperience(player, xp);
            player.sendMessage(CC.GreenB + "+ " + CC.Green + NumberUtils.formatMoney(xp) + " Pet EXP " + CC.GreenB + "[" + CC.Gray + petItem.getExp() + "/" + petItem.getPetType().getInternalClass().getExpForLevel(petItem.getLevel() + 1) + CC.GreenB + "]");
        }
        player.playSound(player.getLocation(), Sound.GHAST_SCREAM2, 2.0f, 2.0f);
        return true;
    }

    @Override
    public NBTWrapper onItemLoad(final NBTWrapper wrapper, final InventoryPetItem instance) {
        return super.onItemLoad(wrapper, instance);
    }

    @Override
    public NBTWrapper onItemCreate(final NBTWrapper wrapper, final InventoryPetItem instance) {
        if (instance.getWrapper() != null) {
            final NBTTagCompoundBuilder oldData = instance.getWrapper().getconaxData();
            final NBTTagCompoundBuilder newData = wrapper.getconaxData();
            if (oldData != null && newData != null && oldData.hasKey("lastExpGained")) {
                newData.setLong("lastExpGained", oldData.getLong("lastExpGained"));
                wrapper.setNBTBase("conaxData", newData.get());
                CosmicSkyInventoryPets.get().getLogger().info("Saving last EXp gained!");
            }
        }
        return super.onItemCreate(wrapper, instance);
    }

    @Override
    public boolean onTickInHotBar(final Player player, final int inventorySlot, final InventoryPetItem item) {
        return false;
    }

    public static void freezePlayer(final Player player, final int level) {
        if (player.hasMetadata("immune_freeze") || player.hasMetadata("equalizer_mask")) {
            return;
        }
        final int dur = 2;
        player.setVelocity(new Vector(0, 0, 0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * dur, 255));
        player.setMetadata("freeze_meta", new FixedMetadataValue(CosmicSkyInventoryPets.get(), true));
        player.setWalkSpeed(0.0f);
        player.setVelocity(new Vector(0, 0, 0));
        new BukkitRunnable() {
            public void run() {
                player.setVelocity(new Vector(0, 0, 0));
                player.removePotionEffect(PotionEffectType.JUMP);
                player.removeMetadata("freeze_meta", CosmicSkyInventoryPets.get());
                player.setWalkSpeed(0.2f);
            }
        }.runTaskLater(CosmicSkyInventoryPets.get(), 20 * dur);
    }
}