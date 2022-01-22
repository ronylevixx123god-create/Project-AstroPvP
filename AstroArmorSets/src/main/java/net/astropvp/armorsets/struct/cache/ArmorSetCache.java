package net.astropvp.armorsets.struct.cache;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.heroicArmor.HeroicArmor;
import net.astropvp.core.utils.cache.Cache;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Setter
@Getter
public class ArmorSetCache extends Cache {
    private final Map<ArmorSetType, Integer> currentArmorSets;
    private final Map<ArmorSetType, Integer> currentSetCrystals;
    private int heroicPieces;
    private ArmorSetType currentArmorSet;
    private final Player player;

    public ArmorSetCache(Player player) {
        super("armor_set");
        this.player = player;
        this.currentArmorSets = new HashMap<>();
        this.currentSetCrystals = new HashMap<>();
        this.currentArmorSet = null;
        this.heroicPieces = 0;
    }

    public void addArmorSet(ArmorSetType type) {
        if (!this.currentArmorSets.containsKey(type)) {
            this.currentArmorSets.put(type, 1);
        } else {
            this.currentArmorSets.put(type, this.currentArmorSets.get(type) + 1);
            if (this.currentArmorSets.containsKey(type) && this.currentArmorSets.get(type) == 4) {
                this.currentArmorSet = type;

                for (String s : type.getArmorSet().getArmorEquipMessage()) {
                    this.player.sendMessage(s);
                }
            }
        }

    }

    public void removeArmorSet(ArmorSetType type) {
        if (this.currentArmorSets.containsKey(type)) {
            int amount = this.currentArmorSets.get(type);
            if (amount - 1 == 0) {
                this.currentArmorSets.remove(type);
            } else {
                this.currentArmorSets.put(type, this.currentArmorSets.get(type) - 1);
            }
            if (this.currentArmorSets.containsKey(type) && this.currentArmorSets.get(type) != 4) {
                this.currentArmorSet = null;
            }
        }

    }

    public void addArmorCrystal(ArmorSetType type) {
        if (this.currentSetCrystals.containsKey(type)) {
            this.currentSetCrystals.put(type, this.currentSetCrystals.get(type) + 1);
        } else {
            this.currentSetCrystals.put(type, 1);
        }

    }

    public void removeArmorCrystal(ArmorSetType type) {
        if (this.currentSetCrystals.containsKey(type)) {
            int amount = this.currentSetCrystals.get(type);
            if (amount - 1 == 0) {
                this.currentSetCrystals.remove(type);
            } else {
                this.currentSetCrystals.put(type, this.currentSetCrystals.get(type) - 1);
            }
        }

    }

    public void addHeroicArmor(ItemStack item) {
        if (AstroArmorSetsAPI.isHeroic(item)) {
            this.heroicPieces++;
        }

    }

    public void removeHeroicArmor(ItemStack item) {
        if (!AstroArmorSetsAPI.isHeroic(item)) {
            this.heroicPieces--;
        }

    }

}
