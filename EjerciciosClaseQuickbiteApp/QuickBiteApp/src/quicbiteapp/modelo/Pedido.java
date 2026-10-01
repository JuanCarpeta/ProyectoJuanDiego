/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quicbiteapp.modelo;

/**
 *
 * @author juandi
 */
public class Pedido {
    //Atributos
    
    private Cliente cliente;
    private Plato plato;
    private int cantidad;
    
    //Constructor

    public Pedido(Cliente cliente, Plato plato, int cantidad) {
        this.cliente = cliente;
        this.plato = plato;
        this.cantidad = cantidad;
    }
    
    //Metodos
    
    public double calcularTotal() {
        
        double valorTotal = this.plato.calcularSubtotal(this.cantidad);
        
        return valorTotal;
    }
    
    public void confirmar(){
        
        boolean hayDisponibilidad = this.plato.hayDisponibilidad(this.cantidad);
        double valorPagar = calcularTotal();
        boolean puedePagar = this.cliente.puedePagar(valorPagar);
        
        if(hayDisponibilidad == true && puedePagar == true){
            
            this.cliente.pagar(valorPagar);
            this.plato.despachar(cantidad);
            
            System.out.println("Pedido confirmado");
        }else{
            
            System.out.println("Pedido no confirmado");
            System.out.println("motivo:" + ((hayDisponibilidad==true)? "No podia pagar": "No habia disponibilidad"));
        }
    }
    
    public void mostrarResumen () {
        cliente.mostrarInformacion();
        plato.mostrarInformacion();
        System.out.println("El valor a pagar es:" + calcularTotal());
    }
}
