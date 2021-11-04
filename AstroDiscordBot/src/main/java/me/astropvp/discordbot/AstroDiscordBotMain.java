package me.astropvp.discordbot;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.astropvp.discordbot.botEvents.JoinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroDiscordBotMain extends JavaPlugin {

    @Getter
    private JDA discordBot;

    @Getter @Setter
    private Guild discordServer;

    @SneakyThrows
    @Override
    public void onEnable() {
        super.onEnable();

        saveDefaultConfig();

        String discordToken = getToken();
        if (discordToken == null) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().severe("Please provide a valid discord token.");
            return;
        }


        this.discordBot = JDABuilder.createDefault(discordToken)
                .build();

        this.discordBot.getPresence().setActivity(Activity.playing("play.astropvp.net"));

        this.discordBot.addEventListener(new JoinEvent());

    }

    @Override
    public void onDisable() {
        discordBot.shutdown();
    }

    public String getToken() {
        return "ODUzMzYwMDkwOTQ5NzQ2Nzc5.YMUPhA.0Mg4Oxs86731XJD6t3bZzTJwxGI";
    }

}