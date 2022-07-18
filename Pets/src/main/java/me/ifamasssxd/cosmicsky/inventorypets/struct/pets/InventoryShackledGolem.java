package me.ifamasssxd.cosmicsky.inventorypets.struct.pets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPet;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class InventoryShackledGolem extends InventoryPet {
    public InventoryPets getOwner() {
        return InventoryPets.SHACKLED_GOLEM;
    }

    public List<String> getPetDescription() {
        return Lists.newArrayList(CC.Gray + "An ancient, chained arcane golem", String.valueOf(CC.Gray) + "that keeps its master grounded.", "", String.valueOf(CC.WhiteB) + "Ability", String.valueOf(CC.Gray) + "Immune to (almost) all Knockback for: " + CC.White + "16s", "", String.valueOf(CC.WhiteB) + "Cooldown", String.valueOf(CC.Gray) + " 5 Minutes");
    }

    public int getCooldown() {
        return (int)TimeUnit.MINUTES.toSeconds(5L);
    }

    public boolean onPetItemInteract(final Player player, ItemStack item, final InventoryPetItem petItem, PlayerInteractEvent event) throws PetException {
        long lastUse = petItem.getLastUsed();
        long delay = TimeUnit.SECONDS.toMillis(getCooldown());
        long timeTillCanUse = delay - System.currentTimeMillis() - lastUse;
        if (lastUse > 0L && timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking()))
            throw new NonPrefixedPetException(String.valueOf(CC.RedB) + "PET: " + CC.Red + "Ability on cooldown: " + CC.RedU + TimeUtils.formatSeconds((int)(timeTillCanUse / 1000L)));
        if (player.hasMetadata("stitched"))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You are pet silenced!");
        if (player.hasMetadata("no_knockback"))
            throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You already have no knockback, wait for it to run out!");
        petItem.getPetType().sendMsg(player, CC.Red + "Shacked Golem activated.");
        player.setMetadata("no_knockback", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSkyInventoryPets.get(), Boolean.TRUE));
        long abilitySeconds = 16L;
        Bukkit.getScheduler().runTaskLater((Plugin)CosmicSkyInventoryPets.get(), new Runnable() {
            public void run() {
                player.removeMetadata("no_knockback", (Plugin)CosmicSkyInventoryPets.get());
                petItem.getPetType().sendMsg(player, CC.translate("Shackled Golem worn off!"));
            }
        }, abilitySeconds * 20L);
        petItem.setLastUsed(System.currentTimeMillis());
        return true;
    }

    public boolean onTickInHotBar(Player player, int inventorySlot, InventoryPetItem item) {
        return false;
    }
}
