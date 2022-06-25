package me.kingsavaqe.astroenvoy.utils;

import java.io.File;

public class FileUtil {
    public static String getFileExtension(File configFile) {
        String name = configFile.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1)
            return "";
        return name.substring(lastIndexOf);
    }
}
