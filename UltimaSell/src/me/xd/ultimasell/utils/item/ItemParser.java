package me.xd.ultimasell.utils.item;

import java.util.ArrayList;
import me.xd.ultimasell.utils.ColorUtils;
import me.xd.ultimasell.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ItemParser {
    public ItemStack parseItem(ConfigurationSection section) {
        try {
            String material = section.getString("type");
            ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial((String)material), 1, section.contains("data") ? (short)section.getInt("data") : (short)0);
            if (section.contains("amount")) {
                itemBuilder.changeItem(item -> item.setAmount(section.getInt("amount")));
            }
            if (section.contains("glow") && section.getBoolean("glow")) {
                itemBuilder.changeItemMeta(meta -> {
                    meta.addEnchant(Enchantment.DURABILITY, 1, true);
                    meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
                });
            }
            if (section.contains("name")) {
                itemBuilder.withName(ColorUtils.colorize(section.getString("name")));
            }
            if (section.contains("lore")) {
                ArrayList<String> lore = new ArrayList<String>();
                for (String description : section.getStringList("lore")) {
                    lore.add(ColorUtils.colorize(description));
                }
                itemBuilder.setLore(lore);
            }
            return itemBuilder.wrap();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            Bukkit.getLogger().warning(String.valueOf(section.getName()) + " is invalid!");
            return null;
        }
    }
}

