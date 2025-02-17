package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public interface Shop {
    ShopItem getItem(int x, int y);
    void setItem(int x, int y, ShopItem item);
    void removeItem(int x, int y);
    Component getName();
    int getLine();
    int getId();
}
