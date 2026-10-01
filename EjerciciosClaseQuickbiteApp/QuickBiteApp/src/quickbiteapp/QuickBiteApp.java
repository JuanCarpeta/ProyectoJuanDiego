/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quickbiteapp;

import quicbiteapp.modelo.Cliente;
import quicbiteapp.modelo.Pedido;
import quicbiteapp.modelo.Plato;

/**
 *
 * @author salones
 */
public class QuickBiteApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Crear 3 platos - ok
        Plato p1 = new Plato("Bandeja paisa", 28000, 10);
        Plato p2 = new Plato("Ajiaco Santafereño", 24000, 3);
        Plato p3 = new Plato("Arroz con pollo", 25000, 5);

        //Cra 2 clientes - ok
        Cliente c1 = new Cliente("Ana Torres", "ana@correo.com", 60000);
        Cliente c2 = new Cliente("Luis Rueda", "luis@correo.com", 15000);

        //Mostrar informacion
        System.out.println("INFORMACIÓN DE PLATOS");
        p1.mostrarInformacion();
        p2.mostrarInformacion();
        p3.mostrarInformacion();

        System.out.println("INFORMACIÓN DE CLIENTES");
        c1.mostrarInformacion();
        c2.mostrarInformacion();
       
        //Crear pedido
        Pedido ped1 = new Pedido(c1, p1, 2);
       
         System.out.println("INFORMACIÓN DE PEDIDO");
        ped1.mostrarResumen();

    }

}