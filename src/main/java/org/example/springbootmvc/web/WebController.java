package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.models.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class WebController {
    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:home"; // Redirigir al home si está autenticado
        }
        return "redirect:login"; // Redirigir al login si no está autenticado
    }

    @GetMapping("/home")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:login"; // Redirigir al login si no está autenticado
        }

        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("user", user);
        return "web/home"; // Página principal después del login
    }
}
