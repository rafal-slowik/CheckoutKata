package service;

import item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import receipt.PdfReceiptGenerator;
import receipt.ReceiptGenerator;
import rule.Rule;
import rule.impl.BuyNForSpecial;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by rafal on 10/02/2018.
 */
public class CashRegisterService {

    private static Logger LOG = LoggerFactory.getLogger(CashRegisterService.class);

    private List<Item> basket = new ArrayList<>();
    private List<Rule> specialOffers = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    private ReceiptGenerator receiptGenerator;

    public CashRegisterService(ReceiptGenerator receiptGenerator) {
        this.receiptGenerator = receiptGenerator;
    }

    private void addToTotal(BigDecimal toAdd) {
        total = total.add(toAdd);
    }

    private void addToTotalDiscount(BigDecimal addDiscount) {
        totalDiscount = totalDiscount.add(addDiscount);
    }

    /**
     * Before print Receipt all price rules will be applied.
     *
     * @return Receipt - Receipt file name
     */
    public String appyPriceRulesAndPrintReceipt() throws Exception {
        if (basket == null || basket.isEmpty()) {
            throw new IllegalArgumentException("It's impossible to process an empty basket!");
        }
        total = BigDecimal.ZERO;
        totalDiscount = BigDecimal.ZERO;
        Optional.ofNullable(specialOffers).orElse(Collections.emptyList()).stream()
                .forEach(rule -> rule.applyPriceRule(basket));
        basket.forEach(it -> {
            addToTotal(it.getRealPrice());
            addToTotalDiscount(it.getNormalPrice().subtract(it.getRealPrice()));
        });
        return receiptGenerator.createReceipt(basket, total, totalDiscount);
    }

    /**
     * @return the basket with the items
     */
    public List<Item> getBasket() {
        return basket;
    }

    /**
     * @return the specialOffers
     */
    public List<Rule> getSpecialOffers() {
        return specialOffers;
    }

    /**
     * Add a new price rule
     *
     * @param rule
     */
    public void addSpecialOffer(Rule rule) {
        specialOffers.add(rule);
    }

    /**
     * Add new {@link Item} to the basket.
     *
     * @param item - item to add
     */
    public void addItemToBasket(Item item) {
        basket.add(item);
    }

    public static void main(String[] args) {
        CashRegisterService registerService = new CashRegisterService(new PdfReceiptGenerator());

        registerService.addSpecialOffer(new BuyNForSpecial("A", 3, BigDecimal.valueOf(1.30)));
        registerService.addSpecialOffer(new BuyNForSpecial("B", 2, BigDecimal.valueOf(0.45)));

        Item itemA = new Item("A", BigDecimal.valueOf(0.50));
        Item itemB = new Item("B", BigDecimal.valueOf(0.30));
        Item itemC = new Item("C", BigDecimal.valueOf(2.00));
        Item itemD = new Item("D", BigDecimal.valueOf(0.75));
        registerService.addItemToBasket(Item.newInstance(itemA));
        registerService.addItemToBasket(Item.newInstance(itemB));
        registerService.addItemToBasket(Item.newInstance(itemD));
        registerService.addItemToBasket(Item.newInstance(itemC));
        registerService.addItemToBasket(Item.newInstance(itemA));
        registerService.addItemToBasket(Item.newInstance(itemA));
        registerService.addItemToBasket(Item.newInstance(itemA));
        registerService.addItemToBasket(Item.newInstance(itemD));
        registerService.addItemToBasket(Item.newInstance(itemD));
        registerService.addItemToBasket(Item.newInstance(itemB));
        registerService.addItemToBasket(Item.newInstance(itemB));
        registerService.addItemToBasket(Item.newInstance(itemA));

        try {
            String file = registerService.appyPriceRulesAndPrintReceipt();
            LOG.info("A new receipt created: " + file);
        } catch (Exception e) {
            LOG.error("An error occured during preparing the receipt", e);
        }
    }
}
