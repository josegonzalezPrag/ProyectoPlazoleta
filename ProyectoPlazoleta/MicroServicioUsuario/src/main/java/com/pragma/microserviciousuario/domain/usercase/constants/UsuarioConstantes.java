package com.pragma.microserviciousuario.domain.usercase.constants;

public class UsuarioConstantes {
    private UsuarioConstantes() {}
    public static final String ROL_NO_ENCONTRADO = "El rol no existe";
    public static final String USUARIO_MENOR_DE_EDAD = "El usuario debe ser mayor de edad";
    public static final String CELULAR_INVALIDO = "El celular debe tener máximo 13 caracteres";
    public static final String CORREO_INVALIDO = "El correo no es válido";
    public static final String CORREO_YA_EXISTE = "Ya existe un usuario con ese correo";
    public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String REGEX_CORREO = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    public static final Long ROL_ID_PROPIETARIO = 2L;
    public static final Long ROL_ID_EMPLEADO = 3L;
    public static final Long ROL_ID_CLIENTE = 4L;
    public static final String ROL_PROPIETARIO = "PROPIETARIO";
    public static final String ROL_EMPLEADO = "EMPLEADO";
    public static final String ROL_CLIENTE = "CLIENTE";
}
