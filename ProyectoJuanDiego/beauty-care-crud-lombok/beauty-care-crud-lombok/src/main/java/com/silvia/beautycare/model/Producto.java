package com.silvia.beautycare.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Un producto del catalogo Beauty Care.
 *
 * Con Lombok ya no escribimos a mano los getters, setters, equals, hashCode ni toString:
 *   @Data              -> getters + setters + equals/hashCode + toString
 *   @NoArgsConstructor -> new Producto() (lo necesita Spring para llenar el formulario)
 *   @AllArgsConstructor-> constructor con todos los campos (lo usa @Builder)
 *   @Builder           -> Producto.builder().nombre("...").precio(1000).build()
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "Maximo 80 caracteres")
    private String nombre;

    @NotBlank(message = "Elige una categoria")
    private String categoria;

    

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Size(max = 255, message = "Maximo 255 caracteres")
    private String descripcion;
}
