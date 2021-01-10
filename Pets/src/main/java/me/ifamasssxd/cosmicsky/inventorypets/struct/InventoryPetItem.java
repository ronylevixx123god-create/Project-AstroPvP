package me.ifamasssxd.cosmicsky.inventorypets.struct;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.TimeUnit;

import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicPersistentItem;
import me.ifamasssxd.cosmicskycustoms.struct.PlayerInteractable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryPetItem extends CosmicPersistentItem implements PlayerInteractable {
    private InventoryPets petType;
    private long lastUsed;
    private int level;
    private int exp;
    private long lastRareCandyUsed;
    private boolean leashed;

    public InventoryPetItem(String internalName) {
        super(internalName);
    }

    public InventoryPetItem() {
        this("inventory_pet");
    }

    public InventoryPetItem(ItemStack stack, NBTWrapper wrapper, String internal) {
        super(stack, wrapper, internal);
        this.loadNBT();
    }

    public NBTWrapper getWrapper() {
        return this.wrapper;
    }

    public void loadNBT() {
        if (this.wrapper != null) {
            NBTTagCompoundBuilder builder = this.wrapper.getCompound("conaxData");
            if (builder != null && builder.hasKey("petType")) {
                this.petType = InventoryPets.fromName(builder.getString("petType"));
                this.level = builder.getInt("level");
                this.exp = builder.getInt("exp");
                this.lastUsed = builder.getLong("lastUsed");
                this.lastRareCandyUsed = builder.getLong("lastRareCandyUsed");
                this.leashed = builder.getBoolean("leashed");
            }
        }

        if (this.petType != null) {
            this.petType.getInternalClass().onItemLoad(this.wrapper, this);
        }

    }

    public ItemStack build() {
        return this.createItem((Player) null, this.petType, this.level, this.exp, this.lastUsed, this.lastRareCandyUsed, this.leashed);
    }

    public void addExperience(Player player, int exp) {
        if (this.exp + exp >= this.exp) {
            this.exp += exp;
            if (this.level < this.petType.getInternalClass().getMaxLevel()) {
                int expNeeded = this.petType.getInternalClass().getExpForLevel(this.level + 1);
                if (this.exp >= expNeeded) {
                    int overlap = this.exp - expNeeded;
                    this.levelUp(player);
                    this.exp = overlap;
                }
            }
        }

    }

    public void levelUp(Player player) {
        this.exp = 0;
        this.level = Math.min(this.petType.getInternalClass().getMaxLevel(), this.level + 1);
        player.sendMessage(CC.GreenB + "*** " + ItemUtils.getDisplayName(this.item) + CC.Green + " has increased to level " + CC.GreenU + this.level + CC.GreenB + " ***");
    }

    public ItemStack createItem(Player player, Object... objects) {
        if (objects != null && objects.length >= 1) {
            InventoryPets pet = (InventoryPets) ((InventoryPets) (objects[0] instanceof InventoryPets ? objects[0] : InventoryPets.fromName(objects[0].toString())));
            Number level = (Number) ((Number) (objects.length > 1 ? (objects[1] instanceof Number ? objects[1] : NumberUtils.parseNumberOrNull(objects[1].toString())) : null));
            if (level == null) {
                level = 1;
            }

            Number exp = (Number) ((Number) (objects.length > 2 ? (objects[2] instanceof Number ? objects[2] : NumberUtils.parseNumberOrNull(objects[2].toString())) : null));
            if (exp == null || ((Number) exp).intValue() < 0) {
                exp = 0;
            }

            Number lastUsed = (Number) ((Number) (objects.length > 3 ? (objects[3] instanceof Number ? objects[3] : NumberUtils.parseNumberOrNull(objects[3].toString())) : null));
            if (lastUsed == null || ((Number) lastUsed).longValue() < 0L) {
                lastUsed = 0;
            }

            Number lastRareCandyUsed = (Number) ((Number) (objects.length > 4 ? (objects[4] instanceof Number ? objects[4] : NumberUtils.parseNumberOrNull(objects[4].toString())) : null));
            if (lastRareCandyUsed == null || ((Number) lastRareCandyUsed).longValue() < 0L) {
                lastRareCandyUsed = 0;
            }
            boolean leashed = false;
            try {
                leashed = (objects.length > 5) ? (Boolean) objects[5] : false;
            } catch (Exception ignored) {
            }

            List<String> lore = Lists.newArrayList();
            pet.getInternalClass().getPetDescription(level.intValue()).forEach((line) -> {
                lore.add(CC.Gray + line);
            });
            lore.add("");
            String cb = CC.YellowB;
            lore.add(cb + "Level");
            lore.add(CC.Gray + " " + level);
            lore.add("");
            lore.add(cb + "EXP");
            lore.addAll(this.getExpLore(pet, ((Number) exp).longValue(), ((Number) level).intValue()));
            if (this.leashed) {
                lore.add("");
                lore.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "* LEASHED *");
            }
            return pet.getInternalClass().onItemCreate(this.applyItemData((new ItemBuilder(Material.SKULL_ITEM, pet.getItemName() + CC.Gray + " [LVL " + ((Number) level).intValue() + "]", new String[0])).data((short) 3).lore(lore).build()).setNBTBase("conaxData", (new NBTTagCompoundBuilder()).setInt("level", ((Number) level).intValue()).setInt("exp", ((Number) exp).intValue()).setString("petType", pet.name()).setLong("lastUsed", ((Number) lastUsed).longValue()).setLong("lastRareCandyUsed", ((Number) lastRareCandyUsed).longValue()).setBoolean("leashed", leashed).get()).setNBTBase("SkullOwner", pet.getBuilder().get()), this).assignIID().build();
        } else {
            return null;
        }
    }

    private List<String> getExpLore(InventoryPets type, long exp, int level) {
        List<String> retr = Lists.newArrayList();
        int required = type.getInternalClass().getExpForLevel(level + 1);
        double percent = required <= 0 ? 1.0D : (double) exp / (double) required;
        boolean bars = true;
        int expBars = (int) Math.floor(50.0D * percent);
        int redBars = 50 - expBars;
        StringBuilder builder = new StringBuilder();

        int i;
        for (i = 0; i < expBars; ++i) {
            builder.append(CC.Green).append("|");
        }

        for (i = 0; i < redBars; ++i) {
            builder.append(CC.Red).append("|");
        }

        retr.add(builder.toString());
        retr.add(CC.Gray + " (" + NumberUtils.formatMoney((double) exp) + "/" + NumberUtils.formatMoney((double) required) + ")");
        return retr;
    }

    public void onPlayerInteractable(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, PlayerInteractEvent playerInteractEvent) throws CosmicException {
        playerInteractEvent.setCancelled(true);
        InventoryPetItem item = CosmicPersistentItem.constructItem(itemStack, nbtWrapper, InventoryPetItem.class);
        if (item != null) {
            InventoryPets type = item.getPetType();
            if (type == null) {
                throw new PrefixedException("Corrupted Inventory Pet found?");
            }
            if (player.hasMetadata("stitchesPet") && player.hasMetadata("stitchesPetTime")) {
                final long delay = player.getMetadata("stitchesPetTime").get(0).asLong();
                final long timeTillCanUse = delay - (System.currentTimeMillis() - player.getMetadata("stitchesPet").get(0).asLong());
                if (timeTillCanUse > 0) {
                    player.sendMessage(CC.translate("&c&lPET: &cYour pets are silenced by a player's stitches pet for: " + (int) (timeTillCanUse / 1000) + "s"));
                    return;
                }
            }
            if (player.hasMetadata("hazmatBoots")) {
//         p.setMetadata("hazmatTime", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), TimeUnit.SECONDS.toMillis(12)));

                //
                long lastUse = player.getMetadata("hazmatBoots").get(0).asLong();
                long delay = TimeUnit.SECONDS.toMillis(12);
                long timeTillCanUse = delay - (System.currentTimeMillis() - lastUse);
                if (timeTillCanUse > 0L && (!player.isOp() || !player.isSneaking())) {
                    throw new NonPrefixedPetException(CC.translate("&cYour pets are currently silenced by a player's hazmat boots for " + TimeUtils.formatSeconds(TimeUnit.MILLISECONDS.toSeconds(timeTillCanUse))));
                } else {
                    player.removeMetadata("hazmatBoots", CosmicSkyInventoryPets.get());
                }
            }
            if (player.hasMetadata("spectator")) {
                throw new NonPrefixedPetException(CC.RedB + "PET: " + CC.Red + "You cannot use that ability while spectating!");
            }

            if (type.getInternalClass().onPetItemInteract(player, itemStack, item, playerInteractEvent)) {
                player.getInventory().setItemInHand(item.build());
                player.updateInventory();
            }
        }

    }

    public InventoryPets getPetType() {
        return this.petType;
    }

    public long getLastUsed() {
        return this.lastUsed;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLeashed(boolean leashed) {
        this.leashed = leashed;
    }

    public boolean isLeashed() {
        return this.leashed;
    }

    public int getExp() {
        return this.exp;
    }

    public long getLastRareCandyUsed() {
        return this.lastRareCandyUsed;
    }

    public void setPetType(InventoryPets petType) {
        this.petType = petType;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLastRareCandyUsed(long lastRareCandyUsed) {
        this.lastRareCandyUsed = lastRareCandyUsed;
    }
}
