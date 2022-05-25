package me.xd.ultimasell.commands;

import me.xd.ultimasell.UltimaSell;
import me.xd.ultimasell.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WandCommand
implements CommandExecutor {
    public static final ItemStack SELL_WAND = UltimaSell.getItemManager().getItem("sell-wand");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("\u00a7cInvalid Syntax.");
            sender.sendMessage("\u00a7cUse /" + label + " wand (player)");
        } else if (args[0].equalsIgnoreCase("wand")) {
            if (args.length == 1) {
                if (!sender.hasPermission("ultimasell.wand")) {
                    sender.sendMessage(Settings.NO_PERMISSION);
                    return true;
                }
                Player player = (Player)sender;
                for (ItemStack drop : player.getInventory().addItem(new ItemStack[]{SELL_WAND}).values()) {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
                player.sendMessage(Settings.SEND_WAND_TARGET.replace("%sender_name%", "CONSOLE"));
            } else {
                if (!sender.hasPermission("ultimasell.wand.admin")) {
                    sender.sendMessage(Settings.NO_PERMISSION);
                    return true;
                }
                Player player = Bukkit.getPlayer((String)args[1]);
                if (player == null || !player.isOnline()) {
                    sender.sendMessage(Settings.NO_PLAYER_FOUND);
                    return true;
                }
                for (ItemStack drop : player.getInventory().addItem(new ItemStack[]{SELL_WAND}).values()) {
                    player.getWorld().dropItem(player.getLocation(), drop);
                }
                sender.sendMessage(Settings.SEND_WAND_SENDER.replace("%player_name%", player.getName()));
                player.sendMessage(Settings.SEND_WAND_TARGET.replace("%sender_name%", sender.getName()));
            }
        }
        return true;
    }
}

