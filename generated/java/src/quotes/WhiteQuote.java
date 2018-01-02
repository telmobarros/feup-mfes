package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class WhiteQuote {
  private static int hc = 0;
  private static WhiteQuote instance = null;

  public WhiteQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static WhiteQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new WhiteQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof WhiteQuote;
  }

  public String toString() {

    return "<White>";
  }
}
