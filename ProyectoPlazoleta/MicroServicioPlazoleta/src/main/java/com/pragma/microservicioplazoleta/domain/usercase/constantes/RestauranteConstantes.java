package com.pragma.microservicioplazoleta.domain.usercase.constantes;

public class RestauranteConstantes {
    private RestauranteConstantes(){}

    public static final String RESTAURANTE_NO_EXISTE = "El restaurante no existe";
    public static final String PROPIETARIO_NO_EXISTE = "El propietario no existe";
    public static final String TELEFONO_INVALIDO = "El teléfono debe tener máximo 13 caracteres";
    public static final String ROL_NO_PROPIETARIO = "El usuario no tiene rol de Propietario";
    public static final String ROL_PROPIETARIO = "PROPIETARIO";
    public static final String REGEX_TELEFONO = "^\\+?\\d{1,12}$";
    public static final String SIN_PERMISO_RESTAURANTE = "No tienes permiso para ver la información de este restaurante";
}
