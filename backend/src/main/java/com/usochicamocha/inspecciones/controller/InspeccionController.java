package com.usochicamocha.inspecciones.controller;

import com.usochicamocha.inspecciones.model.Inspeccion;
import com.usochicamocha.inspecciones.service.InspeccionService;
import jakarta.validation.Valid;
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
    
    // GET - Obtener inspeccion por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerPorId(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        return inspeccionService.obtenerPorId(id)
            .map(inspeccion -> {
                response.put("success", true);
                response.put("data", inspeccion);
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> {
                response.put("success", false);
                response.put("message", "Inspeccion no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            });
    }
    
    // POST - Crear nueva inspeccion
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@Valid @RequestBody Inspeccion inspeccion) {
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
    
    // PUT - Actualizar inspeccion
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @Valid @RequestBody Inspeccion inspeccion) {
        Map<String, Object> response = new HashMap<>();
        try {
            Inspeccion inspeccionActualizada = inspeccionService.actualizar(id, inspeccion);
            response.put("success", true);
            response.put("message", "Inspeccion actualizada correctamente");
            response.put("data", inspeccionActualizada);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    // DELETE - Eliminar inspeccion
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            inspeccionService.eliminar(id);
            response.put("success", true);
            response.put("message", "Inspeccion eliminada correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // GET - Buscar por operador
    @GetMapping("/buscar/operador")
    public ResponseEntity<Map<String, Object>> buscarPorOperador(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        List<Inspeccion> inspecciones = inspeccionService.buscarPorOperador(nombre);
        response.put("success", true);
        response.put("data", inspecciones);
        return ResponseEntity.ok(response);
    }
    
    // GET - Buscar por maquina
    @GetMapping("/buscar/maquina")
    public ResponseEntity<Map<String, Object>> buscarPorMaquina(@RequestParam String numero) {
        Map<String, Object> response = new HashMap<>();
        List<Inspeccion> inspecciones = inspeccionService.buscarPorMaquina(numero);
        response.put("success", true);
        response.put("data", inspecciones);
        return ResponseEntity.ok(response);
    }
}
