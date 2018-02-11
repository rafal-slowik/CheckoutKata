package receipt;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import item.Item;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by rafal on 10/02/2018.
 */
public class PdfReceiptGenerator implements ReceiptGenerator {

    private static NumberFormat format = NumberFormat.getCurrencyInstance(Locale.UK);

    @Override
    public String createReceipt(List<Item> items, BigDecimal totalToPay, BigDecimal totalDiscount) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        String receiptName = "Receipt_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(receiptName));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 25, BaseColor.BLACK);
        addParagraph("Receipt", document, font, Element.ALIGN_CENTER);

        PdfPTable table = new PdfPTable(4);
        addTableHeader(table);
        items.forEach(item -> addRow(table, item));
        document.add(table);
        font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        addPriceParagraph("Total discount: " + format.format(totalDiscount), document, font, Element.ALIGN_LEFT);
        addPriceParagraph("Total: " + format.format(totalToPay), document, font, Element.ALIGN_LEFT);
        font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
        addPriceParagraph("s - special action product", document, font, Element.ALIGN_LEFT);
        document.close();
        return receiptName;
    }

    private void addParagraph(String content, Document document, Font font, int alignment) throws DocumentException {
        addParagraph(content, document, font, alignment, 50, 50);
    }

    private void addPriceParagraph(String content, Document document, Font font, int alignment) throws DocumentException {
        addParagraph(content, document, font, alignment, 10, 0);
    }

    private void addParagraph(String content, Document document, Font font, int alignment, int spacingBefore, int spacingAfter) throws DocumentException {
        Paragraph paragraph = new Paragraph(15);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setSpacingAfter(spacingAfter);
        paragraph.setAlignment(alignment);
        paragraph.add(new Chunk(content, font));
        document.add(paragraph);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Item Name", "Normal Price", "Discount", "To Pay")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setFollowingIndent(15);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRow(PdfPTable table, Item item) {
        table.addCell(item.getItemId());
        table.addCell(format.format(item.getNormalPrice()));
        table.addCell(format.format(item.getRealPrice().subtract(item.getNormalPrice())));
        table.addCell(format.format(item.getRealPrice()) + " " + (item.isActionProduct() ? "(s)" : ""));
    }
}
