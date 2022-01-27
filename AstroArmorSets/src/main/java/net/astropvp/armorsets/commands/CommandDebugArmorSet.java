package net.astropvp.armorsets.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.cache.CachedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDebugArmorSet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (!sender.isOp()) return false;
        CachedPlayer armorSetPlayer = AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) sender);
        ArmorSetCache cache = (ArmorSetCache) ((ArmorSetCache) (armorSetPlayer.hasCache("armor_set") ? armorSetPlayer.getCache("armor_set") : armorSetPlayer.registerCache(new ArmorSetCache((Player) sender))));

        if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            cache.getCurrentArmorSets().clear();
            cache.getCurrentSetCrystals().clear();
            cache.setHeroicPieces(0);
            cache.setOmniPieces(0);

            cache.setCurrentArmorSet(null);
            sender.sendMessage(CC.translate("&aCleared all the values for armorsets plugin"));
            return true;
        }

        sender.sendMessage("");
        sender.sendMessage(CC.translate("&aCurrent ArmorSet: &f" + cache.getCurrentArmorSet()));
        sender.sendMessage(CC.translate("&bCrystals: &f" + cache.getCurrentSetCrystals()));
        sender.sendMessage(CC.translate("&cEquipped Heroic Pieces: &f" + cache.getHeroicPieces()));
        sender.sendMessage(CC.translate("&dArmorSets Equipped: &f" + cache.getCurrentArmorSets()));
        sender.sendMessage(CC.translate("&6AntiMkit Equipped: &f" + cache.getCurrentAntiMkitCrystals()));
        sender.sendMessage(CC.translate("&3Omni Crystals: &f" + cache.getOmniPieces()));

        sender.sendMessage("");

        return false;
    }
}
