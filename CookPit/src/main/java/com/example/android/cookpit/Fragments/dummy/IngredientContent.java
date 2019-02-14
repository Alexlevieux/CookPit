package com.example.android.cookpit.Fragments.dummy;

import com.example.android.cookpit.pojoClass.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class IngredientContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Ingredient> ITEMS = new ArrayList<Ingredient>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Ingredient> ITEM_MAP = new HashMap<String, Ingredient>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createIngredientItem(i, "ingredien" + i, "catebory"));
        }
    }

    private static void addItem(Ingredient item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }

    private static Ingredient createIngredientItem(int position, String name, String CatA) {
        return new Ingredient(position, name, CatA, null, null);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}