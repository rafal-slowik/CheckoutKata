package receipt;

import item.Item;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by rafal on 10/02/2018.
 */
public interface ReceiptGenerator {

    String createReceiptAbc(List<Item> items, BigDecimal totalToPay, BigDecimal totalDiscount) throws Exception;
}
