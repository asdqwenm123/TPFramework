package kr.tpmc;

import kr.tpmc.db.DAO;

public class testDAO extends DAO<test> {
    private static final testDAO instance = new testDAO();
    private testDAO() {}

    public static testDAO getInstance() {
        return instance;
    }
}
