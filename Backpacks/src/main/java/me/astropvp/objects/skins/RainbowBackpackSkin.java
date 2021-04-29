package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Player;

import java.util.List;

public class RainbowBackpackSkin extends Backpack {
    @Override
    public BackpackType getType() {
        return BackpackType.RAINBOW_BACKPACK;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "+1 Max Heart");
    }

    @Override
    public void onEquip(Player player) {
        //double health = player.getMaxHealth();
        //health++;
        player.setMaxHealth(20.0);
        player.sendMessage(CC.translate("&c&lNope"));
    }

    @Override
    public void onUnequip(Player player) {
        //player.setMaxHealth(20.0);
    }
}
