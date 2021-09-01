package net.astropvp.utils;

import net.astropvp.struct.CollectCategory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Serialization {
    public static String getAsString(List<CollectCategory> items) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutput output = new ObjectOutputStream(os);
            output.writeInt(items.size());
            for (Object object : items) {
                output.writeObject(object);
            }
            output.close();;
            return Base64Coder.encodeLines(os.toByteArray());
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> getAsList(String string) {
        ByteArrayInputStream is = new ByteArrayInputStream(Base64Coder.decodeLines(string));
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(is);
            List<Object> list = new ArrayList<>();
            int j = in.readInt();
            for (int i = 0; i < j; i++) {
                list.add(in.readObject());
            }
            in.close();
            return list;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
