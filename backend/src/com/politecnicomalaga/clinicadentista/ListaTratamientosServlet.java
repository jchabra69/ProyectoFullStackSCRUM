
/**
 *
 * @author Nikola
 */

package com.politecnicomalaga.clinicadentista;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListaTratamientosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Pido dni en la request
        String dni = request.getParameter("dni");

        response.setContentType("text/html;charset=UTF-8");

        String resultado = "";
        ServerMySQL bd = new ServerMySQL();

        // La request va a ser el resultado del método de la clase contenedoraa
        resultado = bd.listaTratamientos(dni);

        try (PrintWriter out = response.getWriter()) {
            // Generar la respuesta HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de Tratamientos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Tratamientos</h1>");
            out.println(resultado);
            out.println("</body>");
            out.println("</html>");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
