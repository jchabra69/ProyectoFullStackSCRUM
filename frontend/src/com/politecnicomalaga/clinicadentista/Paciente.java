package com.politecnicomalaga.clinicadentista;

import java.util.ArrayList;


public class Paciente {
	public enum AtributosPaciente {NOMBRE,APELLIDOS,DNI,FNAC,TFNO, EMAIL};
	
	//ESTADOS - Atributos
	private String sNombre;
	private String sApellidos;
	private String sTelefono;
	private String sEmail;
	private String sDni;
	private String sFNac;
	
	private ArrayList<Tratamiento> misTratamientos;
	
	//Comportamiento
	
	//Constructor
	public Paciente(String sNombre, String sApellidos, String sTelefono, String sEmail, String sDni, String sFNac) {
		this.sNombre = sNombre;
		this.sApellidos = sApellidos;
		this.sTelefono = sTelefono;
		this.sEmail = sEmail;
		this.sDni = sDni;
		this.sFNac = sFNac;
		misTratamientos = new ArrayList<>();
	}
	
	
	public Paciente(String sCSV) {
		String[] lineas = sCSV.split("\n");
		//Me vendrá una línea mínimo para paciente
		String[] columnas = lineas[0].split(";");
		if (columnas[0].equals("Paciente")) {
			this.sNombre = columnas[1];
			this.sApellidos = columnas[2];
			this.sTelefono = columnas[3];
			this.sEmail = columnas[4];
			this.sDni = columnas[5];
			this.sFNac = columnas[6];
		} else {
			return;
		}
		
		//Después de 0 a n tratamientos
		misTratamientos = new ArrayList<>();
		
		//Si las líneas son más de 1... Hay tratamientos
		for(int i= 1;i<lineas.length;i++) {
			//trabajo el tratamiento
			Tratamiento t = new Tratamiento(lineas[i]);
			//lo pongo en la lista
			this.misTratamientos.add(t);
		}
		
	}

	//Getters y Setters
	public String getsNombre() {
		return sNombre;
	}

	public void setsNombre(String sNombre) {
		this.sNombre = sNombre;
	}

	public String getsApellidos() {
		return sApellidos;
	}

	public void setsApellidos(String sApellidos) {
		this.sApellidos = sApellidos;
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

	public String getsDni() {
		return sDni;
	}

	public void setsDni(String sDni) {
		this.sDni = sDni;
	}

	public String getsFNac() {
		return sFNac;
	}

	public void setsFNac(String sFNac) {
		this.sFNac = sFNac;
	}
	
	
	//CRUD tratamientos del paciente
	public boolean nuevoTratamiento(String codigo, String descripcion, String fecha, float precio) {

		if (this.buscaUnTratamiento(codigo) == null && precio >= 0f) {
			misTratamientos.add(new Tratamiento(codigo,descripcion,fecha,precio));
			return true;
		}
		return false;
		
	}
	
	public boolean eliminaTratamiento(String codigo) {
		Tratamiento t = this.buscaUnTratamiento(codigo);
		if (t != null) {
			if (t.estaCobrado()) {
				return (misTratamientos.remove(t));
			}
		}
		return false;
	}
	
	public Tratamiento[] buscaTratamientos(String campoBusqueda, Tratamiento.AtributosTratamiento atributoBusqueda) {
		ArrayList<Tratamiento> resultado = new ArrayList<>();
		
		for(Tratamiento t:misTratamientos) {
			if (t.compara(campoBusqueda,atributoBusqueda)) {
				resultado.add(t);
			}
		}
		
		if (resultado.size()>0) {
			Tratamiento[] listaT = new Tratamiento[resultado.size()]; 
			return resultado.toArray(listaT);
		}
		return null;
	}
	
	public Tratamiento buscaUnTratamiento(String codigo) {
		
		for(Tratamiento t:misTratamientos) {
			if (t.getsCodigo().equals(codigo)) {
				return t;
			}
		}
		
		return null;
	}
	
	public Tratamiento[] todosTratamientos() {
		if (misTratamientos.size()==0) return null;
		Tratamiento[] listaT = new Tratamiento[misTratamientos.size()]; 
		return misTratamientos.toArray(listaT);
	}
	
	
	//NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
	public ArrayList<Tratamiento> todosTratamientosNOOOOO() {
		return misTratamientos;
	}
	
	public float cobraTratamiento(String codigo) {
		Tratamiento t = this.buscaUnTratamiento(codigo);
		if (t != null) {
			if (!t.estaCobrado()) {
				t.cobrar();
				return t.getfPrecio();
			}
		}
		return -1f;
	}
	
	
	public boolean tratamientosPendienteCobro() {
		
		for(Tratamiento t:misTratamientos) {
			if (!t.estaCobrado()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean compara(String campo, AtributosPaciente at) {
		
		switch (at) {
		case NOMBRE: return this.sNombre.contains(campo);
		case APELLIDOS: return this.sApellidos.contains(campo);
		case DNI: return this.sDni.contains(campo);
		case FNAC: return this.sFNac.contains(campo);
		case EMAIL: return this.sEmail.contains(campo);
		case TFNO: return this.sTelefono.contains(campo);	
		}
		
	    return false;
	}
	
	//Setter multi-campo. Se le da el valor y el atributo y se asigna el valor en el atributo designado
    public void setValor(String campo, AtributosPaciente at) {
		
		switch (at) {
		case NOMBRE: this.setsNombre(campo);
		break;
		case APELLIDOS:  this.setsApellidos(campo);
		break;
		case DNI:  this.setsDni(campo);
		break;
		case FNAC:  this.setsFNac(campo);
		break;
		case EMAIL:  this.setsEmail(campo);
		break;
		case TFNO: this.setsTelefono(campo);	
		}
		
	}
	
	@Override
	public String toString() {
		return String.format("%15s#%25s#%9s#%10s#%20s#%13s", sNombre, sApellidos, sDni, sFNac, sEmail,sTelefono);
	}
	
	public String toCSV() {
		/* Para recordar el orden de los atributos
		 * this.sNombre = columnas[1];
			this.sApellidos = columnas[2];
			this.sTelefono = columnas[3];
			this.sEmail = columnas[4];
			this.sDni = columnas[5];
			this.sFNac = columnas[6];
		 */
		String resultado = String.format("Paciente;%s;%s;%s;%s;%s;%s\n", sNombre, sApellidos, sTelefono, sEmail, sDni, sFNac);
		for(Tratamiento t:this.misTratamientos) {
			resultado += t.toCSV();
		}
		return resultado;
	}
	

}
