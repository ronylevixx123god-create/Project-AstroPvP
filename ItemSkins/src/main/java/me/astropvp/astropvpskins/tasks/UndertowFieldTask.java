package me.astropvp.astropvpskins.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.objects.skins.helmets.UndertowHelmet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UndertowFieldTask implements Runnable{
    public static List<Player> affected;
    public UndertowFieldTask() {
        affected = new ArrayList<>();
    }
    @SneakyThrows
    @Override
    public void run() {
        affected.clear();

        for (Player p : UndertowHelmet.undertowField) {
            if (p != null) {
                ParticleEffect.DRIP_LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(5.0, 1.0, 5.0), new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.65f, 30);
                List<Entity> nearby = p.getNearbyEntities(5, 5, 5);
                for (Entity e : nearby) {
                    if (e instanceof Player) {
                        affected.add((Player) e);
                    }
                }
            }
        }

        for (Player p : affected) {
            p.damage(1);
            p.sendMessage(CC.translate("&c&l- 1HP &7(Undertow Ability)"));
        }
    }
}
