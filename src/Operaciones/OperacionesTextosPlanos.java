//Obtiene la instancia para poder leer el archivo
package Operaciones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



public class OperacionesTextosPlanos {
    
    public void CrearGuardarArchivoDatos(String nombreArchivo,ArrayList arreglo){
      try {
            java.io.FileOutputStream archivo=new java.io.FileOutputStream(nombreArchivo+".dat");
            java.io.ObjectOutputStream obj_archivo=new java.io.ObjectOutputStream(archivo);
            obj_archivo.writeObject(arreglo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesTextosPlanos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OperacionesTextosPlanos.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
    
   public ArrayList leerRecuperarArrayArchivoDatos(String nombreArchivo){
     ArrayList lista = null;
        try {
            FileInputStream archivo=new java.io.FileInputStream(nombreArchivo+".dat");
            java.io.ObjectInputStream obj_archivo=new java.io.ObjectInputStream(archivo);
            lista=(ArrayList) obj_archivo.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OperacionesTextosPlanos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(OperacionesTextosPlanos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }  
}
