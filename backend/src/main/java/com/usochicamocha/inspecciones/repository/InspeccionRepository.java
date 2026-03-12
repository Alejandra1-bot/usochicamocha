package com.usochicamocha.inspecciones.repository;

import com.usochicamocha.inspecciones.model.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    // JpaRepository ya tiene los métodos básicos:
    // - findAll() para obtener todas las inspecciones
    // - save() para guardar una inspección
}

