package kr.tpmc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class TPFrameworkPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // api 등록
        Bukkit.getServicesManager().register(TPFramework.class, ITPFramework.getInstance(), this, ServicePriority.Normal);
    }
}
