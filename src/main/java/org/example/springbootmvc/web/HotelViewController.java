package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.Hotel;
import org.example.springbootmvc.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/hotel")
public class HotelViewController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping("/lista")
    public String listarHoteles(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/hotel/lista");
            return "redirect:/web/login";
        }

        model.addAttribute("hoteles", hotelRepository.findAll());
        return "hotel/lista";
    }

    @GetMapping("/{id}")
    public String detalleHotel(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/hotel/" + id);
            return "redirect:/web/login";
        }

        Hotel hotel = hotelRepository.findById(id).get();
        model.addAttribute("hotel", hotel);
        return "hotel/detalle";
    }
}
