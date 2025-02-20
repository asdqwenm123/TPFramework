package kr.tpmc.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

public class EconomyLoader {
    private static boolean loaded = false;

    private EconomyLoader() {}

    public static boolean isLoaded() {
        return loaded;
    }

    public static void onEnable(Plugin plugin) {
        Bukkit.getServicesManager().register(Economy.class, IEconomyManager.getInstance().getEconomy(), plugin, ServicePriority.Normal);
        loaded = true;
    }
}
