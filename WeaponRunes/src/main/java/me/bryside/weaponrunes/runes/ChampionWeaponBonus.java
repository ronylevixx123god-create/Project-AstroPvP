package me.bryside.weaponrunes.runes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ChampionWeaponBonus extends WeaponBonus {
    public List<String> getLore() {
        return Lists.newArrayList(new String[] { CC.GreenB + " ➥ " + CC.Gray + "50% Chance to block enemy Self Destruct", CC.GreenB + " ➥ " + CC.Gray + "-25% Incoming DMG from Axes", CC.GreenB + " ➥ " + CC.Gray + "Zeus Strike Effect"});
    }

    public WeaponBonusType getType() {
        return WeaponBonusType.CHAMPION;
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Player)) return;
        if (!(damaged instanceof Player)) return;
        if (Math.random() > 0.05) return;
        Random rn = new Random();


        List<Entity> nearbyEnemies =  damager.getNearbyEntities(8, 8, 8);

        for (Entity entity : nearbyEnemies) {
            if (!(entity instanceof Player)) continue;
            Player player = ((Player) entity).getPlayer();
            player.getWorld().strikeLightningEffect(player.getLocation());
            int damage = rn.nextInt(10 - 4 + 1) + 5;
            player.damage(damage);

            player.sendMessage("");
            player.sendMessage(CC.translate("&a&l** ZEUS WEAPON BONUS &7[&f"  + damager.getName() + "&7] **"));
            player.sendMessage(CC.translate("&cZeus Lightning Effect: &l&n" + damage  + "&r &c&lDMG"));
            player.sendMessage("");

            damager.sendMessage("");
            damager.sendMessage(CC.translate("&a&l** ZEUS WEAPON BONUS EFFECT &7[&f" + nearbyEnemies.size() + " Player(s) Struck&7]"));
            damager.sendMessage(CC.translate("&cZeus Lightning Effect: &l&n" + damage  + "&r &c&lDMG"));
            damager.sendMessage("");
        }

    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if (!(damager instanceof Player)) return;
        if (!(damaged instanceof Player)) return;
        if (((Player) damager).getItemInHand().getType() == Material.DIAMOND_AXE || ((Player) damager).getItemInHand().getType() == Material.GOLD_AXE) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.75D);
        }
    }
}
