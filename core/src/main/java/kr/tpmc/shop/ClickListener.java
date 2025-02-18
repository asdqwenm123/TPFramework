package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class ClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Component title = e.getView().title();

        if (IShopManager.getInstance().containsShop(title)) {
            e.setCancelled(true);
        }

        Shop shop = IShopManager.getInstance().getShop(title);
        ShopItem item = shop.getItem(e.getSlot());

        if (e.isShiftClick()) {
            if (e.isLeftClick()) {
                if (item.getShopBuyingTypes().contains(ShopBuyingType.LEFT_CLICK)) {

                }
            } else if (e.isRightClick()) {
                if (item.getShopBuyingTypes().contains(ShopBuyingType.RIGHT_CLICK)) {

                }
            }
        } else {
            if (e.isLeftClick()) {
                if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_LEFT_CLICK)) {

                }
            } else if (e.isRightClick()) {
                if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_RIGHT_CLICK)) {

                }
            }
        }


    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Component title = e.getView().title();

        if (IShopManager.getInstance().containsShop(title)) {
            e.setCancelled(true);
        }
    }
}
