package com.minsait.testingMicroservices.repositories;

import com.minsait.testingMicroservices.TestingMicroservicesApplication;
import com.minsait.testingMicroservices.models.Banco;
import com.minsait.testingMicroservices.models.Cuenta;
import com.minsait.testingMicroservices.services.CuentaService;
import com.minsait.testingMicroservices.services.CuentaServiceImpl;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class CuentaServicesTest {

    @Mock
    CuentaRepository cuentaRepository;

    @Mock
    BancoRepository bancoRepository;



    @InjectMocks
    CuentaServiceImpl cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        //cuenta=Cuenta.builder().
    }

    @Test
    void testFindAll() {
        List<Cuenta> cuentas = new ArrayList<>();
        when(cuentaService.findAll()).thenReturn(List.of(Datos.crearCuenta1()
                .get(), Datos.crearCuenta2().get()));

        cuentas = cuentaService.findAll();
        assertTrue(!cuentas.isEmpty());
        verify(cuentaRepository, atLeastOnce()).findAll();
    }


    @Test
    void testSave() {
        Cuenta cuenta = new
                Cuenta(11L, "VICTOR", new BigDecimal(100));
        //   given(cuentaRepository.findById(cuenta.getId()))
        //       .willReturn(Optional.empty());
        given(cuentaRepository.save(cuenta)).willReturn(cuenta);
        Cuenta nuevaCuenta = cuentaService.save(cuenta);
        assertThat(nuevaCuenta).isNotNull();

    }


    @Test
    void testRevisarTotalTransferencia() {
        given(bancoRepository.findById(1L))
                .willReturn(Optional.of(Datos.crearBanco().get()));
        int total = cuentaService.revisarTotalTransferencias(1L);
        assertTrue(total >= 0);
    }

    @Test
    void testRevisarSaldo() {
        given(cuentaRepository.findById(1L)).willReturn(Optional.of(Datos.crearCuenta1().get()));
        BigDecimal saldo = cuentaService.revisarSaldo(1L);
        assertTrue(Datos.crearCuenta1().get().getSaldo().equals(saldo));
    }

    @Test
    void testFindById() {
        given(cuentaRepository.findById(1L)).willReturn(Optional.of(Datos.crearCuenta1().get()));
        Cuenta cuenta = cuentaService.findById(Datos.crearCuenta1().get().getId());
        assertThat(cuenta).isNotNull();
    }

    @Test
    void testDeleteById() {
        given(cuentaRepository.findById(1L)).willReturn(Optional.of(Datos.crearCuenta1().get()));
        boolean flag = cuentaService.deletedById(1L);
        assertTrue(flag);
    }

    @Test
    void testTransferir() {

        given(cuentaRepository.findById(1L)).willReturn(Optional.of(Datos.crearCuenta1().get()));
        given(cuentaRepository.findById(2L)).willReturn(Optional.of(Datos.crearCuenta2().get()));
        given(bancoRepository.findById(1L)).willReturn(Optional.of(Datos.crearBanco().get()));
        cuentaService.transferir(1L, 2L, new BigDecimal(1), 1L);
        int total=bancoRepository.findById(1L).get().getTotalTransferencias();
        assertTrue(total==1);
    }


    @Test
    void getBanco() {
        given(bancoRepository.findById(1L)).willReturn(Optional.of(Datos.crearBanco().get()));
        Optional<Banco> banco=bancoRepository.findById(1L);
        assertTrue(banco.get().getId()!=null);
        assertTrue(banco.get().getNombre()!=null);
        assertTrue(banco.get().getTotalTransferencias()!=null);

    }

    @Test
    public
    void main() {
        TestingMicroservicesApplication.main(new String[]{});
    }
}
