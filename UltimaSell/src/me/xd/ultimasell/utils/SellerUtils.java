package me.xd.ultimasell.utils;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import me.sat7.dynamicshop.DynaShopAPI;
import me.sat7.dynamicshop.transactions.Calc;
import me.sat7.dynamicshop.utilities.ShopUtil;
import me.xd.ultimasell.hook.VaultHook;
import me.xd.ultimasell.model.SellResponse;
import me.xd.ultimasell.settings.Settings;
import me.xd.ultimasell.utils.NumberFormat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellerUtils {
    private static final Economy ECONOMY = VaultHook.getEconomy();
    private static final int INVALID_ITEM_IDX = -1;

    public static SellResponse sellItems(Player player, Inventory inventory) {
        List<ItemStack> filteredItems = Arrays.stream(inventory.getContents()).filter(Objects::nonNull).collect(Collectors.toList());
        SellResponse sellResponse = SellerUtils.sellItems(filteredItems);
        if (sellResponse.isValid()) {
            sellResponse.clearFromInventory(inventory);
            double totalQuantity = sellResponse.getTotalQuantity().get();
            double totalPrice = sellResponse.getTotalPrice().get();
            player.sendMessage(Settings.SOLD_ITEMS.replace("{items-amount}", NumberFormat.formatNumber0(totalQuantity)).replace("{total-price}", NumberFormat.formatNumber(totalPrice)));
            SellerUtils.updateStock(sellResponse.getAcceptedItems());
            ECONOMY.depositPlayer((OfflinePlayer)player, totalPrice);
        }
        return sellResponse;
    }

    private static SellResponse sellItems(List<ItemStack> items) {
        ArrayList<ItemStack> acceptedItems = new ArrayList<ItemStack>();
        AtomicDouble totalPrice = new AtomicDouble();
        AtomicDouble totalQuantity = new AtomicDouble();
        for (String shopName : DynaShopAPI.getShops()) {
            for (ItemStack item : items) {
                double price = SellerUtils.getPrice(shopName, item, false);
                if (price == -1.0) continue;
                double finalPrice = price * (double)item.getAmount();
                totalPrice.addAndGet(finalPrice);
                totalQuantity.addAndGet((double)item.getAmount());
                acceptedItems.add(item);
            }
        }
        return new SellResponse(totalPrice, totalQuantity, acceptedItems);
    }

    private static double getPrice(String shopName, ItemStack itemStack, boolean useTax) {
        if (shopName == null) {
            throw new NullPointerException("shopName is marked @NonNull but is null");
        }
        if (itemStack == null) {
            throw new NullPointerException("itemStack is marked @NonNull but is null");
        }
        if (DynaShopAPI.validateShopName((String)shopName)) {
            int idx = ShopUtil.findItemFromShop((String)shopName, (ItemStack)itemStack);
            if (idx != -1) {
                double price = Calc.getCurrentPrice((String)shopName, (String)String.valueOf(idx), (boolean)false);
                return useTax ? price - price / 100.0 * (double)DynaShopAPI.getTaxRate((String)shopName) : price;
            }
            return idx;
        }
        throw new IllegalArgumentException("Shop: " + shopName + " does not exist");
    }

    private static void updateStock(List<ItemStack> items) {
        for (String shopName : DynaShopAPI.getShops()) {
            items.forEach(item -> {
                int tradeIdx = ShopUtil.findItemFromShop((String)shopName, (ItemStack)item);
                if (tradeIdx != -1) {
                    int stockOld = ShopUtil.ccShop.get().getInt(String.valueOf(shopName) + "." + tradeIdx + ".stock");
                    if (ShopUtil.ccShop.get().getInt(String.valueOf(shopName) + "." + tradeIdx + ".stock") > 0) {
                        ShopUtil.ccShop.get().set(String.valueOf(shopName) + "." + tradeIdx + ".stock", (Object)(stockOld + item.getAmount()));
                    }
                }
            });
            ShopUtil.ccShop.save();
        }
    }
}

