package net.astropvp.armorsets.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.cache.CachedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDebugArmorSet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!sender.hasPermission("astrocore.staff")) {
            sender.sendMessage(CC.Red + "No perms!");
            return false;
        }


        if (args.length == 1) {
            checkArmor(Bukkit.getPlayer(args[0]));
        } else {
            checkArmor((Player) sender);
        }

        return false;
    }


    public void checkArmor(Player sender) {
        CachedPlayer armorSetPlayer = AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) sender);
        ArmorSetCache cache = (ArmorSetCache) ((ArmorSetCache) (armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache((Player) sender))));



        sender.sendMessage(CC.translate("&fChecking armorsets for: " + sender));
        sender.sendMessage(CC.translate("&aCurrent ArmorSet: &f" + cache.getCurrentArmorSet()));
        sender.sendMessage(CC.translate("&bCrystals: &f" + cache.getCurrentSetCrystals()));
        sender.sendMessage(CC.translate("&cEquipped Heroic Pieces: &f" + cache.getHeroicPieces()));
        sender.sendMessage(CC.translate("&dArmorSets Equipped: &f" + cache.getCurrentArmorSets()));
        sender.sendMessage(CC.translate("&6AntiMkit Equipped: &f" + cache.getCurrentAntiMkitCrystals()));
        sender.sendMessage(CC.translate("&3Omni Crystals: &f" + cache.getOmniPieces()));

        sender.sendMessage("");
    }
}
