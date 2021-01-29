package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class InventoryStitchesPet extends InventoryPet {
    @Override
    public InventoryPets getOwner() {
        return InventoryPets.STITCHES;
    }

    @Override
    public List<String> getPetDescription(int p0) {
        return Lists.newArrayList(CC.Gray + "An abomination with a chilling",
                CC.Gray + "presence, striking fear into all",
                CC.Gray + "who witness its bloodthirsty gaze.",
                "",
                CC.WhiteB + "Ability",
                CC.Gray + "Silence nearby enemy pets, preventing",
                CC.Gray + "any enemy player from using pets in a",
                CC.Yellow + "14x14 radius " + CC.Gray + "for: " + CC.Yellow + (18 + p0) + "s",
                CC.Gray + "( Players can be pet-silenced 1x per 90s )",
                "",
                CC.WhiteB + "Cooldown",
                CC.Gray + (12 - (p0 - 1)) + " Minutes");
    }

    @Override
    public int getExpForLevel(final int level) {
        return 2000 * level;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getCooldown(int p0) {
        return (int) TimeUnit.MINUTES.toSeconds(9);
    }


    @Override
    public boolean onPetItemInteract(Player player, ItemStack itemStack, InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        final long lastUse = petItem.getLastUsed();
        final long delay = TimeUnit.SECONDS.toMillis(this.getCooldown(petItem.getLevel()));
        long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
        if (lastUse > 0L && timeTillCanUse > 0L) {
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int) (timeTillCanUse / 1000L)));
        }
        if (!WorldGuardUtils.isPvPEnabled(player.getLocation())) {
            throw new PrefixedPetException("You cannot use this pet in non pvp-enabled territory.");
        }
        List<Player> nearbyEntities = player.getNearbyEntities(14, 14, 14).stream().filter(nearbyEntity -> {
            if (!(nearbyEntity instanceof Player))
                return false;
            Player casted = (Player) nearbyEntity;
            if (casted.getGameMode() == GameMode.CREATIVE)
                return false;
            if (EssentialsUtils.isVanished(casted))
                return false;
            if (EssentialsUtils.isInGodMode(casted))
                return false;
            if (nearbyEntity.hasMetadata("lastStitches") &&
                    TimeUnit.SECONDS.toMillis(90) - (System.currentTimeMillis() - nearbyEntity.getMetadata("lastStitches").get(0).asLong()) > 0) {
                return false;
            }
            return !GeneralUtil.isAtleastTruce(player, (Player) nearbyEntity);
        }).map(e -> (Player) e).collect(Collectors.toList());
        if (nearbyEntities.isEmpty())
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "No valid target found! (14x14)");
        nearbyEntities.forEach(needToBeStitched -> {
            needToBeStitched.sendMessage(CC.translate(String.format("&6&l** %s STITCHES PET [&6%d.0s&6&l] - PET SILENCE! **",
                    player.getName() + (player.getName().endsWith("s") ? "'" : "'s"),
                    (18 + petItem.getLevel()))));
            needToBeStitched.sendMessage(CC.translate("&6( All pets silenced for " + (18.0 + petItem.getLevel()) + "s )"));
            needToBeStitched.setMetadata("stitchesPet", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));
            needToBeStitched.setMetadata("stitchesPetTime", new FixedMetadataValue(CosmicSkyInventoryPets.get(), TimeUnit.SECONDS.toMillis(18 + petItem.getLevel())));
            needToBeStitched.setMetadata("lastStitches", new FixedMetadataValue(CosmicSkyInventoryPets.get(), System.currentTimeMillis()));

        });
        petItem.setLastUsed(System.currentTimeMillis());

        player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1.1f, 3.0f);
        player.sendMessage("");
        player.sendMessage(CC.translate("&6&lPET: ** STITCHES [&e" + (18.0 + petItem.getLevel()) + "s&6&l] &6- " + nearbyEntities.size() + " player(s) pet-silenced! **"));
        player.sendMessage("");

        return false;
    }

    @Override
    public boolean onTickInHotBar(Player p0, int p1, InventoryPetItem p2) {
        return false;
    }
}
