package net.astropvp.lootfilter.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.enums.CrateType;
import me.ifamasssxd.cosmicguishop.utils.SlotUtils;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter

public enum LootTable {
    SPACE("Space Loot", AstroSpaceGrindingAPI.getListItems(MonsterType.SPACE_ALIEN),Material.NETHER_STAR, "&d"),
    SLOTBOT("Slotbot Loot", CosmicSlotBotAPI.getSlotBotItems(),Material.PAPER, "&b"),
    ASTRO("Astro Key Loot", AstroCrateKeysAPI.getListItems(CrateType.ASTRO),Material.TRIPWIRE_HOOK, "&a"),
    LEGENDARY("Legendary Key Loot", AstroCrateKeysAPI.getListItems(CrateType.LEGENDARY),Material.TRIPWIRE_HOOK, "&6"),
    GODLY("Godly Key Loot", AstroCrateKeysAPI.getListItems(CrateType.GODLY),Material.TRIPWIRE_HOOK, "&4"),
    MUTATED("Mutated Key Loot", AstroCrateKeysAPI.getListItems(CrateType.MUTATED),Material.TRIPWIRE_HOOK, "&2"),
    SEASON("Summer Key Loot", AstroCrateKeysAPI.getListItems(CrateType.SEASON),Material.TRIPWIRE_HOOK, "&e"),
    ;
    @Setter
    public String fancyName;
    @Setter
    public  List<ItemStack> loot;
    @Setter
    public  Material guiItem;
    @Setter
    public  String colorCode;
}
