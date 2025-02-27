package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.Hotel;
import org.example.springbootmvc.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/editar")
    public String editarHotel(HttpSession session, @PathVariable String id, Model model) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/hotel/" + id + "/editar");
            return "redirect:/web/login";
        }

        Hotel hotel = hotelRepository.findById(id).get();
        model.addAttribute("hotel", hotel);
        return "hotel/editar";
    }

    @PostMapping("/{id}/editar")
    public String guardarHotel(HttpSession session, @PathVariable String id, @ModelAttribute Hotel hotel) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/hotel/" + id + "/editar");
            return "redirect:/web/login";
        }

        Hotel hotelActual = hotelRepository.findById(id).get();
        hotelActual.setNombre(hotel.getNombre());
        hotelActual.setDireccion(hotel.getDireccion());
        hotelActual.setTelefono(hotel.getTelefono());
        hotelActual.setEstrellas(hotel.getEstrellas());
        hotelActual.setPrecio(hotel.getPrecio());
        hotelActual.setCiudad(hotel.getCiudad());
        hotelActual.setEmail(hotel.getEmail());

        hotelRepository.save(hotelActual);
        return "redirect:/web/hotel/" + id;
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarHotel(HttpSession session, @PathVariable String id) {
        if (session.getAttribute("user") == null) {
//            session.setAttribute("source", "/web/hotel/" + id + "/eliminar");
            return "redirect:/web/login";
        }

        if (!hotelRepository.existsById(id)) {
            return "redirect:/web/hotel/lista";
        }

        hotelRepository.deleteById(id);
        return "redirect:/web/hotel/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoHotel(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/web/login";
        }

        Hotel hotel = new Hotel();
        model.addAttribute("hotel", hotel);
        return "hotel/nuevo";
    }

    @PostMapping("/nuevo")
    public String guardarNuevoHotel(HttpSession session, @ModelAttribute Hotel hotel) {
        if (session.getAttribute("user") == null) {
            return "redirect:/web/login";
        }

        hotelRepository.save(hotel);
        return "redirect:/web/hotel/lista";
    }
}
