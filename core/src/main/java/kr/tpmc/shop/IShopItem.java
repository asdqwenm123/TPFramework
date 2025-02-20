package kr.tpmc.shop;

import kr.tpmc.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

//db 만들었기때문에 갈아엎어야함 ㅅㅂ
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

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(item);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(name);

        List<Component> lore = new ArrayList<>();

        if (itemMeta.hasLore()) {
            lore.addAll(itemMeta.lore());
        }


        if (buyingTypes.contains(ShopBuyingType.LEFT_CLICK)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(Config.config.shop_left_click);
            lore.add(component);
        }

        if (buyingTypes.contains(ShopBuyingType.RIGHT_CLICK)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(Config.config.shop_right_click);
            lore.add(component);

        }

        if (buyingTypes.contains(ShopBuyingType.SHIFT_LEFT_CLICK)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(Config.config.shop_shift_left_click);
            lore.add(component);

        }

        if (buyingTypes.contains(ShopBuyingType.SHIFT_RIGHT_CLICK)) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(Config.config.shop_shift_right_click);
            lore.add(component);

        }

        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
