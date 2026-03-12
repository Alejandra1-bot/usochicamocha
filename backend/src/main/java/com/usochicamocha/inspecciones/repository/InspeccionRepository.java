package com.usochicamocha.inspecciones.repository;

import com.usochicamocha.inspecciones.model.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    
    // Buscar por nombre del operador
    List<Inspeccion> findByNombreOperadorContainingIgnoreCase(String nombreOperador);
    
    // Buscar por numero de maquina
    List<Inspeccion> findByNumeroMaquinaContainingIgnoreCase(String numeroMaquina);
    
    // Ordenar por fecha descendente
    List<Inspeccion> findAllByOrderByFechaInspeccionDesc();
    
    // Obtener el maximo ID
    @Query("SELECT COALESCE(MAX(i.id), 0) FROM Inspeccion i")
    Long findMaxId();
}
