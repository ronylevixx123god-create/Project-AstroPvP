package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Relation;
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
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryAbominableSnowmanPet extends InventoryPet {
    int effectDuration = 5;

    @Override
    public InventoryPets getOwner() {
        return InventoryPets.ABOMINABLE_SNOWMANPET;
    }




    @Override
    public List<String> getPetDescription() {
        return Lists.newArrayList(String.valueOf(CC.Gray) + "An enormous, monstrous spirit", String.valueOf(CC.Gray) + "well trained in illusionist magic.", String.valueOf(CC.Gray), CC.White + "Ability [" + CC.DarkRed + "Polymorph" + CC.White + "] " + CC.Gray + "target player, transforming", String.valueOf(CC.Gray) + "them into harmless, immoble snowman for: " + this.freezeDuration(this.effectDuration) + "s", String.valueOf(CC.Gray) + "Any dammage dealt to them will break the spell", String.valueOf(CC.Gray), String.valueOf(CC.WhiteB) + "Cooldown", String.valueOf(CC.Gray) + " " + TimeUtils.formatSeconds((long)this.getCooldown()));
    }

    @Override
    public int getCooldown() {

        return 600;
    }




    @Override
    public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        FPlayer surroundingPlayer;
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis(this.getCooldown());
        long timeTillCanUse = delay - System.currentTimeMillis() - lastUse;

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        if (player.hasMetadata("spectator")) {
            return true;
        }
        if (player.hasMetadata("in_duel")) {
            return true;
        }
        Faction faction = FactionUtils.getFaction((Player)player);
        if (faction == null || faction.isNone()) {
            throw new PrefixedPetException("You must be in a faction to use this pet!");
        }
        Location locationClicked = player.getLocation();
        if (!WorldGuardUtils.isPvPEnabled((Location)locationClicked)) {
            player.sendMessage(ChatColor.RED + "Combat is disabled in this region.");
            return true;
        }
        this.effectDuration = petItem.getLevel();
        double closest = Double.MAX_VALUE;
        Entity closestp = null;
        for (Entity i : player.getNearbyEntities(3.0, 3.0, 3.0)) {
            Player p;
            if (!(i instanceof Player) || (p = (Player)i).getGameMode().equals((Object)GameMode.CREATIVE) || AstroItemSkinsAPI.hasSkinEquipped(p, SkinType.SEXY_HELMET) || p.hasMetadata("NPC")) continue;
            if (p.hasMetadata("in_duel")) {
                return true;
            }
            double dist = i.getLocation().distance(event.getPlayer().getLocation());
            if (closest != Double.MAX_VALUE && dist >= closest) continue;
            closest = dist;
            closestp = i;
        }
        if (closestp == null) {
            player.sendMessage(String.valueOf(CC.Red) + "PET: No targets found.");
            return true;
        }
        Player p2 = (Player)closestp;
        if (p2.hasMetadata("frozenSnowman")) {
            p2.sendMessage(String.valueOf(CC.Red) + "Player is already a Snowman! ");
            return true;
        }
        FPlayer castingPlayer = (FPlayer)FPlayers.getInstance().getByPlayer(player);
        if (castingPlayer.getRelationTo(FPlayers.getInstance().getByPlayer((p2))) == Relation.ENEMY || castingPlayer.getRelationTo(FPlayers.getInstance().getByPlayer((p2))) == Relation.NEUTRAL) {
            petItem.setLastUsed(System.currentTimeMillis());
            this.freezeThePlayer(p2, player);
            player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1f, 3.0f);
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1f, 3.0f);
            return true;
        }
        return true;
    }

    private void freezeThePlayer(final Player p, Player caster) {
        p.setFlying(false);
        MobDisguise snowman = new MobDisguise(DisguiseType.SNOWMAN);
        p.playSound(p.getLocation(), Sound.STEP_SNOW, 1.0f, 1.4f);
        DisguiseAPI.disguiseEntity((Entity)p, (Disguise)snowman);
        p.setWalkSpeed(0.0f);
        p.removePotionEffect(PotionEffectType.JUMP);
        p.removePotionEffect(PotionEffectType.SLOW);
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int)((long) this.freezeDuration(this.effectDuration) * 20L), 128));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)((long) this.freezeDuration(this.effectDuration) * 20L), 128));
        p.setMetadata("frozenSnowman", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), (Object)System.currentTimeMillis()));
        WorldUtil.sendWorldEffect((Block)p.getLocation().getBlock(), (int)57, (int)2001);
        p.sendMessage(String.valueOf(CC.YellowB) + "POLYMORPH'D [" + CC.Gray + this.freezeDuration(this.effectDuration) + "s" + CC.YellowB + "] " + CC.YellowB + "[" + CC.Gray + caster.getName() + CC.YellowB + "]");
        caster.sendMessage(CC.YellowB + "POLYMORPH'D [" + CC.Gray + this.freezeDuration(this.effectDuration) + "s" + CC.YellowB + "] " + CC.YellowB + "[" + CC.Gray + p.getName() + CC.YellowB + "]");
        new BukkitRunnable(){

            public void run() {
                if (p.hasMetadata("cancelrunnable")) {
                    p.removeMetadata("cancelrunnable", (Plugin)CosmicSkyInventoryPets.get());
                } else {
                    InventoryAbominableSnowmanPet.this.removeDisguise(p);
                }
            }
        }.runTaskLater((Plugin)CosmicSkyInventoryPets.get(), (long) this.freezeDuration(this.effectDuration) * 20L);
    }

    private int freezeDuration(int level) {
        if (level == 1) {
            return 10;
        }
        if (level == 2) {
            return 15;
        }
        if (level == 3) {
            return 20;
        }
        return 10;
    }

    private void removeDisguise(Player player) {
        player.removeMetadata("frozenSnowman", (Plugin)CosmicSkyInventoryPets.get());
        player.removeMetadata("cancelrunnable", (Plugin)CosmicSkyInventoryPets.get());
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.setWalkSpeed(0.2f);
        DisguiseAPI.undisguiseToAll((Entity)player);
        player.setFlying(true);
        player.sendMessage(String.valueOf(CC.Red) + "You are no longer a frozen Snowman!");
    }

    @Override
    public boolean onTickInHotBar(Player var1, int var2, InventoryPetItem var3) {
        return false;
    }

    public static int getDistance(Player e) {
        Location loc = e.getLocation().clone();
        double y = loc.getBlockY();
        int distance = 0;
        for (double i = y; i >= 0.0; i -= 1.0) {
            loc.setY(i);
            if (loc.getBlock().getType().isSolid()) break;
            ++distance;
        }
        return distance;
    }
}
