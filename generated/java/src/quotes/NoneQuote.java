package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class NoneQuote {
  private static int hc = 0;
  private static NoneQuote instance = null;

  public NoneQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static NoneQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new NoneQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof NoneQuote;
  }

  public String toString() {

    return "<None>";
  }
}
