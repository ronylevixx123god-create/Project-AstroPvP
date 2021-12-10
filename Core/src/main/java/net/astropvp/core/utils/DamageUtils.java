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
        if (!crystals.containsKey(ArmorSet.APOLLO)) {
            increase = increase + 25;
        } else {
            increase = increase + ((4 - crystals.get(ArmorSet.APOLLO)) * 5);
        }
        if (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            increase = increase + 25;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ULTRA))
                amount = amount + ((4 - crystals.get(ArmorSet.ULTRA)) * 5);
            if (crystals.containsKey(ArmorSet.CHAMPION))
                amount = amount + ((4 - crystals.get(ArmorSet.CHAMPION)) * 5);
            if (amount < 1)
                amount = 0;
            increase = increase + amount;
        }
        if (!crystals.containsKey(ArmorSet.ANCIENT) || !crystals.containsKey(ArmorSet.ASTRO) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
            increase = increase + 10;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ANCIENT))
                amount = amount + ((4 - crystals.get(ArmorSet.ANCIENT)) * 5);
            if (crystals.containsKey(ArmorSet.ASTRO))
                amount = amount + ((4 - crystals.get(ArmorSet.ASTRO)) * 5);
            if (amount < 1)
                amount = 0;
            increase = increase + amount;
        }
        return increase;
    }

    public static Double getDecreasedDamageOn(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        double decrease = 0;
        if (!crystals.containsKey(ArmorSet.APOLLO)) {
            decrease = decrease + 25;
        } else {
            decrease = decrease + ((4 - crystals.get(ArmorSet.APOLLO)) * 5);
        }
        if (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO))
            decrease = decrease + 25;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ULTRA))
                amount = amount + ((4 - crystals.get(ArmorSet.ULTRA)) * 5);
            if (crystals.containsKey(ArmorSet.CHAMPION))
                amount = amount + ((4 - crystals.get(ArmorSet.CHAMPION)) * 5);
            if (amount < 1)
                amount = 0;
            decrease = decrease + amount;
        }
        if (!crystals.containsKey(ArmorSet.ANCIENT) || !crystals.containsKey(ArmorSet.ASTRO) && (!crystals.containsKey(ArmorSet.ULTRA) || !crystals.containsKey(ArmorSet.CHAMPION) && !crystals.containsKey(ArmorSet.APOLLO)))
            decrease = decrease + 10;
        else {
            double amount = 0;
            if (crystals.containsKey(ArmorSet.ANCIENT))
                amount = amount + ((4 - crystals.get(ArmorSet.ANCIENT)) * 5);
            if (crystals.containsKey(ArmorSet.ASTRO))
                amount = amount + ((4 - crystals.get(ArmorSet.ASTRO)) * 5);
            if (amount < 1)
                amount = 0;
            decrease = decrease + amount;
        }
        return decrease;
    }


    public static boolean isInPremade(Player player) {
        Map<ArmorSet, Integer> crystals = CosmicArmorSetsAPI.getBonusCrystalsEquipped(player);
        if (crystals.containsKey(ArmorSet.SUPREME) &&
                crystals.containsKey(ArmorSet.PHANTOM) &&
                crystals.containsKey(ArmorSet.ENGINEER) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER))
            return true;
        else if (crystals.containsKey(ArmorSet.PHANTOM) &&
                crystals.containsKey(ArmorSet.SUPREME) &&
                crystals.containsKey(ArmorSet.YETI) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER)) {
            return true;

        } else if (crystals.containsKey(ArmorSet.SUPREME) &&
                crystals.containsKey(ArmorSet.PHANTOM) &&
                crystals.containsKey(ArmorSet.ENGINEER) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER)) {
            return true;

        } else if (crystals.containsKey(ArmorSet.SUPREME) &&
                crystals.containsKey(ArmorSet.YETI) &&
                crystals.containsKey(ArmorSet.KOTH) &&
                crystals.containsKey(ArmorSet.ENGINEER)) {
            return true;
        } else if (crystals.containsKey(ArmorSet.KOTH) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER) &&
                crystals.containsKey(ArmorSet.MOTHER_OF_YIJKI) &&
                crystals.containsKey(ArmorSet.PHANTOM)) {
            return true;
        } else if (crystals.containsKey(ArmorSet.KOTH) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER) &&
                crystals.containsKey(ArmorSet.DRAGON_SLAYER) &&
                crystals.containsKey(ArmorSet.PHANTOM)) {
            return true;
        }else if (crystals.containsKey(ArmorSet.ENGINEER) &&
                crystals.containsKey(ArmorSet.DIMENSIONAL_TRAVELER) &&
                crystals.containsKey(ArmorSet.DRAGON_SLAYER) &&
                crystals.containsKey(ArmorSet.YETI)) {
            return true;
        }

        return false;

    }
}
