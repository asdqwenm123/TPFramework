package kr.tpmc.economy;

import kr.tpmc.db.DAO;

import java.util.UUID;

public class EconomyDAO extends DAO<EconomyEntity, UUID> {
    private static final EconomyDAO instance = new EconomyDAO();
    private EconomyDAO() {}

    static EconomyDAO getInstance() {
        return instance;
    }
}
