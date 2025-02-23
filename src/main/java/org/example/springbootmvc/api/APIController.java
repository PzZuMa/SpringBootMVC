package org.example.springbootmvc.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador para manejar las solicitudes a la API.
 */
@Controller
@RequestMapping("/api")
public class APIController {

    /**
     * Maneja las solicitudes GET a la ruta raíz de la API.
     *
     * @return el nombre de la vista "index".
     */
    @GetMapping("/")
    public String index() {
        return "api_index";
    }
}