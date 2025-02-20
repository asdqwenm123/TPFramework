package kr.tpmc;

import kr.tpmc.config.Config;
import kr.tpmc.config.ConfigLoader;
import kr.tpmc.db.EntityLoader;
import kr.tpmc.db.HibernateLoader;
import kr.tpmc.economy.EconomyLoader;
import kr.tpmc.economy.EconomyManager;
import kr.tpmc.economy.IEconomyManager;
import kr.tpmc.loader.TPFrameworkLoader;
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

    @Override
    public EconomyManager getEconomyManager() {
        return IEconomyManager.getInstance();
    }

    public static void onReload() {
        ConfigLoader.onReload();
    }

    public static void onEnable(Plugin plugin) {
        //----api----
        TPFrameworkLoader.onEnable(plugin);
        ConfigLoader.onEnable(plugin, Config.class);
        EntityLoader.onEnable(plugin);
        HibernateLoader.onEnable(Config.config.MySQL_url, Config.config.MySQL_user, Config.config.MySQL_password);

        //----core----
        EconomyLoader.onEnable(plugin);

        Bukkit.getServicesManager().register(TPFramework.class, ITPFramework.getInstance(), plugin, ServicePriority.Normal);
    }

    public static void onDisable(Plugin plugin) {
        HibernateLoader.onDisable();
    }
}
