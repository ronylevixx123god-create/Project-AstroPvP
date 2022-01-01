package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.avaje.ebean.validation.NotNull;
import com.compilex.CompilexPermissions.PermissionsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.sun.prism.impl.ps.BaseShaderContext;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.managers.BlockModifier;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.tasks.BleedTask;
import me.ifamasssxd.cosmicsky.inventorypets.utils.CageCooldownUtils;
import me.ifamasssxd.cosmicsky.inventorypets.utils.DamageIndicatorUtils;
import me.ifamasssxd.cosmicsky.inventorypets.utils.PetUtils;
import org.bukkit.*;
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
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InventoryWorldDestroyerPet extends InventoryPet {
    public static List<Block> blockList = new ArrayList<Block>();
    public static ArrayList<Location> locations = new ArrayList<>();

    public InventoryPets getOwner() {
        return InventoryPets.WORLD_DESTROYER;
    }

    public List<String> getPetDescription(int level) {
        return Lists.newArrayList(CC.Gray + "A fragment of the legendary", CC.Gray + "World Destroyer, Yijki.", (new StringBuilder(String.valueOf(CC.Gray))).toString(), CC.WhiteB + "Ability", CC.Gray + "Summon the power of Yijki's", CC.Gray + "[" + CC.DarkRed + "Dimensional Cage" + CC.Gray + "] ability (" + this.cageDuration + "s)", (new StringBuilder(String.valueOf(CC.Gray))).toString(), CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(getCooldown(5)));
    }

    private double cageDuration = 5.0;


    public int getCooldown(int level) {
        return 300;
    }

    public int getExpForLevel(int level) {
        return 1700 * 5;
    }

    public int getMaxLevel() {
        return 5;
    }


    public boolean onPetItemInteract(Player player, ItemStack item, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis((long) this.getCooldown(5));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);


        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new PrefixedPetException("Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        if (player.hasMetadata("in_duel"))
            return true;
        if (player.hasMetadata("spectator"))
            return true;
        Faction faction = FactionUtils.getFaction(player);
        Location locationClicked = player.getLocation();
        Player target = null;
        for (Entity other : player.getNearbyEntities(4.0D, 4.0D, 4.0D)) {
            if (other instanceof Player) {
                Player nearPlayer = (Player) other;
                if (!WorldGuardUtils.isPvPEnabled(nearPlayer.getLocation()))
                    continue;
                if (!player.canSee(nearPlayer))
                    continue;
                if (nearPlayer.getGameMode() == GameMode.CREATIVE)
                    continue;
                Faction nearFac = FactionUtils.getFaction(nearPlayer);
                Relation rel = nearFac.getRelationTo((RelationParticipator) faction);
                if (rel.isAlly() || rel.isTruce())
                    continue;
                if (rel.isMember())
                    continue;
                double distance = (event == null) ? 10.9D : 6.9D;
                if (player.getLocation().distance(other.getLocation()) < distance) {
                    target = nearPlayer;
                    break;
                }
            }
        }

        if (MaskUtils.hasMaskEquipped(player, "ELF")) {
            cageDuration = 7.5;
        } else {
            cageDuration = 5.0;
        }


        final Player test = target;
        if (WorldGuardUtils.isPvPDisabled(locationClicked))
            throw new PrefixedPetException("Not allowed in this region!");
        if (player.hasMetadata("inDungeonParkour"))
            throw new PrefixedPetException("You cannot use this pet while in the dungeon parkour.");
        if (target == null || (event != null && target.getLocation().distance(player.getLocation()) > 4.0D))
            throw new PrefixedPetException("Could not find a valid target!");
        /*if (CageCooldown.isOnCageCooldown(target))
            throw new PrefixedPetException(target.getName() + " is still on Cage Cooldown! This lasts 20 seconds.");*/
        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SEXY_HELMET) && Math.random() < 0.5D) {
            petItem.setLastUsed(System.currentTimeMillis());


            player.sendMessage(CC.translate("&d&l* CHAD SUNGLASSES BLOCKED &7[&c" + target.getName() + "&7] &d&l*"));
            target.sendMessage(CC.translate("&d&l* CHAD SUNGLASSES &7[&cBlocked World Destroyer&7] &d&l*"));
            return true;
        }
        if (CageCooldownUtils.isOnCageCooldown(target)) {
            player.sendMessage(CC.translate("&c&lPET: &c" + target.getName() + "&c is still on &nCage Cooldown&c, this lasts for 60s!"));
            return false;
        }


        //CageCooldown.enterCageCooldown(target);
        player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);

        player.sendMessage("");
        player.sendMessage(CC.translate("&4&l*** WORLD DESTROYER PET [&6" + target.getName() + "s&4&l] - CAGED! **"));
        player.sendMessage("");

        target.sendMessage("");
        target.sendMessage(CC.translate("&4&l*** " + player.getName() + "&4&l's WORLD DESTROYER PET [&6" + cageDuration + "s&4&l] - CAGE! **"));
        target.sendMessage("");
        DamageIndicatorUtils.sendDamageIndicator(target, CC.translate("&e&l* WORLD DESTROYER [&77s&e&l] *"), "RKO");
        CageCooldownUtils.enterCageCooldown(target);

        petItem.setLastUsed(System.currentTimeMillis());
        cagePlayer(player, target);


        Player finalTarget1 = target;
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin) CosmicSkyInventoryPets.get(), () -> {
            try {
                PermissionsAPI.getPermissionUser(finalTarget1.getUniqueId()).getPermissionNodes().remove("verus.bypass");
                PermissionsAPI.getPermissionUser(player.getUniqueId()).getPermissionNodes().remove("verus.bypass");
            } catch (Exception e) {
            }

        }, (long) this.cageDuration * 20L);

        return true;
    }

    public ArrayList<Player> getPlayersInRadius(Player player, int radius) {
        ArrayList<Player> list = new ArrayList<>();
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player))
                continue;
            Player p = (Player) entity;
            if (p.hasMetadata("spectator"))
                continue;
            list.add(p);
        }
        return list;
    }


    private static List<Location> setInside(Location baseLocation, Material material) {
        List<Location> locs = new ArrayList<>(Arrays.asList(baseLocation, baseLocation

                .clone().add(-1.0D, 0.0D, 0.0D), baseLocation
                .clone().add(0.0D, 0.0D, 1.0D), baseLocation
                .clone().add(-1.0D, 0.0D, 1.0D)));
        return setWithLocations(material, locs);
    }

    private static List<Location> setOutside(Location baseLocation, Material material) {
        List<Location> locs = new ArrayList<>(Arrays.asList(baseLocation
                        .clone().add(0.0D, 0.0D, -1.0D), baseLocation
                        .clone().add(1.0D, 0.0D, 0.0D), baseLocation
                        .clone().add(1.0D, 0.0D, -1.0D), baseLocation
                        .clone().add(1.0D, 0.0D, 1.0D), baseLocation
                        .clone().add(1.0D, 0.0D, 2.0D), baseLocation
                        .clone().add(0.0D, 0.0D, 2.0D), baseLocation
                        .clone().add(-1.0D, 0.0D, 2.0D), baseLocation
                        .clone().add(-2.0D, 0.0D, 2.0D), baseLocation
                        .clone().add(-2.0D, 0.0D, 0.0D), baseLocation
                        .clone().add(-2.0D, 0.0D, 1.0D),
                baseLocation
                        .clone().add(-2.0D, 0.0D, -1.0D), baseLocation
                        .clone().add(-1.0D, 0.0D, -1.0D)));
        return setWithLocations(material, locs);
    }

    @NotNull
    private static List<Location> setWithLocations(Material material, List<Location> locs) {
        List<Location> filteredLocations = new ArrayList<>();
        for (Location loc : locs) {
            if (loc.getBlock().getType() == Material.AIR) {
                filteredLocations.add(loc);
                loc.getBlock().setType(material);
                loc.getBlock().setMetadata("cage", (MetadataValue) new FixedMetadataValue((Plugin) CosmicSkyInventoryPets.get(), "cage"));
            }
        }
        return filteredLocations;
    }

    public static void cagePlayer(Player player, final Player affected) {
        final List<Location> list = new ArrayList<>();
        List<Location> toPlace = new ArrayList<>();
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Block block = affected.getLocation().getBlock();
        Block current = null;
        Location baseLocation = affected.getLocation();
        list.addAll(setInside(baseLocation.clone().add(0.0D, -1.0D, 0.0D), Material.OBSIDIAN));
        list.addAll(setInside(baseLocation.clone().add(0.0D, 3.0D, 0.0D), Material.OBSIDIAN));
        list.addAll(setOutside(baseLocation.clone().add(0.0D, -1.0D, 0.0D), Material.OBSIDIAN));
        list.addAll(setOutside(baseLocation.clone().add(0.0D, 3.0D, 0.0D), Material.OBSIDIAN));
        list.addAll(setOutside(baseLocation.clone().add(0.0D, 0.0D, 0.0D), Material.IRON_FENCE));
        list.addAll(setOutside(baseLocation.clone().add(0.0D, 1.0D, 0.0D), Material.IRON_FENCE));
        list.addAll(setOutside(baseLocation.clone().add(0.0D, 2.0D, 0.0D), Material.IRON_FENCE));
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.4F);
        locations.addAll(list);

        scheduler.scheduleSyncDelayedTask((Plugin) CosmicSkyInventoryPets.get(), (Runnable) (new BukkitRunnable() {
            int count = 0;

            public void run() {
                if (this.count >= 6) {
                    InventoryWorldDestroyerPet.removeBlocks(list);
                    cancel();
                }
                if (WorldGuardUtils.isPvPEnabled(affected.getLocation())) {
                    affected.damage(1.0D);
                }
                if (affected.getHealth() <= 0.0D || affected.isDead()) {
                    InventoryWorldDestroyerPet.removeBlocks(list);
                    cancel();
                }
                this.count++;
            }
        }).runTaskTimer((Plugin) CosmicSkyInventoryPets.get(), 20L, 20L));
    }

    public static String stringLocation(Location loc) {
        int x = (int) loc.getX();
        int y = (int) loc.getY();
        int z = (int) loc.getZ();
        String world = loc.getWorld().getName();
        String result = String.valueOf(world) + " " + x + " " + y + " " + z;
        return result;
    }

    public static Location stringLocationConvert(String str) {
        String[] list = str.split(" ");
        World world = Bukkit.getWorld(list[0]);
        int x = Integer.valueOf(list[1]).intValue();
        int y = Integer.valueOf(list[2]).intValue();
        int z = Integer.valueOf(list[3]).intValue();
        return new Location(world, x, y, z);
    }

    public static Block[] getBlockList(List<String> list) {
        Block[] blocks = new Block[list.size()];
        for (int i = 0; i < list.size(); i++)
            blocks[i] = stringLocationConvert(list.get(i)).getBlock();
        return blocks;
    }

    public static void removeBlocks(List<Location> list) {
        for (int i = 0; i < list.size(); i++) {
            Location location = list.get(i);
            Block block = location.getBlock();
            if (block != null) {
                block.setType(Material.AIR);
                locations.remove(location);
            }
        }
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
        return false;
    }

}
