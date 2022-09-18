
package Clases;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatoDecimal extends DecimalFormat{
  private boolean invertirSimbolos = false;
  private DecimalFormatSymbols dfs;
  
  public FormatoDecimal(String pattern, boolean invertirSimbolos)
  {
        this.invertirSimbolos = invertirSimbolos;
        if (this.invertirSimbolos)
        {
          this.dfs = new DecimalFormatSymbols();
          this.dfs.setDecimalSeparator('.');
          this.dfs.setGroupingSeparator(',');
          setDecimalFormatSymbols(this.dfs);
        }
        else
        {
          this.dfs = new DecimalFormatSymbols(Locale.getDefault());
          setDecimalFormatSymbols(this.dfs);
        }
        applyPattern(pattern);
  }
}
