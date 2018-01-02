package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BlueQuote {
  private static int hc = 0;
  private static BlueQuote instance = null;

  public BlueQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BlueQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BlueQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BlueQuote;
  }

  public String toString() {

    return "<Blue>";
  }
}
