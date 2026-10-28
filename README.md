# Sistema de préstamos de biblioteca

Proyecto Maven con Spring Boot, Java 21, Spring Web, Spring Data JPA, Validation, Lombok, MySQL, SpringDoc OpenAPI y DevTools.

## Antes de ejecutar

1. Instala/ejecuta MySQL.
2. Crea la base de datos una sola vez:
   ```sql
   CREATE DATABASE bd_prestamos;
   ```
3. Edita `src/main/resources/application.properties` y cambia `spring.datasource.username` y `spring.datasource.password`.
4. Abre el proyecto en IntelliJ IDEA como proyecto Maven y espera a que descargue dependencias.
5. Ejecuta `PrestamosApplication.java`.

Hibernate crea las tablas automáticamente cuando la conexión funciona.

## Endpoints principales

### Lectores
- `POST /lector` — crear lector.
- `GET /lector` — listar lectores.
- `GET /lector/{id}` — consultar lector.

Ejemplo de creación:
```json
{
  "nombre": "Ana López",
  "activo": true,
  "membresiaPremium": false
}
```

### Libros
- `POST /libro` — crear libro.
- `GET /libro` — listar libros.
- `GET /libro/{id}` — consultar libro.

Ejemplo:
```json
{
  "titulo": "El principito",
  "ejemplaresDisponibles": 4
}
```

### Préstamos
- `POST /prestamo/solicitar` — solicitar préstamo.
- `PUT /prestamo/devolver` — devolver libro.
- `PUT /prestamo/renovar` — renovar préstamo.
- `PUT /prestamo/rehabilitarLector` — rehabilitar lector.
- `GET /prestamo/activos/lector/{lectorId}` — préstamos PRESTADO o VENCIDO de un lector.
- `GET /prestamo` — listar todos los préstamos.

Ejemplo de solicitud:
```json
{
  "lectorId": 1,
  "libroId": 1
}
```

Para devolver o renovar:
```json
{
  "prestamoId": 1
}
```

Para rehabilitar:
```json
{
  "lectorId": 1
}
```

## Swagger
Al iniciar la aplicación, visita `http://localhost:8080/swagger-ui.html`.

## Reglas incluidas
- Lector debe existir y estar activo.
- Libro debe existir y tener ejemplares disponibles.
- Máximo 3 préstamos activos para lectores normales y 5 para premium. Se consideran activos los estados PRESTADO y VENCIDO.
- Cada préstamo inicia con fecha actual y fecha máxima a 14 días.
- La devolución suma un ejemplar; si se entrega después de la fecha límite, se bloquea al lector.
- No se renuevan préstamos VENCIDO o DEVUELTO; se agregan 7 días a la fecha límite de un préstamo PRESTADO.
- Las validaciones de DTO y errores de negocio se manejan con excepciones y `@RestControllerAdvice`.

## GitHub
No incluye un repositorio remoto. Para entregarlo, crea un repositorio en GitHub, abre esta carpeta como proyecto, y ejecuta `git init`, `git add .`, `git commit` y `git push` siguiendo las instrucciones de GitHub.
