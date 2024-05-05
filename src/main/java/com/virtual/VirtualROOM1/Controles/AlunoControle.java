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


import com.virtual.VirtualROOM1.Dtos.AlunoDtos;
import com.virtual.VirtualROOM1.Modelo.AlunoModelo;
import com.virtual.VirtualROOM1.Repositorios.AlunoRepositorio;

import jakarta.validation.Valid;

@RestController
public class AlunoControle {

    @Autowired
    AlunoRepositorio alunoRepositorio;

    @PostMapping("/Aluno")
    public ResponseEntity<AlunoModelo> saveAluno(@RequestBody @Valid AlunoDtos alunoDtos){
        var alunoModelo = new AlunoModelo();
        BeanUtils.copyProperties(alunoDtos, alunoModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoRepositorio.save(alunoModelo));
    }

    @GetMapping("/Aluno")
    public ResponseEntity<List<AlunoModelo>> consultarAluno(){


    return ResponseEntity.status(HttpStatus.OK).body(alunoRepositorio.findAll());
    }

    
    @PutMapping("/Aluno/{id}")
    public ResponseEntity<AlunoModelo> updateAluno(@PathVariable UUID id, @RequestBody @Valid AlunoDtos alunoDtos) {
        Optional<AlunoModelo> optionalAluno = alunoRepositorio.findById(id);
        if (optionalAluno.isPresent()) {
            AlunoModelo existingAluno = optionalAluno.get();
            BeanUtils.copyProperties(alunoDtos, existingAluno);
            AlunoModelo updatedAluno = alunoRepositorio.save(existingAluno);
            return ResponseEntity.ok(updatedAluno);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Aluno/{id}")
    public ResponseEntity<AlunoModelo> getAlunoById(@PathVariable UUID id) {
        Optional<AlunoModelo> optionalAluno = alunoRepositorio.findById(id);
        return optionalAluno.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Aluno/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable UUID id) {
        Optional<AlunoModelo> optionalAluno = alunoRepositorio.findById(id);
        if (optionalAluno.isPresent()) {
            alunoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    
}

