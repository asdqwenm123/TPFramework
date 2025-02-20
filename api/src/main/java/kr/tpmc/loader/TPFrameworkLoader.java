package kr.tpmc.loader;

import jakarta.persistence.Entity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.jar.JarFile;

public class TPFrameworkLoader {
    private static boolean loaded = false;

    private TPFrameworkLoader() {}

    public static boolean isLoaded() {
        return loaded;
    }

    public static void onEnable(Plugin plugin) {
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
                                if (Arrays.stream(clazz.getInterfaces()).toList().contains(Listener.class)) {
                                    for (Method method : clazz.getDeclaredMethods()) {
                                        if (method.isAnnotationPresent(EventHandler.class)) {
                                            Bukkit.getPluginManager().registerEvents((Listener) clazz.getConstructor().newInstance(), plugin);
                                        }
                                    }
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


