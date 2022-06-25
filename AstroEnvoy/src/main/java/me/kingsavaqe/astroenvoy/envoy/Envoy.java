package me.kingsavaqe.astroenvoy.envoy;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Envoy {
    private boolean started = false;

    private int currentDuration;

    private int fixedDuration;

    private int currentStartTime;

    private int fixedStartTime;

    private List<Location> crateLocations = new ArrayList<>();

    private List<Location> randomCrateLocations = new ArrayList<>();

    private List<LootItem> lootItems = new ArrayList<>();

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean hasStarted() {
        return this.started;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }

    public int getCurrentDuration() {
        return this.currentDuration;
    }

    public void setFixedDuration(int fixedDuration) {
        this.fixedDuration = fixedDuration;
    }

    public int getFixedDuration() {
        return this.fixedDuration;
    }

    public int getCountdown() {
        return this.fixedDuration - this.currentDuration;
    }

    public void setCurrentStartTime(int currentStartTime) {
        this.currentStartTime = currentStartTime;
    }

    public int getCurrentStartTime() {
        return this.currentStartTime;
    }

    public void setFixedStartTime(int fixedStartTime) {
        this.fixedStartTime = fixedStartTime;
    }

    public int getFixedStartTime() {
        return this.fixedStartTime;
    }

    public void addCrateLocation(Location location) {
        this.crateLocations.add(location);
    }

    public void removeCrateLocation(Location location) {
        Iterator<Location> crateLocationsIterator = this.crateLocations.iterator();
        while (crateLocationsIterator.hasNext()) {
            Location crateLocation = crateLocationsIterator.next();
            if (crateLocation.getWorld().getName().equals(location.getWorld().getName()) && crateLocation
                    .getBlockX() == location.getBlockX() && crateLocation
                    .getBlockY() == location.getBlockY() && crateLocation
                    .getBlockZ() == location.getBlockZ()) {
                crateLocationsIterator.remove();
                break;
            }
        }
    }

    public List<Location> getCrateLocations() {
        return this.crateLocations;
    }

    public boolean isCrateLocation(Location location) {
        for (Location crateLocation : this.crateLocations) {
            if (crateLocation.getWorld().getName().equals(location.getWorld().getName()) && crateLocation
                    .getBlockX() == location.getBlockX() && crateLocation
                    .getBlockY() == location.getBlockY() && crateLocation
                    .getBlockZ() == location.getBlockZ())
                return true;
        }
        return false;
    }

    public boolean hasCrateLocation() {
        if (this.crateLocations.size() == 0)
            return false;
        return true;
    }

    public void addRandomCrateLocation(Location location) {
        this.randomCrateLocations.add(location);
    }

    public void removeRandomCrateLocation(Location location) {
        Iterator<Location> randomCrateLocationsIterator = this.randomCrateLocations.iterator();
        while (randomCrateLocationsIterator.hasNext()) {
            Location randomCrateLocation = randomCrateLocationsIterator.next();
            if (randomCrateLocation.getWorld().getName().equals(location.getWorld().getName()) && randomCrateLocation
                    .getBlockX() == location.getBlockX() && randomCrateLocation
                    .getBlockY() == location.getBlockY() && randomCrateLocation
                    .getBlockZ() == location.getBlockZ()) {
                randomCrateLocationsIterator.remove();
                break;
            }
        }
    }

    public void setRandomCrateLocations(List<Location> randomCrateLocations) {
        this.randomCrateLocations = randomCrateLocations;
    }

    public List<Location> getRandomCrateLocations() {
        return this.randomCrateLocations;
    }

    public boolean isRandomCrateLocation(Location location) {
        for (Location randomCrateLocation : this.randomCrateLocations) {
            if (randomCrateLocation.getWorld().getName().equals(location.getWorld().getName()) && randomCrateLocation
                    .getBlockX() == location.getBlockX() && randomCrateLocation
                    .getBlockY() == location.getBlockY() && randomCrateLocation
                    .getBlockZ() == location.getBlockZ())
                return true;
        }
        return false;
    }

    public boolean hasRandomCrateLocation() {
        if (this.randomCrateLocations.size() == 0)
            return false;
        return true;
    }

    public void addLootItem(LootItem lootItem) {
        this.lootItems.add(lootItem);
    }

    public void removeLoot(LootItem lootItem) {
        this.lootItems.remove(lootItem);
    }

    public LootItem getLootItem(ItemStack item) {
        for (LootItem lootItem : this.lootItems) {
            if (lootItem.getItemStack() == item)
                return lootItem;
        }
        return null;
    }

    public ItemStack getRandomLootItem() {
        if (hasLootItems()) {
            ItemStack item = null;
            int randomIndex = (new Random()).nextInt(getLootItemChance());
            int currentIndex = 0;
            for (LootItem lootItem : this.lootItems) {
                for (int i = 0; i < lootItem.getChance(); i++) {
                    if (randomIndex == currentIndex) {
                        item = lootItem.getItemStack();
                        break;
                    }
                    currentIndex++;
                }
                if (item != null)
                    break;
            }
            if (item == null)
                return ((LootItem)this.lootItems.get((new Random()).nextInt(this.lootItems.size()))).getItemStack().clone();
            return item.clone();
        }
        return new ItemStack(Material.AIR);
    }

    public int getLootItemChance() {
        int lootItemChance = 0;
        for (LootItem lootItem : this.lootItems)
            lootItemChance += lootItem.getChance();
        return lootItemChance;
    }

    public boolean hasLootItem(ItemStack item) {
        return (getLootItem(item) != null);
    }

    public boolean hasLootItems() {
        return (this.lootItems.size() != 0);
    }

    public List<LootItem> getLootItems() {
        return this.lootItems;
    }

    public boolean hasSetup() {
        if (this.fixedDuration == 0 || this.fixedStartTime == 0 || this.crateLocations.size() < 60 || this.lootItems.size() == 0)
            return false;
        return true;
    }
}
