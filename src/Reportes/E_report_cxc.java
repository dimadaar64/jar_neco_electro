
package Reportes;

import java.io.Serializable;

public class E_report_cxc implements Serializable{
    
    private String nombre,cedula,direccion,tel_1,email_1,saldo;

    public E_report_cxc(String nombre, String cedula, String direccion, String tel_1, String email_1, String saldo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.tel_1 = tel_1;
        this.email_1 = email_1;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTel_1() {
        return tel_1;
    }

    public void setTel_1(String tel_1) {
        this.tel_1 = tel_1;
    }

    public String getEmail_1() {
        return email_1;
    }

    public void setEmail_1(String email_1) {
        this.email_1 = email_1;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    
}
