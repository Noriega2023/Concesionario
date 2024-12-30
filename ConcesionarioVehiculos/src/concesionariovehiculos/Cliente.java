/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package concesionariovehiculos;


/**
 *
 * @author jesan
 */
public class Cliente extends Thread {
    private final String nombre;

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void run() {
        int numeroVehiculo = -1; // Guardará el índice del vehículo asignado

        try {
            // Buscar un vehículo disponible
            for (int i = 0; i < Concesionario.semaforosVehiculos.length; i++) {
                // Intentar adquirir el semáforo (el vehículo)
                if (Concesionario.semaforosVehiculos[i].tryAcquire()) {
                    numeroVehiculo = i; // Asignamos el vehículo al cliente
                    System.out.println(nombre + " ha obtenido el vehículo " + (numeroVehiculo + 1));
                    break;
                }
            }

            // Si no se encontró un vehículo disponible, espera.
            if (numeroVehiculo == -1) {
                System.out.println(nombre + " está esperando un vehículo disponible ");
                synchronized (this) {
                    this.wait(2000); // Esperar 2 segundos antes de intentar de nuevo
                }
                this.run();// Reintentar
                return;
            }

            // Simulamos que el cliente está probando el vehículo por un tiempo aleatorio
            System.out.println(nombre + " está probando el vehículo  " + (numeroVehiculo + 1));
            Thread.sleep((int) (Math.random() * 8000) + 2000); // Tiempo de uso aleatorio entre 2 y 8 segundos

            // Al terminar de probar, el cliente dejará el vehículo libre
            System.out.println(nombre + " ha terminado de probar el vehículo " + (numeroVehiculo + 1)); 
          
        } catch (InterruptedException e) {
            e.printStackTrace();
             }finally {
            // Liberar el vehículo para otro cliente
            if (numeroVehiculo != -1) {
                Concesionario.semaforosVehiculos[numeroVehiculo].release();
                System.out.println(nombre + " ha dejado el vehículo " + (numeroVehiculo + 1));
                }
            
            // Si hay clientes esperando le asignara el vehiculo que quede libre.
            synchronized (Concesionario.colaEspera) {
                if (!Concesionario.colaEspera.isEmpty()) {
                    Cliente siguienteCliente = Concesionario.colaEspera.poll();
            // Siguiente cliente en espera
                    synchronized (siguienteCliente) { siguienteCliente.notify();
                    }  
                }
            }
        }
    }

}