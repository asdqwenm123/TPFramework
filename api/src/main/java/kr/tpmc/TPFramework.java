package kr.tpmc;

import kr.tpmc.economy.EconomyManager;
import kr.tpmc.shop.ShopManager;

public interface TPFramework {
    ShopManager getShopManager();
    EconomyManager getEconomyManager();

    // 권장 로드 순서
    // 1. TPFrameworkLoader
    // 2. ConfigLoader (TPFrameworkLoader 필수)
    // 3. EntityLoader (TPFrameworkLoader 필수)
    // 4. HibernateLoader (TPFrameworkLoader, EntityLoader 필수)

//    static void onEnable(Plugin plugin) {
//        TPFrameworkLoader.onEnable(plugin);
//    }
}
