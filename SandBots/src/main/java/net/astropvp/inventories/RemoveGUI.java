// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventories;

import com.cryptomorin.xseries.XMaterial;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotIterator;
import fr.minuskube.inv.content.SlotPos;
import java.util.concurrent.ConcurrentHashMap;

import net.astropvp.SandBots;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RemoveGUI extends MenuManager implements InventoryProvider {
    private SmartInventory sandbtsinv;
    private SandBots plugin;
    private SandBot bot;
    FactionHook factionHook;

    public RemoveGUI(SandBots plugin, SandBot bot) {
        this.plugin = plugin;
        this.factionHook = (FactionHook)this.plugin.getHookManager().getPluginMap().get("Factions");
        this.bot = bot;
        this.sandbtsinv = SmartInventory.builder().id("confirm").provider(this).size(3, 9).manager(SandBots.getInvManager()).title(utils.color(plugin.getConfig().getString("confirm-gui-title"))).build();
    }

    public RemoveGUI open(Player player) {
        this.openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        ItemStack purpleglass = (new ItemBuilder(XMaterial.valueOf(this.plugin.getConfig().getString("confirm-gui-glass-fill").toUpperCase()).parseItem())).setName(" ").toItemStack();
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
        ItemStack glass = (new ItemBuilder(XMaterial.valueOf(this.plugin.getConfig().getString("confirm-gui-glass-from").toUpperCase()).parseItem())).setName(" ").toItemStack();
        contents.fillBorders(ClickableItem.empty(glass));
        ItemStack mainhead = utils.botItem(this.plugin.getConfig().getString("bot-profile"), this.plugin.getConfig().getStringList("bot-profile-lore"), this.bot);
        contents.set(1, 4, ClickableItem.empty(mainhead));
        ItemStack accept = utils.botItem(this.plugin.getConfig().getString("bot-confirm-button-name"), this.plugin.getConfig().getStringList("bot-confirm-button-lore"), this.bot, skull.getCustomSkull("http://textures.minecraft.net/texture/" + this.plugin.getConfig().getString("bot-confirm-button-texture")));
        contents.set(1, 2, ClickableItem.of(accept, (e) -> {
            Player invplayer = (Player)e.getWhoClicked();
            if (e.isLeftClick() || e.isRightClick()) {
                if (!this.factionHook.isFactionAdmin(player) && !this.bot.getOwnerUUID().equals(player.getUniqueId()) && !player.hasPermission("SandBots.bypass") && !this.plugin.getConfig().getBoolean("sandbot-fee-destroy")) {
                    invplayer.sendMessage(utils.color(this.plugin.getConfig().getString("not-bot-owner").replace("%botowner%", this.bot.getOwnerName())));
                    invplayer.closeInventory();
                    return;
                }

                if (SandBots.botGuis.containsKey(this.bot)) {
                    ItemStack botdropItem = (new ItemBuilder(((XMaterial)XMaterial.matchXMaterial(this.plugin.getConfig().getString("bot-spawn-item-material")).get()).parseItem())).setColoredName(this.plugin.getConfig().getString("bot-spawn-item-name")).setColoredLore(this.plugin.getConfig().getStringList("bot-spawn-item-lore")).toItemStack();
                    if (this.plugin.getConfig().getBoolean("sandbot-fee-destroy")) {
                        double balanceplayer = SandBots.econ.getBalance(player);
                        double howmanytotake = this.plugin.getConfig().getDouble("sandbot-fee-price");
                        if (balanceplayer >= howmanytotake) {
                            SandBots.econ.withdrawPlayer(player, howmanytotake);
                            utils.dropitem(invplayer, this.bot.getLocation(), botdropItem);
                            invplayer.sendMessage(utils.color(this.plugin.getConfig().getString("sandbot-removed-fee").replace("%fee%", String.valueOf(howmanytotake))));
                            utils.removeBot(this.bot);
                        } else {
                            player.sendMessage(utils.color(this.plugin.getConfig().getString("insufficient-balance-player")));
                        }
                    } else {
                        Double cash = this.bot.getBalance();
                        if (cash > 0.0D) {
                            SandBots.econ.depositPlayer(invplayer, cash);
                            invplayer.sendMessage(utils.color(this.plugin.getConfig().getString("cash-back").replace("%amount%", cash.toString())));
                        }

                        utils.dropitem(invplayer, this.bot.getLocation(), botdropItem);
                        invplayer.sendMessage(utils.color(this.plugin.getConfig().getString("remove-succeed")));
                        utils.removeBot(this.bot);
                    }

                    invplayer.closeInventory();
                } else {
                    invplayer.closeInventory();
                }
            }

        }));
        ItemStack cancel = utils.botItem(this.plugin.getConfig().getString("bot-cancel-button-name"), this.plugin.getConfig().getStringList("bot-cancel-button-lore"), this.bot, skull.getCustomSkull("http://textures.minecraft.net/texture/" + this.plugin.getConfig().getString("bot-cancel-button-texture")));
        contents.set(1, 6, ClickableItem.of(cancel, (e) -> {
            Player invplayer = (Player)e.getWhoClicked();
            if (e.isLeftClick() || e.isRightClick()) {
                if (SandBots.botGuis.containsKey(this.bot) && this.bot.getFactionID().equals(this.factionHook.getFactionId(player))) {
                    ((SandBotGui)((ConcurrentHashMap)SandBots.botGuis.get(this.bot)).get(GuiTypes.SANDBOTGUI)).open(invplayer);
                } else {
                    player.closeInventory();
                }

                invplayer.sendMessage(utils.color(this.plugin.getConfig().getString("remove-canceled")));
            }

        }));
    }

    public void update(Player player, InventoryContents contents) {
        int state = (Integer)contents.property("state", 0);
        contents.setProperty("state", state + 1);
        ItemStack glasstmp = (new ItemBuilder(XMaterial.valueOf(this.plugin.getConfig().getString("confirm-gui-glass-to")).parseItem())).setName(" ").toItemStack();
        if (state % this.plugin.getConfig().getInt("sand-spawn-speed") == 0) {
            ItemStack mainhead = utils.botItem(this.plugin.getConfig().getString("bot-profile"), this.plugin.getConfig().getStringList("bot-profile-lore"), this.bot);
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

    void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
