package kr.tpmc.shop;

import kr.tpmc.exception.ShopException;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

//db 만들었기때문에 갈아엎어야함 ㅅㅂ
public class IShopManager implements ShopManager {
    private static final IShopManager instance = new IShopManager();
//    private static final ArrayList<Shop> shops = new ArrayList<>();

    private IShopManager() {}

    public static IShopManager getInstance() {
        return instance;
    }

    @Override
    public Shop createShop(Component name, int line) {
        if (getShop(name) != null) {
            throw new ShopException("상점이 이미 존재합니다.");
        } else {
            Shop shop = new IShop(name, line, (int) ShopDAO.getInstance().count(ShopEntity.class));
            System.out.println(shop);
            ShopDAO.getInstance().save(new ShopEntity(shop.getId(), shop.getName(), shop.getLine(), shop.getItems()));
            return shop;
        }
    }

    @Override
    public ShopItem createShopItem(Component name, ItemStack item, int price, ShopBuyingType... buyingTypes) {
        return new IShopItem(name, item, price, buyingTypes);
    }

    @Override
    public Shop getShop(String name) {
        for (ShopEntity shop : ShopDAO.getInstance().getAll(ShopEntity.class)) {
            Shop shop1 = new IShop(shop.getName(), shop.getLine(), shop.getId(), shop.getItems());
            if (shop1.getName().toString().contains(name)) {
                return shop1;
            }
        }
        return null;
    }

    @Override
    public Shop getShop(Component name) {
        for (ShopEntity shop : ShopDAO.getInstance().getAll(ShopEntity.class)) {
            Shop shop1 = new IShop(shop.getName(), shop.getLine(), shop.getId(), shop.getItems());
            if (shop1.getName().equals(name)) {
                return shop1;
            }
        }
        return null;
    }

    @Override
    public Shop getShop(int id) {
        ShopEntity shopEntity = ShopDAO.getInstance().getById(ShopEntity.class, id);
        return new IShop(shopEntity.getName(), shopEntity.getLine(), shopEntity.getId(), shopEntity.getItems());
    }

    @Override
    public void removeShop(Shop shop) {
        ShopDAO.getInstance().delete(ShopEntity.class, shop.getId());
    }

    @Override
    public void removeShop(int id) {
        ShopDAO.getInstance().delete(ShopEntity.class, id);
    }

    @Override
    public void removeShop(Component name) {
        ShopDAO.getInstance().delete(ShopEntity.class, getShop(name).getId());
    }

    @Override
    public void removeShop(String name) {
        ShopDAO.getInstance().delete(ShopEntity.class, getShop(name).getId());
    }

    @Override
    public boolean containsShop(Shop shop) {
        return ShopDAO.getInstance().existsById(ShopEntity.class, shop.getId());
    }

    @Override
    public boolean containsShop(Component name) {
        return getShop(name) != null;
    }

    @Override
    public boolean containsShop(int id) {
        return getShop(id) != null;
    }

    @Override
    public boolean containsShop(String name) {
        return getShop(name) != null;
    }
}
