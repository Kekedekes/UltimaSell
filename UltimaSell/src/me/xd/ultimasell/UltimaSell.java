/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.xd.ultimasell;

import me.xd.ultimasell.commands.WandCommand;
import me.xd.ultimasell.hook.VaultHook;
import me.xd.ultimasell.listener.ClickListener;
import me.xd.ultimasell.listener.SignListener;
import me.xd.ultimasell.utils.item.ItemManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimaSell
extends JavaPlugin {
    private static UltimaSell instance;
    private static final ItemManager itemManager;

    static {
        itemManager = new ItemManager();
    }

    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        VaultHook.load(this);
        itemManager.registerItem("sell-wand", this.getConfig().getConfigurationSection("sell-wand"));
        this.getCommand("ultimasell").setExecutor((CommandExecutor)new WandCommand());
        this.getServer().getPluginManager().registerEvents((Listener)new SignListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new ClickListener(), (Plugin)this);
    }

    public static UltimaSell getInstance() {
        return instance;
    }

    public static ItemManager getItemManager() {
        return itemManager;
    }
}

