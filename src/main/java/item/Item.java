package item;

import java.math.BigDecimal;

/**
 * Item entity.
 *
 * Created by rafal on 09/02/2018.
 */
public class Item {
    private String itemId;
    private BigDecimal normalPrice;
    private BigDecimal realPrice;
    private boolean actionProduct;

    public static Item newInstance(Item item) {
        return new Item(item.getItemId(), item.getNormalPrice());
    }

    public Item(String itemId, BigDecimal normalPrice) {
        this.itemId = itemId;
        this.normalPrice = normalPrice;
        // init the real price
        this.realPrice = normalPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public BigDecimal getNormalPrice() {
        return normalPrice;
    }

    public BigDecimal getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(BigDecimal realPrice) {
        this.realPrice = realPrice;
    }

    public boolean isActionProduct() {
        return actionProduct;
    }

    public void setActionProduct(boolean actionProduct) {
        this.actionProduct = actionProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (actionProduct != item.actionProduct) return false;
        if (itemId != null ? !itemId.equals(item.itemId) : item.itemId != null) return false;
        if (normalPrice != null ? !normalPrice.equals(item.normalPrice) : item.normalPrice != null) return false;
        return realPrice != null ? realPrice.equals(item.realPrice) : item.realPrice == null;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (normalPrice != null ? normalPrice.hashCode() : 0);
        result = 31 * result + (realPrice != null ? realPrice.hashCode() : 0);
        result = 31 * result + (actionProduct ? 1 : 0);
        return result;
    }
}
