package kr.tpmc.shop;

public class ShopItemLocation {
    private final int x;
    private final int y;

    private ShopItemLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static ShopItemLocation of(int x, int y) {
        return new ShopItemLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShopItemLocation other) {
            return x == other.getX() && y == other.getY();
        }
        return false;
    }
}
