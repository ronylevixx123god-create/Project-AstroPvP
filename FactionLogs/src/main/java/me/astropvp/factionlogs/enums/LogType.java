package me.astropvp.factionlogs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public enum LogType {

    SPAWNER_PLACE("Spawner Place", Material.MOB_SPAWNER),
    SPAWNER_BREAK("Spawner Break", Material.MOB_SPAWNER),
    RELATION_EDITS("Relation Edits", Material.DIAMOND_CHESTPLATE),
    FACTION_RENAME("Relation Edits", Material.NAME_TAG),
    PLAYER_JOIN_LEAVE_KICK("Relation Edits", Material.WOOD_DOOR),
    CLAIM_EDITS("Claim Edits", Material.GRASS);


    private final String displayName;
    private final Material guiMaterial;

}
