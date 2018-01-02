package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class OrangeQuote {
  private static int hc = 0;
  private static OrangeQuote instance = null;

  public OrangeQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static OrangeQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new OrangeQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof OrangeQuote;
  }

  public String toString() {

    return "<Orange>";
  }
}
