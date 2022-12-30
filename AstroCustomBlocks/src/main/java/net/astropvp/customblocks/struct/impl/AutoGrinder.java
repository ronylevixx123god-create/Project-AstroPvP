package net.astropvp.customblocks.struct.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.astropvp.customblocks.CustomBlocks;
import net.astropvp.customblocks.guis.AutoGrinderGui;
import net.astropvp.customblocks.struct.CustomBlock;
import net.astropvp.customblocks.struct.CustomBlockType;
import net.astropvp.customblocks.struct.abilities.ActivateAble;
import net.astropvp.customblocks.struct.abilities.LevelAble;
import net.astropvp.customblocks.struct.abilities.TickAble;
import net.astropvp.customblocks.utils.config.ConfigManager;
import org.bukkit.entity.Player;

@Getter
@Setter
public class AutoGrinder extends CustomBlock implements LevelAble, TickAble, ActivateAble {
    private Integer exp;
    private Integer grinderKeys;
    private Integer level;
    private Long lastActivated;

    public AutoGrinder() {
        super(CustomBlockType.BLAZE_STONE_TOTEM);
        this.level = 0;
        this.exp = 0;
        this.grinderKeys = 0;
        this.lastActivated = 0L;
    }

    @SneakyThrows
    @Override
    public void onClick(Player clicker) {
        AutoGrinderGui autoGrinderGui = new AutoGrinderGui(clicker, this);
        autoGrinderGui.getGui().open(clicker);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int lvl) {
        this.level = lvl;
    }

    @Override
    public void onTick() {
        if (!isActive()) return;
        double keyChance = CustomBlocks.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getDouble("keyChance") * 0.01 * level;
        int expPer = CustomBlocks.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("expPer") * level;
        if (Math.random() < keyChance) {
            grinderKeys++;
        }

        exp+=expPer;
    }

    @Override
    public boolean isActive() {
        return System.currentTimeMillis() < lastActivated + 3600000;
    }

    @Override
    public void activate(Player player) {
        this.lastActivated = System.currentTimeMillis();
    }

    @Override
    public double getActivationPrice() {
        return 2500000;
    }

    @Override
    public void setLastActivated(long lastActivated) {
        this.lastActivated = lastActivated;
    }


}
