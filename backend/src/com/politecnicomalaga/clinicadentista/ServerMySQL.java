
package com.politecnicomalaga.clinicadentista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class ServerMySQL {

    private String sLastError;

    public ServerMySQL() {
        sLastError = "";
    }
    /* Método que se encarga de establecer la conexión a una base de datos (MySQL) utilizando los parámetros de conexión especificados y devuelve un objeto Connection que representa dicha conexión*/
    public Connection initDatabase() {
        Connection con = null;
        // Inicializa toda la información de la conexión a la base de datos
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql://130.61.154.70:3306/";
        // Nombre de la base de datos a la que se accederá
        String dbName = "ClinicaDentistadb";
        String dbUsername = "nico";
        String dbPassword = "CURSO2022";

        try {
            // Carga el controlador del driver de MySQL
            Class.forName(dbDriver);
            // Proporciona los datos para acceder
            con = DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);

            // Comprobamos si funciona
            System.out.println("Ole, olee k me  he conectao a tu base de datos");
        } catch (Exception e) {
            // SI FALLA
            sLastError = sLastError + "<p>Error conectando a la BBDD: " + e.getMessage() + "</p>";
            e.printStackTrace();
        }
        return con;
    }

    /* Método que realiza una consulta a la base de datos para obtener información de la tabla "Pacientes" y devuelve un HTML que contiene los datos de los pacientes */
    public String listaPacientes(String apellidos) {
        String resultado = "";
        Connection con = null;
        PreparedStatement ps = null;
        int iRows = 0;
        try {
            con = this.initDatabase();
            ps = con.prepareStatement("SELECT * FROM Pacientes WHERE Apellidos = '" + apellidos + "';");
            ResultSet rs = ps.executeQuery();

            // Iteración sobre el resultset
            while (rs.next()) {
                iRows++;
                // Guardar los resultados
                resultado += "<p>" + rs.getString("DNI") + ";" +
                		rs.getString("Nombre") + ";" +
                		rs.getString("Apellidos") + ";" +
                		rs.getString("Telefono") + ";" +
                        rs.getString("Fnac") + ";" +
                        rs.getString("Email") + "</p>\n";
            }
        } catch (Exception e) {
            sLastError = sLastError + "<p>Error accediendo a la BBDD Select: " + e.getMessage() + "</p>";
            e.printStackTrace();
        } finally {
            // Liberar recursos: cerrar la conexión y la sentencia
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la BBDD: " + e.getMessage() + "</p>";
                e.printStackTrace();
            }
        }
        resultado += "\n<p>Rows recogidas: " + iRows + "</p>\n";
        if (sLastError.isEmpty())
            return resultado;
        else
            return resultado + sLastError;
    }

	public String deletePaciente(String sCSV) {
		String resultado = "???";
        Connection con = null;
        PreparedStatement ps = null;
        int iRows = 0;
        try {
            con = this.initDatabase();
            Paciente miPr = new Paciente(sCSV);
            ps = con.prepareStatement("DELETE FROM Pacientes p WHERE p.DNI = '" + miPr.sDni + "'");
            ResultSet rs = ps.executeQuery();
        } catch (Exception e) {
            sLastError = sLastError + "<p>Error al eliminar un Paciente: " + e.getMessage() + "</p>";
            e.printStackTrace();
        } finally {
            // Liberar recursos: cerrar la conexión y la sentencia
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la conexión a la BBDD: " + e.getMessage() + "</p>";
                e.printStackTrace();
            }
        }
        resultado += "\n<p>Paciente eliminado</p>\n";
        if (sLastError.isEmpty())
            return resultado;
        else
            return resultado + sLastError;
	}
    
    /* Método de inserción en la tabla de Pacientes de un nuevo valor. */
    public String insertPaciente(String jsonPaciente) {
        String resultado = "<p>Error al insertar</p>";
        
        Connection con = null;
        PreparedStatement ps = null;
        //Creamos objeto Paciente
        String[] valores = jsonPaciente.substring(1).replace("}", "").replace("\"", "").replace(" ", "").split(",");
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < 6; i++) {
        	map.put(valores[i].split(":")[0].toLowerCase(), valores[i].split(":")[1]);
        }

        try {
            con = this.initDatabase();
            //Establecemos parámetros
            ps = con.prepareStatement(
                    "INSERT INTO Pacientes (DNI, Nombre, Apellidos, Telefono, Fnac, Email) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, map.get("dni"));
            ps.setString(2, map.get("nombre"));
            ps.setString(3, map.get("apellidos"));
            ps.setString(4, map.get("telefono"));
            ps.setDate(5, java.sql.Date.valueOf(map.get("fnac")));
            ps.setString(6, map.get("email"));

            if (ps.executeUpdate() != 0)
                resultado = "<p>Paciente insertado correctamente</p>";
            else
                resultado = "<p>Algo ha salido mal con la sentencia INSERT Pacientes</p>";

        } catch (Exception e) {
            sLastError = sLastError + "<p>Error accediendo a la BBDD: " + e.getMessage() + "</p>";
            e.printStackTrace();
        } finally {
            // Liberamos recursos. Cerramos sentencia y conexión
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la BBDD: " + e.getMessage() + "</p>";
                e.printStackTrace();
            }
        }

        if (sLastError.isEmpty())
            return resultado;
        else
            return resultado + sLastError;
    }
  
    /* Método de inserción en la tabla de Tratamientos de un nuevo valor */
    public String insertTratamiento(String jsonTratamiento) {
        String resultado = "<p>Error al insertar</p>";
        Connection con = null;
       
        //Creamos objeto Tratamiento
        String[] valores = jsonTratamiento.substring(1).replace("}", "").replace("\"", "").replace(" ", "").split(",");
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < 6; i++) {
        	map.put(valores[i].split(":")[0].toLowerCase(), valores[i].split(":")[1]);
        }

        PreparedStatement ps = null;
      
        try {
            con = this.initDatabase();
            //st = con.createStatement();
            //Establecemos parámetros
            ps = con.prepareStatement("insert into Tratamiento (Codigo,Descripcion,Fecha,Precio,Cobrado,Dni_Paciente) values (?,?,?,?,?,?)");
            ps.setString(1, map.get("codigo"));
            ps.setString(2, map.get("descripcion"));
            ps.setDate(3, java.sql.Date.valueOf(map.get("fecha")));
            ps.setString(4, map.get("precio"));
            ps.setString(5, map.get("cobrado"));
            ps.setString(6, map.get("dnipaciente"));
     
            if (ps.executeUpdate()!=0)
        		resultado = "<p>Tratamiento insertado correctamente</p>";
            else 
            	resultado = "<p>Algo ha salido mal con la sentencia Insert Tratamiento</p>";            
            //En este caso es una orden hacia la BBDD, y no tenemos
            //ResultSet para iterar, las cosas pueden ir bien, o mal, nada más
            //que hacer entonces aquí
            
        } catch (Exception e) {
            sLastError = sLastError + "<p>Error accediendo a la BBDD Select: " + e.getMessage()+ "</p>";
            e.printStackTrace();
        } finally {
            // Liberamos recursos. Cerramos sentencia y conexión
            try {
                if (ps!= null) ps.close();
                if (con!=null) con.close();
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la BBDD: " + e.getMessage()+ "</p>";
                e.printStackTrace();
                     
            }
        }
        if (sLastError.isEmpty()) return resultado;        
        else return resultado + sLastError;
        
    }


    /* Método para obtener la lista de tratamientos de un paciente dado su DNI */
    public String listaTratamientos(String dniPaciente) {
        String resultado = "";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int iRows = 0;

        try {
            //Conexión con la base de datos
            con = this.initDatabase();

           //No se deben usar doble comillas para el ?
            String query = "SELECT * FROM Tratamiento WHERE Dni_Paciente = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, dniPaciente);

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Iterar sobre los resultados del ResultSet
            while (rs.next()) {
                iRows++;

                // Extraemos los valores de cada columna correspondiente a los tratamientos y se construye una cadena de texto resultado con la información de cada tratamiento
                resultado += "<p>Código: " + rs.getInt("Codigo") + "</p>";
                resultado += "<p>Descripción: " + rs.getString("Descripcion") + "</p>";
                resultado += "<p>Fecha: " + rs.getDate("Fecha") + "</p>";
                resultado += "<p>Precio: " + rs.getDouble("Precio") + "</p>";
                resultado += "<p>Cobrado: " + rs.getBoolean("Cobrado") + "</p>";
                resultado += "<br/>";
            }
        } catch (Exception e) {
            sLastError = sLastError + "<p>Error accediendo a la BBDD Select: " + e.getMessage() + "</p>";
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la BBDD: " + e.getMessage() + "</p>";
                e.printStackTrace();
            }
        }

        // Resultado
        resultado += "\n<p>Rows recogidas: " + iRows + "</p>\n";
        if (sLastError.isEmpty()) {
            return resultado;
        } else {
            return resultado + sLastError;
        }
    }
    
    public String cobrarTratamiento(String codigo) { // codigo = codTratamiento
        String resultado = "";
        Connection con = null;
        PreparedStatement ps = null;
        String[] codigos = codigo.split(";");

        // Declara y asigna un valor inicial a sLastError
        String sLastError = "";

        try {
            con = this.initDatabase();
            String query = "UPDATE Tratamiento SET Cobrado = true WHERE Dni_Paciente = " + codigos[0] + " AND Codigo = " + codigos[1];
            ps = con.prepareStatement(query);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 0) {
                resultado = "<p>Tratamiento cobrado correctamente</p>";
            } else {
                resultado = "<p>Algo ha salido mal con la sentencia Update Tratamiento</p>";
            }

        } catch (Exception e) {
            sLastError = sLastError + "<p>Error actualizando la BBDD: " + e.getMessage() + "</p>";
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                sLastError = sLastError + "<p>Error cerrando la BBDD: " + e.getMessage() + "</p>";
                e.printStackTrace();
            }
        }

        if (sLastError.isEmpty()) {
            return resultado;
        } else {
            return resultado + sLastError;
        }
    }

}


/////////////////////////////////////////

/*
 * package com.politecnicomalaga.clinicadentista;
 * 
 * import java.sql.Connection;
 * import java.sql.DriverManager;
 * import java.sql.SQLException;
 * import java.sql.Driver;
 * import java.sql.Statement;
 * 
 * public class serverMySQL {
 * 
 * String url = "jdbc:mysql://130.61.62.9:3306/ClinicaDentistadb";
 * String username = "nico";
 * String password = "CURSO2022";
 * 
 * Connection conexxion;
 * public void connect() throws SQLException {
 * conexxion = DriverManager.getConnection(url, username, password);
 * }
 * 
 * public void test() throws SQLException {
 * 
 * }
 * 
 * 
 * public String[] getIncidencia() throws SQLException {
 * String[] datosIncidencia = new String[8];
 * String sql = "SELECT * FROM incidencia";
 * try (Statement statement = conexxion.createStatement();
 * java.sql.ResultSet resultSet = statement.executeQuery(sql)) {
 * while (resultSet.next()) {
 * datosIncidencia[0] = resultSet.getString("codigo");
 * datosIncidencia[1] = resultSet.getString("dniPropio");
 * datosIncidencia[2] = resultSet.getString("fecha");
 * datosIncidencia[3] = resultSet.getString("hora");
 * datosIncidencia[4] = resultSet.getString("matriculaPropia");
 * datosIncidencia[5] = resultSet.getString("matriculaAjena");
 * datosIncidencia[6] = resultSet.getString("descripcion");
 * datosIncidencia[7] = resultSet.getString("dniAjeno");
 * }
 * } catch (SQLException e) {
 * throw new SQLException("Error executing code: " + sql, e);
 * }
 * return datosIncidencia;
 * }
 * 
 * public void uploadOficinaData(Oficina oficina) throws SQLException {
 * String sql;
 * if(oficina.getsCodigo() < 0)
 * sql = String.
 * format("INSERT INTO oficina (nombre, direccion, ciudad, CP, telefono, email) VALUES ('%s', '%s', '%s', '%s', '%s', '%s');"
 * , oficina.getsNombre(), oficina.getsDireccion(), oficina.getsCiudad(),
 * oficina.getsCP(), oficina.getsTelefono(), oficina.getsEmail());
 * else
 * sql = String.
 * format("INSERT INTO oficina VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');"
 * , oficina.getsCodigo(), oficina.getsNombre(), oficina.getsDireccion(),
 * oficina.getsCiudad(), oficina.getsCP(), oficina.getsTelefono(),
 * oficina.getsEmail());
 * ejecutarCodigo(sql, conexxion);
 * }
 * 
 * 
 * public void uploadOficinaFull(Oficina oficina) throws SQLException { //TODOS
 * los datos dentro de oficina.
 * String sql = "";
 * uploadOficinaData(oficina);
 * int codOficina = oficina.getsCodigo();
 * Cliente[] clientes = oficina.getMisClientes();
 * for(int i = 0; i < clientes.length; i++) { //(dni, nombre, apellidos,
 * codPoliza, email, direccion, telefono, codigoOficina)
 * Cliente c = clientes[i];
 * if(codOficina < 0)
 * throw new
 * SQLException("ERROR CODIGO AUTOMATICO DE OFICINA NO IMPLEMENTADO TODAVIA");
 * sql += String.
 * format("INSERT INTO cliente VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');"
 * , c.getDni(), c.getNombre(), c.getApellidos(), c.getCodPoliza(),
 * c.getEmail(), c.getDireccion(), c.getTfno(), codOficina);
 * 
 * for(int j = 0; j < c.getIncidencias().length; j++) {//(codigo, dniPropio,
 * fecha, hora, matriculaPropia, matriculaAjena, descripcion, !! dniAjeno,
 * diasUrgencia)
 * Incidencia in = c.getIncidencias()[j];
 * if (in instanceof IncidenciaAjena) {
 * sql += String.
 * format("INSERT INTO incidencia (codigo, dniPropio, fecha, hora, matriculaPropia, matriculaAjena, descripcion, dniAjeno) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');"
 * , in.getsCodigo(), c.getDni(), in.getsFecha(), in.getsHora(),
 * in.getsMatriculaPropia(), in.getsMatriculaAjena(), in.getsDescripcion(),
 * ((IncidenciaAjena)in).getDniAjeno());
 * } else if (in instanceof IncidenciaUrgente) {
 * sql += String.
 * format("INSERT INTO incidencia VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', NULL, '%s');"
 * , in.getsCodigo(), c.getDni(), in.getsFecha(), in.getsHora(),
 * in.getsMatriculaPropia(), in.getsMatriculaAjena(), in.getsDescripcion(),
 * ((IncidenciaUrgente)in).getDiasMaximo());
 * } else {
 * sql += String.
 * format("INSERT INTO incidencia (codigo, dniPropio, fecha, hora, matriculaPropia, matriculaAjena, descripcion) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');"
 * , in.getsCodigo(), c.getDni(), in.getsFecha(), in.getsHora(),
 * in.getsMatriculaPropia(), in.getsMatriculaAjena(), in.getsDescripcion());
 * }
 * }
 * }
 * 
 * ejecutarCodigo(sql, conexxion);
 * }
 * 
 * public void ejecutarCodigo(String codigo, Connection conexion) throws
 * SQLException {
 * String sqlError = "Null";
 * try (Statement statement = conexion.createStatement()) {
 * for(String sql: codigo.split(";")) {
 * sqlError = sql;
 * statement.executeUpdate(sql + ";");
 * }
 * } catch (SQLException e) {
 * throw new SQLException("Error executing code: " + sqlError, e);
 * }
 * }
 * }
 */
