package com.politecnicomalaga.clinicadentista;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

public class MainServer {
    public static void main(String[] args) {
    	
    	
    	
        try {
        	ServerMySQL mysql = new ServerMySQL();
            mysql.initDatabase();
            
            mysql.cobrarTratamiento("123456789;1");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        /*
        
        String resultado;
        //EJEMPLO JSON: {name:"John", age:"31", city:"New York"}
        mysql.insertPaciente("{name:\"John\", age:31, city:\"New York\"}");
        
        try {
            // Esto devuelve la lista de pacientes
            resultado = mysql.listaPacientes("Ruiz");
            System.out.println(resultado);

            // Esto nos da la lista de tratamientos para un paciente específico
            String dniPaciente = "123456789"; // Pongo uno que ya se que existe
            resultado = mysql.listaTratamientos(dniPaciente);
            System.out.println(resultado);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
