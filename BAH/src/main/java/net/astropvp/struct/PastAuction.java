package net.astropvp.struct;

import lombok.Getter;
import net.astropvp.utils.ConfigUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

public class PastAuction implements Serializable {
    @Getter
    private String playerName;

    @Getter
    private double bid;

    @Getter
    private long time;

    @Getter
    private ItemStack item;

    public PastAuction(String playerName, double bid, long time, String item) {
        this.playerName = playerName;
        this.bid = bid;
        this.time = time;
        this.item = ConfigUtils.getItem(item);
    }

    public String toString() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(this);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    public static PastAuction fromString(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            PastAuction past = (PastAuction)dataInput.readObject();
            dataInput.close();
            return past;
        } catch (Exception ex) {
            return null;
        }
    }
}
