/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen1clienteservidor;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alext
 */
public class Menu {
     private int opcion = 0;

    public Menu() {
    }
     
    
    ControlArchivos controlArchivo = new ControlArchivos();
    
    public void mostrarMenu()
    {
        while(opcion != 6)
        {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Menú Credenciales:\n"
                                        + "1 - Agregar\n"
                                        + "2 - Modificar\n"
                                        + "3 - Eliminar\n"
                                        + "4 - Buscar\n"
                                        + "5 - Sorteo\n"
                                        + "6 - Salir\n"
                                        + "Digite una opción para continuar: "
            ));
            switch(opcion)
            {
                case 1:
                    controlArchivo.agregar();
                    break;
                case 2:
                    controlArchivo.modificar();
                    break;
                case 3: 
                    controlArchivo.eliminar();
                    break;
                case 4:
                    controlArchivo.buscar();
                    break;
                case 5:
                {
                    
                try {
                    controlArchivo.Sorteo();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                }

                case 6:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
