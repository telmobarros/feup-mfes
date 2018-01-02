package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BrownQuote {
  private static int hc = 0;
  private static BrownQuote instance = null;

  public BrownQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BrownQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BrownQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BrownQuote;
  }

  public String toString() {

    return "<Brown>";
  }
}
