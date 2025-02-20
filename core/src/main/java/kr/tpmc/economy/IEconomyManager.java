package kr.tpmc.economy;

import net.milkbowl.vault.economy.Economy;

public class IEconomyManager implements EconomyManager {
    private static final IEconomyManager instance = new IEconomyManager();
    private static final Economy economy = new IEconomy();

    public static IEconomyManager getInstance() {
        return instance;
    }

    @Override
    public Economy getEconomy() {
        return economy;
    }
}
