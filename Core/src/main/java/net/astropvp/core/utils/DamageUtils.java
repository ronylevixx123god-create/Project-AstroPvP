package net.astropvp.core.utils;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class DamageUtils {
    public static void setDamageIncrease(double amountInPercentage, EntityDamageByEntityEvent event) {
        double increaseAmount = getCorrectIncreasePercentage(amountInPercentage);
        double oldDamage = event.getDamage();
        double newDamage = oldDamage * increaseAmount;
        if (!(event.getEntity() instanceof Player))
            return;
        if (!(event.getDamager() instanceof Player))
            return;
        if (event.getDamage() > 150.0D)
            newDamage = 150.0D;

        event.setDamage(newDamage);
    }

    public static void setDamageReduce(double amountInPercentage, EntityDamageByEntityEvent event) {
        double decreaseAmount = getCorrectDecreasePercentage(amountInPercentage);
        double newDamage = event.getDamage() * decreaseAmount;

        event.setDamage(newDamage);
    }

    public static void setDamageIncrease(double amountInPercentage, EntityDamageEvent event) {
        double increaseAmount = getCorrectIncreasePercentage(amountInPercentage);
        double oldDamage = event.getDamage();
        double newDamage = oldDamage * increaseAmount;

        event.setDamage(newDamage);
    }

    public static void setDamageReduce(double amountInPercentage, EntityDamageEvent event) {
        double decreaseAmount = getCorrectDecreasePercentage(amountInPercentage);
        double newDamage = event.getDamage() * decreaseAmount;
        event.setDamage(newDamage);
    }

    private static Double getCorrectIncreasePercentage(double value) {
        if (value < 100.0D)
            return 0.01D * value + 1.0D;
        return 0.01D * value;
    }

    private static Double getCorrectDecreasePercentage(double value) {
        value = 0.01D * value;
        value = 1.0D - value;
        return value;
    }

    public static Double getIncreasedDamageOn(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        double increase = 0;
        if(!crystals.containsKey(ArmorSet.APOLLO)) {
            increase = increase + 50;
        } else {
            increase = increase + ((4 - crystals.get(ArmorSet.APOLLO)) * 10);
        }
        if(!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            increase = increase + 50;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ULTRA))
                amount = amount + ((4 - crystals.get(ArmorSet.ULTRA)) * 10);
            if (crystals.containsKey(ArmorSet.CHAMPION))
                amount = amount + ((4 - crystals.get(ArmorSet.CHAMPION)) * 10);
            if (amount < 1)
                amount = 0;
            increase = increase + amount;
        }
            if (!crystals.containsKey(ArmorSet.ANCIENT) || !crystals.containsKey(ArmorSet.ASTRO) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
                increase = increase + 20;
            else {
                double amount = 0;
                if (crystals.containsKey(ArmorSet.ANCIENT))
                    amount = amount + ((4 - crystals.get(ArmorSet.ANCIENT)) * 10);
                if (crystals.containsKey(ArmorSet.ASTRO))
                    amount = amount + ((4 - crystals.get(ArmorSet.ASTRO)) * 10);
                if (amount < 1)
                    amount = 0;
                increase = increase + amount;
            }
            return increase;
    }

    public static Double getDecreasedDamageOn(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        double decrease = 0;
        if(!crystals.containsKey(ArmorSet.APOLLO)) {
            decrease = decrease + 50;
        } else {
            decrease = decrease + ((4 - crystals.get(ArmorSet.APOLLO)) * 10);
        }
        if(!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            decrease = decrease + 50;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ULTRA))
                amount = amount + ((4 - crystals.get(ArmorSet.ULTRA)) * 10);
            if (crystals.containsKey(ArmorSet.CHAMPION))
                amount = amount + ((4 - crystals.get(ArmorSet.CHAMPION)) * 10);
            if (amount < 1)
                amount = 0;
            decrease = decrease + amount;
        }
        if (!crystals.containsKey(ArmorSet.ANCIENT) || !crystals.containsKey(ArmorSet.ASTRO) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
            decrease = decrease + 20;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ANCIENT))
                amount = amount + ((4 - crystals.get(ArmorSet.ANCIENT)) * 10);
            if (crystals.containsKey(ArmorSet.ASTRO))
                amount = amount + ((4 - crystals.get(ArmorSet.ASTRO)) * 10);
            if (amount < 1)
                amount = 0;
            decrease = decrease + amount;
        }
        return decrease;
    }
}
