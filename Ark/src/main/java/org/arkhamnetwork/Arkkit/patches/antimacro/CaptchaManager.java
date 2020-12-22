/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.zencode.shortninja.staffplus.methods.Staff
 */
package org.arkhamnetwork.Arkkit.patches.antimacro;

import java.util.Random;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class CaptchaManager {
    private static Random random = new Random();

    CaptchaManager() {
    }

    protected static String generateCaptchaString(int length) {
        StringBuffer captchaStringBuffer = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int baseCharNumber = Math.abs(random.nextInt()) % 62;
            int charNumber = 0;
            charNumber = baseCharNumber < 26 ? 65 + baseCharNumber : (baseCharNumber < 52 ? 97 + (baseCharNumber - 26) : 48 + (baseCharNumber - 52));
            captchaStringBuffer.append((char)charNumber);
        }
        return captchaStringBuffer.toString();
    }

    protected static Inventory generateChallengeInventory(Player p) {
        Material m = CaptchaManager.getRandomMaterial();
        Material junkm = CaptchaManager.getRandomMaterial();
        while (m == Material.DIODE_BLOCK_OFF || m == Material.DIODE_BLOCK_ON) {
            m = CaptchaManager.getRandomMaterial();
        }
        String title = (Object)ChatColor.BOLD + "CHALLENGE:" + (Object)ChatColor.RESET + " Click the " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + (Object)m;
        if (title.length() > 32) {
            title = (Object)ChatColor.BOLD + "CHALLENGE: " + (Object)ChatColor.GOLD + (Object)ChatColor.UNDERLINE + (Object)m;
        }
        if (title.length() > 32) {
            return CaptchaManager.generateChallengeInventory(p);
        }
        Inventory challenge = Bukkit.createInventory(null, (int)36, (String)title);
        for (int x = 0; x < challenge.getSize(); ++x) {
            junkm = CaptchaManager.getRandomMaterial();
            challenge.setItem(x, new ItemStack(junkm, 1));
        }
        challenge.setItem(new Random().nextInt(challenge.getSize()), new ItemStack(m, 1));
        p.setMetadata("challengeItem", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)m.name()));
        return challenge;
    }

    protected static void challengePlayer(Player p) {
        if (p.hasMetadata("captchaKickTime")) {
            return;
        }
        Bukkit.getLogger().info("[Arkkit (AntiMacro)] Recording CPS for " + p.getName() + "...");
        p.setMetadata("captchaKickTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + 2000L)));
        p.setMetadata("captchaPromptTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
        p.setMetadata("captchaCPS_A", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)0));
        p.setMetadata("captchaCPS_B", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)0));
        p.setMetadata("captchaAttempts", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)0));
        final Player fp = p;
        Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                Bukkit.getLogger().info("[Arkkit (AntiMacro)] Generated challenge inventory for: " + fp.getName());
                fp.setMetadata("captchaKickTime", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)(System.currentTimeMillis() + (long)((AntiMacro.captchaTimeSeconds - 1) * 1000))));
                fp.setMetadata("captchaReady", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)true));
                CaptchaManager.promptCaptcha(fp);
                Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        if (fp.hasMetadata("captchaKickTime") && System.currentTimeMillis() >= ((MetadataValue)fp.getMetadata("captchaKickTime").get(0)).asLong()) {
                            int CPS_A = Math.round(((MetadataValue)fp.getMetadata("captchaCPS_A").get(0)).asInt());
                            int CPS_B = Math.round(((MetadataValue)fp.getMetadata("captchaCPS_B").get(0)).asInt());
                            String localServer = FallbackServers.lily_username;
                            fp.removeMetadata("captchaAttempts", (Plugin)Arkkit.get());
                            if (!fp.hasMetadata("commandDetection")) {
                                fp.setMetadata("noCombatLog", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                            }
                            FallbackServers.redirect("hacker", fp);
                            AntiMacro.sql_query.add("INSERT INTO anti_macro(player_uuid, player_name, CPS_A, CPS_B, server_name, time) VALUES('" + fp.getUniqueId().toString() + "', '" + fp.getName() + "', " + CPS_A + ", " + CPS_B + ", '" + localServer + "', NOW())");
                            Bukkit.getLogger().info("[Arkkit (AntiMacro)] " + fp.getName() + " has been kicked due to failure to complete the captcha.");
                        }
                    }
                }, (long)AntiMacro.captchaTimeSeconds * 20L);
            }
        }, 40L);
        AntiMacro.incrementPlayerThreshold(p);
    }

    protected static Material getRandomMaterial() {
        if (new Random().nextBoolean()) {
            return Material.getMaterial((int)(new Random().nextInt(23) + 1));
        }
        return Material.getMaterial((int)(new Random().nextInt(30) + 352));
    }

    protected static void promptCaptcha(Player p) {
        if (p.hasMetadata("captchaReady") && (p.getOpenInventory() == null || !p.getOpenInventory().getTitle().contains("CHALLENGE:"))) {
            if (p.hasMetadata("captchaAttempts") && ((MetadataValue)p.getMetadata("captchaAttempts").get(0)).asInt() >= 5) {
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "Auto Clicker and/or Chest Steal hack detected...");
                p.removeMetadata("captchaAttempts", (Plugin)Arkkit.get());
                String localServer = FallbackServers.lily_username;
                p.setMetadata("noCombatLog", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
                FallbackServers.redirect("hacker", p);
                return;
            }
            p.openInventory(CaptchaManager.generateChallengeInventory(p));
            p.playSound(p.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 1.4f);
        }
    }
}

