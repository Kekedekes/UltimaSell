package me.xd.ultimasell.settings;

import me.xd.ultimasell.UltimaSell;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
    private static final FileConfiguration config = UltimaSell.getInstance().getConfig();
    public static final String SIGN_PATTERN = config.getString("settings.sign-pattern");
    public static final String SIGN_LINE = Settings.colorize(config.getString("settings.sign-line"));
    public static final String NO_PERMISSION = Settings.colorize(config.getString("messages.no-permission"));
    public static final String NO_PLAYER_FOUND = Settings.colorize(config.getString("messages.player-not-found"));
    public static final String SEND_WAND_SENDER = Settings.colorize(config.getString("messages.send-wand-sender"));
    public static final String SEND_WAND_TARGET = Settings.colorize(config.getString("messages.send-wand-target"));
    public static final String SOLD_ITEMS = Settings.colorize(config.getString("messages.sold-items"));
    public static final String NO_ITEMS = Settings.colorize(config.getString("messages.no-items"));

    private static String colorize(String str) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)str);
    }
}

