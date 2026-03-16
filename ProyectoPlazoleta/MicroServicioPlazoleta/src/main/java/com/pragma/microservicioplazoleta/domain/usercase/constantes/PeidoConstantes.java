package com.pragma.microservicioplazoleta.domain.usercase.constantes;

public class PeidoConstantes {
    private PeidoConstantes(){}

    public static final String ESTADO_CANCELADO = "Cancelado";
    public static final String PEDIDO_NO_ESTA_PENDIENTE = "El pedido solo puede cancelarse cuando está en estado Pendiente";
    public static final String CLIENTE_NO_ES_DUENO = "No tienes permiso para cancelar este pedido";
    public static final String ESTADO_ENTREGADO = "Entregado";
    public static final String CODIGO_INVALIDO = "El código de entrega es incorrecto";
    public static final String PEDIDO_NO_ESTA_LISTO = "El pedido debe estar en estado Listo para ser entregado";
    public static final String PEDIDO_NO_EXISTE = "El pedido no existe";
    public static final String CLIENTE_CON_PEDIDO = "El cliente ya tiene un pedido en proceso";
    public static final String PLATOS_RESTAURANTE_INVALIDO = "Todos los platos deben pertenecer al mismo restaurante";
    public static final String PEDIDO_RESTAURANTE_INVALIDO = "El pedido no pertenece al restaurante del empleado";
    public static final String CHEF_NO_ASIGNADO = "Solo el chef asignado puede marcar el pedido como listo";
    public static final String ESTADO_INVALIDO = "El pedido debe estar En_Preparacion para marcarse como listo";
    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_EN_PREPARACION = "En_Preparacion";
    public static final String ESTADO_LISTO = "Listo";
    public static final String EMPLEADO_SIN_RESTAURANTE = "El empleado no pertenece a ningún restaurante";

}
