package com.pragma.microserviciotrazabilidad.domain.usercase.constantes;

public class TrazabilidadConstantes {
    private TrazabilidadConstantes() {}

    public static final String TRAZABILIDAD_NO_ENCONTRADA = "No existe trazabilidad para el pedido: ";
    public static final String PEDIDO_CANCELADO = "El pedido fue cancelado, no se puede calcular el tiempo de entrega";
    public static final String ESTADO_PENDIENTE_NO_ENCONTRADO = "No se encontró el estado Pendiente";
    public static final String PEDIDO_NO_ENTREGADO = "El pedido aún no ha sido entregado";
    public static final String ESTADO_CANCELADO = "Cancelado";
    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_ENTREGADO = "Entregado";
}