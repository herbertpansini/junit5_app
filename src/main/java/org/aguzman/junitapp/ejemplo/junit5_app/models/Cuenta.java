package org.aguzman.junitapp.ejemplo.junit5_app.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Cuenta {
    private String persona;
    private BigDecimal saldo;
}