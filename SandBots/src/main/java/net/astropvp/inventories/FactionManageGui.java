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
        this.sandbtsinv = SmartInventory.builder().id("sandbots").provider(this).size(3, 9).manager(SandBots.getInvManager()).title(CC.YellowB + "Available SandBots").build();
    }

    public FactionManageGui open(Player player) {
        this.openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        ItemStack glass = (new ItemBuilder(Material.STAINED_GLASS_PANE)).data((short) 5).name("").build();
        contents.fillBorders(ClickableItem.empty(glass));
    }

    public void update(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        this.panginationItems = new ClickableItem[(int)SandBots.sandBots.stream().filter((c) -> c.getFactionID().equals(FactionUtils.getFactionId(player))).count()];
        AtomicInteger count = new AtomicInteger(0);
        SandBots.sandBots.stream().filter((c) -> c.getFactionID().equals(FactionUtils.getFactionId(player))).forEach((sandBot) -> {
            ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(CC.YellowB + " * " + CC.Green +  "Owner: " + CC.Gray + sandBot.getOwnerName() , CC.YellowB + " * " + CC.Green + "Faction: " + CC.Gray + sandBot.getFactionID(), CC.YellowB + " * " + CC.Green + "Balance: " + CC.Gray + sandBot.getBalance(), CC.YellowB + " * " + CC.Green + "Activated: " + CC.Gray + (sandBot.isActivated() ? "True" : "False"), CC.YellowB + " * " + CC.Green + "Lapis Blocks " + CC.Gray + sandBot.getLapisBlocks().size(), CC.YellowB + " * " + CC.Green + "Location: " + CC.YellowB + "X: " + CC.Gray + sandBot.getLocation().getBlockX() + CC.YellowB + " Y: " + CC.Gray + sandBot.getLocation().getBlockY() + CC.YellowB + " Z: " + CC.Gray + sandBot.getLocation().getBlockZ()), sandBot);
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

    }

    public void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
