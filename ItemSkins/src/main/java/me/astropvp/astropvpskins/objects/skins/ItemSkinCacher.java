package me.astropvp.astropvpskins.objects.skins;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.google.common.collect.Maps;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class ItemSkinCacher {
    public static HashSet<ItemSkinCacher> cachers = new HashSet<>();
    private Player player;
    private Map<ArmorSlot, SkinType> skins = Maps.newHashMap();

    public ItemSkinCacher(Player player) {
        this.player = player;
        ItemSkinCacher cacher = getItemCacher(player);
        if (cacher == null) {
            cachers.add(this);
            cacher = this;
        }
        update(player, cacher.getSkinTypes());
    }

    public Player getPlayer() {
        return this.player;
    }

    public Map<ArmorSlot, SkinType> getSkinTypes() {
        return this.skins;
    }

    public static ItemSkinCacher getItemCacher(Player player) {
        for (ItemSkinCacher cacher : cachers) {
            Player p = cacher.getPlayer();
            if (p.equals(player)) {
                return cacher;
            }
        }
        return null;
    }

    public static void update(Player player, Map<ArmorSlot, SkinType> skins) {
        Objects.requireNonNull(player, "Player cannot be null");

        Map<ArmorSlot, SkinType> oldskins = Maps.newHashMap();
        oldskins.putAll(skins);
        skins.clear();

        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = player.getInventory().getArmorContents()).length - 1 ; i >= 0; i--) {
            ItemStack item = arrayOfItemStack[i];
            if (item != null && item.getType() != Material.AIR) {


                SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(item);
                if (type != null) {


                    ItemSkin skin = type.getItemSkin();
                    boolean isContained = oldskins.containsValue(type);

                    ArmorSlot slot = ArmorSlot.getArmorType(item);
                    SkinType old_skin = skins.getOrDefault(slot, null);
                    if (old_skin != null && old_skin != type) {
                        type.getItemSkin().onUnequip(player);
                    } else {
                        skins.put(slot, type);

                        if (!isContained) {
                            skin.onEquip(player);
                        } else {
                            skin.onUpdate(player);
                        }
                    }
                }
            }
        }
        if (skins.size() != oldskins.size()) {
            for (SkinType type : oldskins.values()) {
                if (!skins.containsValue(type)) {
                    type.getItemSkin().onUnequip(player);
                }
            }
        }
        ItemSkinCacher cacher = getItemCacher(player);
        if (skins.size() == 0 && cacher != null)
            cachers.remove(cacher);
    }

    public static void removePlayer(Player player) {
        Iterator<ItemSkinCacher> iterator = cachers.iterator();
        while (iterator.hasNext()) {
            ItemSkinCacher cacher = iterator.next();
            if (cacher == null) {
                continue;
            }
            if (cacher.getPlayer().equals(player)) {
                Map<ArmorSlot, SkinType> types = cacher.getSkinTypes();
                for (SkinType type : types.values()) {
                    type.getItemSkin().onUnequip(player);
                }
                iterator.remove();
            }
        }
    }

    public static HashSet<ItemSkinCacher> getCachers() {
        return cachers;
    }
}


