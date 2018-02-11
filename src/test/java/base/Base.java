package base;

import item.Item;
import item.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafal on 10/02/2018.
 */
public class Base {

    protected static BigDecimal specialPriceA = BigDecimal.valueOf(4.25);

    protected static BigDecimal specialPriceB = BigDecimal.valueOf(2.99);
    protected static BigDecimal itemAPrice = BigDecimal.valueOf(1.66);
    protected static BigDecimal itemVPrice = BigDecimal.valueOf(5.66);
    protected static BigDecimal itemBPrice = BigDecimal.valueOf(5.66);
    protected Item itemA = new Item("A", itemAPrice);
    protected Item itemB = new Item("B", itemBPrice);
    protected Item itemV = new Item("V", itemVPrice);

    public static class ItemDataBuilder {
        private List<Item> items = new ArrayList<>();

        public ItemDataBuilder addItemToList(Item item) {
            items.add(item);
            return this;
        }

        public ItemDataBuilder addItemToList(String itemId, BigDecimal normalPrice) {
            items.add(new Item(itemId, normalPrice));
            return this;
        }

        public ItemDataBuilder removeItemFromList(Item item) {
            items.remove(item);
            return this;
        }

        public List<Item> build() {
            return items;
        }
    }
}
