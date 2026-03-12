package com.usochicamocha.inspecciones.service;

import com.usochicamocha.inspecciones.model.Inspeccion;
import com.usochicamocha.inspecciones.repository.InspeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspeccionService {
    
    @Autowired
    private InspeccionRepository inspeccionRepository;
    
    // Obtener todas las inspecciones
    public List<Inspeccion> obtenerTodas() {
        return inspeccionRepository.findAll();
    }
    
    // Crear nueva inspeccion
    public Inspeccion crear(Inspeccion inspeccion) {
        return inspeccionRepository.save(inspeccion);
    }
}

