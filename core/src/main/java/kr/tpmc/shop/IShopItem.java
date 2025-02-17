package kr.tpmc.shop;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class IShopItem implements ShopItem {
    private final Component name;
    private final ItemStack item;
    private final int amount;
    private final Collection<ShopBuyingType> buyingTypes;

    protected IShopItem(Component name, ItemStack item, int amount, ShopBuyingType... buyingTypes) {
        this.name = name;
        this.item = item;
        this.amount = amount;
        this.buyingTypes = new HashSet<>(Arrays.asList(buyingTypes));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return item;
    }

    @Override
    public int getPrice() {
        return amount;
    }

    @Override
    public Collection<ShopBuyingType> getShopBuyingTypes() {
        return buyingTypes;
    }
}
