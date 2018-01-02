
import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Client {
  public VDMMap cart = MapUtil.map();
  public VDMSet wishlist = SetUtil.set();
  public String email;
  public VDMSeq buyHistory = SeqUtil.seq();

  public void cg_init_Client_1(final String e) {

    email = e;
    return;
  }

  public Client(final String e) {

    cg_init_Client_1(e);
  }

  public void addToWishlist(final String title, final Object color) {

    wishlist = SetUtil.union(Utils.copy(wishlist), SetUtil.set(Tuple.mk_(title, color)));
  }

  public void removeFromWishlist(final String title, final Object color) {

    wishlist = SetUtil.diff(Utils.copy(wishlist), SetUtil.set(Tuple.mk_(title, color)));
  }

  public void addToCart(final String title, final Object color) {

    cart = MapUtil.munion(Utils.copy(cart), MapUtil.map(new Maplet(Tuple.mk_(title, color), 1L)));
  }

  public void removeFromCart(final String title, final Object color) {

    cart = MapUtil.domResBy(SetUtil.set(Tuple.mk_(title, color)), Utils.copy(cart));
  }

  public void setQtyInCart(final String title, final Object color, final Number qty) {

    cart =
        MapUtil.override(Utils.copy(cart), MapUtil.map(new Maplet(Tuple.mk_(title, color), qty)));
  }

  public void pushCartToHistory() {

    buyHistory = SeqUtil.conc(SeqUtil.seq(Utils.copy(cart)), Utils.copy(buyHistory));
    cart = MapUtil.map();
  }

  public void convertWishlist() {

    for (Iterator iterator_6 = wishlist.iterator(); iterator_6.hasNext(); ) {
      Tuple tuplePattern_3 = (Tuple) iterator_6.next();
      Boolean success_3 = tuplePattern_3.compatible(String.class, Object.class);
      String title = null;
      Object color = null;
      if (success_3) {
        title = SeqUtil.toStr(tuplePattern_3.get(0));
        color = ((Object) tuplePattern_3.get(1));
      }

      if (!(success_3)) {
        continue;
      }

      if (!(SetUtil.inSet(Tuple.mk_(title, color), MapUtil.dom(Utils.copy(cart))))) {
        addToCart(title, ((Object) color));
      }
    }
    wishlist = SetUtil.set();
  }

  public Client() {}

  public String toString() {

    return "Client{"
        + "cart := "
        + Utils.toString(cart)
        + ", wishlist := "
        + Utils.toString(wishlist)
        + ", email := "
        + Utils.toString(email)
        + ", buyHistory := "
        + Utils.toString(buyHistory)
        + "}";
  }
}
