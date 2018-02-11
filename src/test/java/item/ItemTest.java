package item;

import base.Base;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by rafal on 10/02/2018.
 */
public class ItemTest extends Base {

    @Test
    public void testGetters() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        Item item = new Item(itemId, price);
        Assert.assertEquals(itemId, item.getItemId());
        Assert.assertEquals(price, item.getNormalPrice());
        Assert.assertEquals(price, item.getRealPrice());
        Assert.assertEquals(false, item.isActionProduct());
    }

    @Test
    public void testSetters() {
        final String itemId = "A";
        final BigDecimal price = BigDecimal.valueOf(2.33);
        final BigDecimal newRealPrice = BigDecimal.valueOf(2.55);
        Item item = new Item(itemId, price);
        Assert.assertEquals(price, item.getRealPrice());
        Assert.assertEquals(false, item.isActionProduct());
        item.setRealPrice(newRealPrice);
        item.setActionProduct(true);
        Assert.assertEquals(newRealPrice, item.getRealPrice());
        Assert.assertEquals(true, item.isActionProduct());
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

        Assert.assertEquals(itemA.getNormalPrice(), itemAClone.getNormalPrice());
        Assert.assertEquals(itemA.getItemId(), itemAClone.getItemId());
    }
}