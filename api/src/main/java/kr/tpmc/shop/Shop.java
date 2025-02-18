package kr.tpmc.shop;

import net.kyori.adventure.text.Component;

public interface Shop {
    ShopItem getItem(int x, int y);
    ShopItem getItem(int index);
    void setItem(int x, int y, ShopItem item);
    void removeItem(int x, int y);
    Component getName();
    int getLine();
    int getId();
}
