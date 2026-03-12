package com.usochicamocha.inspecciones.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "inspecciones")
public class Inspeccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_maquina", nullable = false)
    private String numeroMaquina;
    
    @Column(name = "nombre_operador", nullable = false)
    private String nombreOperador;
    
    @Column(name = "horometro", nullable = false)
    private Double horometro;
    
    @Column(name = "estado", nullable = false)
    private String estado;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "fecha_inspeccion", nullable = false)
    private LocalDate fechaInspeccion;
    
    @Column(name = "hora_inspeccion", nullable = false)
    private LocalTime horaInspeccion;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroMaquina() { return numeroMaquina; }
    public void setNumeroMaquina(String numeroMaquina) { this.numeroMaquina = numeroMaquina; }
    
    public String getNombreOperador() { return nombreOperador; }
    public void setNombreOperador(String nombreOperador) { this.nombreOperador = nombreOperador; }
    
    public Double getHorometro() { return horometro; }
    public void setHorometro(Double horometro) { this.horometro = horometro; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public LocalDate getFechaInspeccion() { return fechaInspeccion; }
    public void setFechaInspeccion(LocalDate fechaInspeccion) { this.fechaInspeccion = fechaInspeccion; }
    
    public LocalTime getHoraInspeccion() { return horaInspeccion; }
    public void setHoraInspeccion(LocalTime horaInspeccion) { this.horaInspeccion = horaInspeccion; }
}
