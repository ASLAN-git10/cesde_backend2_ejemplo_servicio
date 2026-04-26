# Solución de la Actividad – Entidad Proveedor

## 1. Descripción General

En esta actividad se implementó la entidad **Proveedor** siguiendo la arquitectura por capas del proyecto `demo_service`. El patrón aplicado es el mismo que el de la entidad `Usuario` ya existente:

```
Controller → Service → Repository → Entity (Base de Datos)
```

---

## 2. Archivos Creados

| Capa | Archivo | Ruta |
|---|---|---|
| Entidad | `Proveedor.java` | `src/main/java/com/example/demo_service/model/entity/` |
| Repositorio | `ProveedorRepository.java` | `src/main/java/com/example/demo_service/repository/` |
| Servicio | `ProveedorService.java` | `src/main/java/com/example/demo_service/service/` |
| Controlador | `ProveedorController.java` | `src/main/java/com/example/demo_service/controller/` |

---

## 3. Descripción de Cada Capa

### 3.1 Entidad – `Proveedor.java`

La entidad `Proveedor` representa la tabla `proveedores` en la base de datos. Contiene los siguientes campos:

| Campo | Tipo | Restricción |
|---|---|---|
| `id` | `Long` | Clave primaria, auto-generada |
| `nombre` | `String` | No nulo |
| `nit` | `String` | No nulo, único |
| `telefono` | `String` | No nulo |
| `ciudad` | `String` | No nulo |

Se usaron anotaciones de **JPA** para la persistencia y **Lombok** (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`) para eliminar código repetitivo.

---

### 3.2 Repositorio – `ProveedorRepository.java`

Extiende `JpaRepository<Proveedor, Long>`, lo que provee automáticamente operaciones CRUD como `findAll()`, `findById()`, `save()`, `deleteById()`, etc.

Se agregaron dos métodos personalizados:

- **`findByNit(String nit)`** → *Query Method*: Spring Data JPA genera la consulta SQL automáticamente según el nombre del método.
- **`buscarPorCiudad(String ciudad)`** → *Consulta JPQL personalizada* con `@Query`, que filtra proveedores por su ciudad.

---

### 3.3 Servicio – `ProveedorService.java`

Contiene la **lógica de negocio** del sistema. Las reglas de negocio implementadas son:

1. **El NIT es obligatorio** → Si es nulo o está en blanco, se lanza un `RuntimeException`.
2. **El NIT debe ser único** → Se consulta el repositorio antes de guardar; si ya existe, se rechaza el registro.

Métodos expuestos:
- `listarTodos()` → Retorna todos los proveedores.
- `buscarPorId(Long id)` → Retorna un `Optional<Proveedor>`.
- `guardarProveedor(Proveedor proveedor)` → Valida y guarda el proveedor.
- `buscarPorCiudad(String ciudad)` → Usa el método personalizado del repositorio.
- `eliminar(Long id)` → Elimina un proveedor por su ID.

---

### 3.4 Controlador – `ProveedorController.java`

Expone los endpoints REST bajo la ruta base `/api/proveedores`:

| Método HTTP | Endpoint | Descripción |
|---|---|---|
| `GET` | `/api/proveedores` | Lista todos los proveedores |
| `GET` | `/api/proveedores/{id}` | Busca un proveedor por ID |
| `POST` | `/api/proveedores` | Crea un nuevo proveedor (con validaciones) |
| `GET` | `/api/proveedores/por-ciudad?ciudad=X` | Busca proveedores por ciudad |
| `DELETE` | `/api/proveedores/{id}` | Elimina un proveedor por ID |

El endpoint `POST` captura excepciones de lógica de negocio y retorna un `400 Bad Request` con el mensaje de error correspondiente.

---

## 4. Ejemplo de Uso

### Crear un Proveedor (POST)

**Request:**
```json
POST /api/proveedores
Content-Type: application/json

{
  "nombre": "Distribuidora Nacional S.A.S",
  "nit": "900123456-7",
  "telefono": "3001234567",
  "ciudad": "Medellín"
}
```

**Response exitoso (200 OK):**
```json
{
  "id": 1,
  "nombre": "Distribuidora Nacional S.A.S",
  "nit": "900123456-7",
  "telefono": "3001234567",
  "ciudad": "Medellín"
}
```

**Response si el NIT ya existe (400 Bad Request):**
```
Lógica de Negocio: El NIT ya está en uso por otro proveedor.
```

### Buscar por Ciudad (GET)

```
GET /api/proveedores/por-ciudad?ciudad=Medellín
```

---

## 5. Conceptos Clave Aplicados

- **Arquitectura por capas**: Separación clara de responsabilidades entre Controller, Service y Repository.
- **JPA + Hibernate**: Mapeo objeto-relacional automático con anotaciones como `@Entity`, `@Table`, `@Column`.
- **Lombok**: Reducción de código boilerplate con `@Getter`, `@Setter`, `@Builder`, `@RequiredArgsConstructor`.
- **Spring Data JPA**: Operaciones CRUD automáticas y métodos de consulta derivados del nombre.
- **Lógica de negocio en el Service**: Las validaciones de negocio se centralizan en la capa de servicio, no en el controlador.
- **Query Methods**: Spring genera SQL automáticamente desde el nombre del método (e.g., `findByNit`).
- **JPQL**: Lenguaje de consulta orientado a objetos para consultas personalizadas más complejas.
