package com.politecnicomalaga.clinicadentista;

import java.util.ArrayList;


public class Clinica {
	
	//Estados
	private String sNombre;
	private String sDireccion;
	private String sTelefono;
	private String sEmail;
	private String sCIF;
	
	private ArrayList<Paciente> misPacientes;
	
	
	//Comportamiento
	
	//Constructor
	public Clinica(String sNombre, String sDireccion, String sTelefono, String sEmail, String sCIF) {
		this.sNombre = sNombre;
		this.sDireccion = sDireccion;
		this.sTelefono = sTelefono;
		this.sEmail = sEmail;
		this.sCIF = sCIF;
		this.misPacientes = new ArrayList<>();
	}
	
	public Clinica(String sCSV) {
		String[] lineas = sCSV.split("\n");
		//Me vendrá una línea mínimo para clinica
		String[] columnas = lineas[0].split(";");
		if (columnas[0].equals("Clinica")) {
			this.sNombre = columnas[1];
			this.sDireccion = columnas[2];
			this.sTelefono = columnas[3];
			this.sEmail = columnas[4];
			this.sCIF = columnas[5];
		} else {
			return;
		}
		
		//Después de 0 a n tratamientos
		this.misPacientes = new ArrayList<>();
		
		String[] pacientesPosibles = sCSV.split("Paciente");
		String miPacienteCSV;
		
		for (int i=1;i<pacientesPosibles.length;i++) {
			miPacienteCSV = "Paciente" + pacientesPosibles[i];
			Paciente p = new Paciente(miPacienteCSV);
			misPacientes.add(p);
		}
	}
	


	public String getsNombre() {
		return sNombre;
	}


	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}


	public String getsDireccion() {
		return sDireccion;
	}


	public void setsDireccion(String sDireccion) {
		this.sDireccion = sDireccion;
	}


	public String getsTelefono() {
		return sTelefono;
	}


	public void setsTelefono(String sTelefono) {
		this.sTelefono = sTelefono;
	}


	public String getsEmail() {
		return sEmail;
	}


	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}


	public String getsCIF() {
		return sCIF;
	}


	public void setsCIF(String sCIF) {
		this.sCIF = sCIF;
	}
	
	//CRUD de Pacientes
	public boolean nuevoPaciente(String nombre, String apellidos, String dni, String fNac, String email, String tfno) {

		if (this.buscaPacientes(dni, Paciente.AtributosPaciente.DNI) == null) {
			misPacientes.add(new Paciente(nombre, apellidos, tfno, email, dni, fNac));
			return true;
		}
		return false;
		
	}
	
	public boolean eliminaPaciente(String dni) {
		Paciente[] p = this.buscaPacientes(dni, Paciente.AtributosPaciente.DNI);
		if (p != null && p.length == 1) {
			if (!p[0].tratamientosPendienteCobro()) {
				return (misPacientes.remove(p[0]));
			}
		}
		return false;
	}
	
	
	public Paciente[] buscaPacientes(String campoBusqueda, Paciente.AtributosPaciente atributoBusqueda) {
        ArrayList<Paciente> resultado = new ArrayList<>();
		
		for(Paciente p: misPacientes) {
			if (p.compara(campoBusqueda,atributoBusqueda)) {
				resultado.add(p);
			}
		}
		
		if (resultado.size()>0) {
			Paciente[] listaP = new Paciente[resultado.size()]; 
			return resultado.toArray(listaP);
		}
		return null;
	}
	
	public Paciente buscaUnPaciente(String dni) {
        
		for(Paciente p: misPacientes) {
			if (p.compara(dni,Paciente.AtributosPaciente.DNI)) {
				return p;
			}
		}
		
		return null;
	}
	
	
	public Paciente[] todosPacientes() {
		if (misPacientes.size()==0) return null;
		Paciente[] listaP = new Paciente[misPacientes.size()]; 
		return misPacientes.toArray(listaP);
	}
	
	public boolean actualizaPaciente(String dni, String campo, Paciente.AtributosPaciente atrActualizar) {
		Paciente p = this.buscaUnPaciente(dni);
		if (p != null) {
			p.setValor(campo, atrActualizar);
			return true;
		}
		return false;
	}
	
	
	public String toCSV() {
		String resultado = String.format("Clinica;%s;%s;%s;%s;%s\n", sNombre, sDireccion, sTelefono, sEmail, sCIF);
		for(Paciente p:this.misPacientes) {
			resultado += p.toCSV();
		}
		return resultado;
	}
	
	

}
