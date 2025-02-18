package kr.tpmc;

import kr.tpmc.loader.TPFrameworkLoader;
import kr.tpmc.shop.ShopManager;
import org.bukkit.plugin.Plugin;

public interface TPFramework {
    ShopManager getShopManager();

    static void onEnable(Plugin plugin) {
        TPFrameworkLoader.onEnable(plugin);
    }
}
