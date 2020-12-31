package me.ily2bb.slotbot.objects;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import me.ily2bb.slotbot.utils.LootTable;
import me.ily2bb.slotbot.utils.Utils;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

        for(int var5 = 0; var5 < var4; ++var5) {
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

            cacher = (ItemListCacher)var2.next();
        } while(cacher == null || cacher.getUUID() == null || !cacher.getUUID().equals(u));

        return cacher;
    }

    public ItemListCacher addItem(ItemStack item) {
        this.items.add(item);
        return this;
    }

    public Player getPlayer() {
        Player[] onlinePlayers = Bukkit.getOnlinePlayers();
        int length = Bukkit.getOnlinePlayers().length;

        for(int i = 0; i < length; ++i) {
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
        Iterator var3 = this.items.iterator();

        while(var3.hasNext()) {
            ItemStack item = (ItemStack)var3.next();
            Iterator var5 = broadcastItems.iterator();

            while(var5.hasNext()) {
                ItemStack x = (ItemStack)var5.next();
                if (x.equals(item)) {
                    ++y;
                }
            }
        }

        if (y != 0) {
            Player p = this.getPlayer();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
            StringBuilder spaces = new StringBuilder();
            int size = 40 - p.getName().length();

            for(int x2 = 0; x2 < size; ++x2) {
                spaces.append(" ");
            }

            Bukkit.broadcastMessage(spaces + CC.AquaB + "Cosmic" + CC.WhiteB + "-" + CC.LightPurpleB + "Slot Bot");
            String sentence = "     " + CC.Gray + this.getPlayer().getName() + " rolled the slot /bot with " + CC.White + this.items.size() + " tickets " + CC.Gray + "and won:";
            Bukkit.broadcastMessage(sentence);
            Bukkit.broadcastMessage(spaces + CC.AquaB + "");
            Player[] onlinePlayers = Bukkit.getOnlinePlayers();
            int length = Bukkit.getOnlinePlayers().length;

            for(int l = 0; l < length; ++l) {
                Player pl = onlinePlayers[l];

                for(int i = 0; i < this.items.size(); ++i) {
                    ItemStack k = (ItemStack)this.items.get(i);
                    String itemName = Utils.getDisplayName(k);
                    String line = CC.AquaB + "* " + CC.White + k.getAmount() + "x " + itemName;
                    ItemLinking.sendItemViaJSON(line, k, pl);
                }
            }

            if (caches.contains(this)) {
                caches.remove(this);
            }

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("");
        }
    }
}
