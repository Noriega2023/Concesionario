/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concesionariovehiculos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;
/**
 *
 * @author jesan
 */
public class Concesionario {
    // Definimos un array de semáforos para controlar los vehículos
    public static final Semaphore[] semaforosVehiculos = {
        new Semaphore(1), // Vehículo 1
        new Semaphore(1), // Vehículo 2
        new Semaphore(1), // Vehículo 3
        new Semaphore(1)  // Vehículo 4
    };
    //Cola para gestionar la espera de los clientes
    public static Queue<Cliente> colaEspera = new LinkedList<>();
  
    public static void main(String[] args) {
        //Para volver a inicar el programa
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {            
        // Crear e iniciar los clientes
        Cliente cliente1 = new Cliente("Cliente 1");
        Cliente cliente2 = new Cliente("Cliente 2");
        Cliente cliente3 = new Cliente("Cliente 3");
        Cliente cliente4 = new Cliente("Cliente 4");
        Cliente cliente5 = new Cliente("Cliente 5");
        Cliente cliente6 = new Cliente("Cliente 6");
        Cliente cliente7 = new Cliente("Cliente 7");
        Cliente cliente8 = new Cliente("Cliente 8");
        Cliente cliente9 = new Cliente("Cliente 9");
    
        // Iniciar los hilos de los clientes
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();
        cliente6.start();
        cliente7.start();
        cliente8.start();
        cliente9.start();
        
        // Espera a que todos los clientes terminen
            try {
                cliente1.join();
                cliente2.join();
                cliente3.join();
                cliente4.join();
                cliente5.join();
                cliente6.join();
                cliente7.join();
                cliente8.join();
                cliente9.join();
            } catch (InterruptedException e) {
            }
            
        // Pregunta si volver a empezar
            System.out.println("¿Quiere volver a empezar? (si/no)");
            String respuesta = scanner.nextLine();
            
        // Termina el programa si responde no    
            if (respuesta.equalsIgnoreCase("no")) {
                continuar = false;
            } else { 
        // Limpia cola de espera
                synchronized (colaEspera) {
                    colaEspera.clear();
                }
            }
        }
        // finaliza el programa
            scanner.close();
            System.out.println("Gracias por usar el programa.");
    }
}
