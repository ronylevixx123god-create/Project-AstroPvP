package net.astropvp.loottable;

import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.components.TypeResult;

import net.astropvp.loottable.commands.LootTableCommand;
import net.astropvp.loottable.data.LootTableManager;
import net.astropvp.loottable.struct.LootTableType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class LootTablePlugin extends JavaPlugin {
    @Getter
    private static LootTablePlugin instance;
    private LootTableManager lootTableManager;
    private CommandManager commandManager;
    @Override
    public void onEnable() {
        instance = this;
        this.lootTableManager = new LootTableManager(this);
        lootTableManager.loadAll();
        this.commandManager = new CommandManager(this, true);
        registerCommandParameters();
        registerCommandCompletion();
        commandManager.register(new LootTableCommand());
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, ()-> {
            System.out.println("[LootTable] Started auto saving");
            lootTableManager.saveAll();
            System.out.println("[LootTable] Finished auto saving");

        }, 60L, 10*20*60);
    }

    @Override
    public void onDisable() {
        lootTableManager.saveAll();
        instance = null;
    }

    private void registerCommandParameters() {
        this.commandManager.getParameterHandler().register(LootTableType.class, argument -> {
            LootTableType type = LootTableType.valueOf(String.valueOf(argument));
            return new TypeResult(type, argument);
        });
    }

    private void registerCommandCompletion() {
        this.commandManager.getCompletionHandler().register("#loottables", input -> {
            return Arrays.stream(LootTableType.values()).map(LootTableType::name).collect(Collectors.toList());
        });
    }
}
