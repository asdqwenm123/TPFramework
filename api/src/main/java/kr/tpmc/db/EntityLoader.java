package kr.tpmc.db;

import jakarta.persistence.Entity;
import kr.tpmc.config.ConfigLoader;
import kr.tpmc.exception.NotInitializedException;
import kr.tpmc.loader.TPFrameworkLoader;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.jar.JarFile;

public class EntityLoader {
    private static boolean loaded = false;

    private EntityLoader() {}

    public static boolean isLoaded() {
        return loaded;
    }

    public static void onEnable(Plugin plugin) {
        if (!TPFrameworkLoader.isLoaded()) throw new NotInitializedException("TPFrameworkLoader 부터 로드해 주세요");

        String mainClass = plugin.getPluginMeta().getMainClass();
        int lastDotIndex = mainClass.lastIndexOf('.');

        if (lastDotIndex == -1) throw new RuntimeException("패키지 이름 오류");

        String packageName = mainClass.substring(0, lastDotIndex).replace('.', '/');

        try {
            File pluginFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

            try (JarFile jarFile = new JarFile(pluginFile)) {

                jarFile.stream()
                        .filter(entry -> entry.getName().startsWith(packageName) && entry.getName().endsWith(".class"))
                        .forEach(entry -> {
                            try {
                                String className = entry.getName().replace('/', '.').replace(".class", "");
                                Class<?> clazz = Class.forName(className);
                                if (clazz.isAnnotationPresent(Entity.class)) {
                                    HibernateLoader.entities.add(clazz);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        loaded = true;
    }
}
