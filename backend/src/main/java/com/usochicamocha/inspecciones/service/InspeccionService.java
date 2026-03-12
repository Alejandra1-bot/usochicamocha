package com.usochicamocha.inspecciones.service;

import com.usochicamocha.inspecciones.model.Inspeccion;
import com.usochicamocha.inspecciones.repository.InspeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InspeccionService {
    
    @Autowired
    private InspeccionRepository inspeccionRepository;
    
    // Obtener todas las inspecciones
    public List<Inspeccion> obtenerTodas() {
        return inspeccionRepository.findAllByOrderByFechaInspeccionDesc();
    }
    
    // Obtener una inspeccion por ID
    public Optional<Inspeccion> obtenerPorId(Long id) {
        return inspeccionRepository.findById(id);
    }
    
    // Crear nueva inspeccion
    public Inspeccion crear(Inspeccion inspeccion) {
        return inspeccionRepository.save(inspeccion);
    }
    
    // Actualizar inspeccion existente
    public Inspeccion actualizar(Long id, Inspeccion inspeccionActualizada) {
        return inspeccionRepository.findById(id)
            .map(inspeccion -> {
                inspeccion.setNombreOperador(inspeccionActualizada.getNombreOperador());
                inspeccion.setNumeroMaquina(inspeccionActualizada.getNumeroMaquina());
                inspeccion.setFechaInspeccion(inspeccionActualizada.getFechaInspeccion());
                inspeccion.setHoraInspeccion(inspeccionActualizada.getHoraInspeccion());
                inspeccion.setHorometro(inspeccionActualizada.getHorometro());
                inspeccion.setEstadoFreno(inspeccionActualizada.getEstadoFreno());
                inspeccion.setEstadoLuces(inspeccionActualizada.getEstadoLuces());
                inspeccion.setNivelAceite(inspeccionActualizada.getNivelAceite());
                inspeccion.setObservaciones(inspeccionActualizada.getObservaciones());
                return inspeccionRepository.save(inspeccion);
            })
            .orElseThrow(() -> new RuntimeException("Inspeccion no encontrada con ID: " + id));
    }
    
    // Eliminar inspeccion
    public void eliminar(Long id) {
        inspeccionRepository.deleteById(id);
    }
    
    // Buscar por operador
    public List<Inspeccion> buscarPorOperador(String nombre) {
        return inspeccionRepository.findByNombreOperadorContainingIgnoreCase(nombre);
    }
    
    // Buscar por maquina
    public List<Inspeccion> buscarPorMaquina(String numero) {
        return inspeccionRepository.findByNumeroMaquinaContainingIgnoreCase(numero);
    }
}
