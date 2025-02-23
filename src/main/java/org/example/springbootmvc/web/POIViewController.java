package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.POI;
import org.example.springbootmvc.repositories.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
