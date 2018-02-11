package item;

import base.BaseTest;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigDecimal;

/**
 * Created by rafal on 10/02/2018.
 */
public class ItemTest extends BaseTest {

    @Test
    public void testGetters() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        Item item = new Item(itemId, price);
        assertEquals(itemId, item.getItemId());
        assertEquals(price, item.getNormalPrice());
        assertEquals(price, item.getRealPrice());
        assertEquals(false, item.isActionProduct());
    }

    @Test
    public void testSetters() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        final BigDecimal newRealPrice = BigDecimal.valueOf(2.55);
        Item item = new Item(itemId, price);
        assertEquals(price, item.getRealPrice());
        assertEquals(false, item.isActionProduct());
        item.setRealPrice(newRealPrice);
        item.setActionProduct(true);
        assertEquals(newRealPrice, item.getRealPrice());
        assertEquals(true, item.isActionProduct());
    }

    @Test
    public void testEquals() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        Item item = new Item(itemId, price);
        Item item1 = new Item(itemId, price);
        assertEquals(item1.hashCode(), item.hashCode());
        assertEquals(true, item.equals(item1));
    }

    @Test
    public void testNotEqual() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        Item item = new Item(itemId, price);
        Item item1 = new Item(itemId, price);
        item1.setActionProduct(true);
        assertNotEquals(item1.hashCode(), item.hashCode());
        assertNotEquals(true, item.equals(item1));
    }

    @Test
    public void testNewInstance() {
        Item itemA = new Item("A", BigDecimal.valueOf(3.33));
        Item itemAClone = Item.newInstance(itemA);

        assertEquals(itemA.getNormalPrice(), itemAClone.getNormalPrice());
        assertEquals(itemA.getItemId(), itemAClone.getItemId());
    }
}