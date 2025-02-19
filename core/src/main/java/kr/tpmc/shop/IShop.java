package kr.tpmc.shop;

import kr.tpmc.exception.ShopException;
import net.kyori.adventure.text.Component;

import java.util.HashMap;

//db 만들었기때문에 갈아엎어야함 ㅅㅂ
public class IShop implements Shop {
    private final Component name;
    private final int line;
    private final int id;

    private final HashMap<ShopItemLocation, ShopItem> items;

    protected IShop(Component name, int line, int id) {
        this.name = name;
        if (line <= 0) {
            throw new ShopException("라인 수는 0 이하가 되면 안 됩니다.");
        } else if (line > 6) {
            throw new ShopException("라인 수는 6 초과(7 이상)가 되면 안 됩니다.");
        }
        this.line = line;
        this.id = id;
        this.items = new HashMap<>();
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public int getLine() {
        return line;
    }

    public int getId() {
        return id;
    }

    @Override
    public ShopItem getItem(int x, int y) {
        if (x >= 9) {
            throw new ShopException("x가 9 이상 되면 안 됩니다.");
        } else if (y >= line) {
            throw new ShopException("y가 " + line + " 이상 되면 안 됩니다.");
        }
        return items.get(ShopItemLocation.of(x, y));
    }

    @Override
    public ShopItem getItem(int index) {
        return getItem(index / 9, index % 9);
    }

    @Override
    public void setItem(int x, int y, ShopItem item) {
        if (x >= 9) {
            throw new ShopException("x가 9 이상 되면 안 됩니다.");
        } else if (y >= line) {
            throw new ShopException("y가 " + line + " 이상 되면 안 됩니다.");
        }
        items.put(ShopItemLocation.of(x, y), item);
    }

    @Override
    public void removeItem(int x, int y) {
        if (x >= 9) {
            throw new ShopException("x가 9 이상 되면 안 됩니다.");
        } else if (y >= line) {
            throw new ShopException("y가 " + line + " 이상 되면 안 됩니다.");
        }
        items.remove(ShopItemLocation.of(x, y));
    }
}
