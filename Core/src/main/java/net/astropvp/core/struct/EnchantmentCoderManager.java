package net.astropvp.core.struct;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnchantmentCoderManager {
    private Map<String, Map<String, List<String>>> storedData;

    private String fileName;

    public EnchantmentCoderManager(String fileName) {
        this.storedData = Maps.newHashMap();
        this.fileName = fileName;
    }

    public List<CustomEnchantment> getEnchantmentsForUser(UUID uuid, Material type) {
        if (!this.storedData.containsKey(uuid.toString()))
            return Lists.newArrayList();
        Map<String, List<String>> map = this.storedData.get(uuid.toString());
        if (!map.containsKey(type.name()))
            return Lists.newArrayList();
        List<CustomEnchantment> enchantments = Lists.newArrayList();
        List<String> ce = map.get(type.name());
        for (String s : ce)
            enchantments.add(EnchantmentAPI.getEnchantment(s));
        return enchantments;
    }

    public void saveDataToUser(UUID uuid, Material type, List<CustomEnchantment> enchantments) {
        List<String> list = Lists.newArrayList();
        enchantments.stream().forEach(ce -> list.add(ce.name()));
        Map<String, List<String>> map = this.storedData.containsKey(uuid.toString()) ? this.storedData.get(uuid.toString()) : Maps.newHashMap();
        map.put(type.name(), list);
        this.storedData.putIfAbsent(uuid.toString(), map);
    }

    public void loadData() {
        EnchantmentCoderController inst = EnchantmentCoderController.getInstance();
        this.storedData = inst.readFromJSONFile(this.fileName);
    }

    public void saveData() {
        EnchantmentCoderController inst = EnchantmentCoderController.getInstance();
        inst.writeToJSONFile(this);
    }

    public void clearData() {
        this.storedData.clear();
    }

    public String getFileName() {
        return this.fileName;
    }

    public void add(UUID uuid, Material type, List<CustomEnchantment> data) {
        List<String> list = Lists.newArrayList();
        data.stream().forEach(ce -> list.add(ce.name()));
        Map<String, List<String>> map = this.storedData.containsKey(uuid.toString()) ? this.storedData.get(uuid.toString()) : Maps.newHashMap();
        map.put(type.name(), list);
        this.storedData.putIfAbsent(uuid.toString(), map);
    }

    public void add(UUID uuid, Map<String, List<String>> data) {
        this.storedData.put(uuid.toString(), data);
    }

    public Map<String, Map<String, List<String>>> getStoredData() {
        return this.storedData;
    }

    public void setStoredData(Map<String, Map<String, List<String>>> storedData) {
        this.storedData = storedData;
    }
}

