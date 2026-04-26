package com.example.demo_service.repository;

import com.example.demo_service.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Proveedor.
 * Extiende JpaRepository para obtener operaciones CRUD básicas automáticamente.
 */
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    /**
     * Ejemplo de un "Query Method": Spring Data JPA genera la consulta automáticamente
     * basándose en el nombre del método.
     */
    Optional<Proveedor> findByNit(String nit);

    /**
     * Ejemplo de consulta personalizada utilizando JPQL (Java Persistence Query Language).
     * Permite buscar todos los proveedores que pertenezcan a una ciudad específica.
     */
    @Query("SELECT p FROM Proveedor p WHERE p.ciudad = :ciudad")
    List<Proveedor> buscarPorCiudad(@Param("ciudad") String ciudad);
}
