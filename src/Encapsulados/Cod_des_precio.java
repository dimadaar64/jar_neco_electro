
package Encapsulados;


public class Cod_des_precio {
    
    private String cod,descrip,precio;

    public Cod_des_precio(String cod, String descrip, String precio) {
        this.cod = cod;
        this.descrip = descrip;
        this.precio = precio;
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
    
    
    
}
