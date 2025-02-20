package kr.tpmc.shop;


import java.util.Objects;

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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShopItemLocation other = (ShopItemLocation) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
