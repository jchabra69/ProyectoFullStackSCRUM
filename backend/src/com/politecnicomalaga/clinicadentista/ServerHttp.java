package com.politecnicomalaga.clinicadentista;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerHttp extends HttpServlet {


/* Método que procesa una solicitud HTTP, obtiene sus parámetros y realiza operaciones en una base de datos MySQL según el valor de la petición que genera una respuesta HTML que incluye el resultado de las operaciones.*/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
	// Obtener los parámetros de la petición
        String peticionSolicitada = request.getParameter("peticion");
        String datos = request.getParameter("datos");  //Datos enviados en CSV
       
        // Configurar el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");
        
        String resultado = "";
        ServerMySQL bd = new ServerMySQL();
	    
        // Realizar operaciones según la petición solicitada
        switch (peticionSolicitada) {
            case "todos": resultado = bd.getPacientes();
                 break;
            case "insertar": resultado = bd.insertPaciente(datos);
                 break;
            default: resultado = "<p>Parámetro desconocido</p>";
        }
        
        try (PrintWriter out = response.getWriter()) {
            // Generar la respuesta HTML
            //out.print(resultado);
            out.println("<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                            "<title>Get Pacientes Result</title>\n" +
                            "<meta charset=\"UTF-8\">\n" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "</head>\n" +
                        "<body style=\"background-color:red;\"><p>Resultado:</p>\n" +
                            resultado +
                        "</body>\n" +
                        "</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "BackEnd TrabajoTaller Servlet";
    }// </editor-fold>

}
	
