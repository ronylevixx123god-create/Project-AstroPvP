package me.ily2bb.slotbot.objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.ily2bb.slotbot.utils.LootTable;
import me.ily2bb.slotbot.utils.Utils;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemListCacher {
    public static HashSet caches = new HashSet();
    private List items;
    private UUID player;

    public ItemListCacher(Player player, ItemStack item) {
        (this.items = new ArrayList()).add(item);
        this.player = player.getUniqueId();
        caches.add(this);
    }

    public ItemListCacher(Player player, ItemStack... items) {
        this.items = new ArrayList();
        ItemStack[] var3 = items;
        int var4 = items.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            ItemStack x = var3[var5];
            this.items.add(x);
        }

        this.player = player.getUniqueId();
        caches.add(this);
    }

    public ItemListCacher(Player player, List items) {
        (this.items = new ArrayList()).addAll(items);
        this.player = player.getUniqueId();
        caches.add(this);
    }

    public static ItemListCacher getCache(Player player) {
        UUID u = player.getUniqueId();
        Iterator var2 = caches.iterator();

        ItemListCacher cacher;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            cacher = (ItemListCacher) var2.next();
        } while (cacher == null || cacher.getUUID() == null || !cacher.getUUID().equals(u));

        return cacher;
    }

    public ItemListCacher addItem(ItemStack item) {
        this.items.add(item);
        return this;
    }

    public Player getPlayer() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
        int length = Bukkit.getOnlinePlayers().size();

        for (int i = 0; i < length; ++i) {
            Player player = onlinePlayers[i];
            if (player.getUniqueId().equals(this.player)) {
                return player;
            }
        }

        return null;
    }

    public UUID getUUID() {
        return this.player;
    }

    public List getItems() {
        return this.items;
    }

    public void broadcastMessage() {
        List broadcastItems = LootTable.getBroadcastItems();
        int y = 0;

        for (Object o : this.items) {
            ItemStack item = (ItemStack) o;

            for (Object broadcastItem : broadcastItems) {
                ItemStack x = (ItemStack) broadcastItem;
                if (x.equals(item)) {
                    ++y;
                }
            }
        }

        if (y != 0) {
            Player p = this.getPlayer();
            Bukkit.broadcastMessage("");
            StringBuilder spaces = new StringBuilder();
            int size = 40 - p.getName().length();

            for (int x2 = 0; x2 < size; ++x2) {
                spaces.append(" ");
            }

            Bukkit.broadcastMessage(spaces + CC.GoldB + "Astro" + CC.WhiteB + "-" + CC.YellowB + "Slot Bot");
            String sentence = "     " + CC.Gray + this.getPlayer().getName() + " rolled the slot /bot with " + CC.White + this.items.size() + " tickets " + CC.Gray + "and won:";
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(sentence);
            Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[0]);
            int length = Bukkit.getOnlinePlayers().size();

            for (int l = 0; l < length; ++l) {
                Player pl = onlinePlayers[l];

                for (Object item : this.items) {
                    ItemStack k = (ItemStack) item;
                    String itemName = Utils.getDisplayName(k);
                    String line = CC.GoldB + "* " + CC.White + k.getAmount() + "x " + itemName;
                    ItemLinking.sendItemViaJSON(StringUtils.getCenteredMessage(line), k, pl);
                }
            }

            caches.remove(this);

            Bukkit.broadcastMessage("");
        }
    }
}
