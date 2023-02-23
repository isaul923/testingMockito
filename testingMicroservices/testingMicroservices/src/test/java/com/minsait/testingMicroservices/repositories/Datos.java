package com.minsait.testingMicroservices.repositories;

import com.minsait.testingMicroservices.models.Banco;
import com.minsait.testingMicroservices.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {
    public static Optional<Cuenta> crearCuenta1(){
        return Optional.of(new Cuenta(1L, "Ricardo", new BigDecimal(1000)));
    }

    public static Optional<Cuenta> crearCuenta2(){
        return Optional.of(new Cuenta(2L, "Yamani", new BigDecimal(10000)));
    }

    public static Optional<Banco> crearBanco(){
        return Optional.of(new Banco(1L, "BBVA", 0));
    }
}
