package com.minsait.testingMicroservices.controllers;

import com.minsait.testingMicroservices.exceptions.DineroInsuficienteException;
import com.minsait.testingMicroservices.models.Cuenta;
import com.minsait.testingMicroservices.models.TransferirDTO;
import com.minsait.testingMicroservices.services.CuentaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/cuentas")
@Slf4j
public class CuentaController {
    @Autowired
    private CuentaService service;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Cuenta> findAll(){
        log.info("Running list method");
        return service.findAll();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Long id){
        try {
            Cuenta cuenta = service.findById(id);
            return ResponseEntity.ok(cuenta);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * guardar
     * borrar
     * actualizar
     */

    @PostMapping("/guardar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cuenta save(@RequestBody Cuenta cuenta){
        return service.save(cuenta);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deletedById(@PathVariable Long id){
        if (service.deletedById(id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cuenta> actualizar(@PathVariable Long id, @RequestBody Cuenta cuenta){
        try {
            Cuenta cuentaActualizada = service.findById(id);
            cuentaActualizada.setSaldo(cuenta.getSaldo());
            cuentaActualizada.setPersona(cuenta.getPersona());
            return new ResponseEntity<>(service.save(cuentaActualizada), HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Transferir
    @PostMapping
    public ResponseEntity<?> transferir(@RequestBody TransferirDTO dto){
        Map<String, Object> response = new HashMap<>();
        response.put("fecha", LocalDate.now().toString());
        response.put("peticion", dto);
        try {
            service.transferir(dto.getIdCuentaOrigen(), dto.getIdCuentaDestino(), dto.getMonto(), dto.getIdBanco());
            response.put("status", "OK");
            response.put("mensaje", "Transferencia realizada con éxito");
        }catch (DineroInsuficienteException exception){
            response.put("status", "OK");//200
            response.put("mensaje", exception.getMessage());
        }catch (NoSuchElementException exception){
            response.put("status", "Not found");//404
            response.put("mensaje", "Not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(response);
    }
}
