package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class GrayQuote {
  private static int hc = 0;
  private static GrayQuote instance = null;

  public GrayQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static GrayQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new GrayQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof GrayQuote;
  }

  public String toString() {

    return "<Gray>";
  }
}
