
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Product {
  public String title;
  public String description;
  public Number price;
  public String subcategory;
  public VDMMap quantities = MapUtil.map(new Maplet(quotes.NoneQuote.getInstance(), 0L));
  public VDMMap volumeDiscounts = MapUtil.map();
  public VDMSet colors = SetUtil.set(quotes.NoneQuote.getInstance());

  public void cg_init_Product_1(
      final String tit, final String des, final String cat, final Number pr, final VDMMap qties) {

    subcategory = cat;
    title = tit;
    description = des;
    price = pr;
    quantities = Utils.copy(qties);
    colors = MapUtil.dom(Utils.copy(qties));
    return;
  }

  public Product(
      final String tit, final String des, final String cat, final Number pr, final VDMMap qties) {

    cg_init_Product_1(tit, des, cat, pr, Utils.copy(qties));
  }

  public void setVolumeDiscounts(final VDMMap volDiscs) {

    volumeDiscounts = Utils.copy(volDiscs);
  }

  public void removeFromStock(final Object color, final Number qty) {

    quantities =
        MapUtil.override(
            Utils.copy(quantities),
            MapUtil.map(
                new Maplet(
                    color, ((Number) Utils.get(quantities, color)).longValue() - qty.longValue())));
  }

  public void addToStock(final Object color, final Number qty) {

    quantities =
        MapUtil.override(
            Utils.copy(quantities),
            MapUtil.map(
                new Maplet(
                    color, ((Number) Utils.get(quantities, color)).longValue() + qty.longValue())));
  }

  public Number getPriceWithDiscount(final Number qty) {

    Number discountedPrice = price;
    if (!(Utils.empty(volumeDiscounts))) {
      for (Iterator iterator_13 = MapUtil.dom(Utils.copy(volumeDiscounts)).iterator();
          iterator_13.hasNext();
          ) {
        Number quantity = (Number) iterator_13.next();
        Boolean andResult_15 = false;

        if (qty.longValue() >= quantity.longValue()) {
          if (discountedPrice.doubleValue()
              > ((Number) Utils.get(volumeDiscounts, quantity)).doubleValue()) {
            andResult_15 = true;
          }
        }

        if (andResult_15) {
          discountedPrice = ((Number) Utils.get(volumeDiscounts, quantity));
        }
      }
    }

    return discountedPrice;
  }

  public Product() {}

  public String toString() {

    return "Product{"
        + "title := "
        + Utils.toString(title)
        + ", description := "
        + Utils.toString(description)
        + ", price := "
        + Utils.toString(price)
        + ", subcategory := "
        + Utils.toString(subcategory)
        + ", quantities := "
        + Utils.toString(quantities)
        + ", volumeDiscounts := "
        + Utils.toString(volumeDiscounts)
        + ", colors := "
        + Utils.toString(colors)
        + "}";
  }
}
