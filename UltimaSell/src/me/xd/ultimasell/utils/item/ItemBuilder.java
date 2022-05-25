package me.xd.ultimasell.utils.item;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import me.xd.ultimasell.utils.ColorUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
    private ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder(Material type) {
        this(new ItemStack(type));
    }

    public ItemBuilder(Material type, int quantity, short data) {
        this(new ItemStack(type, quantity, data));
    }

    public ItemBuilder changeItemMeta(Consumer<ItemMeta> consumer) {
        ItemMeta itemMeta = this.item.getItemMeta();
        consumer.accept(itemMeta);
        this.item.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
        consumer.accept(this.item);
        return this;
    }

    public ItemBuilder withName(String name) {
        return this.changeItemMeta(it -> it.setDisplayName(ColorUtils.colorize(name)));
    }

    public ItemBuilder setLore(String ... lore) {
        return this.changeItemMeta(it -> it.setLore(ColorUtils.colorize(lore)));
    }

    public ItemBuilder setLore(List<String> lore) {
        return this.changeItemMeta(it -> it.setLore(lore));
    }

    public ItemBuilder withLore(List<String> lore) {
        if (lore == null || lore.isEmpty()) {
            return this;
        }
        List list = this.item.getItemMeta().getLore();
        return this.changeItemMeta(meta -> {
            list.addAll(lore);
            meta.setLore(list);
        });
    }

    private String applyPlaceholder(String text, String[] placeholders, String[] replacers) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        if (placeholders != null && placeholders.length > 0 && placeholders.length == replacers.length) {
            text = StringUtils.replaceEach((String)text, (String[])placeholders, (String[])replacers);
        }
        return text;
    }

    public ItemStack build(String[] placeholder, String[] replace) {
        ItemStack item = this.item;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasDisplayName()) {
            this.withName(this.applyPlaceholder(meta.getDisplayName(), placeholder, replace));
        }
        if (meta.hasLore()) {
            this.setLore(meta.getLore().stream().map(s -> this.applyPlaceholder((String)s, placeholder, replace)).collect(Collectors.toList()));
        }
        return this.item;
    }

    public ItemStack wrap() {
        return this.item;
    }
}

