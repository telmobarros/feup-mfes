
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Manufacturer {
  public String name;
  public VDMMap products = MapUtil.map();

  public void cg_init_Manufacturer_1(final String n) {

    name = n;
    return;
  }

  public Manufacturer(final String n) {

    cg_init_Manufacturer_1(n);
  }

  public void addProduct(final Product p) {

    products = MapUtil.munion(Utils.copy(products), MapUtil.map(new Maplet(p.title, p)));
  }

  public void removeProduct(final String title) {

    products = MapUtil.domResBy(SetUtil.set(title), Utils.copy(products));
  }

  public Manufacturer() {}

  public String toString() {

    return "Manufacturer{"
        + "name := "
        + Utils.toString(name)
        + ", products := "
        + Utils.toString(products)
        + "}";
  }
}
