package org.example.springbootmvc.web;

import jakarta.servlet.http.HttpSession;
import org.example.springbootmvc.SecurityService;
import org.example.springbootmvc.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/login")
public class LoginController {
    @Autowired
    SecurityService securityService;

    @GetMapping
    public String login(Model model) {
        return "web/login";
    }

    @PostMapping
    public String processLogin(HttpSession session, Model model, @ModelAttribute Usuario login) {
        var result = securityService.login(login.getNombre(), login.getEmail());
        if(result.isPresent()){
            session.setAttribute("user", result.get());
//            if (session.getAttribute("source") != null) {
//                return "redirect:" + session.getAttribute("source");
//            }
            return "redirect:/web/home";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "web/login";
        }
    }

    @GetMapping("/exit")
    public String exit(HttpSession session, Model model) {
        session.removeAttribute("user");
        return "redirect:/web/login";
    }

}
