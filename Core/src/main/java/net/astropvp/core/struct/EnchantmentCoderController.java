package net.astropvp.core.struct;

import com.google.common.collect.Maps;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EnchantmentCoderController {
    private static EnchantmentCoderController INSTANCE;

    public static EnchantmentCoderController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnchantmentCoderController();
        }

        return INSTANCE;
    }

    private EnchantmentCoderController() {
    }

    public void writeToJSONFile(EnchantmentCoderManager inst) {
        String fileName = inst.getFileName();
        if (fileName != null && inst != null) {
            File file = new File(fileName);
            this.eraseFileContents(file);
            Map<String, Map<String, List<String>>> storedData = inst.getStoredData();
            if (!storedData.isEmpty()) {
                JSONArray toWrite = new JSONArray();
                Iterator var6 = storedData.entrySet().iterator();

                while(var6.hasNext()) {
                    Entry<String, Map<String, List<String>>> entry = (Entry)var6.next();
                    JSONObject data = new JSONObject();
                    JSONObject object = new JSONObject();
                    data.put("data", entry.getValue());
                    data.put("uuid", entry.getKey());
                    object.put("enchantmentcoder", data);
                    toWrite.add(object);
                }

                try {
                    FileWriter fileWriter = new FileWriter(fileName);
                    Throwable var22 = null;

                    try {
                        fileWriter.write(toWrite.toJSONString());
                        fileWriter.flush();
                    } catch (Throwable var18) {
                        var22 = var18;
                        throw var18;
                    } finally {
                        if (fileWriter != null) {
                            if (var22 != null) {
                                try {
                                    fileWriter.close();
                                } catch (Throwable var17) {
                                    var22.addSuppressed(var17);
                                }
                            } else {
                                fileWriter.close();
                            }
                        }

                    }
                } catch (IOException var20) {
                    var20.printStackTrace();
                }

            }
        } else {
            throw new NullPointerException(fileName == null ? "File name is NULL" : " EnchantmentCoderManager is NULL");
        }
    }

    public Map<String, Map<String, List<String>>> readFromJSONFile(String fileName) {
        Map<String, Map<String, List<String>>> ret = Maps.newHashMap();
        File file = new File(fileName);
        JSONParser parser = new JSONParser();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Throwable var6 = null;

            try {
                Object object = parser.parse((Reader)br);
                JSONArray jArr = (JSONArray)object;
                Iterator var9 = jArr.iterator();

                while(var9.hasNext()) {
                    Object o = var9.next();
                    JSONObject jo = (JSONObject)o;
                    ret.putAll(this.parseJSONObject(jo));
                }
            } catch (Throwable var20) {
                var6 = var20;
                throw var20;
            } finally {
                if (br != null) {
                    if (var6 != null) {
                        try {
                            br.close();
                        } catch (Throwable var19) {
                            var6.addSuppressed(var19);
                        }
                    } else {
                        br.close();
                    }
                }

            }
        } catch (ParseException | IOException var22) {
            var22.printStackTrace();
        }

        return ret;
    }

    private Map<String, Map<String, List<String>>> parseJSONObject(JSONObject object) {
        object = (JSONObject)object.get("enchantmentcoder");
        Map<String, List<String>> map = (Map)object.get("data");
        Map<String, Map<String, List<String>>> ret = Maps.newHashMap();
        ret.put((String)object.get("uuid"), map);
        return ret;
    }

    private void eraseFileContents(File file) {
        if (file.exists()) {
            file.delete();
        }

        try {
            file.createNewFile();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
}
