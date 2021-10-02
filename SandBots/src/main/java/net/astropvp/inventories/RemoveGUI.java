// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import java.util.concurrent.ConcurrentHashMap;

import me.ifamasssxd.cosmicguishop.CosmicGUIShop;
import net.astropvp.SandBots;
import net.astropvp.enums.GuiType;
import net.astropvp.managers.SandBotManager;
import net.astropvp.utils.Utils;
import net.astropvp.utils.inventory.ClickableItem;
import net.astropvp.utils.inventory.SmartInventory;
import net.astropvp.utils.inventory.content.InventoryContents;
import net.astropvp.utils.inventory.content.InventoryProvider;
import net.astropvp.utils.inventory.content.SlotIterator;
import net.astropvp.utils.inventory.content.SlotPos;
import net.astropvp.utils.skull.Skull;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RemoveGUI extends MenuManager implements InventoryProvider {
    private SmartInventory sandbtsinv;
    private SandBots plugin;
    private SandBotManager bot;

    public RemoveGUI(SandBots plugin, SandBotManager bot) {
        this.plugin = plugin;
        this.bot = bot;
        this.sandbtsinv = SmartInventory.builder().id("confirm").provider(this).size(3, 9).manager(SandBots.getInvManager()).title(Utils.color(plugin.getConfig().getString("confirm-gui-title"))).build();
    }

    public RemoveGUI open(Player player) {
        this.openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        ItemStack purpleglass = (new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").data((short)1).build());
        int[] purplec = new int[]{1, 3, 5, 7};
        int[] purpler = new int[]{1, 1, 1, 1};

        for(int i = 0; i < purplec.length; ++i) {
            SlotPos slot = new SlotPos(purpler[i], purplec[i]);
            contents.set(slot, ClickableItem.empty(purpleglass));
        }

        contents.newIterator("iter1", SlotIterator.Type.HORIZONTAL, 0, 4);
        contents.newIterator("iter2", SlotIterator.Type.HORIZONTAL, 2, 4);
        contents.newIterator("iter3", SlotIterator.Type.HORIZONTAL, 2, 4);
        contents.newIterator("iter4", SlotIterator.Type.HORIZONTAL, 0, 4);
        ItemStack glass = (new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").build());
        contents.fillBorders(ClickableItem.empty(glass));
        ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(CC.translate("&d* &2&lOwner: &5%botowner%"), CC.translate("&d* &2&lFaction &d%botfaction%"), CC.translate("&d* &2&lBalance &d%balance%"), CC.translate("&d* &2&lActivated &d%isActivated%"), CC.translate("&d* &2&lLapisBlocks &d%blocksd%"), CC.translate("&d* &2&lLocation: &eX: &d%botlocationx%&e Y: &d%botlocationy%&e Z: &d%botlocationz%")), this.bot);
        contents.set(1, 4, ClickableItem.empty(mainhead));
        ItemStack accept = Utils.botItem(CC.GreenB + "Confirm", Lists.newArrayList(CC.Gray + "Click here to cancel the process."), this.bot, Skull.getCustomSkull("http://textures.minecraft.net/texture/" + "a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6"));
        contents.set(1, 2, ClickableItem.of(accept, (e) -> {
            Player invplayer = (Player)e.getWhoClicked();
            if (e.isLeftClick() || e.isRightClick()) {
                if (SandBots.botGuis.containsKey(this.bot)) {
                    ItemStack botdropItem = (new ItemBuilder(Material.MONSTER_EGG).name(CC.YellowB + "Sandbot").lore(this.plugin.getConfig().getStringList("bot-spawn-item-lore")).build());
                        double balanceplayer = CosmicGUIShop.getEconomy().getBalance(player);
                        double howmanytotake = 2000;
                        if (balanceplayer >= howmanytotake) {
                            CosmicGUIShop.getEconomy().withdrawPlayer(player, howmanytotake);
                            Utils.dropitem(invplayer, this.bot.getLocation(), botdropItem);
                            invplayer.sendMessage(Utils.color("&5SandBot removed successfully you paid %fee% fees.".replace("%fee%", String.valueOf(howmanytotake))));
                            Utils.removeBot(this.bot);
                        } else {
                            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have the sufficient funds for this.");
                        }

                    invplayer.closeInventory();
                } else {
                    invplayer.closeInventory();
                }
            }

        }));
        ItemStack cancel = Utils.botItem(CC.RedB + "Cancel", Lists.newArrayList(CC.Gray + "Click here to cancel the process."), this.bot, Skull.getCustomSkull("http://textures.minecraft.net/texture/" + "beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7"));
        contents.set(1, 6, ClickableItem.of(cancel, (e) -> {
            Player invplayer = (Player)e.getWhoClicked();
            if (e.isLeftClick() || e.isRightClick()) {
                if (SandBots.botGuis.containsKey(this.bot) && this.bot.getFactionID().equals(FactionUtils.getFactionId(player))) {
                    ((SandBotGui)((ConcurrentHashMap)SandBots.botGuis.get(this.bot)).get(GuiType.SANDBOTGUI)).open(invplayer);
                } else {
                    player.closeInventory();
                }

                invplayer.sendMessage(Utils.color("&8&l[&2&l!&8&l] &7Remove canceled successfully."));
            }

        }));
    }

    public void update(Player player, InventoryContents contents) {
        int state = (Integer)contents.property("state", 0);
        contents.setProperty("state", state + 1);
        ItemStack glasstmp = (new ItemBuilder(Material.STAINED_GLASS_PANE).name("").data((short) 1).build());
        if (state % 5 == 0) {
            ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(), this.bot);
            contents.set(1, 4, ClickableItem.empty(mainhead));
        }

        if (state % 5 == 0 && state <= 30) {
            if (state == 25) {
                contents.set(new SlotPos(0, 0), ClickableItem.empty(glasstmp));
                contents.set(new SlotPos(2, 0), ClickableItem.empty(glasstmp));
                contents.set(new SlotPos(0, 8), ClickableItem.empty(glasstmp));
                contents.set(new SlotPos(2, 8), ClickableItem.empty(glasstmp));
                return;
            }

            if (state == 30) {
                contents.set(new SlotPos(1, 0), ClickableItem.empty(glasstmp));
                contents.set(new SlotPos(1, 8), ClickableItem.empty(glasstmp));
                return;
            }

            SlotIterator iter = (SlotIterator)contents.iterator("iter1").get();
            SlotIterator iter2 = (SlotIterator)contents.iterator("iter2").get();
            SlotIterator iter3 = (SlotIterator)contents.iterator("iter3").get();
            SlotIterator iter4 = (SlotIterator)contents.iterator("iter4").get();
            iter.set(ClickableItem.empty(glasstmp)).next();
            iter2.set(ClickableItem.empty(glasstmp)).next();
            iter3.set(ClickableItem.empty(glasstmp)).previous();
            iter4.set(ClickableItem.empty(glasstmp)).previous();
        }

    }

    public void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
