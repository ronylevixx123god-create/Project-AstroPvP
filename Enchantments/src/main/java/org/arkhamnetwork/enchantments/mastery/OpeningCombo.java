package org.arkhamnetwork.enchantments.mastery;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.ItemSkinType;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpeningCombo extends MasteryCustomEnchantment {
    private static List<Player> openingComboCooldowns;

    public OpeningCombo() {
        super("Opening Combo", GeneralUtil.weapons, 10);
        this.max = 5;
        activePlayers = new HashMap<>();
        openingComboCooldowns = new ArrayList<>();
    }


    private static HashMap<Player, Integer> activePlayers;

    public static void proc(Player player, int level) {
        activePlayers.put(player, level);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
            activePlayers.remove(player);
        }, 100L);
        openingComboCooldowns.add(player);
        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
            openingComboCooldowns.remove(player);
        }, 1200L);
    }

    @Override
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(user instanceof Player)) return;
        if (!(target instanceof Player)) return;

        if (activePlayers.containsKey((Player)user)) {
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_MUTATED_HELMET)) return;
            event.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,event.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE)*1.1);

            target.sendMessage(CC.translate( "&4&l** OPENING COMBO &4- &7" + user.getName() + " &4Dealt &7" + 2 * enchantLevel + " &4% More Damage &4&l**"));
        }
    }

    public static boolean isOnCooldown(Player player) {
        return openingComboCooldowns.contains(player);
    }
}
