package com.silvia.beautycare.config;

import com.silvia.beautycare.model.Producto;
import com.silvia.beautycare.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Carga unos productos de ejemplo la primera vez (solo si la tabla esta vacia),
 * para que la lista no aparezca en blanco. Son datos de muestra.
 *
 * @Slf4j (Lombok) crea la variable "log" para escribir mensajes en la consola.
 */
@Slf4j
@Configuration
public class DatosIniciales {

    @Bean
    ApplicationRunner cargarProductosDeEjemplo(ProductoRepository repo) {
        return args -> {
            if (repo.contar() > 0) {
                return;
            }
            repo.crear(ejemplo("Shampoo sólido de romero", "Cabello", 20,
                    "Barra de shampoo con aceite de romero para cabello normal."));
            repo.crear(ejemplo("Acondicionador sólido de coco", "Cabello",  15,
                    "Hidrata y suaviza sin enjuague pesado."));
            repo.crear(ejemplo("Jabón de avena y miel", "Jabones", 30,
                    "Jabón artesanal suave para piel sensible."));
            repo.crear(ejemplo("Mascarilla de arcilla verde", "Facial",  12,
                    "Limpieza profunda para piel mixta o grasa."));
            repo.crear(ejemplo("Crema Piel de Pétalo", "Corporal", 8,
                    "Crema corporal de absorción rápida."));
            repo.crear(ejemplo("Bálsamo labial de karité", "Labios",  3,
                    "Protege y repara labios secos."));
            repo.crear(ejemplo("Jabonera de bambú", "Accesorios", 10,
                    "Mantiene el jabón seco y prolonga su vida."));
            log.info("Se cargaron {} productos de ejemplo", repo.contar());
        };
    }

    private static Producto ejemplo(String nombre, String categoria,  int stock, String descripcion) {
        return Producto.builder()
                .nombre(nombre)
                .categoria(categoria)
                .stock(stock)
                .descripcion(descripcion)
                .build();
    }
}
