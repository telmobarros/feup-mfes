package quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PinkQuote {
  private static int hc = 0;
  private static PinkQuote instance = null;

  public PinkQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static PinkQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new PinkQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof PinkQuote;
  }

  public String toString() {

    return "<Pink>";
  }
}
