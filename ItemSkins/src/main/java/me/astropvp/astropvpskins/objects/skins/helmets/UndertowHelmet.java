package me.astropvp.astropvpskins.objects.skins.helmets;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import me.astropvp.astropvpskins.tasks.UndertowFieldTask;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class UndertowHelmet extends ItemSkin {

    public static List<Player> undertowField;
    public UndertowHelmet() {
        undertowField = new ArrayList<>();
    }

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if (damager.hasMetadata("frozenSkrrt")) return;
        if (damaged.hasMetadata("frozenSkrrt")) return;

        if (damaged instanceof Player) {
            if (UndertowFieldTask.affected.contains(damaged)) {
                event.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,event.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE)*1.05);
            }
        }
    }

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(damaged.getLocation()) || !WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if (damager.hasMetadata("frozenSkrrt")) return;
        if (damaged.hasMetadata("frozenSkrrt")) return;

        if (Math.random() < 0.015) {
            this.undertowField.add(damaged);
            damaged.sendMessage(CC.translate("&a&l*&f&l* &a&lUNDERTOW FIELD &7[15s] &f&l*&a&l*"));
            Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> {
                this.undertowField.remove(damaged);
                damaged.sendMessage(CC.translate("&a&l*&f&l* &a&lUNDERTOW FIELD &cExpired &f&l*&a&l*"));
            },15*20);
        }
    }
}
