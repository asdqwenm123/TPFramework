package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ShopItem {
    Component getName();
    @NotNull ItemStack getItem();
    int getPrice();
    Collection<ShopBuyingType> getShopBuyingTypes();
    ItemStack toItemStack();
}
