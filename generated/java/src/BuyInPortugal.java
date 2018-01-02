
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BuyInPortugal {
  public VDMSet categories = SetUtil.set();
  public VDMMap subcategories = MapUtil.map();
  public VDMMap manufacturers = MapUtil.map();
  public VDMMap products = MapUtil.map();
  public VDMMap clients = MapUtil.map();
  public String adminCode = SeqUtil.toStr(SeqUtil.seq());

  public void cg_init_BuyInPortugal_1() {

    return;
  }

  public BuyInPortugal() {

    cg_init_BuyInPortugal_1();
  }

  public void setAdminCode(final String curCode, final String nextCode) {

    adminCode = nextCode;
  }

  public void registerManufacturer(final String name, final String code) {

    Manufacturer m = new Manufacturer(name);
    addManufacturer(m);
  }

  private void addManufacturer(final Manufacturer m) {

    manufacturers = MapUtil.munion(Utils.copy(manufacturers), MapUtil.map(new Maplet(m.name, m)));
  }

  public void addCategory(final String category, final String code) {

    categories = SetUtil.union(Utils.copy(categories), SetUtil.set(category));
  }

  public void setCategories(final VDMSet cats, final String code) {

    categories = Utils.copy(cats);
  }

  public void addSubcategory(final String subcategory, final String category, final String code) {

    subcategories =
        MapUtil.munion(Utils.copy(subcategories), MapUtil.map(new Maplet(subcategory, category)));
  }

  public void setSubcategories(final VDMMap subcats, final String code) {

    subcategories = Utils.copy(subcats);
  }

  public void addProduct(
      final String manName,
      final String tit,
      final String des,
      final String cat,
      final Number pr,
      final VDMMap qties) {

    Product product = new Product(tit, des, cat, pr, Utils.copy(qties));
    {
      final Manufacturer manufacturer = ((Manufacturer) Utils.get(manufacturers, manName));
      {
        manufacturer.addProduct(product);
        products = MapUtil.munion(Utils.copy(products), MapUtil.map(new Maplet(tit, product)));
      }
    }

    return;
  }

  public void addToStock(
      final String manName, final String title, final Object color, final Number qty) {

    {
      final Product product = ((Product) Utils.get(products, title));
      {
        product.addToStock(color, qty);
      }
    }
  }

  public void removeFromStock(
      final String manName, final String title, final Object color, final Number qty) {

    {
      final Product product = ((Product) Utils.get(products, title));
      {
        product.removeFromStock(color, qty);
      }
    }
  }

  public void addToWishlist(final String email, final String title, final Object color) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.addToWishlist(title, color);
      }
    }
  }

  public void removeFromWishlist(final String email, final String title, final Object color) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.removeFromWishlist(title, color);
      }
    }
  }

  public void addToCart(final String email, final String title, final Object color) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.addToCart(title, color);
      }
    }
  }

  public void setQtyInCart(
      final String email, final String title, final Object color, final Number qty) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.setQtyInCart(title, color, qty);
      }
    }
  }

  public void removeFromCart(final String email, final String title, final Object color) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.removeFromCart(title, color);
      }
    }
  }

  public Number getTotalCart(final String email) {

    Number sum = 0L;
    {
      final Client client = ((Client) Utils.get(clients, email));
      final VDMMap cart = client.cart;
      {
        for (Iterator iterator_2 = MapUtil.dom(Utils.copy(cart)).iterator();
            iterator_2.hasNext();
            ) {
          Tuple tuplePattern_1 = (Tuple) iterator_2.next();
          Boolean success_1 = tuplePattern_1.compatible(String.class, Object.class);
          String title = null;
          Object color = null;
          if (success_1) {
            title = SeqUtil.toStr(tuplePattern_1.get(0));
            color = ((Object) tuplePattern_1.get(1));
          }

          if (!(success_1)) {
            continue;
          }

          {
            final Number qty = ((Number) Utils.get(cart, Tuple.mk_(title, color)));
            sum =
                sum.doubleValue()
                    + ((Product) Utils.get(products, title)).getPriceWithDiscount(qty).doubleValue()
                        * qty.longValue();
          }
        }
        return sum;
      }
    }
  }

  public void buy(final String email) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      final VDMMap cart = client.cart;
      {
        for (Iterator iterator_3 = MapUtil.dom(Utils.copy(cart)).iterator();
            iterator_3.hasNext();
            ) {
          Tuple tuplePattern_2 = (Tuple) iterator_3.next();
          Boolean success_2 = tuplePattern_2.compatible(String.class, Object.class);
          String title = null;
          Object color = null;
          if (success_2) {
            title = SeqUtil.toStr(tuplePattern_2.get(0));
            color = ((Object) tuplePattern_2.get(1));
          }

          if (!(success_2)) {
            continue;
          }

          ((Product) Utils.get(products, title))
              .removeFromStock(color, ((Number) Utils.get(cart, Tuple.mk_(title, color))));
        }
        client.pushCartToHistory();
      }
    }
  }

  public void convertWishlist(final String email) {

    {
      final Client client = ((Client) Utils.get(clients, email));
      {
        client.convertWishlist();
      }
    }
  }

  public void registerClient(final String email) {

    Client c = new Client(email);
    addClient(c);
  }

  private void addClient(final Client c) {

    clients = MapUtil.munion(Utils.copy(clients), MapUtil.map(new Maplet(c.email, c)));
  }

  public Product searchProductsByTitle(final String title) {

    Product product = null;
    product = ((Product) Utils.get(products, title));
    return product;
  }

  public VDMSet searchProductsByManufacturer(final String man) {

    VDMSet resultPoducts = SetUtil.set();
    resultPoducts = MapUtil.rng(((Manufacturer) Utils.get(manufacturers, man)).products);
    return Utils.copy(resultPoducts);
  }

  public VDMSet searchProductBySubcategory(final String subcat) {

    VDMSet resultProducts = SetUtil.set();
    for (Iterator iterator_4 = MapUtil.rng(Utils.copy(products)).iterator();
        iterator_4.hasNext();
        ) {
      Product product = (Product) iterator_4.next();
      if (Utils.equals(product.subcategory, subcat)) {
        resultProducts = SetUtil.union(Utils.copy(resultProducts), SetUtil.set(product));
      }
    }
    return Utils.copy(resultProducts);
  }

  public VDMSet searchProductByCategory(final String cat) {

    VDMSet resultProducts = SetUtil.set();
    for (Iterator iterator_5 = MapUtil.rng(Utils.copy(products)).iterator();
        iterator_5.hasNext();
        ) {
      Product product = (Product) iterator_5.next();
      if (Utils.equals(((String) Utils.get(subcategories, product.subcategory)), cat)) {
        resultProducts = SetUtil.union(Utils.copy(resultProducts), SetUtil.set(product));
      }
    }
    return Utils.copy(resultProducts);
  }

  public String toString() {

    return "BuyInPortugal{"
        + "categories := "
        + Utils.toString(categories)
        + ", subcategories := "
        + Utils.toString(subcategories)
        + ", manufacturers := "
        + Utils.toString(manufacturers)
        + ", products := "
        + Utils.toString(products)
        + ", clients := "
        + Utils.toString(clients)
        + ", adminCode := "
        + Utils.toString(adminCode)
        + "}";
  }
}
