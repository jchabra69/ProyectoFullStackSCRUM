package com.politecnicomalaga.clinicadentista;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
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
        String datosPaciente = request.getParameter("datosPaciente");  //Datos enviados en CSV
        String datosTratamiento = request.getParameter("datosTratamiento");
       
        // Configurar el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");
        
        String resultado = "";
        ServerMySQL bd = new ServerMySQL();
	    
        // Realizar operaciones según la petición solicitada
        switch (peticionSolicitada) {
            case "todosPacientes": resultado = bd.getPacientes();
                 break;
            case "insertarPaciente": resultado = bd.insertPaciente(datosPaciente);
                 break;      
            case "eliminarPaciente": resultado = bd.deletePaciente();
                 break;
            case "insertarTratamiento": resultado = bd.insertTratamiento(datosTratamiento);
                 break; 
            case "eliminarPaciente": resultado = bd.deleteTratamiento();
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
                        "<body style=\"background-color:light-green;\"><p>Resultado:</p>\n" +
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
	
