package kr.tpmc;

import kr.tpmc.config.ConfigLoader;
import kr.tpmc.db.EntityLoader;
import kr.tpmc.db.HibernateUtil;
import kr.tpmc.shop.IShopManager;
import kr.tpmc.shop.ShopManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

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

    public static void onReload(Plugin plugin) {
        ConfigLoader.onReload(plugin);
    }

    public static void onEnable(Plugin plugin) {
        ConfigLoader.onEnable(plugin);
        EntityLoader.onEnable(plugin);
        HibernateUtil.onEnable();

        Bukkit.getServicesManager().register(TPFramework.class, ITPFramework.getInstance(), plugin, ServicePriority.Normal);
    }

    public static void onDisable(Plugin plugin) {
        HibernateUtil.onDisable();
    }
}
