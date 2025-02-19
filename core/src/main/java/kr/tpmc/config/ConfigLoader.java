package kr.tpmc.config;

import kr.tpmc.exception.NotInitializedException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigLoader {
    private static File configFile = null;

    private ConfigLoader() {}


    public static void onEnable(Plugin plugin) {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        LoadType result = load();
        if (result != LoadType.SUCCESS) {
            save();
            load();
        }
    }

    public static void onReload(Plugin plugin) {
        if (configFile == null) {
            throw new NotInitializedException("configFile");
        } else {
            LoadType result = load();
            if (result != LoadType.SUCCESS) {
                save();
                load();
            }
        }
    }

    private static void save() {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        Class<?> clazz = Config.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Comment.class)) {
                Comment comment = field.getAnnotation(Comment.class);
                String[] comments = comment.comments();

                if (!config.contains(field.getName())) {
                    try {
                        config.set(field.getName(), field.get(new Config()));
                        config.setComments(field.getName(), Arrays.stream(comments).toList());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(configFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> formattedLines = new ArrayList<>();

        boolean lastWasComment = false;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                formattedLines.add(line);
                if (line.startsWith("#")) {
                    lastWasComment = true;
                } else if (lastWasComment || line.contains(":")) {
                    formattedLines.add("");
                    lastWasComment = false;
                }
            }
        }
        try {
            Files.write(configFile.toPath(), formattedLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static LoadType load() {
        if (!configFile.exists()) {
            return LoadType.NEED_TO_SAVE;
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            Class<?> clazz = Config.class;
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Comment.class)) {
                    if (!config.contains(field.getName())) {
                        return LoadType.NEED_TO_FILL;
                    }
                }
            }

            //-------------불러 오기 모두 성공----------------
            Config.config = new Config();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Comment.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = config.get(field.getName());
                        if (value != null) {
                            Class<?> fieldType = field.getType();
                            if (fieldType.isPrimitive()) {
                                if (fieldType == int.class) {
                                    value = ((Number) value).intValue();
                                } else if (fieldType == double.class) {
                                    value = ((Number) value).doubleValue();
                                } else if (fieldType == long.class) {
                                    value = ((Number) value).longValue();
                                } else if (fieldType == float.class) {
                                    value = ((Number) value).floatValue();
                                } else if (fieldType == boolean.class) {
                                    value = Boolean.parseBoolean(value.toString());
                                } else if (fieldType == byte.class) {
                                    value = ((Number) value).byteValue();
                                } else if (fieldType == short.class) {
                                    value = ((Number) value).shortValue();
                                } else if (fieldType == char.class) {
                                    value = value.toString().charAt(0);
                                }
                            }
                            field.set(Config.config, value);
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            return LoadType.SUCCESS;
        }
    }


    private enum LoadType {
        SUCCESS,
        NEED_TO_FILL,
        NEED_TO_SAVE
    }
}
