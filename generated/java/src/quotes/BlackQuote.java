package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BlackQuote {
  private static int hc = 0;
  private static BlackQuote instance = null;

  public BlackQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BlackQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BlackQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BlackQuote;
  }

  public String toString() {

    return "<Black>";
  }
}
