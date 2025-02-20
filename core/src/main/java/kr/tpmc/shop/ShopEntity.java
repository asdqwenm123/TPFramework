package kr.tpmc.shop;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.persistence.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;

@Entity
public class ShopEntity {
    private static final Gson GSON = new Gson();

    @Id
    private int id;

    @Convert(converter = ComponentConverter.class)
    private Component name;

    private int line;

    @Convert(converter = ShopItemsConverter.class)
    private HashMap<ShopItemLocation, ShopItem> items;

    public ShopEntity() {}

    public ShopEntity(int id, Component name, int line, HashMap<ShopItemLocation, ShopItem> items) {
        this.id = id;
        this.name = name;
        this.line = line;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public Component getName() {
        return name;
    }

    public HashMap<ShopItemLocation, ShopItem> getItems() {
        return items;
    }

    public int getLine() {
        return line;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public void setItems(HashMap<ShopItemLocation, ShopItem> items) {
        this.items = items;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Converter
    private static class ComponentConverter implements AttributeConverter<Component, String> {
        @Override
        public String convertToDatabaseColumn(Component attribute) {
            return JSONComponentSerializer.json().serialize(attribute);
        }

        @Override
        public Component convertToEntityAttribute(String dbData) {
            return JSONComponentSerializer.json().deserialize(dbData);
        }
    }

    @Converter
    private static class ShopItemsConverter implements AttributeConverter<HashMap<ShopItemLocation, ShopItem>, String> {
        @Override
        public String convertToDatabaseColumn(HashMap<ShopItemLocation, ShopItem> attribute) {
            // 직렬화 logic (예: JSON 변환)

            return GSON.toJson(attribute);
        }

        @Override
        public HashMap<ShopItemLocation, ShopItem> convertToEntityAttribute(String dbData) {
            // 역직렬화 logic (예: JSON 파싱)
            Type type = new TypeToken<HashMap<ShopItemLocation, ShopItem>>(){}.getType();
            return GSON.fromJson(dbData, type);
        }
    }
}

