// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.managers;

import net.astropvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.block.Block;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.ArmorStand;

public class SandBotManager
{
    private ArmorStand sandbotModel;
    private UUID sandbotUUID;
    private UUID ownerUUID;
    private ArrayList<Block> lapisBlocks;
    private Double balance;
    private Location location;
    private boolean isActivated;
    private String factionID;
    private boolean animate;
    private String ownerName;
    
    public SandBotManager(final ArmorStand stand, final Player player) {
        this.sandbotModel = stand;
        this.ownerUUID = player.getUniqueId();
        this.sandbotUUID = stand.getUniqueId();
        this.location = stand.getLocation();
        this.isActivated = false;
        this.factionID = Utils.getFactionID(player);
        this.ownerName = player.getName();
        this.balance = 0.0;
        this.animate = false;
        this.lapisBlocks = Utils.getNearbyBlocks(this);
    }
    
    public SandBotManager(final UUID sandbotUUID, final UUID ownerUUID, final String ownerName, final ArrayList<Block> lapisBlocks, final Double balance, final Location location, final boolean isActivated, final String factionID, final boolean animate) {
        this.sandbotUUID = sandbotUUID;
        this.ownerUUID = ownerUUID;
        this.ownerName = ownerName;
        this.lapisBlocks = lapisBlocks;
        this.balance = balance;
        this.location = location;
        this.isActivated = isActivated;
        this.factionID = factionID;
        this.sandbotModel = (ArmorStand) Utils.getEntityByUUID(sandbotUUID);
        this.animate = animate;
    }
    
    public boolean isActivated() {
        return this.isActivated;
    }
    
    public void setActivated(final boolean activated) {
        this.isActivated = activated;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(final Location location) {
        this.location = location;
    }
    
    public Double getBalance() {
        return this.balance;
    }
    
    public void setBalance(final Double balance) {
        this.balance = balance;
    }
    
    public ArrayList<Block> getLapisBlocks() {
        return this.lapisBlocks;
    }
    
    public void setLapisBlocks(final ArrayList<Block> lapisBlocks) {
        this.lapisBlocks = lapisBlocks;
    }
    
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }
    
    public void setOwnerUUID(final UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }
    
    public UUID getSandbotUUID() {
        return this.sandbotUUID;
    }
    
    public void setSandbotUUID(final UUID sandbotUUID) {
        this.sandbotUUID = sandbotUUID;
    }
    
    public ArmorStand getSandbotModel() {
        return this.sandbotModel;
    }
    
    public void setSandbotModel(final ArmorStand sandbotModel) {
        this.sandbotModel = sandbotModel;
    }
    
    public String getFactionID() {
        return this.factionID;
    }
    
    public void setFactionID(final String factionID) {
        this.factionID = factionID;
    }
    
    public boolean isAnimate() {
        return this.animate;
    }
    
    public void setAnimate(final boolean animate) {
        this.animate = animate;
        if (Utils.getArmorAnimateBoolean() && this.sandbotModel != null && this.sandbotModel.isValid() && !this.sandbotModel.isDead()) {
            if (animate) {
                this.sandbotModel.setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, Utils.enabledArmorColor));
                this.sandbotModel.setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, Utils.enabledArmorColor));
                this.sandbotModel.setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, Utils.enabledArmorColor));
            }
            else {
                this.sandbotModel.setChestplate(Utils.getColorArmor(Material.LEATHER_CHESTPLATE, Utils.disabledArmorColor));
                this.sandbotModel.setLeggings(Utils.getColorArmor(Material.LEATHER_LEGGINGS, Utils.disabledArmorColor));
                this.sandbotModel.setBoots(Utils.getColorArmor(Material.LEATHER_BOOTS, Utils.disabledArmorColor));
            }
        }
    }
    
    public void removeBlock(final Block block) {
        this.lapisBlocks.remove(block);
    }
    
    public void addBlock(final Block block) {
        this.lapisBlocks.add(block);
    }
    
    public void addBalance(final Double balance) {
        this.balance += balance;
    }
    
    public void removeBalance(final Double balance) {
        this.balance -= balance;
    }
    
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(final String ownerName) {
        this.ownerName = ownerName;
    }
}
