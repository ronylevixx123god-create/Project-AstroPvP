package me.astropvp.astropvpskins.utils;

import me.astropvp.astropvpskins.enums.SkinType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static List<SkinType> stringToSkinList(String s) {
        s = s.replaceAll(" ", "");
        s = s.replace("[", "");
        s = s.replace("]", "");
        String[] stringArray;
        List<String> stringSkins = new ArrayList<>(Arrays.asList(stringArray = s.split(",")));
        stringSkins.forEach(st -> {
            st = st.replaceAll(" ", "");
            st = st.replace("[", "");
            st = st.replace("]", "");
        });
        List<SkinType> skins = new ArrayList<>();
        stringSkins.forEach(st -> skins.add(SkinType.valueOf(st)));
        return skins;
    }
}
