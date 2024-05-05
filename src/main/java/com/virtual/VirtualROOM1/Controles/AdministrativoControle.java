package com.virtual.VirtualROOM1.Controles;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtual.VirtualROOM1.Dtos.AdministrativoDtos;
import com.virtual.VirtualROOM1.Modelo.AdministrativoModelo;
import com.virtual.VirtualROOM1.Repositorios.AdministrativoRepositorio;

import jakarta.validation.Valid;

@RestController
public class AdministrativoControle {

    @Autowired
    AdministrativoRepositorio administrativoRepositorio;

    @PostMapping("/Administrativo")
    public ResponseEntity<AdministrativoModelo> saveAdministrativo(@RequestBody @Valid AdministrativoDtos administrativoDtos){
        var administrativoModelo = new AdministrativoModelo();
        BeanUtils.copyProperties(administrativoDtos, administrativoModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrativoRepositorio.save(administrativoModelo));
    }

    @GetMapping("/Administrativo")
    public ResponseEntity<List<AdministrativoModelo>> consultarAdministrativo(){
    
    
        return ResponseEntity.status(HttpStatus.OK).body(administrativoRepositorio.findAll());
    }

    
    @PutMapping("/Administrativo/{id}")
    public ResponseEntity<AdministrativoModelo> updateAdministrativo(@PathVariable UUID id, @RequestBody @Valid AdministrativoDtos administrativoDtos) {
        Optional<AdministrativoModelo> optionalAdministrativo = administrativoRepositorio.findById(id);
        if (optionalAdministrativo.isPresent()) {
            AdministrativoModelo existingAdministrativo = optionalAdministrativo.get();
            BeanUtils.copyProperties(administrativoDtos, existingAdministrativo);
            AdministrativoModelo updatedAdministrativo = administrativoRepositorio.save(existingAdministrativo);
            return ResponseEntity.ok(updatedAdministrativo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Administrativo/{id}")
    public ResponseEntity<AdministrativoModelo> getAdministrativoById(@PathVariable UUID id) {
        Optional<AdministrativoModelo> optionalAdministrativo = administrativoRepositorio.findById(id);
        return optionalAdministrativo.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Administrativo/{id}")
    public ResponseEntity<Void> deleteAdministrativo(@PathVariable UUID id) {
        Optional<AdministrativoModelo> optionalAdministrativo = administrativoRepositorio.findById(id);
        if (optionalAdministrativo.isPresent()) {
            administrativoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
   
    }
    
    

