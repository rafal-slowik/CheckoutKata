package rule;

import item.Item;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Base class for the price rules. All concrete rules should extend this class.
 *
 * Created by rafal on 09/02/2018.
 */
public abstract class PriceRule<T extends Item> implements Rule<T> {

    /**
     * @param itemsToCheck - items to check out
     */
    public void applyPriceRule(List<T> itemsToCheck) {
        List<T> filteredItems = Optional.ofNullable(itemsToCheck).orElse(Collections.emptyList())
                .stream()
                .filter(
                        item -> !item.isActionProduct() && selectionFilter().test(item)
                )
                .collect(Collectors.toList());
        processFilteredItems(filteredItems);
    }

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
}
