package org.arkhamnetwork.enchantments.mastery;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.List;

public class OpeningCombo extends MasteryCustomEnchantment {
    public OpeningCombo() {
        super("Opening Combo", GeneralUtil.weapons, 10);
        this.max = 5;
        activePlayers = new HashMap<>();
    }

    private static List<Player> openingComboCooldowns;

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
    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) throws Exception {
        if(activePlayers.containsKey(user)) {
            event.setDamage(event.getDamage() * ((2 * enchantLevel)/100) + 1);
            target.sendMessage(CC.RedB + "OPENING COMBO - " + user.getName() + " Dealt " + 2*enchantLevel + "% More Damage");
        }
    }

    public static boolean isOnCooldown(Player player) {
        return openingComboCooldowns.contains(player);
    }
}
