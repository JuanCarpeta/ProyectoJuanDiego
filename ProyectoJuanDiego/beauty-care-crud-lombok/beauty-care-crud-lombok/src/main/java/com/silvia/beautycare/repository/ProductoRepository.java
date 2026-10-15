package com.silvia.beautycare.repository;

import com.silvia.beautycare.model.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Las 4 operaciones del CRUD contra SQLite, escritas con SQL puro:
 * Create (INSERT), Read (SELECT), Update (UPDATE) y Delete (DELETE).
 *
 * @RequiredArgsConstructor (Lombok) genera el constructor que recibe el JdbcTemplate,
 * asi Spring lo inyecta sin que escribamos el constructor.
 */
@Repository
@RequiredArgsConstructor
public class ProductoRepository {

    private static final String COLUMNAS = "id, nombre, categoria, stock, descripcion";

    // Convierte cada fila del resultado SQL en un objeto Producto (usando el builder de Lombok)
    private static final RowMapper<Producto> MAPPER = (rs, fila) -> Producto.builder()
            .id(rs.getLong("id"))
            .nombre(rs.getString("nombre"))
            .categoria(rs.getString("categoria"))
            .stock(rs.getInt("stock"))
            .descripcion(rs.getString("descripcion"))
            .build();

    private final JdbcTemplate jdbc;

    // READ: todos los productos
    public List<Producto> listar() {
        return jdbc.query("SELECT " + COLUMNAS + " FROM producto ORDER BY nombre", MAPPER);
    }

    // READ: un producto por id
    public Optional<Producto> buscarPorId(long id) {
        List<Producto> lista = jdbc.query(
                "SELECT " + COLUMNAS + " FROM producto WHERE id = ?", MAPPER, id);
        return lista.stream().findFirst();
    }

    // CREATE
    public void crear(Producto p) {
        jdbc.update(
                "INSERT INTO producto (nombre, categoria,stock, descripcion) VALUES (?, ?, ?, ?)",
                p.getNombre(), p.getCategoria(), p.getStock(), p.getDescripcion());
    }

    // UPDATE: devuelve cuantas filas cambio (0 si el id ya no existe)
    public int actualizar(Producto p) {
        return jdbc.update(
                "UPDATE producto SET nombre = ?, categoria = ?, , stock = ?, descripcion = ? WHERE id = ?",
                p.getNombre(), p.getCategoria(), p.getStock(), p.getDescripcion(), p.getId());
    }

    // DELETE: devuelve cuantas filas borro
    public int eliminar(long id) {
        return jdbc.update("DELETE FROM producto WHERE id = ?", id);
    }

    public long contar() {
        Long total = jdbc.queryForObject("SELECT COUNT(*) FROM producto", Long.class);
        return total == null ? 0 : total;
    }
}
