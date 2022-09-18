
package Utilidades;

import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Con_tablas {
    
    public void eliminarFilasSeleccionadas(javax.swing.JTable Tabla) {
        DefaultTableModel modelo=(DefaultTableModel) Tabla.getModel();
        int row = Tabla.getSelectedRow();
        if (row >= 0) {
            int[] lines = Tabla.getSelectedRows();
            if(lines.length==0)
            for(int i=0; i<lines.length; i++) {
                lines[i]=Tabla.convertRowIndexToModel(lines[i]);
            }
            Arrays.sort(lines);
            for(int i=lines.length-1; i>=0; i--) {
                modelo.removeRow(lines[i]);    
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "No Selecciono Ninguna Fila", "Aviso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
