
package Reportes;

import java.io.Serializable;

public class E_report implements Serializable{
    
    private String cod,descrip,precio,costo;

    public E_report(String cod, String descrip, String precio, String costo) {
        this.cod = cod;
        this.descrip = descrip;
        this.precio = precio;
        this.costo = costo;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    
    
    
}
