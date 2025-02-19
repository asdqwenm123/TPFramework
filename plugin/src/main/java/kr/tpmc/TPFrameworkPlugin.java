package kr.tpmc;

import org.bukkit.plugin.java.JavaPlugin;

public class TPFrameworkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        TPFramework.onEnable(this);
        ITPFramework.onEnable(this);

        testDAO.getInstance().save(new test("asd"));
        System.out.println(testDAO.getInstance().getAll(test.class));
    }

    @Override
    public void onDisable() {
        ITPFramework.onDisable(this);
    }
}
