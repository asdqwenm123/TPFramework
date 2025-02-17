package kr.tpmc;

import kr.tpmc.shop.IShopManager;
import kr.tpmc.shop.ShopManager;

public class ITPFramework implements TPFramework {
    private static final ITPFramework instance = new ITPFramework();

    private ITPFramework() {}

    public static ITPFramework getInstance() {
        return instance;
    }

    @Override
    public ShopManager getShopManager() {
        return IShopManager.getInstance();
    }
}
