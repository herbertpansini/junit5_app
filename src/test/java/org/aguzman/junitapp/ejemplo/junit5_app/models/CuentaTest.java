package org.aguzman.junitapp.ejemplo.junit5_app.models;

import org.aguzman.junitapp.ejemplo.junit5_app.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;

    @BeforeEach
    void initMetodoTest() {
        this.cuenta = new Cuenta("Andres", new BigDecimal("1000.12345"));
        System.out.println("Iniciando el metodo.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo de prueba.");
    }

    @Test
    @DisplayName("probando nombre de la cuenta corriente!")
    void testNombreCuenta() {
        String esperado = "Andres";
        String real = this.cuenta.getPersona();
        assertNotNull(real, () -> "la cuenta no pode ser nula.");
        assertEquals(esperado, real, () -> "el nombre de la cuenta no es el que se esperaba: se esperaba " + esperado + " sin embargo fue " + real);
        assertTrue(real.equals(esperado), () -> "nombre cuenta esperada debe ser igual a la real.");
    }

    @Test
    @DisplayName("probando el saldo de la cuenta corriente, que no sea null, ,ayor que cero, valor esperado.")
    void testSaldoCuenta() {
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1000.12345, this.cuenta.getSaldo().doubleValue());
        assertFalse(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("testeando referencias que sean iguales con el metodo equals.")
    void testReferenciaCuenta() {
        this.cuenta = new Cuenta("John Doe", new BigDecimal("8900.9997"));
        Cuenta cuenta2 = new Cuenta("John Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta2, cuenta);

        assertEquals(cuenta2, this.cuenta);
    }

    @Test
    void testDebitoCuenta() {
        this.cuenta.debito(new BigDecimal(100));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(900, this.cuenta.getSaldo().intValue());
        assertEquals("900.12345", this.cuenta.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta() {
        this.cuenta.credito(new BigDecimal(100));
        assertNotNull(this.cuenta.getSaldo());
        assertEquals(1100, this.cuenta.getSaldo().intValue());
        assertEquals("1100.12345", this.cuenta.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteException() {
        Exception exception = assertThrows(DineroInsuficienteException.class, ()-> this.cuenta.debito(new BigDecimal(1500)));
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuentas() {
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    @Disabled
    @DisplayName("probando relaciones entre las cuentas y el banco con assertAll.")
    void testRelacionBancoCuentas() {
        fail();
        Cuenta cuenta1 = new Cuenta("John Doe", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);

        banco.setNombre("Banco del Estado");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(() -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString(), () -> "el valor del saldo de la cuenta2 no es el esperado."),
                    () -> assertEquals("3000", cuenta1.getSaldo().toPlainString(), () -> "el valor del saldo de la cuenta1 no es el esperado."),
                    () -> assertEquals(2, banco.getCuentas().size(), () -> "el banco no tiene las cuentas esperadas."),
                    () -> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre()),
                    () -> assertEquals("Andres", banco.getCuentas().stream()
                            .filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona()),
                    () -> assertTrue(banco.getCuentas().stream()
                        .anyMatch(c -> c.getPersona().equals("John Doe"))));
    }
}