package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class IShopManager implements ShopManager {
    private static final ArrayList<Shop> shops = new ArrayList<>();

    private IShopManager() {}

    @Override
    public Shop createShop(Component name, int line) {
        Shop shop = new IShop(name, line, shops.size());
        shops.add(shop);
        return shop;
    }

    @Override
    public ShopItem createShopItem(Component name, ItemStack item, int price, ShopBuyingType... buyingTypes) {
        return new IShopItem(name, item, price, buyingTypes);
    }

    @Override
    public Shop getShop(String name) {
        for (Shop shop : shops) {
            if (shop.getName().toString().contains(name)) {
                return shop;
            }
        }
        return null;
    }

    @Override
    public Shop getShop(Component name) {
        for (Shop shop : shops) {
            if (shop.getName().equals(name)) {
                return shop;
            }
        }
        return null;
    }

    @Override
    public Shop getShop(int id) {
        return shops.get(id);
    }
}
