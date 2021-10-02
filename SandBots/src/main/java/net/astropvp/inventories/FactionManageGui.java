// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.*;
import me.mattstudios.mfgui.gui.components.xseries.XMaterial;
import net.astropvp.SandBots;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FactionManageGui extends MenuManager implements InventoryProvider {
    private SmartInventory sandbtsinv;
    private SandBots plugin;
    private ArrayList<SlotPos> botSlots = new ArrayList();
    private ClickableItem[] panginationItems;
    private SlotPos nextPageSlot;
    private SlotPos prevPageSlot;
    FactionHook factionHook;

    public FactionManageGui(SandBots plugin) {
        this.plugin = plugin;
        this.factionHook = (FactionHook)this.plugin.getHookManager().getPluginMap().get("Factions");
        this.sandbtsinv = SmartInventory.builder().id("sandbots").provider(this).size(3, 9).manager(SandBots.getInvManager()).title(utils.color(plugin.getConfig().getString("sandbots-gui-title"))).build();
    }

    public FactionManageGui open(Player player) {
        this.openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        ItemStack glass = (new ItemBuilder(XMaterial.valueOf(this.plugin.getConfig().getString("sandbots-gui-glass-fill").toUpperCase()).parseItem())).setName(" ").toItemStack();
        contents.fillBorders(ClickableItem.empty(glass));
    }

    public void update(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        this.panginationItems = new ClickableItem[(int)SandBots.sandBots.stream().filter((c) -> {
            return c.getFactionID().equals(this.factionHook.getFactionId(player));
        }).count()];
        AtomicInteger count = new AtomicInteger(0);
        SandBots.sandBots.stream().filter((c) -> {
            return c.getFactionID().equals(this.factionHook.getFactionId(player));
        }).forEach((sandBot) -> {
            ItemStack mainhead = utils.botItem(this.plugin.getConfig().getString("bot-profile"), this.plugin.getConfig().getStringList("bot-profile-lore"), sandBot);
            this.panginationItems[count.get()] = ClickableItem.of(mainhead, (e) -> {
                if (e.isLeftClick() || e.isRightClick()) {
                    Player invplayer = (Player)e.getWhoClicked();
                    if (SandBots.botGuis.containsKey(sandBot)) {
                        ((SandBotGui)((ConcurrentHashMap)SandBots.botGuis.get(sandBot)).get(GuiTypes.SANDBOTGUI)).open(invplayer);
                    } else {
                        player.closeInventory();
                    }
                }

            });
            count.addAndGet(1);
        });
        pagination.setItems(this.panginationItems);
        pagination.setItemsPerPage(7);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));
        this.nextPageSlot = new SlotPos(this.plugin.getConfig().getInt("sandbots-faction-gui-next-page-slot") / 9, this.plugin.getConfig().getInt("sandbots-faction-gui-next-page-slot") % 9);
        ItemStack prev;
        if (XMaterial.matchXMaterial(this.plugin.getConfig().getString("sandbots-faction-gui-next-page-item").toUpperCase()).isPresent() && this.nextPageSlot.getColumn() <= contents.inventory().getColumns() && this.nextPageSlot.getRow() <= contents.inventory().getRows()) {
            prev = (new ItemBuilder(((XMaterial)XMaterial.matchXMaterial(this.plugin.getConfig().getString("sandbots-faction-gui-next-page-item").toUpperCase()).get()).parseItem())).setColoredName(this.plugin.getConfig().getString("sandbots-faction-gui-next-page-name").replace("%page%", String.valueOf(pagination.getPage() + 1)).replace("%maxPage%", String.valueOf((this.panginationItems.length - 1) / 7 + 1))).setColoredPagesLore(this.plugin.getConfig().getStringList("sandbots-faction-gui-next-page-lore"), String.valueOf(pagination.getPage() + 1), String.valueOf((this.panginationItems.length - 1) / 7 + 1)).toItemStack();
            contents.set(this.nextPageSlot, ClickableItem.of(prev, (e) -> {
                this.sandbtsinv.open(player, pagination.next().getPage());
            }));
        }

        this.prevPageSlot = new SlotPos(this.plugin.getConfig().getInt("sandbots-faction-gui-previous-page-slot") / 9, this.plugin.getConfig().getInt("sandbots-faction-gui-previous-page-slot") % 9);
        if (XMaterial.matchXMaterial(this.plugin.getConfig().getString("sandbots-faction-gui-previous-page-item").toUpperCase()).isPresent() && this.nextPageSlot.getColumn() <= contents.inventory().getColumns() && this.nextPageSlot.getRow() <= contents.inventory().getRows()) {
            prev = (new ItemBuilder(((XMaterial)XMaterial.matchXMaterial(this.plugin.getConfig().getString("sandbots-faction-gui-previous-page-item").toUpperCase()).get()).parseItem())).setColoredName(this.plugin.getConfig().getString("sandbots-faction-gui-previous-page-name").replace("%page%", String.valueOf(pagination.getPage() + 1)).replace("%maxPage%", String.valueOf((this.panginationItems.length - 1) / 7 + 1))).setColoredPagesLore(this.plugin.getConfig().getStringList("sandbots-faction-gui-previous-page-lore"), String.valueOf(pagination.getPage() + 1), String.valueOf((this.panginationItems.length - 1) / 7 + 1)).toItemStack();
            contents.set(this.prevPageSlot, ClickableItem.of(prev, (e) -> {
                this.sandbtsinv.open(player, pagination.previous().getPage());
            }));
        }

    }

    public void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
