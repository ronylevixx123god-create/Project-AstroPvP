package org.arkhamnetwork.enchantments.util;

import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class RageUtils {
    public static int getComboOfPlayer(Player player, boolean human) {
        if (human) {
            return player.hasMetadata("combo_pvp") ? player.getMetadata("combo_pvp").get(0).asInt() : 0;
        }
        return player.hasMetadata("combo_pve") ? player.getMetadata("combo_pve").get(0).asInt() : 0;
    }

    public static void setPlayerCombo(Player player, int combo, boolean human) {
        if (human) {
            if (combo <= 0) {
                if (player.hasMetadata("combo_pvp")) {
                    player.removeMetadata("combo_pvp", Enchantments.getInstance());
                }
                if (player.hasMetadata("last_combo_pvp")) {
                    player.removeMetadata("last_combo_pvp", Enchantments.getInstance());
                }
                return;
            }
            player.setMetadata("combo_pvp", new FixedMetadataValue(Enchantments.getInstance(), combo));
        } else {
            if (combo <= 0) {
                if (player.hasMetadata("combo_pve")) {
                    player.removeMetadata("combo_pve", Enchantments.getInstance());
                }
                if (player.hasMetadata("last_combo_pve")) {
                    player.removeMetadata("last_combo_pve", Enchantments.getInstance());
                }
                return;
            }
            player.setMetadata("combo_pve", new FixedMetadataValue(Enchantments.getInstance(), combo));
        }
    }

    public static void clearPlayerCombo(Player player, boolean human) {
        setPlayerCombo(player, 0, human);
    }
}
