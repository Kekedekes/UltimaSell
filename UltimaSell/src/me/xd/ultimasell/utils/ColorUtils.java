package me.xd.ultimasell.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;

public class ColorUtils {
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)string);
    }

    public static List<String> colorize(String ... string) {
        return Arrays.stream(string).map(ColorUtils::colorize).collect(Collectors.toList());
    }
}

