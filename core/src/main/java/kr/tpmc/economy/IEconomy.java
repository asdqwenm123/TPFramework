package kr.tpmc.economy;

import jakarta.transaction.NotSupportedException;
import kr.tpmc.config.Config;
import kr.tpmc.db.EntityLoader;
import kr.tpmc.db.HibernateLoader;
import kr.tpmc.exception.NotInitializedException;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.util.List;

public class IEconomy implements Economy {
    @Override
    public boolean isEnabled() {
        return HibernateLoader.isLoaded() && EntityLoader.isLoaded();
    }

    @Override
    public String getName() {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return "TPFramework";
    }

    @Override
    public boolean hasBankSupport() {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public int fractionalDigits() {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return 0;
    }

    @Override
    public String format(double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(v);
    }

    @Override
    public String currencyNamePlural() {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return Config.config.economy_name;
    }

    @Override
    public String currencyNameSingular() {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return Config.config.economy_name;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(s);

        if (player == null) return false;

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, player.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(player.getUniqueId(), player.getUniqueId(), 0));
        }

        return true;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, offlinePlayer.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(offlinePlayer.getUniqueId(), offlinePlayer.getUniqueId(), 0));
        }

        return false;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean hasAccount(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public double getBalance(String s) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(s);

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, player.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(player.getUniqueId(), player.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, player.getUniqueId());

        return economy.getAmount();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, offlinePlayer.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(offlinePlayer.getUniqueId(), offlinePlayer.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, offlinePlayer.getUniqueId());

        return economy.getAmount();
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public double getBalance(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return getBalance(s) >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        return getBalance(offlinePlayer) >= v;
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public boolean has(String s, String s1, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(s);

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, player.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(player.getUniqueId(), player.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, player.getUniqueId());

        if (v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "출금할 금액이 음수입니다");
        }

        if (economy.getAmount() - v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "출금할 금액이 예금된 금액보다 큽니다");
        } else {
            economy.setAmount(economy.getAmount() - v);
            EconomyDAO.getInstance().save(economy);
            return new EconomyResponse(-v, economy.getAmount(), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");


        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, offlinePlayer.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(offlinePlayer.getUniqueId(), offlinePlayer.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, offlinePlayer.getUniqueId());

        if (v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "출금할 금액이 음수입니다");
        }

        if (economy.getAmount() - v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "출금할 금액이 예금된 금액보다 큽니다");
        } else {
            economy.setAmount(economy.getAmount() - v);
            EconomyDAO.getInstance().save(economy);
            return new EconomyResponse(-v, economy.getAmount(), EconomyResponse.ResponseType.SUCCESS, null);
        }
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(s);

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, player.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(player.getUniqueId(), player.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, player.getUniqueId());

        if (v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "입금할 금액이 음수입니다");
        }


        economy.setAmount(economy.getAmount() + v);
        EconomyDAO.getInstance().save(economy);
        return new EconomyResponse(v, economy.getAmount(), EconomyResponse.ResponseType.SUCCESS, null);

    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, offlinePlayer.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(offlinePlayer.getUniqueId(), offlinePlayer.getUniqueId(), 0));
        }

        EconomyEntity economy = EconomyDAO.getInstance().getById(EconomyEntity.class, offlinePlayer.getUniqueId());

        if (v < 0) {
            return new EconomyResponse(0, economy.getAmount(), EconomyResponse.ResponseType.FAILURE, "입금할 금액이 음수입니다");
        }


        economy.setAmount(economy.getAmount() + v);
        EconomyDAO.getInstance().save(economy);
        return new EconomyResponse(v, economy.getAmount(), EconomyResponse.ResponseType.SUCCESS, null);
    }

    /**
     * @param s
     * @param s1
     * @param v
     * @deprecated
     */
    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse createBank(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public List<String> getBanks() {
        throw new NotImplementedException("지원하지 않습니다");
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(s);

        if (player == null) return false;

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, player.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(player.getUniqueId(), player.getUniqueId(), 0));
        }

        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        if (!isEnabled()) throw new NotInitializedException("HibernateLoader, TPFrameworkLoader 부터 로드해 주세요");

        if (!EconomyDAO.getInstance().existsById(EconomyEntity.class, offlinePlayer.getUniqueId())) {
            EconomyDAO.getInstance().save(new EconomyEntity(offlinePlayer.getUniqueId(), offlinePlayer.getUniqueId(), 0));
        }

        return true;
    }

    /**
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public boolean createPlayerAccount(String s, String s1) {
        throw new NotImplementedException("지원하지 않습니다");
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        throw new NotImplementedException("지원하지 않습니다");
    }
}
