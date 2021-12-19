import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AstroGamble extends JavaPlugin {
    @Getter
    private static AstroGamble instance;


    public void onEnable() {
        instance = this;
        registerListeners();
        registerCommands();
        System.out.println("[AstroGamble] Plugin is now enabled");
    }

    public void onDisable() {
        System.out.println("[AstroGamble] Plugin is now disabled");
        instance = null;
    }


    public void registerListeners() {

    }

    public void registerCommands() {

    }


}
