package me.kingsavaqe.astrotournaments.objects;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astropvpskins.enums.SkinType;
import me.kingsavaqe.astrotournaments.managers.TournamentManager;
import me.kingsavaqe.astrotournaments.utils.ItemBuilder;
import me.kingsavaqe.astrotournaments.utils.ItemUtil;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.heroicArmor.HeroicArmor;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.UUID;

@Getter
@Setter
public class TournamentPlayer {

    private UUID uuid;
    private SkinType helmetSkin;
    private SkinType chestplateSkin;
    private SkinType leggingsSkin;
    private SkinType bootsSkin;
    private SkinType swordSkin;

    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;
    private final ItemStack sword;


    public TournamentPlayer(UUID uuid) {
        this.uuid = uuid;
        this.helmetSkin = null;
        this.chestplateSkin = null;
        this.leggingsSkin = null;
        this.bootsSkin = null;
        this.swordSkin = null;
        this.helmet = getHelmet();
        this.chestplate = getChestplate();
        this.leggings = getLeggings();
        this.boots = getBoots();
        this.sword = getSword();

    }


    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


    public ItemStack getHelmet() {
        ItemStack helmet = EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6&lTournament Helmet")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .setUnbreakable(true)
                .colorize()
                .build(), HeroicArmor.HELMET), getHelmetEnchants()));
        if (helmetSkin != null) {
            helmetSkin.apply(helmet);
        }

        return helmet;

    }

    public ItemStack getChestplate() {
        ItemStack chestplate = EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6&lTournament Chestplate")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .colorize()
                .build(), HeroicArmor.CHESTPLATE), getChestplateEnchants()));
        if (chestplateSkin != null) {
            chestplateSkin.apply(chestplate);
        }

        return chestplate;
    }

    public ItemStack getLeggings() {
        ItemStack leggings = EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6&lTournament Leggings")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .colorize()
                .build(), HeroicArmor.LEGGINGS), getLeggingsEnchants()));

        if (leggingsSkin != null) {
            leggingsSkin.apply(leggings);
        }
        return leggings;
    }

    public ItemStack getBoots() {
        ItemStack boots = EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_BOOTS, "&6&lTournament Boots")
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .setUnbreakable(true)
                .colorize()
                .build(), HeroicArmor.BOOTS), getBootsEnchants()));

        if (bootsSkin != null) {
            bootsSkin.apply(boots);
        }
        return boots;
    }

    public ItemStack getSword() {
        ItemStack sword = EnchantmentBook.applyTransmogScroll(ItemUtil.applyCustomEnchantments(AstroArmorSetsAPI.convertArmorToHeroic(new ItemBuilder(Material.DIAMOND_SWORD, "&6&lTournament Sword")
                .addEnchantment(Enchantment.DAMAGE_ALL, 5)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .addEnchantment(Enchantment.FIRE_ASPECT, 2)
                .setUnbreakable(true)
                .colorize()
                .build(), HeroicArmor.SWORD), getSwordEnchants()));

        if (swordSkin != null) {
            swordSkin.apply(sword);
        }
        return sword;
    }

    public LinkedList<CustomEnchantment> getHelmetEnchants() {
        return new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Alien Implants"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Immortal"), EnchantmentAPI.getEnchantment("Drunk"), EnchantmentAPI.getEnchantment("Death God"), EnchantmentAPI.getEnchantment("Protection"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Poisoned"), EnchantmentAPI.getEnchantment("Frozen"), EnchantmentAPI.getEnchantment("Stormcaller"), EnchantmentAPI.getEnchantment("Smoke Bomb"), EnchantmentAPI.getEnchantment("Ender Shift")));
    }

    public LinkedList<CustomEnchantment> getChestplateEnchants() {
        return new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Holy Aegis"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Planetary Deathbringer"), EnchantmentAPI.getEnchantment("Heroic Enchant Reflect"), EnchantmentAPI.getEnchantment("Godly Overload"), EnchantmentAPI.getEnchantment("Vengeful Diminish"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Blood Lust"), EnchantmentAPI.getEnchantment("Leadership"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Spirit Link"), EnchantmentAPI.getEnchantment("Self Destruct")));
    }

    public LinkedList<CustomEnchantment> getLeggingsEnchants() {
        return new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Mighty Cactus"), EnchantmentAPI.getEnchantment("Holy Aegis"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Nature Wrath"), EnchantmentAPI.getEnchantment("Blood Link"), EnchantmentAPI.getEnchantment("Clarity"), EnchantmentAPI.getEnchantment("Guardians"), EnchantmentAPI.getEnchantment("Obsidianshield"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Dodge"), EnchantmentAPI.getEnchantment("Wither"), EnchantmentAPI.getEnchantment("Plague Carrier")));
    }

    public LinkedList<CustomEnchantment> getBootsEnchants() {
        return new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Paladin Armored"), EnchantmentAPI.getEnchantment("Reinforced Tank"), EnchantmentAPI.getEnchantment("Infinite Luck"), EnchantmentAPI.getEnchantment("Martyr Valor"), EnchantmentAPI.getEnchantment("Guided Rocket Escape"), EnchantmentAPI.getEnchantment("Polymorphic Metaphysical"), EnchantmentAPI.getEnchantment("Ethereal Dodge"), EnchantmentAPI.getEnchantment("Divine Enlighted"), EnchantmentAPI.getEnchantment("Phoenix"), EnchantmentAPI.getEnchantment("Aegis"), EnchantmentAPI.getEnchantment("Ender Walker"), EnchantmentAPI.getEnchantment("Angelic"), EnchantmentAPI.getEnchantment("Undead Ruse"), EnchantmentAPI.getEnchantment("Repair Guard"), EnchantmentAPI.getEnchantment("Frozen"), EnchantmentAPI.getEnchantment("Planetary Deathbringer")));
    }

    public LinkedList<CustomEnchantment> getSwordEnchants() {
        return new LinkedList<>(Arrays.asList(EnchantmentAPI.getEnchantment("Neutralize"), EnchantmentAPI.getEnchantment("Blackout"), EnchantmentAPI.getEnchantment("Permanent Execute"), EnchantmentAPI.getEnchantment("Perfect Solitude"), EnchantmentAPI.getEnchantment("Reality Inversion"), EnchantmentAPI.getEnchantment("Unrestrained Enrage"), EnchantmentAPI.getEnchantment("Reflective Block"), EnchantmentAPI.getEnchantment("Deep Bleed"), EnchantmentAPI.getEnchantment("Bloody Deep Wounds"), EnchantmentAPI.getEnchantment("Shadow Assassin"), EnchantmentAPI.getEnchantment("Divine Immolation"), EnchantmentAPI.getEnchantment("Rage"), EnchantmentAPI.getEnchantment("Silence"), EnchantmentAPI.getEnchantment("Double Strike"), EnchantmentAPI.getEnchantment("Devour"),EnchantmentAPI.getEnchantment("Ice Aspect") ));
    }

}

