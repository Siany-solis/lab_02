package modelo;

import java.io.Serializable;

public class Cliente implements Serializable {
    private String nombre;
    private String cedula;
    private String telefono;
    private String direccion;

    public Cliente(String nombre, String cedula, String telefono, String direccion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }

    @Override
    public String toString() {
        return nombre + " (" + cedula + ")";
    }
}
