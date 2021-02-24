package com.conaxgames.customskulls;

import com.conaxgames.customskulls.blocks.CustomBlock;
import com.conaxgames.customskulls.blocks.impl.FivePercentDamageIncrease;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class CoreSkulls extends JavaPlugin {

    @Getter public CoreSkulls instance;
    public static Map<String, CustomBlock> customBlocks;

    @Override
    public void onEnable() {
        instance = this;
        registerCustomBlocks();
    }

    @Override
    public void onDisable() {

    }

    private void registerCustomBlocks() {
        customBlocks.put("FIVE_PERCENT_DAMAGE_INCREASE", new FivePercentDamageIncrease("FIVE_PERCENT_DAMAGE_INCREASE", Material.SKULL_ITEM));
    }
}
