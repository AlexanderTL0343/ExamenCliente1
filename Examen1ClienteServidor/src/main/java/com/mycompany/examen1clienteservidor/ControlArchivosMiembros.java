/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.examen1clienteservidor;
import static com.mycompany.examen1clienteservidor.ControlArchivos.intercambiar;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
/**
 *
 * @author alext
 */
public class ControlArchivosMiembros {
    private static String cedula, nombre, tel, monto,numeroDeRifa;

    public ControlArchivosMiembros() {
    }
    
    
    public static boolean camposRequeridosIncompletos()
    {
        if((cedula.equals(""))
           || (nombre.equals(""))
           || (tel.equals(""))
           || (monto.equals(""))
           || (numeroDeRifa.equals("")))
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
            DataOutputStream archivo = new DataOutputStream(new FileOutputStream("acta.txt",true));
            //Aquí solicitamos los datos al usuario
            cedula = JOptionPane.showInputDialog("Digite su cédula:");
            nombre = JOptionPane.showInputDialog("Digite su nombre:");
            tel = JOptionPane.showInputDialog("Digite su teléfono:");
            monto = JOptionPane.showInputDialog("Digite el monto:");
            numeroDeRifa= JOptionPane.showInputDialog("Digite el numero de rifa asignado:");
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
                archivo.writeUTF(tel);
                archivo.writeUTF(monto);
                archivo.writeUTF(numeroDeRifa);
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
        String cedBusca, ced, nom, tel, monto;
        //boolean proximo = true;
        cedBusca = JOptionPane.showInputDialog("Digite la cédula de la persona a modificar: ");
        try
        {
            //Todo lo que debe salir bien, va en este try
            //En este try estamos generando los procesos de lectura y escritura de archivos
            //Definimos el archivo que queremos leer
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("acta.txt"));
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
                    tel = archivoLectura.readUTF();
                    monto = archivoLectura.readUTF();
                    //Buscamos la cédula que digitó el usuario
                    if(cedBusca.equals(ced))
                    {
                        //Si la cédula existe entonces vamos a modificar el dato en el archivo
                        //Solicitamos los nuevos datos al usuario
                        nom = JOptionPane.showInputDialog("Digite el nuevo nombre:");
                        tel = JOptionPane.showInputDialog("Digite el nuevo teléfono:");
                        monto = JOptionPane.showInputDialog("Digite el nuevo monto:");
                    }
                    archivoEscritura.writeUTF(ced);
                    archivoEscritura.writeUTF(nom);
                    archivoEscritura.writeUTF(tel);
                    archivoEscritura.writeUTF(monto);
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
        String ced, nom, tel, monto;
        try
        {
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("temporal.txt"));
            DataOutputStream archivoEscritura = new DataOutputStream(new FileOutputStream("acta.txt"));
            
            try
            {
                while(true)
                {
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    tel = archivoLectura.readUTF();
                    monto = archivoLectura.readUTF();
                    archivoEscritura.writeUTF(ced);
                    archivoEscritura.writeUTF(nom);
                    archivoEscritura.writeUTF(tel);
                    archivoEscritura.writeUTF(monto);
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
        String nomBusca, ced, nom, tel, monto;
        boolean proximo = true;
        nomBusca = JOptionPane.showInputDialog("Digite el nombre a buscar:");
        try
        {
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("acta.txt"));
            try
            {
                while(proximo)
                {
                    ced = archivoLectura.readUTF();
                    nom = archivoLectura.readUTF();
                    tel = archivoLectura.readUTF();
                    monto = archivoLectura.readUTF();
                    if(nomBusca.equals(nom))
                    {
                        JOptionPane.showMessageDialog(null, "Se encontró a la persona: " + nom
                                            + ", cuya cédula es: " + ced + ", su teléfono es: " + tel 
                                            + " y el moto es: " + monto + ".");
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
        String cedBusca, ced, nom, tel, monto;
        //boolean proximo = true;
        cedBusca = JOptionPane.showInputDialog("Digite la cédula de la persona a eliminar: ");
        try
        {
            //Todo lo que debe salir bien, va en este try
            //En este try estamos generando los procesos de lectura y escritura de archivos
            //Definimos el archivo que queremos leer
            DataInputStream archivoLectura = new DataInputStream(new FileInputStream("acta.txt."));
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
                    tel = archivoLectura.readUTF();
                    monto = archivoLectura.readUTF();
                    //Buscamos la cédula que digitó el usuario
                    if(!cedBusca.equals(ced))
                    {
                        //Si la cédula no es la que estamos buscando, mantenemos los datos en el archivo
                        archivoEscritura.writeUTF(ced);
                        archivoEscritura.writeUTF(nom);
                        archivoEscritura.writeUTF(tel);
                        archivoEscritura.writeUTF(monto);
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
    
}
