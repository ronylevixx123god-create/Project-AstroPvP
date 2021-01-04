/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  net.ess3.api.events.PlayerSignSellEvent
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.quests.quests;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.quests.FactionQuests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellItemChallenge extends IncrementableChallenge {
    private Material item;
    private String worldRestriction;
    private String description;

    public SellItemChallenge(Material itemSelling, String worldRestriction, String description, int countNeeded) {
        super(countNeeded);
        this.description = description;
        this.item = itemSelling;
        this.worldRestriction = worldRestriction;
    }

    @Override
    public String getDataKey() {
        return "sold";
    }

    @Override
    public void onQuestEvent(FactionQuests parent, Player player, Faction faction, Object object) {
        /*if (object instanceof PlayerSignSellEvent) {
            if (this.worldRestriction != null && !player.getWorld().getName().equalsIgnoreCase(this.worldRestriction)) {
                return;
            }
            PlayerSignSellEvent event = (PlayerSignSellEvent)object;
            int soldOfOurs = 0;
            ItemStack item = event.getItemSelling();
            if (item != null && item.getType() == this.item) {
                soldOfOurs += item.getAmount();
            }
            if (soldOfOurs > 0) {
                this.incrementChallenge(parent, player, faction, soldOfOurs, object);
            }
        }*/
    }

    @Override
    public String getDescription() {
        return String.format(this.description, this.getCountNeeded());
    }
}

