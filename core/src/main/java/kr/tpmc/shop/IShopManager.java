package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class IShopManager implements ShopManager {
    private static final IShopManager instance = new IShopManager();
    private static final ArrayList<Shop> shops = new ArrayList<>();

    private IShopManager() {}

    public static IShopManager getInstance() {
        return instance;
    }

    @Override
    public Shop createShop(Component name, int line) {
        if (getShop(name) != null) {
            throw new ShopException("상점이 이미 존재합니다.");
        } else {
            Shop shop = new IShop(name, line, shops.size());
            shops.add(shop);
            return shop;
        }
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

    @Override
    public void removeShop(Shop shop) {
        shops.remove(shop);
    }

    @Override
    public void removeShop(int id) {
        shops.remove(id);
    }

    @Override
    public void removeShop(Component name) {
        shops.remove(getShop(name));
    }

    @Override
    public void removeShop(String name) {
        shops.remove(getShop(name));
    }
}
