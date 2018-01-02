package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PurpleQuote {
  private static int hc = 0;
  private static PurpleQuote instance = null;

  public PurpleQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static PurpleQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new PurpleQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof PurpleQuote;
  }

  public String toString() {

    return "<Purple>";
  }
}
