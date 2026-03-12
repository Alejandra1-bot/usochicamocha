package com.usochicamocha.inspecciones.controller;

import com.usochicamocha.inspecciones.model.Inspeccion;
import com.usochicamocha.inspecciones.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inspecciones")
@CrossOrigin(origins = "http://localhost:5173")
public class InspeccionController {
    
    @Autowired
    private InspeccionService inspeccionService;
    
    // GET - Obtener todas las inspecciones
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerTodas() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Inspeccion> inspecciones = inspeccionService.obtenerTodas();
            response.put("success", true);
            response.put("data", inspecciones);
            response.put("total", inspecciones.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener inspecciones: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // POST - Crear nueva inspeccion
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Inspeccion inspeccion) {
        Map<String, Object> response = new HashMap<>();
        try {
            Inspeccion nuevaInspeccion = inspeccionService.crear(inspeccion);
            response.put("success", true);
            response.put("message", "Inspeccion registrada correctamente");
            response.put("data", nuevaInspeccion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear inspeccion: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
