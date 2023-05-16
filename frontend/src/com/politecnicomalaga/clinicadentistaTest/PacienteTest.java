package com.politecnicomalaga.clinicadentistaTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.politecnicomalaga.clinicadentista.Tratamiento;
import com.politecnicomalaga.clinicadentista.Paciente;

class PacienteTest {
	
	//Objetos y valores para los test
	static private String nombre;
	static private String apellidos;
	static private String sTelefono;
	static private String sEmail;
	static private String sDni;
	static private String sFNac;
	
	static String cod;// = "cod112233";
    static String descr;// = "Empaste premolar 3";
    static String fecha;// = "12/04/2023";
    static float precio;// = 40f;
	
	static private Tratamiento t1;
	static private Tratamiento t2;
	static private Tratamiento t3;
	static private Tratamiento t4;
	
	@BeforeAll
	public static void init() {
		 cod = "cod112233";
		 descr = "Empaste premolar 3";
		 fecha = "12/04/2023";
		 precio = 40f;
		 
		 nombre = "Juan";
		 apellidos = "Aguilar";
		 sTelefono = "600000000";
		 sEmail = "estono@nosirve.com";
		 sDni = "11223344A";
		 sFNac = "10/11/1999";
		 
		 t1 = new Tratamiento(cod,descr,fecha,precio);
		 t2 = t1;
		 t3 = new Tratamiento(cod+"A",descr+" newA",fecha,precio+10f);
		 t4 = new Tratamiento(cod+"B",descr+" newB",fecha,precio+20f);
		 
		 System.out.println("#before ejecutado");
		 
		 
	}

	@Test
	void testConstructor() {
		Paciente p = new Paciente(nombre,apellidos,sTelefono,sEmail,sDni,sFNac);
		
		assertTrue(nombre.equals(p.getsNombre()));
		assertTrue(apellidos.equals(p.getsApellidos()));
		assertTrue(sTelefono.equals(p.getsTelefono()));
		assertTrue(sEmail.equals(p.getsEmail()));
		assertTrue(sDni.equals(p.getsDni()));
		assertTrue(sFNac.equals(p.getsFNac()));
		
		assertNull(p.todosTratamientos());
		
	}
	
	@Test
	void testGettersSetters() {
        Paciente p = new Paciente(".",".",".",".",".",".");
		
        
        p.setsNombre(nombre);
        p.setsApellidos(apellidos);
        p.setsTelefono(sTelefono);
        p.setsEmail(sEmail);
        p.setsDni(sDni);
        p.setsFNac(sFNac);
        
        
		assertTrue(nombre.equals(p.getsNombre()));
		assertTrue(apellidos.equals(p.getsApellidos()));
		assertTrue(sTelefono.equals(p.getsTelefono()));
		assertTrue(sEmail.equals(p.getsEmail()));
		assertTrue(sDni.equals(p.getsDni()));
		assertTrue(sFNac.equals(p.getsFNac()));
		
		assertNull(p.todosTratamientos());
		
	}
	
	@Test
	void testAddyBuscarTratamiento() {
		
        Paciente p = new Paciente(nombre,apellidos,sTelefono,sEmail,sDni,sFNac);
		
		assertTrue(nombre.equals(p.getsNombre()));
		assertTrue(apellidos.equals(p.getsApellidos()));
		assertTrue(sTelefono.equals(p.getsTelefono()));
		assertTrue(sEmail.equals(p.getsEmail()));
		assertTrue(sDni.equals(p.getsDni()));
		assertTrue(sFNac.equals(p.getsFNac()));
		
		assertNull(p.todosTratamientos());
		
		p.nuevoTratamiento(cod, descr, fecha, precio);
		Tratamiento lista[] = p.buscaTratamientos("1122", Tratamiento.AtributosTratamiento.CODIGO);
		
		assertTrue(lista.length==1);
		assertTrue(lista[0].getsCodigo().equals(cod));
		assertTrue(lista[0].getsDescripcion().equals(t2.getsDescripcion()));
		assertTrue(lista[0].getsCodigo().equals(t2.getsCodigo()));
		
		p.nuevoTratamiento(cod+"A", descr+" newA", fecha, precio+10f);
		lista = p.buscaTratamientos("1122", Tratamiento.AtributosTratamiento.CODIGO);
		assertTrue(lista.length==2);
		
		assertTrue(lista[1].getsCodigo().equals(t3.getsCodigo()));
		assertFalse(lista[1].getsDescripcion().equals(t2.getsDescripcion()));
		assertFalse(lista[1].getsCodigo().equals(t2.getsCodigo()));
		assertTrue(lista[1].getsDescripcion().equals(t3.getsDescripcion()));
		assertTrue(lista[1].getfPrecio()==t3.getfPrecio());
		
		
	}
	
	@Test
	void testAddyEliminarTratamiento() {
        Paciente p = new Paciente(nombre,apellidos,sTelefono,sEmail,sDni,sFNac);
		
		
		assertNull(p.todosTratamientos());
		
		p.nuevoTratamiento(cod, descr, fecha, precio);
		Tratamiento lista[] = p.buscaTratamientos("1122", Tratamiento.AtributosTratamiento.CODIGO);		
		
		
		assertTrue(lista.length==1);
		assertTrue(lista[0].getsCodigo().equals(cod));
		assertTrue(lista[0].getsDescripcion().equals(t2.getsDescripcion()));
		assertTrue(lista[0].getsCodigo().equals(t2.getsCodigo()));
		
		p.nuevoTratamiento(cod+"A", descr+" newA", fecha, precio+10f);
		lista = p.buscaTratamientos("1122", Tratamiento.AtributosTratamiento.CODIGO);
		assertTrue(lista.length==2);
		
		assertTrue(lista[1].getsCodigo().equals(t3.getsCodigo()));
		assertFalse(lista[1].getsDescripcion().equals(t2.getsDescripcion()));
		assertFalse(lista[1].getsCodigo().equals(t2.getsCodigo()));
		assertTrue(lista[1].getsDescripcion().equals(t3.getsDescripcion()));
		assertTrue(lista[1].getfPrecio()==t3.getfPrecio());
		
		
		assertFalse(p.eliminaTratamiento(cod)); //El tratamiento está sin cobrar
		
		float resultado = p.cobraTratamiento(cod);
		assertTrue(resultado == precio);
		
		assertTrue(p.eliminaTratamiento(cod));  //Ahora sí, ya está cobrado.
		
		lista = p.buscaTratamientos("1122", Tratamiento.AtributosTratamiento.CODIGO);
		assertTrue(lista.length==1);  //Ya sólo queda uno
		assertTrue(lista[0].getsCodigo().equals(t3.getsCodigo()));
		
	}
	
	@Test
	void testAddyTodosTratamiento() {
		assertTrue(true);
	}
	
	
	@Test
	void testSetValor() {
		assertTrue(true);
	}
	
	@Test
	void testToString() {
		assertTrue(true);
	}
	
	
}
