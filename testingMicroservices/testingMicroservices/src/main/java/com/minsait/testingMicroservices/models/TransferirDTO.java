package com.minsait.testingMicroservices.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferirDTO {
    private Long idCuentaOrigen;
    private Long idCuentaDestino;
    private BigDecimal monto;
    private Long idBanco;
}
