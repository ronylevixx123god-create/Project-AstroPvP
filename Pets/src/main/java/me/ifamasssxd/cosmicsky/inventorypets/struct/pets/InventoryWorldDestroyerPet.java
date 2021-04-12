package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Relation;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.managers.BlockModifier;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.tasks.BleedTask;
import me.ifamasssxd.cosmicsky.inventorypets.utils.CageCooldown;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InventoryWorldDestroyerPet extends InventoryPet {
    public static List<Block> blockList = new ArrayList<Block>();

    public InventoryPets getOwner() {
        return InventoryPets.WORLD_DESTROYER;
    }

    public List<String> getPetDescription(int level) {
        return Lists.newArrayList(CC.Gray + "A fragment of the legendary", CC.Gray + "World Destroyer, Yijki.", (new StringBuilder(String.valueOf(CC.Gray))).toString(), CC.WhiteB + "Ability", CC.Gray + "Summon the power of Yijki's", CC.Gray + "[" + CC.DarkPurple + "Dimensional Cage" + CC.Gray + "] ability (" + cageDuration(5) + "s)", (new StringBuilder(String.valueOf(CC.Gray))).toString(), CC.WhiteB + "Cooldown", CC.Gray + " " + TimeUtils.formatSeconds(getCooldown(5)));
    }

    private double cageDuration(int level) {
        return 6.00;
    }


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

        if (item.getAmount() > 1) {
            player.sendMessage(CC.translate("&cYou cannot use stacked pets!"));

            return false;
        }

        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));

        if (player.hasMetadata("in_duel"))
            return true;
        if (player.hasMetadata("spectator"))
            return true;
        Faction faction = FactionUtils.getFaction(player);
        Location locationClicked = player.getLocation();
        Player target = null;

        for (final Entity other : player.getNearbyEntities(4.0, 4.0, 4.0)) {
            if (other instanceof Player) {
                final Player nearPlayer = (Player) other;
                if (!WorldGuardUtils.isPvPEnabled(nearPlayer.getLocation())) {
                    continue;
                }
                if (!player.canSee(nearPlayer)) {
                    continue;
                }
                if (nearPlayer.getGameMode() == GameMode.CREATIVE) {
                    continue;
                }
                final Faction nearFac = FactionUtils.getFaction(nearPlayer);
                final Relation rel = nearFac.getRelationTo(faction);
                if (rel.isAlly() || rel.isTruce()) {
                    continue;
                }

                if (rel.isMember()) {
                    continue;
                }
                final double distance = (event == null) ? 10.9 : 6.9;
                if (player.getLocation().distance(other.getLocation()) < distance) {
                    target = nearPlayer;
                    break;
                }
            }
        }


        if (WorldGuardUtils.isPvPDisabled(locationClicked)) {
            throw new PrefixedPetException("Not allowed in this region!");
        }
        if (player.hasMetadata("inDungeonParkour"))
            throw new PrefixedPetException("You cannot use this pet while in the dungeon parkour.");
        if (target == null || (event != null && target.getLocation().distance(player.getLocation()) > 4.0)) {
            throw new PrefixedPetException("Could not find a valid target!");
        }

        if (CageCooldown.isOnCageCooldown(target)) {
            throw new PrefixedPetException(target.getName() + " is still on Cage Cooldown! This lasts 20 seconds.");
        }


        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SEXY_HELMET) && Math.random() < 0.5 && !playerHasApollo(player)) {
            petItem.setLastUsed(System.currentTimeMillis());
            if (playerHasApollo(player)) {
                target.sendMessage(CC.translate("&c&l* CHAD SUNGLASSES &a&lBYPASSED &7[&c" + player.getName() + "&cApollo Set" + "&7] &c&l*"));
                player.sendMessage(CC.translate("&a&l* BYPASSED CHAD SUNGLASSED FROM &c&n" + target.getName() + "&a&l *"));

            }
            player.sendMessage(CC.translate("&d&l* CHAD SUNGLASSES BLOCKED &7[&c" + target.getName() + "&7] &d&l*"));
            target.sendMessage(CC.translate("&d&l* CHAD SUNGLASSES &7[&cBlocked World Destroyer&7] &d&l*"));
            return true;
        }


        final Player p = target;
        final Location location = p.getLocation();

        final ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.BEDROCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().add(0.0, -1.0, 0.0), Material.BEDROCK));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 0.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 1.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 2.0, 0.0), Material.IRON_FENCE));
        blocks.addAll(this.setOutlinedBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.BEDROCK));
        blocks.addAll(this.setInsideBlocks(player.getLocation().clone().add(0.0, 3.0, 0.0), Material.BEDROCK));
        blockList.addAll(blocks);

        p.playSound(p.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.4f);
        Location loc = p.getLocation().clone();
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 4));
        BleedTask.bleedPlayer(p, 4);

        if (playerHasApollo(player)) {
            Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), () -> {
                List<Block> newBlocks = blocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
                for (Block block : newBlocks) {
                    block.setType(Material.AIR);
                }
                blockList.removeAll(newBlocks);
            }, (long) ((long) (cageDuration(petItem.getLevel()) + cageDuration(petItem.getLevel() / 2)) * 20));

        } else {
            Bukkit.getScheduler().runTaskLater(CosmicSkyInventoryPets.get(), () -> {
                        List<Block> newBlocks = blocks.stream().filter(Objects::nonNull).collect(Collectors.toList());
                        for (Block block : newBlocks) {
                            block.setType(Material.AIR);
                        }
                        blockList.removeAll(newBlocks);
                    }


                    , (long) cageDuration(petItem.getLevel()) * 20);

        }


        CageCooldown.enterCageCooldown(target);

        player.playSound(player.getLocation(), Sound.DIG_SNOW, 1.1F, 3.0F);
        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1F, 3.0F);
        petItem.setLastUsed(System.currentTimeMillis());

        return true;
    }

    public Block setBlock(Location location, Material material) {
        if (location.getBlock().getType() == Material.AIR) {
            location.getBlock().setType(material);
            return location.getBlock();
        }
        return null;
    }

    public List<Block> setOutlinedBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 2.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(2.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(-2.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -2.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -2.0), material));
        return blocks;
    }

    public List<Block> setInsideBlocks(Location location, Material material) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        blocks.add(this.setBlock(location, material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 0.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(0.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, 1.0), material));
        blocks.add(this.setBlock(location.clone().add(-1.0, 0.0, -1.0), material));
        blocks.add(this.setBlock(location.clone().add(1.0, 0.0, -1.0), material));
        return blocks;
    }


    public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
        return false;
    }

    public boolean playerHasApollo(Player pl) {
        return CosmicArmorSetsAPI.getBonusCrystalsEquipped(pl).containsKey(ArmorSet.APOLLO) || CosmicArmorSetsAPI.getArmorSet(pl) == ArmorSet.APOLLO;
    }

}
