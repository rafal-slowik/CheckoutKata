package service;

import base.BaseTest;
import item.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import receipt.PdfReceiptGenerator;
import rule.Rule;
import rule.impl.BuyNForSpecial;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rafal on 10/02/2018.
 */
public class CashRegisterServiceTest extends BaseTest {

    private CashRegisterService service;
    private String generatedReceiptFile;

    @Before
    public void init() {
        service = new CashRegisterService(new PdfReceiptGenerator());
    }

    @After
    public void cleanUp() {
        if (generatedReceiptFile != null && generatedReceiptFile.trim().length() > 0) {
            File file = new File(generatedReceiptFile);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Test
    public void appyPriceRulesAndPrintReceipt() throws Exception {
        service.getSpecialOffers().clear();
        service.getBasket().clear();

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
        service.getBasket().addAll(items);
        service.getSpecialOffers().addAll(rules);
        generatedReceiptFile = service.appyPriceRulesAndPrintReceipt();
        assertTrue(generatedReceiptFile != null && generatedReceiptFile.trim().length() > 0);
    }

    @Test
    public void appyPriceRulesAndPrintReceiptEmptyBasket() throws Exception {
        service.getSpecialOffers().clear();
        service.getBasket().clear();

        Rule<Item> ruleA = new BuyNForSpecial("A", 3, specialPriceA);
        Rule<Item> ruleB = new BuyNForSpecial("B", 2, specialPriceB);
        List<Rule> rules = Arrays.asList(ruleA, ruleB);
        service.getBasket().addAll(Collections.emptyList());
        service.getSpecialOffers().addAll(rules);
        try {
            generatedReceiptFile = service.appyPriceRulesAndPrintReceipt();
            fail("The basket must not be proceeded!");
        } catch (IllegalArgumentException e) {
            // OK -> empty basket
        } catch (Exception e) {
            fail("Unexpected exception was thrown!");
        }
    }

    @Test
    public void addSpecialOffer() throws Exception {
        assertNotNull(service.getSpecialOffers());
        int size = service.getSpecialOffers().size();
        Rule rule = new BuyNForSpecial("C", 5, BigDecimal.ONE);
        assertFalse(service.getSpecialOffers().contains(rule));
        service.addSpecialOffer(rule);
        assertNotNull(service.getSpecialOffers());
        assertTrue(service.getSpecialOffers().contains(rule));
        assertEquals(size + 1, service.getSpecialOffers().size());
    }

    @Test
    public void addItemToBasket() throws Exception {
        assertNotNull(service.getBasket());
        int size = service.getBasket().size();
        service.addItemToBasket(new Item("A", BigDecimal.valueOf(2.33)));
        assertNotNull(service.getBasket());
        assertEquals(size + 1, service.getBasket().size());
    }
}