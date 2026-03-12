package com.usochicamocha.inspecciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "inspecciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inspeccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del operador es obligatorio")
    @Column(name = "nombre_operador", nullable = false)
    private String nombreOperador;
    
    @NotBlank(message = "El numero de maquina es obligatorio")
    @Column(name = "numero_maquina", nullable = false)
    private String numeroMaquina;
    
    @NotNull(message = "La fecha de inspeccion es obligatoria")
    @Column(name = "fecha_inspeccion", nullable = false)
    private LocalDate fechaInspeccion;
    
    @NotNull(message = "La hora de inspeccion es obligatoria")
    @Column(name = "hora_inspeccion", nullable = false)
    private LocalTime horaInspeccion;
    
    @NotNull(message = "El horometro es obligatorio")
    @Min(value = 0, message = "El horometro debe ser un numero positivo")
    @Column(name = "horometro", nullable = false)
    private Double horometro;
    
    @NotBlank(message = "El estado es obligatorio")
    @Column(name = "estado_frenos", nullable = false)
    private String estadoFreno;
    
   
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}
