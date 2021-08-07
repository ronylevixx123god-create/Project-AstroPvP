package me.astropvp.astropvpskins.objects.skins.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MauisHookSkin extends ItemSkin {

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (Math.random() > 0.06D)
            return;
        if (damager.hasMetadata("mauiEffect") && ((MetadataValue)damager.getMetadata("mauiEffect").get(0)).asInt() == 10 && Math.random() < 0.3D) {
            damager.sendMessage(CC.translate("&c&l* MAUI ABILITY is Ready for Use &c&l*"));
            return;
        }
        if (!damager.hasMetadata("mauiEffect")) {
            damager.setMetadata("mauiEffect", (MetadataValue)new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), 1));
        } else {
            damager.setMetadata("mauiEffect", (MetadataValue)new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), ((MetadataValue) damager.getMetadata("mauiEffect").get(0)).asInt() + 1));
        }
        damager.sendMessage(CC.translate("&c&l* MAUI'S HOOK &7[&fStack x" + ((MetadataValue)damager.getMetadata("mauiEffect").get(0)).asInt() + "&7] &c&l*"));
    }

    //TODO: SNEAK LISTENER
}
