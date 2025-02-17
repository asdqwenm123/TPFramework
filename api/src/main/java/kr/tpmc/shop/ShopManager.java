package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface ShopManager {
    Shop createShop(Component name, int line);
    ShopItem createShopItem(Component name, ItemStack item, int price, ShopBuyingType... buyingTypes);

    Shop getShop(String name);
    Shop getShop(Component name);
    Shop getShop(int id);

    void removeShop(Shop shop);
    void removeShop(int id);
    void removeShop(Component name);
    void removeShop(String name);
}
