# Checkout Kata
<h2><i>Implement the code for a supermarket checkout that calculates the total price of a number of items.</i></h2>

<h4>Currently items are priced individually and there is implemented a special price rule which supports the following rule:<BR/></h4>
'buy n of them and which will cost you y' - the rule works according to the following algorithm: for instance there is an Item A which is priced £0.50 and a special price '3 for £1.30'. If the algorithm found that there are 3 items A, it changes the current price for the first item from £0.50 to £1.30 and set the price to £0.00 for the remaining 2 items. So, on a receipt the following items will displayed:
<ul>
  <li><b>Item A, current price: £1.30</b></li>
  <li><b>Item A, current price: £0.00</b></li>
  <li><b>Item A, current price: £0.00</b></li>
</ul>
The order of items in a basket doesn't matter.<BR/>

<h4>There is the class <b>BuyNForSpecial</b> which implements the rule 'buy n of them and which will cost you y'. The code below shows how to apply the rule:</BR></h4>

```java
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
```
In order the genarate the receipt, the main method of the class [src/main/java/service/CashRegisterService.java](src/main/java/service/CashRegisterService.java)<br> should be started. The receipt will be generated as a pdf file in a root directory of the project.

The example of the receipt can be find [here](receipt_example/Receipt_2018-02-11_09:22:49.pdf).<br/>

All price rules which will be created in the future should extend [src/main/java/rule/PriceRule.java](src/main/java/rule/PriceRule.java) class and implement its abstract methods:
```java
    /**
     * A definition of the calculation process.
     *
     * @param filteredItems
     */
    protected abstract void processFilteredItems(List<T> filteredItems);

    /**
     * A selection criterion which decides if an item meets the rule's conditions
     *
     * @return a select criterion for the concrete rule
     */
    protected abstract Predicate<T> selectionFilter();
```
</BR>

Additionally all rules must be a type of [src/main/java/rule/Rule.java](src/main/java/rule/Rule.java) and it should be used as a general type.




