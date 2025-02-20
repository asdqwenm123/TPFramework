package kr.tpmc.shop;

import kr.tpmc.economy.EconomyEntity;
import kr.tpmc.economy.EconomyManager;
import kr.tpmc.economy.IEconomy;
import kr.tpmc.economy.IEconomyManager;
import kr.tpmc.exception.ShopException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Component title = e.getView().title();

        if (e.getClickedInventory() != null && e.getView().getTopInventory() == e.getClickedInventory()) {
            if (IShopManager.getInstance().containsShop(title)) {
                e.setCancelled(true);
                Shop shop = IShopManager.getInstance().getShop(title);
                ShopItem item = shop.getItem(e.getSlot());
                OfflinePlayer player = (Player) e.getWhoClicked();
                Economy economy = IEconomyManager.getInstance().getEconomy();


                if (e.isShiftClick()) {
                    if (canHoldItem(player.getPlayer(), item.getItem(), 1)) {
//            player.getPlayer().playSound();
                        e.setCancelled(true);
                        return;
                    }

                    if (e.isLeftClick()) {
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.LEFT_CLICK)) {
                            if (economy.has(player, item.getPrice())) {
                                economy.withdrawPlayer(player, item.getPrice());
//                      player.getPlayer().playSound();
                                player.getPlayer().getInventory().addItem(item.getItem());
                                return;
                            }
                        }
                    } else if (e.isRightClick()) {
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.RIGHT_CLICK)) {

                        }
                    }
                } else {
                    if (canHoldItem(player.getPlayer(), item.getItem(), 64)) {
//            player.getPlayer().playSound();
                        player.getPlayer().closeInventory();
                        e.setCancelled(true);
                        return;
                    }

                    if (e.isLeftClick()) {
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_LEFT_CLICK)) {
                            if (economy.has(player, ShopBuyingType.SHIFT_LEFT_CLICK.getAmount() * item.getPrice())) {
                                economy.withdrawPlayer(player, ShopBuyingType.SHIFT_LEFT_CLICK.getAmount() * item.getPrice());
//                      player.getPlayer().playSound();
                                for (int i = 0; i < ShopBuyingType.SHIFT_LEFT_CLICK.getAmount(); i++) {
                                    player.getPlayer().getInventory().addItem(item.getItem());
                                }
                                return;
                            }
                        }
                    } else if (e.isRightClick()) {
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_RIGHT_CLICK)) {

                        }
                    }
                }
                return;
            } else if (LegacyComponentSerializer.legacyAmpersand().serialize(title).contains(" - 아이템 설정")) {
                String sTitle = LegacyComponentSerializer.legacyAmpersand().serialize(title).split(" - 아이템 설정")[0];

                if (IShopManager.getInstance().containsShop(sTitle)) {
                    e.setCancelled(true);

                    Shop shop = IShopManager.getInstance().getShop(sTitle);
                    ShopItem item = shop.getItem(e.getSlot());

                    if (e.isLeftClick()) {
                        Player player = (Player) e.getWhoClicked();

                        if (e.getSlot() == 8) {
                            Inventory inventory = Bukkit.createInventory(null, InventoryType.ANVIL, shop.getName().append(Component.text(" - 아이템 가격 설정")));
                            ItemStack itemStack = new ItemStack(item.getItem().getType());
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("가격을 작성하세요."));
                            itemMeta.lore(List.of(Component.text(e.getSlot())));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(0, itemStack);
                            player.openInventory(inventory);
                        } else if (e.getSlot() == 0) {
                            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().add(ShopBuyingType.LEFT_CLICK);
                            } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().remove(ShopBuyingType.LEFT_CLICK);
                            }
                        } else if (e.getSlot() == 1) {
                            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().add(ShopBuyingType.RIGHT_CLICK);
                            } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().remove(ShopBuyingType.RIGHT_CLICK);
                            }
                        } else if (e.getSlot() == 2) {
                            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().add(ShopBuyingType.SHIFT_LEFT_CLICK);
                            } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().remove(ShopBuyingType.SHIFT_LEFT_CLICK);
                            }
                        } else if (e.getSlot() == 3) {
                            if (e.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().add(ShopBuyingType.SHIFT_RIGHT_CLICK);
                            } else if (e.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE) {
                                ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                                itemStack.setItemMeta(itemMeta);
                                e.getClickedInventory().setItem(0, itemStack);
                                item.getShopBuyingTypes().remove(ShopBuyingType.SHIFT_RIGHT_CLICK);
                            }
                        }
                    }
                }
                return;
            } else if (LegacyComponentSerializer.legacyAmpersand().serialize(title).contains(" - 설정")) {
                String sTitle = LegacyComponentSerializer.legacyAmpersand().serialize(title).split(" - 설정")[0];

                if (IShopManager.getInstance().containsShop(sTitle)) {
                    e.setCancelled(true);

                    Shop shop = IShopManager.getInstance().getShop(sTitle);
                    ShopItem item = shop.getItem(e.getSlot());

                    if (e.isShiftClick() && e.isLeftClick()) {
                        Player player = (Player) e.getWhoClicked();

                        Inventory inventory = Bukkit.createInventory(null, 9, shop.getName().append(Component.text(" - 아이템 설정")));
                        ItemStack itemStack = new ItemStack(item.getItem().getType());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("가격 설정"));
                        itemMeta.lore(List.of(Component.text(e.getSlot())));
                        itemStack.setItemMeta(itemMeta);
                        inventory.setItem(8, itemStack);

                        if (item.getShopBuyingTypes().contains(ShopBuyingType.LEFT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(0, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(0, itemStack);
                        }
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.RIGHT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(1, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(1, itemStack);
                        }
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_LEFT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(2, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(2, itemStack);
                        }
                        if (item.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_RIGHT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(3, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(3, itemStack);
                        }

                        player.openInventory(inventory);
                    }
                }
                return;
            } else if (LegacyComponentSerializer.legacyAmpersand().serialize(title).contains(" - 아이템 가격 설정")) {
                String sTitle = LegacyComponentSerializer.legacyAmpersand().serialize(title).split(" - 아이템 가격 설정")[0];

                if (IShopManager.getInstance().containsShop(sTitle)) {
                    e.setCancelled(true);

                    if (e.getSlot() == 2) {
                        Shop shop = IShopManager.getInstance().getShop(sTitle);

                        String priceString = PlainTextComponentSerializer.plainText().serialize(e.getCurrentItem().getItemMeta().displayName());

                        try {
                            Integer.parseInt(priceString);
                        } catch (NumberFormatException ex) {
                            throw new ShopException("잘못된 가격");
                        }

                        int price = Integer.parseInt(priceString);
                        int slot = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(e.getCurrentItem().getItemMeta().lore().get(0)));

                        ShopItem shopItem = IShopManager.getInstance().createShopItem(e.getCurrentItem().getItemMeta().displayName(), e.getCurrentItem(), price, (ShopBuyingType) null);
                        shop.setItem(slot, shopItem);

                        e.getWhoClicked().closeInventory();


                        //------아이템 설정 다시 열기------
                        Inventory inventory = Bukkit.createInventory(null, 9, shop.getName().append(Component.text(" - 아이템 설정")));
                        ItemStack itemStack = new ItemStack(shopItem.getItem().getType());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.displayName(Component.text("가격 설정"));
                        itemMeta.lore(List.of(Component.text(e.getSlot())));
                        itemStack.setItemMeta(itemMeta);
                        inventory.setItem(8, itemStack);

                        if (shopItem.getShopBuyingTypes().contains(ShopBuyingType.LEFT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(0, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(0, itemStack);
                        }
                        if (shopItem.getShopBuyingTypes().contains(ShopBuyingType.RIGHT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(1, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(1, itemStack);
                        }
                        if (shopItem.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_LEFT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(2, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(2, itemStack);
                        }
                        if (shopItem.getShopBuyingTypes().contains(ShopBuyingType.SHIFT_RIGHT_CLICK)) {
                            itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하지 않기").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(3, itemStack);
                        } else {
                            itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            itemMeta = itemStack.getItemMeta();
                            itemMeta.displayName(Component.text("적용하기").color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
                            itemStack.setItemMeta(itemMeta);
                            inventory.setItem(3, itemStack);
                        }

                        e.getWhoClicked().openInventory(inventory);
                    }
                }
                return;
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Component title = e.getView().title();

        if (IShopManager.getInstance().containsShop(title)) {
            e.setCancelled(true);
        }
    }

    private boolean canHoldItem(Player player, ItemStack itemStack, int amount) {
        PlayerInventory inventory = player.getInventory();
        int maxStackSize = itemStack.getMaxStackSize();

        for (int i = 0; i < 36; i++) {
            ItemStack slotItem = inventory.getItem(i);

            if (slotItem == null || slotItem.getType().isAir()) {
                amount -= maxStackSize;
            } else if (slotItem.isSimilar(itemStack)) {
                int spaceLeft = maxStackSize - slotItem.getAmount();
                amount -= spaceLeft;
            }

            if (amount <= 0) {
                return true;
            }
        }

        return false;
    }
}
