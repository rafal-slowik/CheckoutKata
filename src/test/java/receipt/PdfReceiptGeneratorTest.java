package receipt;

import base.Base;
import item.Item;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by rafal on 10/02/2018.
 */
public class PdfReceiptGeneratorTest extends Base {
    @Test
    public void createReceiptTest() throws Exception {
        List<Item> items = new Base.ItemDataBuilder()
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

        ReceiptGenerator receipt = new PdfReceiptGenerator();
        try {
            String fileName = receipt.createReceipt(items, BigDecimal.valueOf(25.66), BigDecimal.valueOf(13.66));
            File receiptFile = new File(fileName);
            assertTrue(receiptFile.exists());
            receiptFile.delete();
        } catch (Exception e) {
            fail("Unexpected exception was thrown");
        }
    }
}