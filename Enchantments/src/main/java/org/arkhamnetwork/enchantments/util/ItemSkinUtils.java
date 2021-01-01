package org.arkhamnetwork.enchantments.util;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.entity.Player;

public class ItemSkinUtils {


    public static final boolean hasItemSkin(Player player, String skin) {
        return AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.valueOf(skin));
    }
}
