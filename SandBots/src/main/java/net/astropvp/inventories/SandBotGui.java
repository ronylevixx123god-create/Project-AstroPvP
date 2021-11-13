package net.astropvp.inventories;

import com.cosmicpvp.cosmicutils.utils.CC;
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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SandBotGui extends MenuManager implements InventoryProvider {
    private SmartInventory sandbtsinv;

    private SandBots plugin;

    private SandBotManager bot;

    public SandBotGui(SandBots plugin, SandBotManager bot) {
        this.plugin = plugin;
        this.bot = bot;
        this

                .sandbtsinv = SmartInventory.builder().id("mainInv").provider(this).size(5, 9).manager(SandBots.getInvManager()).title(CC.YellowB + "SandBot").build();
    }

    public SandBotGui open(Player player) {
        openPlayer(player);
        return this;
    }

    public void init(Player player, InventoryContents contents) {
        contents.newIterator("iter1", SlotIterator.Type.HORIZONTAL, 0, 0);
        contents.newIterator("iter2", SlotIterator.Type.HORIZONTAL, 4, 8);
        ItemStack glass = (new ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 4).name("").build());
        ItemStack pinkglass = (new ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 5).name("").build());
        ItemStack purpleglass = (new ItemBuilder(Material.STAINED_GLASS_PANE).data((short) 5).name("").build());
        int[] pinkidc = { 1, 3, 5, 7, 1, 3, 5, 7 };
        int[] pinkidr = { 1, 1, 1, 1, 3, 3, 3, 3 };
        int[] purplec = {
                0, 2, 4, 6, 8, 0, 2, 4, 6, 8,
                0, 2, 4, 6, 8 };
        int[] purpler = {
                1, 1, 1, 1, 1, 2, 2, 2, 2, 2,
                3, 3, 3, 3, 3 };
        int i;
        for (i = 0; i < pinkidc.length; i++) {
            SlotPos slot = new SlotPos(pinkidr[i], pinkidc[i]);
            contents.set(slot, ClickableItem.empty(pinkglass));
        }
        for (i = 0; i < purplec.length; i++) {
            SlotPos slot = new SlotPos(purpler[i], purplec[i]);
            contents.set(slot, ClickableItem.empty(purpleglass));
        }
        contents.fillRow(0, ClickableItem.empty(glass));
        contents.fillRow(4, ClickableItem.empty(glass));
        ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(CC.YellowB + " * " + CC.Green +  "Owner: " + CC.Gray + bot.getOwnerName() , CC.YellowB + " * " + CC.Green + "Faction: " + CC.Gray + bot.getFactionID(), CC.YellowB + " * " + CC.Green + "Balance: " + CC.Gray + bot.getBalance(), CC.YellowB + " * " + CC.Green + "Activated: " + CC.Gray + (bot.isActivated() ? "True" : "False"), CC.YellowB + " * " + CC.Green + "Lapis Blocks " + CC.Gray + bot.getLapisBlocks(), CC.GreenB + " * " + CC.Green + "Location: " + CC.YellowB + "X: " + CC.Gray + bot.getLocation().getBlockX() + CC.YellowB + " Y: " + CC.Gray + bot.getLocation().getBlockY() + CC.YellowB + " Z: " + CC.Gray + bot.getLocation().getBlockZ()), bot);
        contents.set(2, 1, ClickableItem.empty(mainhead));
        updateGold(player, contents, this.bot);
        ItemStack remove = Utils.botItem(CC.RedB + "Remove", Lists.newArrayList(CC.Gray + "Click here to remove the bot."), this.bot, Skull.getCustomSkull("http://textures.minecraft.net/texture/" + "beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7"));
        contents.set(2, 7, ClickableItem.of(remove, e -> {
            Player invplayer = (Player)e.getWhoClicked();
            if (e.isLeftClick() || e.isRightClick())
                if (SandBots.botGuis.containsKey(this.bot)) {
                    ((RemoveGUI)((ConcurrentHashMap)SandBots.botGuis.get(this.bot)).get(GuiType.REMOVEGUI)).open(invplayer);
                } else {
                    player.closeInventory();
                }
        }));
        updateCore(player, contents, this.bot);
    }

    public void update(Player player, InventoryContents contents) {
        int state = contents.property("state", Integer.valueOf(0));
        contents.setProperty("state", Integer.valueOf(state + 1));
        ItemStack glasstmp = (new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").build());
        if (state % 5 == 0) {
            updateCore(player, contents, this.bot);
            updateGold(player, contents, this.bot);
            ItemStack mainhead = Utils.botItem(CC.YellowB + "SandBot", Lists.newArrayList(CC.YellowB + " * " + CC.Green +  "Owner: " + CC.Gray + bot.getOwnerName() , CC.YellowB + " * " + CC.Green + "Faction: " + CC.Gray + bot.getFactionID(), CC.YellowB + " * " + CC.Green + "Balance: " + CC.Gray + bot.getBalance(), CC.YellowB + " * " + CC.Green + "Activated: " + CC.Gray + (bot.isActivated() ? "True" : "False"), CC.YellowB + " * " + CC.Green + "Lapis Blocks " + CC.Gray + bot.getLapisBlocks().size(), CC.YellowB + " * " + CC.Green + "Location: " + CC.YellowB + "X: " + CC.Gray + bot.getLocation().getBlockX() + CC.YellowB + " Y: " + CC.Gray + bot.getLocation().getBlockY() + CC.YellowB + " Z: " + CC.Gray + bot.getLocation().getBlockZ()), bot);
            contents.set(2, 1, ClickableItem.empty(mainhead));
        }
        if (state % 3 == 0 && state <= 27) {
            if (state == 27) {
                contents.set(new SlotPos(4, 0), ClickableItem.empty(glasstmp));
                contents.set(new SlotPos(0, 8), ClickableItem.empty(glasstmp));
                return;
            }
            SlotIterator iter = contents.iterator("iter1").get();
            SlotIterator iter2 = contents.iterator("iter2").get();
            iter.set(ClickableItem.empty(glasstmp)).next();
            iter2.set(ClickableItem.empty(glasstmp)).previous();
        }
    }

    private void updateGold(Player player, InventoryContents contents, SandBotManager bot) {
        ItemStack gold = Utils.botItem(CC.YellowB + "Balance", Lists.newArrayList(CC.translate("&7Click to add balance."), CC.translate(""), CC.YellowB + " * " + CC.Green + "Balance: " + CC.Gray + "$" + bot.getBalance()), bot, Skull.getCustomSkull("http://textures.minecraft.net/texture/" + "54bf893fc6defad218f7836efefbe636f1c2cc1bb650c82fccd99f2c1ee6"));
        contents.set(2, 5, ClickableItem.of(gold, e -> {
            Player invplayer = (Player)e.getWhoClicked();
            double balanceplayer = CosmicGUIShop.getEconomy().getBalance((OfflinePlayer)player);
            double balancebot = bot.getBalance();
            double howmanytotake = 100000;
            if (SandBots.botGuis.containsKey(bot)) {
                if (e.isLeftClick()) {
                    if (balanceplayer >= howmanytotake) {
                        CosmicGUIShop.getEconomy().withdrawPlayer((OfflinePlayer)invplayer, howmanytotake);
                        bot.addBalance(howmanytotake);
                        updateGold(invplayer, contents, bot);
                    } else {
                        invplayer.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have sufficient funds.");
                    }
                } else if (e.isRightClick()) {
                    if (balancebot > 0.0D) {
                        CosmicGUIShop.getEconomy().depositPlayer((OfflinePlayer)invplayer, balancebot);
                        invplayer.sendMessage(Utils.color("&8&l[&2&l!&8&l] &7You have received &6%amount%$&7.".replace("%amount%", bot.getBalance().toString())));
                        bot.removeBalance(bot.getBalance());
                        updateGold(invplayer, contents, bot);
                    } else {
                        invplayer.sendMessage(CC.RedB + "(!) " + CC.Red + "No balance to return");
                    }
                }
            } else {
                invplayer.closeInventory();
            }
        }));
    }

    private void updateCore(Player player, InventoryContents contents, SandBotManager bot) {
        ItemStack core = Utils.botItem(CC.YellowB + "Toggle", Lists.newArrayList(CC.Gray + "Click to toggle the bot.", "", CC.Green + "Enabled: " + CC.Gray + (bot.isActivated() ? "True" : "False")), bot, Skull.getCustomSkull("http://textures.minecraft.net/texture/" + "32fa8f38c7b22096619c3a6d6498b405530e48d5d4f91e2aacea578844d5c67"));
        contents.set(2, 3, ClickableItem.of(core, e -> {
            if (e.isLeftClick() || e.isRightClick()) {
                Player invplayer = (Player)e.getWhoClicked();
                if (bot.isActivated()) {
                    bot.setActivated(false);
                    bot.setAnimate(false);
                } else {
                    bot.setActivated(true);
                    bot.setAnimate(true);
                }
                updateCore(invplayer, contents, bot);
            }
        }));
    }

    void openPlayer(Player player) {
        this.sandbtsinv.open(player);
    }
}
