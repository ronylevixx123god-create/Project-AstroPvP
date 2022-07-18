package net.astropvp.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import net.astropvp.CollectPlugin;
import net.astropvp.utils.KeyToSave;
import net.astropvp.utils.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static net.astropvp.utils.Serialization.*;

@Getter
public class PlayerCollect implements KeyToSave {

    private UUID uuid;
    private final List<CollectCategory> categories;

    public PlayerCollect(UUID uuid) {
        this.uuid = uuid;
        categories = new ArrayList<>();
    }

    public void add(CollectCategory category) {
        this.categories.add(category);
    }

    public void add(List<CollectCategory> categories) {
        this.categories.addAll(categories);
    }

    @Override
    public String getKey() {
        return uuid.toString();
    }

    @Override
    public void setKey(String key) {
        this.uuid = UUID.fromString(key);
    }
}