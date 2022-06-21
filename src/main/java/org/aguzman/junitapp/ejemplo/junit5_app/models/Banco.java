package org.aguzman.junitapp.ejemplo.junit5_app.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Banco {
    private String nombre;
    private Set<Cuenta> cuentas;

    public Banco() {
        this.cuentas = new HashSet<>();
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setBanco(this);
    }

    public void transferir(Cuenta origen, Cuenta destino, BigDecimal monto) {
        origen.debito(monto);
        destino.credito(monto);
    }
}