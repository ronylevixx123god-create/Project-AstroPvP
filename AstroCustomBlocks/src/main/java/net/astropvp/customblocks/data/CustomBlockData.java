package net.astropvp.customblocks.data;

import com.cosmicpvp.cosmicutils.utils.LocationUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.astropvp.storage.utils.KeyToSave;
import org.bukkit.Location;

@Getter
@Setter
@AllArgsConstructor
public class CustomBlockData  implements KeyToSave {
    private Location location;
    private Integer level;
    private String type;

    @Override
    public String getKey() {
        return LocationUtils.getStringFromLocation(location);
    }

    @Override
    public void setKey(String key) {
        this.location = LocationUtils.getLocationFromString(key, false);
    }
}
