# Beauty Care CRUD (con Lombok)

Misma aplicación que `beauty-care-crud`, pero usando **Lombok** para quitar el código repetitivo.
Stack: **Spring Boot + Thymeleaf + Bootstrap 5 + SQLite + Lombok**.

## Qué cambia frente a la versión sin Lombok

| Clase | Sin Lombok | Con Lombok |
|-------|-----------|------------|
| `Producto` | getters, setters y 2 constructores escritos a mano | `@Data @NoArgsConstructor @AllArgsConstructor @Builder` |
| `ProductoRepository` | constructor manual, RowMapper con setters | `@RequiredArgsConstructor` + `Producto.builder()` |
| `ProductoController` | constructor manual | `@RequiredArgsConstructor` + `@Slf4j` (variable `log`) |
| `DatosIniciales` | `new Producto(...)` con 5 argumentos | `@Slf4j` + `Producto.builder()` |

El resto (plantillas, SQL, rutas) es idéntico.

## Cómo ejecutarlo

Requisitos: **JDK 17 o 21** y Maven. Lombok **no funciona con Java 26** (error `EndPosTable`), así que fuerza el JDK 17:

```bash
# desde esta carpeta
JAVA_HOME=$(/usr/libexec/java_home -v 17) mvn spring-boot:run
```

Abre http://localhost:8080. La primera vez se crea `beautycare-crud.db` con 7 productos de ejemplo.

Si estás en IntelliJ: instala/activa el plugin de Lombok y marca *Enable annotation processing* (Settings > Build > Compiler > Annotation Processors). Si no, el editor mostrará errores en rojo aunque Maven compile bien.

Jar:

```bash
JAVA_HOME=$(/usr/libexec/java_home -v 17) mvn clean package
java -jar target/beauty-care-crud-lombok.jar
```

Si la otra versión (o la API) ya usa el puerto 8080, cambia `server.port` en `application.properties`.

## Probar con curl

La app responde HTML, no JSON, así que los POST se envían como formulario. `--data-urlencode` se encarga de espacios y tildes.

```bash
# 1) Listar (muestra solo los nombres)
curl -s http://localhost:8080/productos | grep -o 'fw-semibold">[^<]*'

# 2) Ver el formulario vacío / el de edición del producto 1
curl -s http://localhost:8080/productos/nuevo
curl -s http://localhost:8080/productos/1/editar

# 3) Crear (responde 302 y redirige a /productos)
curl -i -X POST http://localhost:8080/productos/guardar \
  --data-urlencode "nombre=Sérum de vitamina C" \
  --data-urlencode "categoria=Facial" \
  --data-urlencode "precio=25000" \
  --data-urlencode "stock=10" \
  --data-urlencode "descripcion=Producto de prueba"

# 4) Actualizar el producto 1 (lleva id)
curl -i -X POST http://localhost:8080/productos/guardar \
  --data-urlencode "id=1" \
  --data-urlencode "nombre=Shampoo sólido de romero" \
  --data-urlencode "categoria=Cabello" \
  --data-urlencode "precio=27000" \
  --data-urlencode "stock=18"

# 5) Validación: nombre vacío y precio negativo (responde 200 con el formulario y los errores)
curl -s -X POST http://localhost:8080/productos/guardar \
  --data-urlencode "nombre=" \
  --data-urlencode "categoria=Facial" \
  --data-urlencode "precio=-5" \
  --data-urlencode "stock=1" | grep -o 'invalid-feedback">[^<]*'

# 6) Eliminar el producto 8 (responde 302)
curl -i -X POST http://localhost:8080/productos/8/eliminar

# 7) Ver la base de datos directamente
sqlite3 beautycare-crud.db "SELECT id, nombre, precio, stock FROM producto;"
```

## Estructura

```
src/main/java/com/silvia/beautycare/
  BeautyCareApplication.java
  model/Producto.java                 datos + validaciones + Lombok
  repository/ProductoRepository.java  SQL (INSERT / SELECT / UPDATE / DELETE)
  controller/ProductoController.java  rutas web
  config/DatosIniciales.java          productos de ejemplo
src/main/resources/
  application.properties, schema.sql
  templates/productos/lista.html, form.html, fragments/layout.html
  static/css/estilos.css
```

## Notas

- Bootstrap se carga desde un CDN: el navegador necesita internet.
- Sin inicio de sesión: no lo publiques en internet tal cual.
- Los productos de ejemplo son datos de muestra, no el catálogo real.
