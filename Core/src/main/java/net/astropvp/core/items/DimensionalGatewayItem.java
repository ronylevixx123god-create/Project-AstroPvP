package net.astropvp.core.items;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.astropvp.core.AstroCore;
import net.astropvp.core.objects.GatewayData;
import net.astropvp.core.tasks.DimensionalGatewayTask;
import net.astropvp.core.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DimensionalGatewayItem extends CosmicItem implements InventoryInteractable {
    private HashMap<Player, GatewayData> savedLocations;

    public DimensionalGatewayItem() {
        super("dimensional_gateway");
        savedLocations = new HashMap<>();
    }

    public ItemStack createItem(Player player, Object... objects) {
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.YellowB + "Dimensional Gateway");
        List<String> lore = Lists.newArrayList();
        lore.add(CC.Gray + "Use this item to jump through dimensions, saving");
        lore.add(CC.Gray + "your location. Right-Click again within 20 seconds");
        lore.add(CC.Gray + "to return to your original position. If not used");
        lore.add(CC.Gray + "within this time, the item will vanish and you");
        lore.add(CC.Gray + "will no longer be able to return.");
        lore.add("");
        lore.add(CC.RedB + "WARNING: This item is 1 time use!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("cosmicType", "dimensional_gateway");
        wrapper.setString("random", UUID.randomUUID().toString());
        return wrapper.build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CLICKED;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {

    }

    public void onItemUse(Player player, ItemStack clicked, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || wrapper == null)
            return;
        if(wrapper.hasKey("used"))
            throw new PrefixedException("This Dimensional Gateway has already been used.");
        if (!player.getLocation().getWorld().getName().equals("world")) {
            throw new PrefixedException("You cannot use a Dimensional Gateway in this world.");
        } if(!WorldGuardUtils.isPvPEnabled(player.getLocation()))
            throw new PrefixedException("You can only use Demonic Gateways in PvP-enabled areas");
        if(!savedLocations.containsKey(player)) {
            wrapper.setString("gateway_in_use", player.getName());
            ItemUtils.addGlow(clicked);
            List<String> lines = new ArrayList<>();
            lines.add(CC.Green + CC.Magic + "oooooooooooooooooooooooooooooo");
            lines.add(CC.YellowB + player.getName() + "'s Dimensional Gateway");
            lines.add(CC.Gray + "20s");
            lines.add(CC.Green + CC.Magic + "oooooooooooooooooooooooooooooo");
            Hologram hologram = (Hologram) HolographicDisplaysAPI.createHologram(AstroCore.getInstance(), player.getLocation().add(0, 2, 0), getHologramText(player, 20));
            DimensionalGatewayTask task = new DimensionalGatewayTask(player, hologram, this, clicked, savedLocations.get(player));
            savedLocations.put(player, new GatewayData(hologram, player.getLocation(),task));
            AtomicReference<Integer> i = new AtomicReference<>(20);
            Bukkit.getScheduler().runTaskTimer(AstroCore.getInstance(), task, 20L, 20L);
        } else {
            player.teleport(savedLocations.get(player).getLocation());
            savedLocations.get(player).getHologram().delete();
            savedLocations.remove(player);
            wrapper.setString("used", "true");
            clicked.setType(Material.FIREBALL);
        }
    }

    public void endGateway(Player player, ItemStack item, GatewayData data) {
        savedLocations.get(player).getHologram().delete();
        savedLocations.remove(player);
        NBTWrapper wrapper = new NBTWrapper(item);
        wrapper.setString("used", "true");
        item.setType(Material.FIREBALL);
        Bukkit.getScheduler().cancelTask(savedLocations.get(player).getTask().getTaskId());
    }

    public String[] getHologramText(Player player, int i) {
        List<String> lines = new ArrayList<>();
        lines.add(CC.Green + CC.Magic + "oooooooooooooooooooooooooooooo");
        lines.add(CC.YellowB + player.getName() + "'s Dimensional Gateway");
        lines.add(CC.Gray +  i + "s");
        lines.add(CC.Green + CC.Magic + "oooooooooooooooooooooooooooooo");
        return lines.toArray(new String[0]);
    }
}
