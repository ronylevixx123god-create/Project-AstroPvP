package net.astropvp.core.struct;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class EnchantmentCoderController {
    private static EnchantmentCoderController INSTANCE;

    public static EnchantmentCoderController getInstance() {
        if (INSTANCE == null)
            INSTANCE = new EnchantmentCoderController();
        return INSTANCE;
    }

    public void writeToJSONFile(EnchantmentCoderManager inst) {
        String fileName = inst.getFileName();
        if (fileName == null)
            throw new NullPointerException("File name is NULL");
        File file = new File(fileName);
        eraseFileContents(file);
        Map<String, Map<String, List<String>>> storedData = inst.getStoredData();
        if (storedData.isEmpty())
            return;
        JSONArray toWrite = new JSONArray();
        for (Map.Entry<String, Map<String, List<String>>> entry : storedData.entrySet()) {
            JSONObject data = new JSONObject();
            JSONObject object = new JSONObject();
            data.put("data", entry.getValue());
            data.put("uuid", entry.getKey());
            object.put("enchantmentcoder", data);
            toWrite.add(object);
        }
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(toWrite.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<String, List<String>>> readFromJSONFile(String fileName) {
        Map<String, Map<String, List<String>>> ret = Maps.newHashMap();
        File file = new File(fileName);
        JSONParser parser = new JSONParser();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Object object = parser.parse(br);
            JSONArray jArr = (JSONArray)object;
            for (Object o : jArr) {
                JSONObject jo = (JSONObject)o;
                ret.putAll(parseJSONObject(jo));
            }
        } catch (IOException|org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private Map<String, Map<String, List<String>>> parseJSONObject(JSONObject object) {
        object = (JSONObject)object.get("enchantmentcoder");
        Map<String, List<String>> map = (Map<String, List<String>>)object.get("data");
        Map<String, Map<String, List<String>>> ret = Maps.newHashMap();
        ret.put((String)object.get("uuid"), map);
        return ret;
    }

    private void eraseFileContents(File file) {
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

