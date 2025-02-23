package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.Vuelo;
import org.example.springbootmvc.repositories.VuelosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
