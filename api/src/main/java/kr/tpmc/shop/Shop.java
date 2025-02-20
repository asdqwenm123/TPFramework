package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;

public interface Shop {
    ShopItem getItem(int x, int y);
    ShopItem getItem(int index);
    void setItem(int x, int y, ShopItem item);
    void setItem(int index, ShopItem item);
    void removeItem(int x, int y);
    Component getName();
    int getLine();
    int getId();
    HashMap<ShopItemLocation, ShopItem> getItems();

    void openShop(Player player);
    void openSetting(Player player);
}
