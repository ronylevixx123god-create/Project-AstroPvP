// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.google.common.collect.Lists;
import net.astropvp.SandBots;
import net.astropvp.enums.GuiType;
import net.astropvp.utils.Utils;
import net.astropvp.utils.inventory.ClickableItem;
import net.astropvp.utils.inventory.SmartInventory;
import net.astropvp.utils.inventory.content.*;
import org.bukkit.Material;
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

    public FactionManageGui(SandBots plugin) {
        this.plugin = plugin;
        this.sandbtsinv = SmartInventory.builder().id("sandbots").provider(this).size(3, 9).manager(SandBots.getInvManager()).title(Utils.color(plugin.getConfig().getString("sandbots-gui-title"))).build();
    }

    public FactionManageGui open(Player player) {
        this.openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        ItemStack glass = (new ItemBuilder(Material.STAINED_GLASS_PANE)).data((short) 3).name(" ").build();
        contents.fillBorders(ClickableItem.empty(glass));
    }

    public void update(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        this.panginationItems = new ClickableItem[(int)SandBots.sandBots.stream().filter((c) -> c.getFactionID().equals(FactionUtils.getFactionId(player))).count()];
        AtomicInteger count = new AtomicInteger(0);
        SandBots.sandBots.stream().filter((c) -> c.getFactionID().equals(FactionUtils.getFactionId(player))).forEach((sandBot) -> {
            ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(CC.translate("&d* &2&lOwner: &5%botowner%"), CC.translate("&d* &2&lFaction &d%botfaction%"), CC.translate("&d* &2&lBalance &d%balance%"), CC.translate("&d* &2&lActivated &d%isActivated%"), CC.translate("&d* &2&lLapisBlocks &d%blocksd%"), CC.translate("&d* &2&lLocation: &eX: &d%botlocationx%&e Y: &d%botlocationy%&e Z: &d%botlocationz%")), sandBot);
            this.panginationItems[count.get()] = ClickableItem.of(mainhead, (e) -> {
                if (e.isLeftClick() || e.isRightClick()) {
                    Player invplayer = (Player)e.getWhoClicked();
                    if (SandBots.botGuis.containsKey(sandBot)) {
                        ((SandBotGui)((ConcurrentHashMap)SandBots.botGuis.get(sandBot)).get(GuiType.SANDBOTGUI)).open(invplayer);
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
        this.nextPageSlot = new SlotPos(24 / 9, 24 % 9);
        ItemStack prev;
        if (this.nextPageSlot.getColumn() <= contents.inventory().getColumns() && this.nextPageSlot.getRow() <= contents.inventory().getRows()) {
            prev = (new ItemBuilder(Material.ARROW).name(CC.translate("&2&lNext Page &e(%page%/%maxPage%)").replace("%page%", String.valueOf(pagination.getPage() + 1)).replace("%maxPage%", String.valueOf((this.panginationItems.length - 1) / 7 + 1))).lore(CC.Green + "Next Page " + CC.Yellow + "(%page%/%maxPage%)", String.valueOf(pagination.getPage() + 1), String.valueOf((this.panginationItems.length - 1) / 7 + 1)).build());
            contents.set(this.nextPageSlot, ClickableItem.of(prev, (e) -> {
                this.sandbtsinv.open(player, pagination.next().getPage());
            }));
        }

        this.prevPageSlot = new SlotPos(20 / 9, 20 % 9);
        if (this.nextPageSlot.getColumn() <= contents.inventory().getColumns() && this.nextPageSlot.getRow() <= contents.inventory().getRows()) {
            prev = (new ItemBuilder(Material.ARROW).name(CC.Red.replace("%page%", String.valueOf(pagination.getPage() + 1)).replace("%maxPage%", String.valueOf((this.panginationItems.length - 1) / 7 + 1))).lore(this.plugin.getConfig().getStringList("sandbots-faction-gui-previous-page-lore")).build());
            contents.set(this.prevPageSlot, ClickableItem.of(prev, (e) -> {
                this.sandbtsinv.open(player, pagination.previous().getPage());
            }));
        }

    }

    public void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
