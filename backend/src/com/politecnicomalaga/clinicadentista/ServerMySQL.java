
package com.politecnicomalaga.clinicadentista;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


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

    //ListaPacientes
    /* Método que realiza una consulta a la base de datos para obtener información de la tabla "Pacientes" y devuelve un HTML que contiene los datos de los pacientes */
    public String getPacientes() {
        String resultado = "";
        String dni, nombre, apellidos, telefono, fnac, email;
        Connection con = null;
        PreparedStatement ps = null;
        int iRows = 0;
        try {
            con = this.initDatabase();
            ps = con.prepareStatement("SELECT * FROM Pacientes");
            ResultSet rs = ps.executeQuery();

            // Iteración sobre el resultset
            while (rs.next()) {
                iRows++;
                dni = rs.getString("DNI");
                nombre = rs.getString("Nombre");
                apellidos = rs.getString("Apellidos");
                telefono = rs.getString("Telefono");
                fnac = rs.getString("Fnac");
                email = rs.getString("Email");

                // Guardar los resultados
                resultado += "<p>" + dni + ";" +
                        nombre + ";" +
                        apellidos + ";" +
                        telefono + ";" +
                        fnac + ";" +
                        email + "</p>\n";
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
    public String insertPaciente(String sCSV) {
        String resultado = "<p>Error al insertar</p>";
        Connection con = null;
        PreparedStatement ps = null;

        //Creamos objeto Paciente
        Paciente miPr = new Paciente(sCSV);

        try {
            con = this.initDatabase();
            //Establecemos parámetros
            ps = con.prepareStatement(
                    "INSERT INTO Pacientes (DNI, Nombre, Apellidos, Telefono, Fnac, Email) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, miPr.sDni);
            ps.setString(2, miPr.sNombre);
            ps.setString(3, miPr.sApellidos);
            ps.setString(4, miPr.sTelefono);
            ps.setDate(5, java.sql.Date.valueOf(miPr.sFNac));
            ps.setString(6, miPr.sEmail);

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
    public String insertTratamiento(String sCSV) {
        String resultado = "<p>Error al insertar</p>";
        String id, desc, fecha, precio, cobrado, dniPac;
        Connection con = null;
       
        //Creamos objeto Tratamiento
        Tratamiento miPr = new Tratamiento(sCSV);
        
        PreparedStatement ps = null;
      
        try {
            con = this.initDatabase();
            //st = con.createStatement();
            //Establecemos parámetros
            /*
			    Codigo INT AUTO_INCREMENT PRIMARY KEY,
			    Descripcion VARCHAR(100),
			    Fecha DATE,
			    Precio FLOAT,
			    Cobrado BOOLEAN DEFAULT FALSE,
			    Dni_Paciente VARCHAR(9) NOT NULL,
		    */
            ps = con.prepareStatement("insert into Tratamiento (Codigo,Descripcion,Fecha,Precio,Cobrado, Dni_Paciente) values (?,?,?,?,?,?)");
            ps.setString(1, miPr.sCodigo);
            ps.setString(2, miPr.sDescripcion);
            ps.setString(3, miPr.sFecha);
            ps.setString(4, miPr.fPrecio + "");
            ps.setString(5, miPr.bCobrado + "");
            //ps.setString(6, miPr.);
     
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

           
            String query = "SELECT * FROM Tratamiento WHERE Dni_Paciente = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, dniPaciente);

            // Ejecutar la consulta
            rs = ps.executeQuery();

            // Iterar sobre los resultados del ResultSet
            while (rs.next()) {
                iRows++;
                int codigo = rs.getInt("Codigo");
                String descripcion = rs.getString("Descripcion");
                Date fecha = rs.getDate("Fecha");
                double precio = rs.getDouble("Precio");
                boolean cobrado = rs.getBoolean("Cobrado");

                // Extraemos los valores de cada columna correspondiente a los tratamientos y se construye una cadena de texto resultado con la información de cada tratamiento
                resultado += "<p>Código: " + codigo + "</p>";
                resultado += "<p>Descripción: " + descripcion + "</p>";
                resultado += "<p>Fecha: " + fecha + "</p>";
                resultado += "<p>Precio: " + precio + "</p>";
                resultado += "<p>Cobrado: " + cobrado + "</p>";
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
