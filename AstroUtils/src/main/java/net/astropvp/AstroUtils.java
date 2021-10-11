package net.astropvp;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class AstroUtils extends JavaPlugin {
    private static AstroUtils instance;

    private JDA jda;

    public void onEnable() {
        instance = this;
        JDABuilder builder = JDABuilder.create("token here", GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES);
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        instance = null;
    }

    public static AstroUtils get() {
        return instance;
    }

    public JDA getJda() { return this.jda; }
}
