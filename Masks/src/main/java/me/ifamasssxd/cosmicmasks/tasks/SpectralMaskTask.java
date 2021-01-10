/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  me.libraryaddict.disguise.DisguiseAPI
 *  me.libraryaddict.disguise.disguisetypes.Disguise
 *  me.libraryaddict.disguise.disguisetypes.DisguiseType
 *  me.libraryaddict.disguise.disguisetypes.MobDisguise
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicmasks.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import me.ifamasssxd.cosmicmasks.CosmicMasks;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class SpectralMaskTask
implements Runnable {
    @Override
    public void run() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getLocation().getY() > 200.0) {
                boolean hasMaskEquipped = MaskUtils.hasMaskEquipped((Player)p, (String)"SPECTRAL");
                if (!DisguiseAPI.isDisguised((Entity)p) && hasMaskEquipped) {
                    DisguiseAPI.disguiseToAll((Entity)p, (Disguise)new MobDisguise(DisguiseType.ZOMBIE, true));
                    p.sendMessage(CC.DarkAquaB + "(SPECTRAL) " + CC.DarkAqua + "You have been auto-disguised as a Zombie.");
                    p.sendMessage(CC.Gray + "This disguise will remain as long as you stay above y:200");
                    p.setMetadata("spectralMaskDisguise", (MetadataValue)new FixedMetadataValue((Plugin)CosmicMasks.get(), (Object)true));
                    continue;
                }
                if (!DisguiseAPI.isDisguised((Entity)p) || hasMaskEquipped || !p.hasMetadata("spectralMaskDisguise")) continue;
                p.sendMessage(CC.RedB + "(SPECTRAL) " + CC.Red + "Your spectral-zombie disguise has been unequipped!");
                p.removeMetadata("spectralMaskDisguise", (Plugin)CosmicMasks.get());
                DisguiseAPI.undisguiseToAll((Entity)p);
                continue;
            }
            if (p.getLocation().getY() >= 200.0 || !p.hasMetadata("spectralMaskDisguise") || !DisguiseAPI.isDisguised((Entity)p) || DisguiseAPI.getDisguise((Entity)p).getType() != DisguiseType.ZOMBIE) continue;
            p.sendMessage(CC.RedB + "(SPECTRAL) " + CC.Red + "Your spectral-zombie disguise has been unequipped!");
            p.removeMetadata("spectralMaskDisguise", (Plugin)CosmicMasks.get());
            DisguiseAPI.undisguiseToAll((Entity)p);
        }
    }
}

