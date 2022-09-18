
package Reportes;

import java.sql.Connection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public abstract class AbstracJasperReport
{
  private static JasperReport report;
  private static JasperPrint reportFilled;
  private static JasperViewer viewer;
  
  public static void createReport(Connection conn, String path)
    throws JRException
  {
    report = (JasperReport)JRLoader.loadObjectFromFile(path);
    reportFilled = JasperFillManager.fillReport(report, null, conn);
  }
  
  public static void showViewer()
  {
    viewer = new JasperViewer(reportFilled);
    viewer.setVisible(true);
  }
}
