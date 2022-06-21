package org.aguzman.junitapp.ejemplo.junit5_app.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        String esperado = "Andres";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
        assertTrue(real.equals(esperado));
    }
}