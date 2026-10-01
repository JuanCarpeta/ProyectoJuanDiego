/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miProyecto;

/**
 *
 * @author Juan Diego
 */
public class Usuario {

    //Atributos
    public int id_usuario;
    public String nombre;
    public String apellido;
    public String email_institucional;
    public String contacto;

    //Constructor
    // 1 -> constructor - vacio
    public Usuario() {
    }

    // 2 -> constructor - completo
    public Usuario(int id_usuario, String nombre, String apellido, String email_institucional, String contacto) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email_institucional = email_institucional;
        this.contacto = contacto;
    }

// 3 -> contructor - parcial
    public Usuario(String nombre, String apellido, String email_institucional, String contacto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email_institucional = email_institucional;
        this.contacto = contacto;
    }

    //Metodos

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail_institucional() {
        return email_institucional;
    }

    public void setEmail_institucional(String email_institucional) {
        this.email_institucional = email_institucional;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public void imprimir(){
        System.out.println("El usuario" + nombre + apellido);
    }
    
    //Metodo principal
    public static void main (String[] args){
        
        Usuario n1 = new Usuario(1, "Juan Diego", "Carpeta Ramirez", "jcarpeta@unab.edu.co", "3125813394");
        n1.imprimir();
         
        
    }
    
}
