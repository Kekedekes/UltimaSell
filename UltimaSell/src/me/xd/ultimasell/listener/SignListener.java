package me.xd.ultimasell.listener;

import java.util.Optional;
import me.xd.ultimasell.model.SellResponse;
import me.xd.ultimasell.settings.Settings;
import me.xd.ultimasell.utils.SellerUtils;
import me.xd.ultimasell.utils.SignUtils;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

public class SignListener
implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player)event.getPlayer();
        Inventory inventory = event.getInventory();
        Optional<Sign> signOptional = Optional.empty();
        if (inventory.getHolder() instanceof Chest) {
            Chest chest = (Chest)inventory.getHolder();
            signOptional = SignUtils.getAttached(chest.getBlock());
        }
        if (inventory instanceof DoubleChestInventory) {
            DoubleChestInventory chestInventory = (DoubleChestInventory)inventory;
            if (chestInventory.getHolder() == null) {
                return;
            }
            signOptional = SignUtils.getAttached(chestInventory.getHolder());
        }
        signOptional.filter(SignUtils::isValid).ifPresent(sign -> {
            if (!player.hasPermission("ultimasell.sign.use")) {
                player.sendMessage(Settings.NO_PERMISSION);
                return;
            }
            SellResponse sellResponse = SellerUtils.sellItems(player, inventory);
            if (!sellResponse.isValid()) {
                player.sendMessage(Settings.NO_ITEMS);
            }
        });
    }

    @EventHandler
    public void onCreate(SignChangeEvent event) {
        String line = event.getLine(0);
        if (line != null && line.equals(Settings.SIGN_PATTERN)) {
            Player player = event.getPlayer();
            if (!player.hasPermission("ultimasell.sign.create")) {
                event.getBlock().breakNaturally();
                player.sendMessage(Settings.NO_PERMISSION);
                return;
            }
            event.setLine(0, Settings.SIGN_LINE);
        }
    }
}

