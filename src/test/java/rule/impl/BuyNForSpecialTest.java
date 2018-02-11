package rule.impl;

import base.Base;
import item.Item;

import static org.junit.Assert.*;

import org.junit.Test;
import rule.Rule;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by rafal on 09/02/2018.
 */
public class BuyNForSpecialTest extends Base {

    @Test
    public void buyNForSpecialMissingItemIdTest() {
        try {
            BuyNForSpecial buyNForSpecial = new BuyNForSpecial(null, 2, specialPriceA);
            fail("An IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException ex) {
            // ok
        } catch (Exception ex) {
            fail("Wrong exception type was thrown!");
        }
    }

    @Test
    public void buyNForSpecialItemsNumberNegativeTest() {
        try {
            BuyNForSpecial buyNForSpecial = new BuyNForSpecial("A", -1, specialPriceA);
            fail("An IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException ex) {
            // ok
        } catch (Exception ex) {
            fail("Wrong exception type was thrown!");
        }
    }

    @Test
    public void buyNForSpecialSpecialPriceNegativeTest() {
        try {
            BuyNForSpecial buyNForSpecial = new BuyNForSpecial("A", 3, BigDecimal.valueOf(-1));
            fail("An IllegalArgumentException should be thrown!");
        } catch (IllegalArgumentException ex) {
            // ok
        } catch (Exception ex) {
            fail("Wrong exception type was thrown!");
        }
    }

    @Test
    public void selectionFilterTest() {
        BuyNForSpecial buyNForSpecial = new BuyNForSpecial("A", 2, specialPriceA);
        List<Item> items = new ItemDataBuilder()
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemA))
                .build();
        int numberOfItems = items.size();

        Predicate<Item> filter = buyNForSpecial.selectionFilter();
        assertNotNull(items);
        assertEquals(numberOfItems, items.size());
        Iterator<Item> it = items.iterator();
        assertEquals(true, filter.test(it.next()));
        assertEquals(false, filter.test(it.next()));
        assertEquals(true, filter.test(it.next()));
    }

    @Test
    public void processFilteredItemsNullTest() {
        List<Item> items = null;
        BuyNForSpecial buyNForSpecial = new BuyNForSpecial("A", 2, specialPriceB);
        try {
            buyNForSpecial.processFilteredItems(items);
            assertNull(items);
        } catch (Exception e) {
            fail("The exception shouldn't be thrown at this point");
        }
    }

    @Test
    public void processFilteredItemsEmptyTest() {
        List<Item> items = Collections.emptyList();
        BuyNForSpecial buyNForSpecial = new BuyNForSpecial("A", 2, specialPriceB);
        try {
            buyNForSpecial.processFilteredItems(items);
            assertNotNull(items);
        } catch (Exception e) {
            fail("The exception shouldn't be thrown at this point");
        }
    }

    @Test
    public void applyPriceRuleSingleRuleTest() {
        Rule<Item> rule = new BuyNForSpecial("A", 3, specialPriceA);
        List<Item> items = new ItemDataBuilder()
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemV))
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemA))
                .build();
        int numberOfItems = items.size();
        rule.applyPriceRule(items);
        assertNotNull(items);
        assertEquals(numberOfItems, items.size());
        Iterator<Item> it = items.iterator();
        assertEquals(specialPriceA, it.next().getRealPrice());
        assertEquals(itemVPrice, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
    }

    @Test
    public void applyPriceRuleTwoRulesTest() {
        Rule<Item> ruleA = new BuyNForSpecial("A", 3, specialPriceA);
        Rule<Item> ruleB = new BuyNForSpecial("B", 2, specialPriceB);
        List<Rule> rules = Arrays.asList(ruleA, ruleB);
        List<Item> items = new ItemDataBuilder()
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemV))
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemB))
                .build();
        int numberOfItems = items.size();
        rules.stream().forEach(r -> {
            r.applyPriceRule(items);
        });
        assertNotNull(items);
        assertEquals(numberOfItems, items.size());
        Iterator<Item> it = items.iterator();
        assertEquals(specialPriceA, it.next().getRealPrice());
        assertEquals(specialPriceB, it.next().getRealPrice());
        assertEquals(itemVPrice, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
    }

    @Test
    public void applyPriceRuleTwoRulesTwiceBTest() {
        Rule<Item> ruleA = new BuyNForSpecial("A", 3, specialPriceA);
        Rule<Item> ruleB = new BuyNForSpecial("B", 2, specialPriceB);

        List<Rule> rules = Arrays.asList(ruleA, ruleB);
        List<Item> items = new ItemDataBuilder()
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemV))
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemA))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemB))
                .addItemToList(Item.newInstance(itemB))
                .build();
        int numberOfItems = items.size();
        rules.stream().forEach(r -> {
            r.applyPriceRule(items);
        });
        assertNotNull(items);
        assertEquals(numberOfItems, items.size());
        Iterator<Item> it = items.iterator();
        assertEquals(specialPriceA, it.next().getRealPrice());
        assertEquals(specialPriceB, it.next().getRealPrice());
        assertEquals(itemVPrice, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(specialPriceB, it.next().getRealPrice());
        assertEquals(BigDecimal.ZERO, it.next().getRealPrice());
        assertEquals(itemBPrice, it.next().getRealPrice());
    }
}