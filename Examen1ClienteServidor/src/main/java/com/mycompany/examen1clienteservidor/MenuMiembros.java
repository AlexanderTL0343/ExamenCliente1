/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen1clienteservidor;

import javax.swing.JOptionPane;

/**
 *
 * @author alext
 */
public class MenuMiembros {
    private int opcion = 0;

    public MenuMiembros() {
    }
    
    
    ControlArchivosMiembros controlArchivo = new ControlArchivosMiembros();
    
    public void mostrarMenuMiembros()
    {
        while(opcion != 5)
        {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Menú Miembros:\n"
                                        + "1 - Agregar\n"
                                        + "2 - Modificar\n"
                                        + "3 - Eliminar\n"
                                        + "4 - Buscar\n"
                                        + "5 - Salir\n"
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
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
