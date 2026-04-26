package com.example.demo_service.service;

import com.example.demo_service.model.entity.Proveedor;
import com.example.demo_service.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio que contiene la lógica de negocio para la entidad Proveedor.
 * El controlador llama al servicio, y el servicio interactúa con el repositorio.
 */
@Service
@RequiredArgsConstructor // Genera el constructor para la inyección de dependencias (ProveedorRepository)
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> buscarPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    /**
     * Ejemplo de Lógica de Negocio:
     * Antes de guardar, validamos condiciones específicas del proveedor.
     */
    public Proveedor guardarProveedor(Proveedor proveedor) {
        // Regla 1: El NIT no puede estar vacío ni en blanco
        if (proveedor.getNit() == null || proveedor.getNit().isBlank()) {
            throw new RuntimeException("Lógica de Negocio: El NIT del proveedor es obligatorio.");
        }

        // Regla 2: El NIT no debe estar registrado previamente
        if (proveedorRepository.findByNit(proveedor.getNit()).isPresent()) {
            throw new RuntimeException("Lógica de Negocio: El NIT ya está en uso por otro proveedor.");
        }

        return proveedorRepository.save(proveedor);
    }

    /**
     * Uso de método personalizado del repositorio para buscar por ciudad.
     */
    public List<Proveedor> buscarPorCiudad(String ciudad) {
        return proveedorRepository.buscarPorCiudad(ciudad);
    }

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
