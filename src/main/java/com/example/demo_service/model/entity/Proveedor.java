package com.example.demo_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un Proveedor en el sistema.
 * Se utilizan anotaciones de JPA para la persistencia y Lombok para reducir el código repetitivo (Boilerplate).
 */
@Entity
@Table(name = "proveedores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String ciudad;

    // Los estudiantes deben notar que gracias a Lombok (@Getter @Setter),
    // no necesitamos escribir explícitamente los métodos getNombre(), setNombre(), etc.
}
