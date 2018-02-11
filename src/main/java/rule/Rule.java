package rule;

import java.util.List;

/**
 * Created by rafal on 10/02/2018.
 */
public interface Rule<T> {

    /**
     * Apply a price rule to the checkout items.
     *
     * @param items - items to checkout
     */
    void applyPriceRule(List<T> items);
}
