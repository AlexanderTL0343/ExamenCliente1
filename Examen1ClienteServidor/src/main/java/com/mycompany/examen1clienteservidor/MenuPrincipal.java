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
public class MenuPrincipal {
        private int opcion = 0;
        Menu men = new Menu(); 
        MenuMiembros menmi= new MenuMiembros();

    public MenuPrincipal() {
    }
        
      
    public void mostrarMenuPricipal()
            
    {
         OUTER:
        while(opcion != 3)
        {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Menú Credenciales:\n"
                                        + "1 - MenuMiembros\n"
                                        + "2 - Menu\n"
                                        + "3 - Salir\n"
                                       
                                        + "Digite una opción para continuar: "
            ));
            switch(opcion)
            {
                case 1:
                    menmi.mostrarMenuMiembros();  
                    break;
                case 2:
                    men.mostrarMenu();
                
                    break;
                case 3: 
                    break OUTER;
                case 4:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
