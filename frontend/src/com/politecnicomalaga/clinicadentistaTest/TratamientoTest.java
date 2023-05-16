package com.politecnicomalaga.clinicadentistaTest;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.politecnicomalaga.clinicadentista.Tratamiento;

class TratamientoTest {
	
    static String cod;// = "cod112233";
    static String descr;// = "Empaste premolar 3";
    static String fecha;// = "12/04/2023";
    static float precio;// = 40f;
	
	@BeforeAll
	public static void definirValores() {
		 cod = "cod112233";
		 descr = "Empaste premolar 3";
		 fecha = "12/04/2023";
		 precio = 40f;
		 System.out.println("#before ejecutado");
	}

	@Test
	void testConstructor() {
		
		Tratamiento t = new Tratamiento(TratamientoTest.cod, TratamientoTest.descr, TratamientoTest.fecha,  TratamientoTest.precio);
		assertTrue(TratamientoTest.cod.equals(t.getsCodigo()));
		assertTrue(TratamientoTest.descr.equals(t.getsDescripcion()));
		assertTrue(TratamientoTest.fecha.equals(t.getsFecha()));
		assertTrue(TratamientoTest.precio == t.getfPrecio());
		
		
	}
	
	@Test
	void testSettersGetters() {
		
		
		Tratamiento t = new Tratamiento("a", "a", "a",  0f);
		t.setfPrecio(TratamientoTest.precio);
		t.setsCodigo(TratamientoTest.cod);
		t.setsDescripcion(TratamientoTest.descr);
		t.setsFecha(TratamientoTest.fecha);
		
		
		assertTrue(TratamientoTest.cod.equals(t.getsCodigo()));
		assertTrue(TratamientoTest.descr.equals(t.getsDescripcion()));
		assertTrue(TratamientoTest.fecha.equals(t.getsFecha()));
		assertTrue(TratamientoTest.precio == t.getfPrecio());
		
		
	}
	
	@Test
	void testCompara() {
	
		
		Tratamiento t = new Tratamiento(TratamientoTest.cod, TratamientoTest.descr, TratamientoTest.fecha,  TratamientoTest.precio);
		
		assertTrue(t.compara("11", Tratamiento.AtributosTratamiento.CODIGO));
		assertTrue(t.compara("paste", Tratamiento.AtributosTratamiento.DESCRIPCION));
		assertTrue(t.compara("04", Tratamiento.AtributosTratamiento.FECHA));
		assertTrue(t.compara(">11", Tratamiento.AtributosTratamiento.PRECIO));
		assertTrue(t.compara("40", Tratamiento.AtributosTratamiento.PRECIO));
		assertTrue(t.compara("false", Tratamiento.AtributosTratamiento.COBRADO));
		
		assertFalse(t.compara("89", Tratamiento.AtributosTratamiento.CODIGO));
		assertFalse(t.compara("frase", Tratamiento.AtributosTratamiento.DESCRIPCION));
		assertFalse(t.compara("13", Tratamiento.AtributosTratamiento.FECHA));
		assertFalse(t.compara("<30", Tratamiento.AtributosTratamiento.PRECIO));
		assertFalse(t.compara("30", Tratamiento.AtributosTratamiento.PRECIO));
		assertFalse(t.compara("true", Tratamiento.AtributosTratamiento.COBRADO));
	}

}
