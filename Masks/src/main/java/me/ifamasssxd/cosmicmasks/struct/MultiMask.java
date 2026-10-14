package me.ifamasssxd.cosmicmasks.struct;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicmasks.MaskType;
import me.ifamasssxd.cosmicmasks.items.MultiMaskItem;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/** Dispatches lifecycle callbacks to the actual components of an attached multi-mask. */
public class MultiMask extends CosmicMask {
    private final Map<UUID, Set<MaskType>> applied = new HashMap<>();

    private CosmicMask implementation(MaskType type) {
        return type == MaskType.MULTI_MASK ? null : type.getMaskData().getInternalMask();
    }

    private Set<MaskType> synchronize(Player player) {
        Set<MaskType> next = new LinkedHashSet<>(MultiMaskItem.getMasks(new NBTWrapper(player.getInventory().getHelmet())));
        Set<MaskType> previous = applied.get(player.getUniqueId());
        if (previous == null) previous = new LinkedHashSet<>();
        for (MaskType type : previous) {
            if (!next.contains(type) && implementation(type) != null) implementation(type).onUnequip(player);
        }
        applied.put(player.getUniqueId(), next);
        for (MaskType type : next) {
            if (!previous.contains(type) && implementation(type) != null) implementation(type).onEquip(player);
        }
        return next;
    }

    @Override public void onEquip(Player player) { synchronize(player); }

    @Override public void onUnequip(Player player) {
        Set<MaskType> previous = applied.remove(player.getUniqueId());
        if (previous != null) for (MaskType type : previous) {
            if (implementation(type) != null) implementation(type).onUnequip(player);
        }
    }

    @Override public void onUpdate(Player player) {
        for (MaskType type : synchronize(player)) {
            if (implementation(type) != null) implementation(type).onUpdate(player);
        }
    }
}
