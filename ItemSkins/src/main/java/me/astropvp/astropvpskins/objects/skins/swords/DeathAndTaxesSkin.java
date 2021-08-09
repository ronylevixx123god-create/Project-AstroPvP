package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeathAndTaxesSkin extends ItemSkin {
    private HashMap<Player, Double> markList;
    private List<Player> markedPlayers;

    public DeathAndTaxesSkin() {
        markList = new HashMap<>();
        markedPlayers = new ArrayList<>();
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(damaged instanceof Player && Math.random() >= 0.08 && !markedPlayers.contains(damaged)) {
            damager.sendMessage(CC.GoldB + " ** MARKED " + damaged.getName() + " WITH DEATH AND TAXES **");
            damaged.sendMessage(CC.GoldB + " ** DEATH AND TAXES MARK " + CC.Gray + "[" + damager.getName() + "]" + CC.GoldB + " **");
            markedPlayers.add((Player) damaged);
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                markedPlayers.remove(damaged);
                ((Player) damaged).damage(markList.get(damaged));
                markList.remove(damaged);
                damager.sendMessage(CC.GoldB + " ** MARKED DEALT " + Math.round(markList.get(damaged)) + " **");
                damaged.sendMessage(CC.GoldB + " ** DEATH AND TAXES DEALT " + CC.Gray + "[" + Math.round(markList.get(damaged)) + "]" + CC.GoldB + " **");
            }, 120L);
        }
        if(damaged instanceof Player && markedPlayers.contains(damaged)) {
            markList.put((Player) damaged, markList.get(damaged) + event.getDamage());
        }
    }
}
