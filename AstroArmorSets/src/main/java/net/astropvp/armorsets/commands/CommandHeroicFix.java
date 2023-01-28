package net.astropvp.armorsets.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import de.tr7zw.nbtapi.NBTItem;
import net.astropvp.armorsets.struct.heroicArmor.HeroicArmor;
import net.astropvp.armorsets.utils.Pair;
import net.astropvp.armorsets.utils.modifiers.AttributeModifier;
import net.astropvp.armorsets.utils.modifiers.ModifierType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public class CommandHeroicFix implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!sender.isOp()) return false;
        Player player = (Player) sender;
        if (ItemUtils.isAirOrNull(player.getItemInHand())) {
            player.sendMessage(CC.Red + "Hold an item!");
            return false;
        }
        ItemStack item = player.getItemInHand();
        ItemMeta meta = item.getItemMeta();

        if (GeneralUtil.isArmor(item)) {
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setBoolean("heroic", true);
            nbtItem.setInteger("wine-armor-override", Objects.requireNonNull(HeroicArmor.getFromMaterial(item.getType())).getReduction());
            item = nbtItem.getItem();
            player.setItemInHand(item);
            player.sendMessage(CC.Green + "Successfully made armor into new heroic!");
            return true;
        } else if (GeneralUtil.isWeapon(item)) {
            meta.spigot().setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            item.setItemMeta(meta);
            Pair<ModifierType, Double> modifierTypePair = new Pair<>(ModifierType.ATTACK_DAMAGE, 7D);
            player.setItemInHand(AttributeModifier.applyModifications(item, Collections.singletonList(modifierTypePair)));
            player.sendMessage(CC.Green + "Successfully made weapon into new heroic!");
            return true;
        } else {
            player.sendMessage(CC.Red + "Cannot fix this item to heroic fixed");
            return false;
        }
    }

}
