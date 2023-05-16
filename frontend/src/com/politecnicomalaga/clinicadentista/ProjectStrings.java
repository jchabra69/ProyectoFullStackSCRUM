package com.politecnicomalaga.clinicadentista;

public class ProjectStrings {
    public static final String CLINICANULL = "La Clínica todavía no se ha inicializado";
    public static final String OPCION_NO_VALIDA = "Introduzca un número de opción, por favor.";
    public static final String ALTAPACIENTE_OK = "Paciente dado de alta correctamente";
    public static final String ALTAPACIENTE_ERROR = "Error al dar de alta paciente: ¿existe ya?";
    public static final String ALTA_TRATAMIENTO_OK = "Nuevo tratamiento asignado correctamente";
    public static final String ALTA_TRATAMIENTO_ERROR = "Error al crear el tratamiento: código existente o precio inadecuado";
    public static final String ELIMINA_TRATAMIENTO_OK = "Nuevo tratamiento eliminado correctamente";
    public static final String ELIMINA_TRATAMIENTO_ERROR = "Error al eliminar el tratamiento: ¿código existe?";
    public static final String COBRA_TRATAMIENTO_OK = "El tratamiento se considera cobrado";
    public static final String COBRA_TRATAMIENTO_ERROR = "Error: el tratamiento estaba ya cobrado o no se encontró el código";
    public static final String UPDATEPACIENTE_OK = "Paciente modificado correctamente";	
    public static final String UPDATEPACIENTE_ERROR = "Error al modificar al paciente con dni: ";
    public static final String ELIMINAPACIENTE_OK = "Paciente eliminado correctamente";
    public static final String ELIMINAPACIENTE_ERROR = "Error al eliminar al paciente.\n Puede tener cobros pendientes.\n Paciente con dni: ";
    public static final String BUSQUEDA_NO_ENCUENTRA ="La búsqueda no encontró pacientes con esos datos";
}
