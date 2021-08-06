package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class BugOutBagSkin extends Backpack {
    @Override
    public BackpackType getType() {
        return BackpackType.DOOMSDAY_BACKPACK_BUGOUT;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "10% chance to keep each equipped item on death.");
    }
}
