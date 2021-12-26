package net.astropvp.giftcardshop.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class GiftcardData {
    private final Map<UUID, List<String>> giftcardDataMap;

    public GiftcardData() {
        this.giftcardDataMap = new HashMap<>();
    }


    public HashMap<String, Double> getCodesAndAmountMap(UUID uuid) {
        HashMap<String, Double> codesAmountMap = new HashMap<>();
        for (String codeAndAmount : giftcardDataMap.get(uuid)) {
            String code = codeAndAmount.split(":")[0];
            double amount = Double.parseDouble(codeAndAmount.split(":")[1]);
            codesAmountMap.put(code, amount);
        }
        return codesAmountMap;
    }

}
