package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.Hotel;
import org.example.springbootmvc.models.Vuelo;
import org.example.springbootmvc.repositories.VuelosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/vuelo")
public class VueloViewController {

    @Autowired
    private VuelosRepository vueloRepository;

    @GetMapping("/lista")
    public String listarVuelos(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/vuelo/lista");
            return "redirect:/web/login";
        }

        model.addAttribute("vuelos", vueloRepository.findAll());
        return "vuelo/lista";
    }

    @GetMapping("/{id}")
    public String detalleVuelo(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/vuelo/" + id);
            return "redirect:/web/login";
        }

        Vuelo vuelo = vueloRepository.findById(id).get();
        model.addAttribute("vuelo", vuelo);
        return "vuelo/detalle";
    }

    @GetMapping("/{id}/editar")
    public String editarVuelo(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/vuelo/" + id + "/editar");
            return "redirect:/web/login";
        }

        Vuelo vuelo = vueloRepository.findById(id).get();
        model.addAttribute("vuelo", vuelo);
        return "vuelo/editar";
    }

    @PostMapping("/{id}/editar")
    public String guardarVuelo(HttpSession session, @PathVariable String id, @ModelAttribute Vuelo vuelo) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/vuelo/" + id + "/editar");
            return "redirect:/web/login";
        }

        Vuelo vueloActual = vueloRepository.findById(id).get();
        vueloActual.setCodigo(vuelo.getCodigo());
        vueloActual.setAerolinea(vuelo.getAerolinea());
        vueloActual.setOrigen(vuelo.getOrigen());
        vueloActual.setDestino(vuelo.getDestino());
        vueloActual.setFecha_salida(vuelo.getFecha_salida());
        vueloActual.setHora_salida(vuelo.getHora_salida());
        vueloActual.setDuracion(vuelo.getDuracion());

        vueloRepository.save(vueloActual);
        return "redirect:/web/vuelo/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarVuelo(HttpSession session, @PathVariable String id) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/vuelo/" + id + "/eliminar");
            return "redirect:/web/login";
        }

        if (!vueloRepository.existsById(id)) {
            return "redirect:/web/vuelo/lista";
        }

        vueloRepository.deleteById(id);
        return "redirect:/web/vuelo/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoVuelo(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/web/login";
        }

        // Crear un hotel vacío para el formulario
        Vuelo vuelo = new Vuelo();
        model.addAttribute("vuelo", vuelo);
        return "vuelo/nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarNuevoVuelo(HttpSession session, @ModelAttribute Vuelo vuelo) {
        if (session.getAttribute("user") == null) {
            return "redirect:/web/login";
        }

        vueloRepository.save(vuelo);
        return "redirect:/web/vuelo/lista";
    }
}
