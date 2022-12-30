package net.astropvp.lootfilter.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.enums.CrateType;
import me.ily2bb.slotbot.CosmicSlotBotAPI;
import net.astropvp.loottable.LootTableAPI;
import net.astropvp.loottable.struct.LootTableType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter

public enum LootTable {
    SPACE("Space Loot", AstroSpaceGrindingAPI.getListItems(MonsterType.SPACE_ALIEN),Material.NETHER_STAR, "&d"),
    SLOTBOT("Slotbot Loot", CosmicSlotBotAPI.getSlotBotItems(),Material.PAPER, "&b"),
    GRINDER("Grinder Key Loot", AstroCrateKeysAPI.getListItems(CrateType.GRINDER),Material.TRIPWIRE_HOOK, "&6"),
    GODLY("Godly Key Loot", AstroCrateKeysAPI.getListItems(CrateType.GODLY),Material.TRIPWIRE_HOOK, "&e"),
    ENVOY("Envoy Loot",  LootTableAPI.getAllLootFromLootTable(LootTableType.ENVOY),Material.FIREWORK, "&b")


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
