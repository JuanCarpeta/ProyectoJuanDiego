package com.silvia.beautycare.controller;

import com.silvia.beautycare.model.Producto;
import com.silvia.beautycare.repository.ProductoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Recibe las peticiones del navegador y decide que pagina HTML mostrar.
 *
 *   GET  /productos               -> lista
 *   GET  /productos/nuevo         -> formulario vacio
 *   GET  /productos/{id}/editar   -> formulario con los datos
 *   POST /productos/guardar       -> crea o actualiza
 *   POST /productos/{id}/eliminar -> borra
 *
 * Lombok: @RequiredArgsConstructor crea el constructor con el repositorio
 * y @Slf4j crea el "log" para dejar rastro en la consola.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoRepository repo;

    // Disponible en todas las vistas de este controlador (lista del <select>)
    @ModelAttribute("categorias")
    public List<String> categorias() {
        return List.of("Cabello", "Jabones", "Facial", "Corporal", "Labios", "Accesorios");
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/productos";
    }

    // READ (lista)
    @GetMapping("/productos")
    public String listar(Model model) {
        model.addAttribute("productos", repo.listar());
        return "productos/lista";
    }

    // CREATE (mostrar formulario)
    @GetMapping("/productos/nuevo")
    public String nuevo(Model model) {
        Producto p = new Producto();
        p.setStock(0);
        model.addAttribute("producto", p);
        model.addAttribute("titulo", "Nuevo producto");
        return "productos/form";
    }

    // UPDATE (mostrar formulario con datos)
    @GetMapping("/productos/{id}/editar")
    public String editar(@PathVariable long id, Model model, RedirectAttributes flash) {
        return repo.buscarPorId(id)
                .map(p -> {
                    model.addAttribute("producto", p);
                    model.addAttribute("titulo", "Editar producto");
                    return "productos/form";
                })
                .orElseGet(() -> {
                    flash.addFlashAttribute("error", "El producto ya no existe.");
                    return "redirect:/productos";
                });
    }

    // CREATE / UPDATE (guardar)
    @PostMapping("/productos/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                          BindingResult errores, Model model, RedirectAttributes flash) {
        if (errores.hasErrors()) {
            model.addAttribute("titulo", producto.getId() == null ? "Nuevo producto" : "Editar producto");
            return "productos/form";
        }
        if (producto.getId() == null) {
            repo.crear(producto);
            log.info("Producto creado: {}", producto.getNombre());
            flash.addFlashAttribute("mensaje", "Producto creado.");
        } else if (repo.actualizar(producto) > 0) {
            log.info("Producto actualizado: id={}", producto.getId());
            flash.addFlashAttribute("mensaje", "Producto actualizado.");
        } else {
            flash.addFlashAttribute("error", "El producto ya no existe.");
        }
        return "redirect:/productos";
    }

    // DELETE
    @PostMapping("/productos/{id}/eliminar")
    public String eliminar(@PathVariable long id, RedirectAttributes flash) {
        if (repo.eliminar(id) > 0) {
            log.info("Producto eliminado: id={}", id);
            flash.addFlashAttribute("mensaje", "Producto eliminado.");
        } else {
            flash.addFlashAttribute("error", "El producto ya no existe.");
        }
        return "redirect:/productos";
    }
}
