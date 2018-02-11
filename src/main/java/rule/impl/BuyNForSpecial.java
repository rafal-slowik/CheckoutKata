package rule.impl;

import com.google.common.base.Preconditions;
import item.Item;
import rule.PriceRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Buy N items of the same type for special price for the set.
 *
 * Created by rafal on 09/02/2018.
 */
public class BuyNForSpecial extends PriceRule<Item> {

    private BigDecimal specialPrice;
    private String itemId;
    private int necesseryItemsNumber;

    /**
     * Buy {@code necesseryItemsNumber} of items {@code itemId} and pay for the set a special price ({@code specialPrice})
     *
     * @param itemId               - id of the item
     * @param necesseryItemsNumber - a necessary number of bought items to meet the condition of the rule
     * @param specialPrice         - special price
     * @throws IllegalArgumentException
     */
    public BuyNForSpecial(String itemId, int necesseryItemsNumber, BigDecimal specialPrice) {
        checkPreconditions(itemId, necesseryItemsNumber, specialPrice);
        this.itemId = itemId;
        this.necesseryItemsNumber = necesseryItemsNumber;
        this.specialPrice = specialPrice;
    }

    private void checkPreconditions(String itemId, int necesseryItemsNumber, BigDecimal specialPrice) {
        Preconditions.checkArgument(itemId != null && itemId.trim().length() > 0,
                "The item ID must be set!");
        Preconditions.checkArgument(necesseryItemsNumber >= 0,
                "The expected number of items can't be negative!");
        Preconditions.checkArgument(specialPrice != null,
                "The special price must be set!");
        Preconditions.checkArgument(specialPrice.compareTo(BigDecimal.ZERO) >= 0,
                "The special price must not be less than 0!");
    }

    @Override
    protected Predicate<Item> selectionFilter() {
        return item -> item.getItemId() == itemId;
    }

    /**
     * @param filteredItems
     */
    @Override
    protected void processFilteredItems(List<Item> filteredItems) {
        AtomicInteger atomicInt = new AtomicInteger(0);
        List<Item> listToOperate = Optional.ofNullable(filteredItems).orElse(new ArrayList<>());
        listToOperate.subList(0, (listToOperate.size() / necesseryItemsNumber) * necesseryItemsNumber).stream().forEach(item -> {
            if (atomicInt.getAndIncrement() % necesseryItemsNumber == 0) {
                item.setRealPrice(specialPrice);
            } else {
                item.setRealPrice(BigDecimal.ZERO);
            }
            item.setActionProduct(true);
        });
    }
}

