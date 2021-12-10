package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.EnchantmentAPI;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import me.ifamasssxd.cosmicsky.inventorypets.utils.PetUtils;
import me.ifamasssxd.cosmicsky.inventorypets.utils.SkrrtCooldownUtils;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventorySkrrtPet extends InventoryPet {
    public int petLevel = 5;

    public InventoryPets getOwner() {
        return InventoryPets.SKRRT;
    }

    public List getPetDescription(int level) {
        return Lists.newArrayList(CC.Gray + "Yeeeeeeeeeeet.", CC.Gray, CC.WhiteB + "Ability", CC.Gray + "Activates " + CC.DarkRedB + "[Skrrt]:", CC.DarkRed + "* " + CC.Gray + "Freezes enemy players within 16x16 for " + this.freezeDuration(this.petLevel) + "s", CC.DarkRed + "* " + CC.Gray + "Disables /fly, combat tags enemy player within 16x16 ", CC.DarkRed + "* " + CC.Gray + "Apply Gears V for (" + this.freezeDuration(this.petLevel) + "s)", CC.DarkRed + "* " + CC.Gray + "Invulnerable for (" + this.freezeDuration(this.petLevel) + "s)", CC.DarkRed + "* " + CC.Gray + "Cannot attack for (" + this.freezeDuration(this.petLevel) + "s)", CC.Gray, CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds((long) this.getCooldown(level)));
    }

    public int getCooldown(int level) {
        return 360;
    }

    private int freezeDuration(int level) {
        return 5;
    }

    public int getExpForLevel(int level) {
        return 1000 * level;
    }

    public int getMaxLevel() {
        return 5;
    }

    public boolean onPetItemInteract(Player player, ItemStack itemStack, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {

        /*if (PetUtils.isOnCooldown(player, petItem.getPetType()) && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException(CC.Red + "Ability on cooldown: " + CC.RedU +
                    TimeUtils.formatSeconds(PetUtils.getSecondsOnCooldown(player, petItem.getPetType())));*/

        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(5));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        //PetUtils.addPetCooldown(player, petItem.getPetType(), (getCooldown(5) * 1000));

        petItem.setLastUsed(System.currentTimeMillis());



        this.petLevel = 5;
        this.givePlayerGearFive(player);
        this.skrrtAbilities(player);
        return true;
    }


    private void givePlayerGearFive(final Player player) {
        EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 5);
        player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
        (new BukkitRunnable() {
            public void run() {
                EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 5);
            }
        }).runTaskLater(CosmicSkyInventoryPets.get(), 1L * (long) this.freezeDuration(this.petLevel) * 20L);
    }

    private void skrrtAbilities(final Player player) throws PrefixedPetException {
        Iterator var2 = player.getNearbyEntities(16.0D, 16.0D, 16.0D).iterator();
        if (player.getNearbyEntities(16.0D, 16.0D, 16.0D).isEmpty()) {

            throw new PrefixedPetException("Could not find a valid target!");
        }

        while (true) {
            Player p;
            do {
                do {
                    do {
                        Entity ent;
                        do {
                            if (!var2.hasNext()) {
                                return;
                            }

                            ent = (Entity) var2.next();
                        } while (!(ent instanceof Player));

                        p = (Player) ent;
                    } while (EssentialsUtils.isVanished(p));
                } while (p.hasMetadata("NPC"));
            } while (p.hasMetadata("in_duel"));

            FPlayer castingPlayer = (FPlayer) FPlayers.i.get(player);
            FPlayer surroundingPlayer = (FPlayer) FPlayers.i.get(p);
            if (SkrrtCooldownUtils.isOnSkrrtCooldown(p)) {
                player.sendMessage(CC.translate("&c&lPET: &c" + p.getName() + "&c is still on &nSkrrt Cooldown&c, this lasts for 60s!"));
                return;
            }
            if (!WorldGuardUtils.isPvPEnabled(p.getLocation())) {
                player.sendMessage(CC.translate("&c" + p.getName() + " is in a non-pvp enabled region."));
                return;
            }
            if (castingPlayer.getRelationTo(surroundingPlayer) == Relation.ENEMY || castingPlayer.getRelationTo(surroundingPlayer) == Relation.NEUTRAL) {
                p.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* SKRRT: (" + ChatColor.GRAY + player.getName() + ChatColor.DARK_RED + ")");
                player.sendMessage("" + ChatColor.DARK_RED + ChatColor.BOLD + "* SKRRT: (" + ChatColor.GRAY + p.getName() + ChatColor.DARK_RED + ")");

                DamageIndicatorUtils.sendDamageIndicator(p, CC.translate("&4&l* SKRRT PET *"), "SKRRT");

                SkrrtCooldownUtils.enterSkrrtCooldown(p);

                CombatLog.flagCombat(p);
                this.freezeThePlayer(p);
                player.setMetadata("frozenSkrrt", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
            }

            (new BukkitRunnable() {
                public void run() {
                    player.removeMetadata("frozenSkrrt", CosmicSkyInventoryPets.get());
                }
            }).runTaskLater(CosmicSkyInventoryPets.get(), (long) this.freezeDuration(this.petLevel) * 20L);
        }
    }

    public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
        return false;
    }

    private void freezeThePlayer(final Player p) {
        p.playSound(p.getLocation(), Sound.STEP_SNOW, 1.0F, 1.4F);
        p.setWalkSpeed(0.0F);
        p.removePotionEffect(PotionEffectType.JUMP);
        p.removePotionEffect(PotionEffectType.SLOW);
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int) (1L * (long) this.freezeDuration(this.petLevel) * 20L), 128));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int) (1L * (long) this.freezeDuration(this.petLevel) * 20L), 128));
        p.setMetadata("frozenSkrrt", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
        (new BukkitRunnable() {
            public void run() {
                p.removeMetadata("frozenSkrrt", CosmicSkyInventoryPets.get());
                p.removePotionEffect(PotionEffectType.SLOW);
                p.removePotionEffect(PotionEffectType.JUMP);
                p.setWalkSpeed(0.2F);
            }
        }).runTaskLater(CosmicSkyInventoryPets.get(), 1L * (long) this.freezeDuration(this.petLevel) * 20L);
    }
}
