package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.POI;
import org.example.springbootmvc.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/web/poi")
public class POIViewController {
    @Autowired
    private POIRepository poiRepository;

    @GetMapping("/lista")
    public String listarPOIs(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/poi/lista");
            return "redirect:/web/login";
        }

        model.addAttribute("pois", poiRepository.findAll());
        return "poi/lista";
    }

    @GetMapping("/{id}")
    public String detallePOI(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/poi/" + id);
            return "redirect:/web/login";
        }

        POI poi = poiRepository.findById(id).get();
        model.addAttribute("poi", poi);
        return "poi/detalle";
    }

    @GetMapping("/{id}/editar")
    public String editarPOI(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/poi/" + id + "/editar");
            return "redirect:/web/login";
        }

        POI poi = poiRepository.findById(id).get();
        model.addAttribute("poi", poi);
        return "poi/editar";
    }

    @PostMapping("/{id}/editar")
    public String guardarPOI(HttpSession session, @PathVariable String id, @ModelAttribute POI poi) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/poi/" + id + "/editar");
            return "redirect:/web/login";
        }

        POI poiActual = poiRepository.findById(id).get();
        poiActual.setNombre(poi.getNombre());
        poiActual.setCiudad(poi.getCiudad());
        poiActual.setTipo(poi.getTipo());
        poiActual.setHorario_apertura(poi.getHorario_apertura());
        poiActual.setPrecio(poi.getPrecio());

        poiRepository.save(poiActual);
        return "redirect:/web/poi/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarPOI(HttpSession session, @PathVariable String id) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/poi/" + id + "/eliminar");
            return "redirect:/web/login";
        }

        if (!poiRepository.existsById(id)) {
            return "redirect:/web/poi/lista";
        }

        poiRepository.deleteById(id);
        return "redirect:/web/poi/lista";
    }
}
