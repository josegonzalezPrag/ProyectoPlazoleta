package com.pragma.microserviciomensajeria;


import com.pragma.microserviciomensajeria.infrastructure.configuration.TwilioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwilioServiceTest {

    @InjectMocks
    private TwilioService twilioService;

    private TwilioService twilioServiceSpy;

    @BeforeEach
    void setUp() {
        twilioServiceSpy = Mockito.spy(twilioService);
    }

    @Test
    void deberiaEnviarSmsExitosamente() {
        doNothing().when(twilioServiceSpy).crearMensaje(any(), any());

        assertDoesNotThrow(() ->
                twilioServiceSpy.enviarSms("+573001234567", "Tu pedido está listo"));

        verify(twilioServiceSpy, times(1)).crearMensaje("+573001234567", "Tu pedido está listo");
    }

    @Test
    void deberiaEnviarSmsConCodigoDeEntrega() {
        doNothing().when(twilioServiceSpy).crearMensaje(any(), any());

        assertDoesNotThrow(() ->
                twilioServiceSpy.enviarSms("+573001234567", "Tu PIN es 1234"));

        verify(twilioServiceSpy, times(1)).crearMensaje("+573001234567", "Tu PIN es 1234");
    }

}
