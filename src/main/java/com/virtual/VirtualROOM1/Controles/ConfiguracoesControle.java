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

import com.virtual.VirtualROOM1.Dtos.ConfiguracoesDtos;
import com.virtual.VirtualROOM1.Modelo.ConfiguracoesModelo;
import com.virtual.VirtualROOM1.Repositorios.ConfiguracoesRepositorio;

import jakarta.validation.Valid;

@RestController
public class ConfiguracoesControle {

    @Autowired
    ConfiguracoesRepositorio configuracoesRepositorio;

    @PostMapping("/Configuracoes")
    public ResponseEntity<ConfiguracoesModelo> saveConfiguracoes(@RequestBody @Valid ConfiguracoesDtos configuracoesDtos){
        var configuracoesModelo = new ConfiguracoesModelo();
        BeanUtils.copyProperties(configuracoesDtos, configuracoesModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(configuracoesRepositorio.save(configuracoesModelo));
    }

    @GetMapping("/Configuracoes")
    public ResponseEntity<List<ConfiguracoesModelo>> consultarConfiguracoes(){


    return ResponseEntity.status(HttpStatus.OK).body(configuracoesRepositorio.findAll());
}
  
@PutMapping("/Configuracoes/{id}")
    public ResponseEntity<ConfiguracoesModelo> updateConfiguracoes(@PathVariable UUID id, @RequestBody @Valid ConfiguracoesDtos configuracoesDtos) {
        Optional<ConfiguracoesModelo> optionalConfiguracoes = configuracoesRepositorio.findById(id);
        if (optionalConfiguracoes.isPresent()) {
            ConfiguracoesModelo existingConfiguracoes = optionalConfiguracoes.get();
            BeanUtils.copyProperties(configuracoesDtos, existingConfiguracoes);
            ConfiguracoesModelo updatedConfiguracoes = configuracoesRepositorio.save(existingConfiguracoes);
            return ResponseEntity.ok(updatedConfiguracoes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Configuracoes/{id}")
    public ResponseEntity<ConfiguracoesModelo> getConfiguracoesById(@PathVariable UUID id) {
        Optional<ConfiguracoesModelo> optionalConfiguracoes = configuracoesRepositorio.findById(id);
        return optionalConfiguracoes.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Configuracoes/{id}")
    public ResponseEntity<Void> deleteConfiguracoes(@PathVariable UUID id) {
        Optional<ConfiguracoesModelo> optionalConfiguracoes = configuracoesRepositorio.findById(id);
        if (optionalConfiguracoes.isPresent()) {
            configuracoesRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
