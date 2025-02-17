package kr.tpmc.shop;

import kr.tpmc.TPFramework;
import org.bukkit.Bukkit;


// api에 넣어도 되지만 넣어야 할 이유를 모르겠음
// 파라미터에서 한번더 wrapping해야하는 불편함도 있고 x, y만으로도 직관적이라고 생각
// 하지만 코어에서는 hashmap을 기반으로 아이템을 넣기때문에 key로 쓰일 클래스가 필요하다고 생각
// hashmap이나 다른걸 기반으로하는 클래스를 구현하면 제거할수도 있음
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
