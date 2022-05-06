package me.ifamasssxd.cosmicsky.inventorypets.struct;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.tasks.UndertowFieldTask;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.NonPrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PetException;
import me.ifamasssxd.cosmicsky.inventorypets.exceptions.PrefixedPetException;
import me.ifamasssxd.cosmicsky.inventorypets.utils.SkullUtils;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicPersistentItem;
import me.ifamasssxd.cosmicskycustoms.struct.PlayerInteractable;
import me.kingsavaqe.cagepvp.AstroCagePvP;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
            NBTTagCompoundBuilder builder = this.wrapper.getCompound("cosmicData");
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
            if (this.leashed) {
                lore.add("");
                lore.add(ChatColor.GOLD + ChatColor.BOLD.toString() + "* LEASHED *");
            }
            return pet.getInternalClass().onItemCreate(this.applyItemData((new ItemBuilder(Material.SKULL_ITEM, pet.getItemName(), new String[0])).data((short) 3).lore(lore).build()).setNBTBase("cosmicData", (new NBTTagCompoundBuilder()).setInt("level", ((Number) level).intValue()).setInt("exp", ((Number) exp).intValue()).setString("petType", pet.name()).setLong("lastUsed", ((Number) lastUsed).longValue()).setLong("lastRareCandyUsed", ((Number) lastRareCandyUsed).longValue()).setBoolean("leashed", leashed).get()).setNBTBase("SkullOwner", pet.getBuilder().get()), this).assignIID().build();
        } else {
            return null;
        }
    }


    @SneakyThrows
    public void onPlayerInteractable(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, PlayerInteractEvent playerInteractEvent) throws CosmicException {
        //playerInteractEvent.setCancelled(true);
        //InventoryPetItem preitem = CosmicPersistentItem.constructItem(itemStack, nbtWrapper, InventoryPetItem.class);


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



            if (player.hasMetadata("transEffect")) {
                player.sendMessage(CC.translate("&c&lPET: &cYour pets are silenced by a player's Transcendence Effect."));
                return;
            }

            if (UndertowFieldTask.affected.contains(player)) {
                player.sendMessage(CC.translate("&c&lPET: &cYour pets are silenced by a player's UnderTow Field."));
                return;
            }

            if (AstroCagePvP.getInstance().getImmuneManager().hasImmune(player)) {
                player.sendMessage(CC.translate("&c&lPET: &cYou cannot use pets while you are in immunity."));
                return;
            }

            if (AstroCagePvP.getInstance().getWildPvPManager().hasWildPvP(player) && AstroCagePvP.getInstance().getWildPvPManager().getWildPvP(player).isGrace()) {
                player.sendMessage(CC.translate("&c&lPET: &cYou cannot use pets before the match has started."));
                return;
            }



                if (player.hasMetadata("hazmatBoots")) {
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

            if (!WorldGuardUtils.isPvPEnabled(player.getLocation())) {
                playerInteractEvent.setCancelled(true);
                throw new PrefixedPetException(CC.Red + "You cannot use that pet here (Safezone)!");
            }




            if (player.getItemInHand().getAmount() > 1) {
                playerInteractEvent.setCancelled(true);
                throw new PrefixedPetException(CC.Red + "You cannot use stacked pets!");

            }

            if (type.getInternalClass().onPetItemInteract(player, itemStack, item, playerInteractEvent)) {





                /*NBTWrapper wrapper = new NBTWrapper(item.build());

                NBTTagCompoundBuilder cosmicData = wrapper.getCompound("cosmicData");
                cosmicData.setLong("lastUsed", System.currentTimeMillis());

                nbtWrapper.setNBTBase("cosmicData", cosmicData.get());
                itemStack = wrapper.build();
                ItemStack finalItem = itemStack;*/



                ItemStack finalItem = item.build();

                String skinTexture;
                switch (type){
                    case FEIGN_DEATH:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwODg1MTk1NywKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTg3OWNlY2YyZWQ4YWZkNWI1Y2ZkZmVkNzdhMzRlNzUxNTVlMzI0YzA1MmRjN2Y2NzU1ZTEyOWRiODU1ZTgzNiIKICAgIH0KICB9Cn0=";
                        break;
                    case BANSHEE:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNTcwMDYxOSwKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVmMzdjMjVmMjcyOTZlNTkzMTA0OTY3YzIzY2YwZTA5ZTJiMTRiZjMzNmQxZjI5YTYzYWE4ZTYwMmNiZDIzOSIKICAgIH0KICB9Cn0=";
                        break;
                    case BLOODHOUND:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNzU4NzkwNywKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIwNGZiMTJhOTI1MTE2N2UxYzRmMjRiMjY1NDY3ZDdjYjAzMWJkMjRjNWJkYTI5NDNmNzFlMzNkZmNkOTdmZiIKICAgIH0KICB9Cn0=";
                        break;
                    case WITCHER:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNzY4Mjg2NCwKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWFkNjc1MDZmYmVhZGU3MGI3YjhkODI4NjlkMGFkMTUxMDU5NTg2ZTZkZTQ2NjRmYzA1ZTZiMzNiZDQ1NjlkMiIKICAgIH0KICB9Cn0=";
                        break;
                    case WORLD_DESTROYER:
                        skinTexture="eyJ0aW1lc3RhbXAiOjE1OTQ2OTg0NjYxMzYsInByb2ZpbGVJZCI6IjNmNjUzNTNiODlhMzQ5MzE4Nzc1MWI1NzM0YTA4NTUxIiwicHJvZmlsZU5hbWUiOiJIYWthR2FtYmEiLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBiZTY1MTlkYjI0MjFmZTY2MmFkNWFhZmM1YjljMmM0NmM2ZGM5NGIwNjdlODNjNjA0MWEzMTg1MzZiN2Q2ZCJ9fX0=";
                        break;
                    case STITCHES:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNzk3NTg5MywKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjFiMWQxZjk3MzlhOTBlMjhjNjhhOGYxMTlkZDU2MzM0ZjIxYzhmMWUxN2MwNDc1OWQ0MzFmNjM0OWVhMWY5MiIKICAgIH0KICB9Cn0=";
                        break;
                    case SKRRT:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNzQ5OTkxOSwKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNThhOGMzODg2YWY5ZGIyZTEwMzQ0MDk3OGIxYjRhNDE1Y2VhODk5ZjUwMDkzOTM5MDNjNDQ0MmE4MTc1ZTAyMCIKICAgIH0KICB9Cn0\n";
                        break;

                    case TITAN:
                        skinTexture="ewogICJ0aW1lc3RhbXAiIDogMTU5NDcwNzQyMjk4MSwKICAicHJvZmlsZUlkIiA6ICIzZjY1MzUzYjg5YTM0OTMxODc3NTFiNTczNGEwODU1MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJIYWthR2FtYmEiLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjQ1MzI1N2Q4MjdhYTA1YWVhZjU0NjVkMjI5ODM5OTJiYjg1NjU3MDNkYzNjOGY1NjkwMDVjYjI1M2UzYzNkMyIKICAgIH0KICB9Cn0";
                        break;
                    default:
                        skinTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVlOWU2NTY3NDk2NzVlNTYzZGE3MTM1N2MxZjg0OTY3MjQ0YzQyMDUwNDY5NzI5M2NlODc4Y2RiOGEzNiJ9fX0=";
                }
                player.getInventory().setItemInHand(SkullUtils.buildSkullNative(finalItem, skinTexture));
                //player.getInventory().setItemInHand(finalItem);

            }
        }

    }

    public static void printNbt(ItemStack item) {
        System.out.println(CraftItemStack.asNMSCopy(item).getTag().toString());
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
