/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author JuanExzildor
 */
public class Cliente {
    //En esta parte se crean las variables que se utilizaran para obtener los datos de la base de datos que se haya creado
    private int id;
    private String dni;
    private String nombre;
    private String telefono;
    private String direccion;
//Se crea el metodo constructor
    public Cliente() {
    }
   //se crea una clase donde se almancen todas las variables que fueron nombradas con anterioridad
    public Cliente(int id, String dni, String nombre, String telefono, String direccion) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }
   //Se obtiene la informacion de arriba con los metodos get y set que son para obtener y otro para mostrar
    /*
    Para eso es necesario agregar uno por uno 
    los metodos de arriba como se mostro con anteriodidad
    y tambien es importante agregar a que clase pertenecen
    */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
}
