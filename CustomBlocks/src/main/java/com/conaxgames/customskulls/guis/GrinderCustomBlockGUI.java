package com.conaxgames.customskulls.guis;

import com.conaxgames.customskulls.CoreSkulls;
import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.SaveCustomBlock;
import com.conaxgames.customskulls.struct.MobGrinderCache;
import com.conaxgames.customskulls.util.CC;
import com.conaxgames.customskulls.util.inventoryui.TimeUtil;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import de.inventivegames.particle.ParticleEffect;
import lombok.Getter;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.GuiItem;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
public class GrinderCustomBlockGUI {
    private final PaginatedGui customBlockGui;
    private final ItemStack astroKey = CrateKeyItem.getItem(CrateType.ASTRO);
    private final ItemStack legKey = CrateKeyItem.getItem(CrateType.LEGENDARY);
    private final ItemStack godlyKey = CrateKeyItem.getItem(CrateType.GODLY);


    public GrinderCustomBlockGUI(AbilityData data, Block block) {
        MobGrinderCache mobGrinderCache = CoreSkulls.instance.getGrinderBlocks().get(data.getUuid());
        int level = data.getLevel();
        int price = 0;
        if (level == 0) {
            price = 100000000;
        } else if (level == 1) {
            price = 500000000;
        } else if (level == 2) {
            price = 1000000000;
        }

        int time = 3600000;
        if (level == 1) {
            time = 4500000;
        } else if (level == 2) {
            time = 5850000;
        } else if (level == 3) {
            time = 7200000;
        }

        this.customBlockGui = new PaginatedGui(5, 27, "Grinder Custom Block Menu");
        ItemStack activateButton = new ItemBuilder(Material.STONE_BUTTON, CC.translate("&a&lActivate Button"))
                .addLoreLine("")
                .addLoreLine(CC.translate("&f&l● &6Expires in: &f" + ((data.getLastActivated() + time) - System.currentTimeMillis() <= 0 ? "✗" : TimeUtil.millisToRoundedTime((data.getLastActivated() + 3600000) - System.currentTimeMillis()))))
                .addLoreLine(CC.translate("&f&l● &6Status: " + (data.isActive() ? "&a&lACTIVE" : "&c&lINACTIVE")))
                .addLoreLine(CC.translate("&f&l● &6Price: &e$" + NumberUtils.formatMoney(data.getAbilityType().getPricePerAction())))
                .addLoreLine("")
                .addLoreLine(CC.translate("&7Click to activate this custom block"))
                .build();


        this.customBlockGui.setDefaultClickAction(event -> event.setCancelled(true));

        this.customBlockGui.setItem(3, 4, new GuiItem(CoreSkulls.customBlocks.get(data.getAbilityType().getName()).getCustomBlockItem()));

        this.customBlockGui.setItem(3, 6, new GuiItem(activateButton, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }
            if (EconomyUtils.hasBalance((OfflinePlayer) event.getWhoClicked(), data.getAbilityType().getPricePerAction())) {
                EconomyUtils.withdrawBalance((OfflinePlayer) event.getWhoClicked(), data.getAbilityType().getPricePerAction());
                data.setLastActivated(System.currentTimeMillis());
                SaveCustomBlock.saveCustomBlock((Player) event.getWhoClicked(), data.getAbilityType(), block);
                event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a Reactivated the CustomBlock for 1 hour!"));
                event.getWhoClicked().sendMessage(CC.translate("&c&l- $" + NumberUtils.formatMoney(data.getAbilityType().getPricePerAction())));
                ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.VILLAGER_YES, 20, 20);
                event.getWhoClicked().closeInventory();
                try {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), data.getLocation(), 2, 2, 2, 0.05f, 60);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c You do not have enough money to reactivate this custom block."));
                event.getWhoClicked().closeInventory();
            }

        }));

        //Astro key
        ItemStack astroKey = new ItemBuilder(Material.GOLD_NUGGET, CC.translate("&a&lAstro Crate Keys &8(&7" + mobGrinderCache.getCrateKeys().get(CrateType.ASTRO) + "&8)"), CC.translate("&7Click to redeem the astro crate keys")).build();
        this.customBlockGui.setItem(2, 2, new GuiItem(astroKey, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }
            int astroKeys = mobGrinderCache.getCrateKeys().get(CrateType.ASTRO);
            if (astroKeys == 0) return;
            int givenKeys = 0;
            int amountToGive;
            if (event.getWhoClicked().getInventory().firstEmpty() != -1) {
                int space = getEmptySlots((Player) event.getWhoClicked()) * 64;
                amountToGive = Math.min(astroKeys, space);
                while (givenKeys != amountToGive) {
                    event.getWhoClicked().getInventory().addItem(this.astroKey);
                    givenKeys++;
                }
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please clear some inventory space."));
                return;
            }


            event.getWhoClicked().sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(givenKeys) + " Astro &f&lCrate Keys"));
            mobGrinderCache.getCrateKeys().put(CrateType.ASTRO, astroKeys - givenKeys);
            GrinderCustomBlockGUI grinderCustomBlockGUI = new GrinderCustomBlockGUI(data, block);
            grinderCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());

        }));

        //Legendary key
        ItemStack legKey = new ItemBuilder(Material.GOLD_NUGGET, CC.translate("&6&lLegendary Crate Keys &8(&7" + mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY) + "&8)"), CC.translate("&7Click to redeem the legendary crate keys")).build();
        this.customBlockGui.setItem(3, 2, new GuiItem(legKey, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }
            int legKeys = mobGrinderCache.getCrateKeys().get(CrateType.LEGENDARY);
            if (legKeys == 0) return;
            int givenKeys = 0;
            int amountToGive;
            if (event.getWhoClicked().getInventory().firstEmpty() != -1) {
                int space = getEmptySlots((Player) event.getWhoClicked()) * 64;
                amountToGive = Math.min(legKeys, space);
                while (givenKeys != amountToGive) {
                    event.getWhoClicked().getInventory().addItem(this.legKey);
                    givenKeys++;
                }
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please clear some inventory space."));
                return;
            }


            event.getWhoClicked().sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(givenKeys) + " &6&lLegendary &f&lCrate Keys"));
            mobGrinderCache.getCrateKeys().put(CrateType.LEGENDARY, legKeys - givenKeys);
            GrinderCustomBlockGUI grinderCustomBlockGUI = new GrinderCustomBlockGUI(data, block);
            grinderCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());
        }));

        //Godly key
        ItemStack godlyKey = new ItemBuilder(Material.GOLD_NUGGET, CC.translate("&4&lGodly Crate Keys &8(&7" + mobGrinderCache.getCrateKeys().get(CrateType.GODLY) + "&8)"), CC.translate("&7Click to redeem the godly crate keys")).build();
        this.customBlockGui.setItem(4, 2, new GuiItem(godlyKey, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }
            int godlyKeys = mobGrinderCache.getCrateKeys().get(CrateType.GODLY);
            if (godlyKeys == 0) return;
            int givenKeys = 0;
            int amountToGive;
            if (event.getWhoClicked().getInventory().firstEmpty() != -1) {
                int space = getEmptySlots((Player) event.getWhoClicked()) * 64;
                amountToGive = Math.min(godlyKeys, space);
                while (givenKeys != amountToGive) {
                    event.getWhoClicked().getInventory().addItem(this.godlyKey);
                    givenKeys++;
                }
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please clear some inventory space."));
                return;
            }


            event.getWhoClicked().sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(givenKeys) + " &4&lGodly &f&lCrate Keys"));
            mobGrinderCache.getCrateKeys().put(CrateType.GODLY, godlyKeys - givenKeys);
            GrinderCustomBlockGUI grinderCustomBlockGUI = new GrinderCustomBlockGUI(data, block);
            grinderCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());

        }));

        //XPBottle key
        double xp = mobGrinderCache.getXp();
        ItemStack xpBottle = new ItemBuilder(Material.EXP_BOTTLE, CC.translate("&d&lExp Bottle &8(&7" + (int) xp + "&8)"), CC.translate("&7Click to redeem the experience")).build();
        this.customBlockGui.setItem(4, 8, new GuiItem(xpBottle, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }
            if (xp == 0) return;

            if (event.getWhoClicked().getInventory().firstEmpty() == -1) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please clear some inventory space."));
                return;
            }

            event.getWhoClicked().getInventory().addItem(ExpBottles.getXpBottle((int) xp, "MobGrinder"));

            event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a You have received an expbottle worth " + NumberUtils.formatMoney(xp) + "XP"));
            mobGrinderCache.setXp(0);
            GrinderCustomBlockGUI grinderCustomBlockGUI = new GrinderCustomBlockGUI(data, block);
            grinderCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());
        }));


        //Upgrade
        //XPBottle key

        ItemStack levelItem;
        if (level != 3) {
            levelItem = new ItemBuilder(Material.FEATHER, CC.translate("&e&lGrinder Block Upgrade &7&nLVL "+ level ))
                    .addLoreLine(CC.translate("&7Click to upgrade this auto mob grinder."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&f&l● &eCurrent Level: &f" + level))
                    .addLoreLine(CC.translate("&8Price to Upgrade: &7$" + NumberUtils.formatMoney(price)))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7Upgrading your grinder block will increase"))
                    .addLoreLine(CC.translate("&7the chance of getting more and better crate"))
                    .addLoreLine(CC.translate("&7keys and increase the grinding duration."))
                    .addLoreLine(CC.translate("&7The max auto mob grinder level is 3."))
                    .build();
        } else {
            levelItem = new ItemBuilder(Material.FEATHER, CC.translate("&e&lGrinder Block Upgrade &7&nLVL "+ level ))
                    .addLoreLine(CC.translate("&7Your auto mob grinder block has reached max level."))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&f&l● &eCurrent Level: &f" + level))
                    .addLoreLine("")
                    .addLoreLine(CC.translate("&7Upgrading your grinder block will increase"))
                    .addLoreLine(CC.translate("&7the chance of getting more and better crate"))
                    .addLoreLine(CC.translate("&7keys and increase the grinding duration."))
                    .addLoreLine(CC.translate("&7The max auto mob grinder level is 3."))

                    .build();
        }
        int finalPrice = price;
        this.customBlockGui.setItem(2, 8, new GuiItem(levelItem, event -> {
            if (CoreSkulls.isSavingGrinderBlocks) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c Please retry in a second, the customblock is saving the data"));
                return;
            }

            if (data.getLevel() == 3) {
                event.getWhoClicked().sendMessage(CC.translate("&c&l(!)&c This grinder customblock has reached the max level of 3."));
                return;
            }


            if (EconomyUtils.hasBalance((OfflinePlayer) event.getWhoClicked(), finalPrice)) {
                EconomyUtils.withdrawBalance((OfflinePlayer) event.getWhoClicked(), finalPrice);
                data.setLevel(data.getLevel() + 1);
                event.getWhoClicked().sendMessage(CC.translate("&c&l- $" + NumberUtils.formatMoney(finalPrice)));
                event.getWhoClicked().sendMessage(CC.translate("&a&l(!)&a You have upgraded your grinder customblock to level " + data.getLevel()));
            } else {
                event.getWhoClicked().sendMessage(CC.translate("&c(!)&c You don't not have enough money to purchase this upgrade."));
            }


            GrinderCustomBlockGUI grinderCustomBlockGUI = new GrinderCustomBlockGUI(data, block);
            grinderCustomBlockGUI.getCustomBlockGui().open(event.getWhoClicked());
        }));


        this.customBlockGui.getFiller().

                fillBorder(new GuiItem(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7)).

                        name(CC.translate("&7")).

                        build()));
    }

    public int getEmptySlots(Player p) {
        PlayerInventory inventory = p.getInventory();
        ItemStack[] cont = inventory.getContents();
        int i = 0;
        for (ItemStack item : cont)
            if (item != null && item.getType() != Material.AIR) {
                i++;
            }
        return 36 - i;
    }


}
