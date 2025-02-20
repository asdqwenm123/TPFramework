package kr.tpmc.shop;

import kr.tpmc.db.DAO;

public class ShopDAO extends DAO<ShopEntity, Integer> {
    private static final ShopDAO instance = new ShopDAO();
    private ShopDAO() {}

    static ShopDAO getInstance() {
        return instance;
    }
}
