package me.xd.ultimasell.utils.item;

import java.util.HashMap;
import java.util.Map;
import me.xd.ultimasell.utils.item.ItemParser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    private final ItemParser itemParser = new ItemParser();
    private final Map<String, ItemStack> items = new HashMap<String, ItemStack>();

    public void registerItem(String identifier, ConfigurationSection section) {
        this.items.put(identifier, this.itemParser.parseItem(section));
    }

    public ItemStack getItem(String identifier) {
        return this.items.get(identifier);
    }
}

