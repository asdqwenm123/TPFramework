package kr.tpmc;

import kr.tpmc.shop.Shop;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TPFrameworkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        ITPFramework.onEnable(this);

        TPFramework tpFramework = Bukkit.getServicesManager().getRegistration(TPFramework.class).getProvider();
        Shop shop = tpFramework.getShopManager().createShop(Component.text("테스트"), 2);
        System.out.println(tpFramework.getShopManager().containsShop(shop.getId()));
        System.out.println(tpFramework.getShopManager().containsShop(shop.getName()));
        System.out.println(tpFramework.getShopManager().containsShop("테스트"));
    }

    @Override
    public void onDisable() {
        ITPFramework.onDisable(this);
    }
}
