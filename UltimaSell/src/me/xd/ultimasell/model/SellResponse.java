package me.xd.ultimasell.model;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.List;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SellResponse {
    private final AtomicDouble totalPrice;
    private final AtomicDouble totalQuantity;
    private final List<ItemStack> acceptedItems;

    public void clearFromInventory(Inventory inventory) {
        inventory.removeItem(this.acceptedItems.toArray(new ItemStack[0]));
    }

    public boolean isValid() {
        return this.totalPrice.get() != 0.0 && !this.acceptedItems.isEmpty();
    }

    public AtomicDouble getTotalPrice() {
        return this.totalPrice;
    }

    public AtomicDouble getTotalQuantity() {
        return this.totalQuantity;
    }

    public List<ItemStack> getAcceptedItems() {
        return this.acceptedItems;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SellResponse)) {
            return false;
        }
        SellResponse other = (SellResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        AtomicDouble this$totalPrice = this.getTotalPrice();
        AtomicDouble other$totalPrice = other.getTotalPrice();
        if (this$totalPrice == null ? other$totalPrice != null : !this$totalPrice.equals(other$totalPrice)) {
            return false;
        }
        AtomicDouble this$totalQuantity = this.getTotalQuantity();
        AtomicDouble other$totalQuantity = other.getTotalQuantity();
        if (this$totalQuantity == null ? other$totalQuantity != null : !this$totalQuantity.equals(other$totalQuantity)) {
            return false;
        }
        List<ItemStack> this$acceptedItems = this.getAcceptedItems();
        List<ItemStack> other$acceptedItems = other.getAcceptedItems();
        return !(this$acceptedItems == null ? other$acceptedItems != null : !((Object)this$acceptedItems).equals(other$acceptedItems));
    }

    protected boolean canEqual(Object other) {
        return other instanceof SellResponse;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        AtomicDouble $totalPrice = this.getTotalPrice();
        result = result * 59 + ($totalPrice == null ? 43 : $totalPrice.hashCode());
        AtomicDouble $totalQuantity = this.getTotalQuantity();
        result = result * 59 + ($totalQuantity == null ? 43 : $totalQuantity.hashCode());
        List<ItemStack> $acceptedItems = this.getAcceptedItems();
        result = result * 59 + ($acceptedItems == null ? 43 : ((Object)$acceptedItems).hashCode());
        return result;
    }

    public String toString() {
        return "SellResponse(totalPrice=" + this.getTotalPrice() + ", totalQuantity=" + this.getTotalQuantity() + ", acceptedItems=" + this.getAcceptedItems() + ")";
    }

    public SellResponse(AtomicDouble totalPrice, AtomicDouble totalQuantity, List<ItemStack> acceptedItems) {
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.acceptedItems = acceptedItems;
    }
}

