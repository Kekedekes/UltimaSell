package me.xd.ultimasell.listener;

import me.xd.ultimasell.UltimaSell;
import me.xd.ultimasell.model.SellResponse;
import me.xd.ultimasell.settings.Settings;
import me.xd.ultimasell.utils.SellerUtils;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickListener
implements Listener {
    public static final ItemStack SELL_WAND = UltimaSell.getItemManager().getItem("sell-wand");

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getItem() == null) {
            return;
        }
        if (!event.getItem().isSimilar(SELL_WAND)) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.CHEST && event.getClickedBlock().getState() instanceof Chest) {
            Player player = event.getPlayer();
            Chest chest = (Chest)event.getClickedBlock().getState();
            if (!player.hasPermission("ultimasell.wand.use")) {
                player.sendMessage(Settings.NO_PERMISSION);
                return;
            }
            SellResponse sellResponse = SellerUtils.sellItems(player, chest.getInventory());
            if (!sellResponse.isValid()) {
                player.sendMessage(Settings.NO_ITEMS);
            }
            event.setCancelled(true);
        }
    }
}

