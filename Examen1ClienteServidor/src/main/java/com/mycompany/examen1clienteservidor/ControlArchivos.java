/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen1clienteservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author alext
 */
public class ControlArchivos {
    static HashMap<String, String> premiosGanados = new HashMap<>();
    private static String cedula, nombre, apellidos, puesto,contrasena,id;

    
    public static boolean camposRequeridosIncompletos()
    {
        if((cedula.equals(""))
           || (nombre.equals(""))
           || (apellidos.equals(""))
           || (puesto.equals(""))
           || (contrasena.equals(""))
           || (id.equals("")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static void agregar()
    {
        try
        {
            //Aquí creamos el archivo y la entrada de datos
            DataOutputStream archivo = new DataOutputStream(new FileOutputStream("credenciales.txt",true));
            //Aquí solicitamos los datos al usuario
            cedula = JOptionPane.showInputDialog("Digite su cédula:");
            nombre = JOptionPane.showInputDialog("Digite su nombre:");
            apellidos = JOptionPane.showInputDialog("Digite sus apellidos:");
            puesto = JOptionPane.showInputDialog("Digite su puesto:");
            contrasena= JOptionPane.showInputDialog("Digite la contrasena:");
            id= JOptionPane.showInputDialog("Digite el id:");

            //Validamos que el usuario digitó toda la información requerida
            if(camposRequeridosIncompletos())
            {
                //Si nos faltó escribir algún dato, lanzamos un error, esto cae en el catch
                throw new Exception("Algunos de los campos requeridos no fueron completados");
            }
            else
            {
                //Si los datos están correctos escribimos en el archivo
                archivo.writeUTF(cedula);
                archivo.writeUTF(nombre);
                archivo.writeUTF(apellidos);
                archivo.writeUTF(puesto);
                //Notificarle al usario que se guardaron los datos
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente!", "Agregar Datos",
                        JOptionPane.INFORMATION_MESSAGE);
                //Debemos cerrar el archivo una vez que dejemos de utilizarlo
                archivo.close();
                
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error al agregar los datos: " + e.getMessage(), "Error!", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void modificar()
    {
        String cedBusca, ced, nom, apellido, puesto;
        //boolean proximo = true;
        cedBusca = JOptionPane.showInputDialog("Digite la cédula de la persona a modificar: ");
        try
        {
            //Todo lo que debe salir bien, va en este try
            //En este try estamos generando los procesos de lectura y escritura de archivos
            //Definimos el archivo que queremos leer
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("credenciales.txt"));
            //Definimos un archivo temporal, para almacenar los cambios del usuario, que se registrarán en el archivo original
            DataOutputStream archivoEscritura = new DataOutputStream(new FileOutputStream("temporal.txt"));
            
            //Vamos a hacer un try adicional que se va a encargar de buscar, recolectar la nueva información 
            // y modificar el archivo general
            try
            {
                while(true)
                {
                    //Descargamos el contenido del archivo
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    apellido = archivoLectura.readUTF();
                    puesto = archivoLectura.readUTF();
                    //Buscamos la cédula que digitó el usuario
                    if(cedBusca.equals(ced))
                    {
                        //Si la cédula existe entonces vamos a modificar el dato en el archivo
                        //Solicitamos los nuevos datos al usuario
                        nom = JOptionPane.showInputDialog("Digite el nuevo nombre:");
                        apellido = JOptionPane.showInputDialog("Digite los nuevos apellidos:");
                        puesto = JOptionPane.showInputDialog("Digite el nuevo puesto:");
                    }
                    archivoEscritura.writeUTF(ced);
                    archivoEscritura.writeUTF(nom);
                    archivoEscritura.writeUTF(apellido);
                    archivoEscritura.writeUTF(puesto);
                }
            }
            catch(EOFException e)
            {
                archivoEscritura.close();
                archivoLectura.close();
                //En el momento en que alcancemos el final del archivo, vamos a intercambiar los datos del archivo 
                // temporal al archivo original
                intercambiar();
            }
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error al localizar el archivo: " + e.getMessage(), "Error!", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            //Todo lo que puede salir mal, va en este catch
            JOptionPane.showMessageDialog(null, "Los archivos tienen errores de lectura/escritura: " + e.getMessage(), "Error!", 
        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void intercambiar()
    {
        String ced, nom, apellidos, puesto;
        try
        {
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("temporal.txt"));
            DataOutputStream archivoEscritura = new DataOutputStream(new FileOutputStream("credenciales.txt"));
            
            try
            {
                while(true)
                {
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    apellidos = archivoLectura.readUTF();
                    puesto = archivoLectura.readUTF();
                    archivoEscritura.writeUTF(ced);
                    archivoEscritura.writeUTF(nom);
                    archivoEscritura.writeUTF(apellidos);
                    archivoEscritura.writeUTF(puesto);
                }
            }
            catch(EOFException e)
            {
                archivoEscritura.close();
                archivoLectura.close();
            }
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error al localizar el archivo: " + e.getMessage(), "Error!", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            //Todo lo que puede salir mal, va en este catch
            JOptionPane.showMessageDialog(null, "Los archivos tienen errores de lectura/escritura: " + e.getMessage(), "Error!", 
        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void buscar()
    {
        String nomBusca, ced, nom, apellidos, puesto;
        boolean proximo = true;
        nomBusca = JOptionPane.showInputDialog("Digite el nombre a buscar:");
        try
        {
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("credenciales.txt"));
            try
            {
                while(proximo)
                {
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    apellidos = archivoLectura.readUTF();
                    puesto = archivoLectura.readUTF();
                    if(nomBusca.equals(nom))
                    {
                        JOptionPane.showMessageDialog(null, "Se encontró a la persona: " + nom
                                            + ", cuya cédula es: " + ced + ", sus apellidos son: " + apellidos 
                                            + " y su puesto es: " + puesto + ".");
                        proximo = false;
                    }
                }
            }
            catch(EOFException e)
            {
                archivoLectura.close();
            }
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error al localizar el archivo: " + e.getMessage(), "Error!", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            //Todo lo que puede salir mal, va en este catch
            JOptionPane.showMessageDialog(null, "Los archivos tienen errores de lectura/escritura: " + e.getMessage(), "Error!", 
        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void eliminar()
    {
        String cedBusca, ced, nom, apellidos, puesto;
        //boolean proximo = true;
        cedBusca = JOptionPane.showInputDialog("Digite la cédula de la persona a eliminar: ");
        try
        {
            //Todo lo que debe salir bien, va en este try
            //En este try estamos generando los procesos de lectura y escritura de archivos
            //Definimos el archivo que queremos leer
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("credenciales.txt"));
            //Definimos un archivo temporal, para almacenar los cambios del usuario, que se registrarán en el archivo original
            DataOutputStream archivoEscritura = new DataOutputStream(new FileOutputStream("temporal.txt"));
            
            //Vamos a hacer un try adicional que se va a encargar de buscar, recolectar la nueva información 
            // y modificar el archivo general
            try
            {
                while(true)
                {
                    //Descargamos el contenido del archivo
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    apellidos = archivoLectura.readUTF();
                    puesto = archivoLectura.readUTF();
                    //Buscamos la cédula que digitó el usuario
                    if(!cedBusca.equals(ced))
                    {
                        //Si la cédula no es la que estamos buscando, mantenemos los datos en el archivo
                        archivoEscritura.writeUTF(ced);
                        archivoEscritura.writeUTF(nom);
                        archivoEscritura.writeUTF(apellidos);
                        archivoEscritura.writeUTF(puesto);
                    }
                }
            }
            catch(EOFException e)
            {
                archivoEscritura.close();
                archivoLectura.close();
                //En el momento en que alcancemos el final del archivo, vamos a intercambiar los datos del archivo 
                // temporal al archivo original
                intercambiar();
            }
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error al localizar el archivo: " + e.getMessage(), "Error!", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
        catch(IOException e)
        {
            //Todo lo que puede salir mal, va en este catch
            JOptionPane.showMessageDialog(null, "Los archivos tienen errores de lectura/escritura: " + e.getMessage(), "Error!", 
        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void Sorteo() throws FileNotFoundException{
        String buscarNum, monto, numeroDeRifa;
        
        buscarNum = JOptionPane.showInputDialog("Digite un numero aleatorio para eligir un ganador ");

           
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("acta.txt"));
            
            DataOutputStream archivoEscritura = new DataOutputStream(new FileOutputStream("temporal.txt"));       
            try
            {
                while(true)
                {
                    //Descargamos el contenido del archivo
                    monto = archivoLectura.readUTF();
                    numeroDeRifa = archivoLectura.readUTF();
                   
                    
                    if(buscarNum.equals(numeroDeRifa)&& "500".equals(monto))
                    {
                       JOptionPane.showMessageDialog(null,"¡Felicidades! ¡Has ganado la rifa  " +  "Esto contiene Hamburguesa con papas y gaseosa.");
            } else{
                if (buscarNum.equals(numeroDeRifa)&& "1000".equals(monto)) {
                JOptionPane.showMessageDialog(null,"¡Felicidades! ¡Has ganado la rifa  " +  "Esto contiene Cupon cena para 2 personas.");
            } else{
                if (buscarNum.equals(numeroDeRifa)&& "2000".equals(monto)) {
                JOptionPane.showMessageDialog(null,"¡Felicidades! ¡Has ganado la rifa  " +  "Esto contiene Un día en el parque de diversiones con transporte y comida pago para 3 personas.");
            } else{
                if (buscarNum.equals(numeroDeRifa)&& "5000".equals(monto)) {
                JOptionPane.showMessageDialog(null,"¡Felicidades! ¡Has ganado la rifa " +  "Esto contiene Fin de semana todo incluido en hotel paradisiaco para 2 personas.");
                 premiosGanados.put(numeroDeRifa, monto);
            }else{
                JOptionPane.showMessageDialog(null,"No hubo Ganador");
            }  
            }
            }
            }
               archivoEscritura.writeUTF(monto);
               archivoEscritura.writeUTF(numeroDeRifa);
                  
             }  
            }catch(IOException e)
                
            {
            try {
                archivoEscritura.close();
                archivoLectura.close();
                //En el momento en que alcancemos el final del archivo, vamos a intercambiar los datos del archivo 
                // temporal al archivo original
                intercambiar();
            }
            //Todo lo que puede salir mal, va en este catch
            catch (IOException ex) {
                Logger.getLogger(ControlArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
                    }

    }
}


