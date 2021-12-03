package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
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

    public DeathAndTaxesSkin() {
        this.markList = new HashMap<>();
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(damaged instanceof Player && this.markList.containsKey(damaged) && !event.isCancelled())
            this.markList.put((Player) damaged, this.markList.get(damaged) + event.getDamage());
        if(damaged instanceof Player && Math.random() <= 0.08 && !markList.containsKey(damaged)) {
            damager.sendMessage(CC.GoldB + " ** MARKED " + damaged.getName() + " WITH DEATH AND TAXES **");
            damaged.sendMessage(CC.GoldB + " ** DEATH AND TAXES MARK " + CC.Gray + "[" + damager.getName() + "]" + CC.GoldB + " **");
            this.markList.put((Player) damaged, 0.0);
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                if(!WorldGuardUtils.isPvPEnabled(damaged.getLocation()))
                    return;
                ((Player) damaged).damage(markList.get(damaged)/4);
                damager.sendMessage(CC.GoldB + " ** MARKED DEALT " + Math.round(markList.get(damaged)/4) + "HP **");
                damaged.sendMessage(CC.GoldB + " ** DEATH AND TAXES DEALT " + CC.Gray + "[" + Math.round(markList.get(damaged)/4) + "]" + CC.GoldB + " **");
                this.markList.remove(damaged);
            }, 120L);
        }
    }
}
