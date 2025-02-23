package org.example.springbootmvc.api;

import org.example.springbootmvc.SecurityService;
import org.example.springbootmvc.models.Usuario;
import org.example.springbootmvc.models.Vuelo;
import org.example.springbootmvc.repositories.UsuarioRepository;
import org.example.springbootmvc.repositories.VuelosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los vuelos.
 */
@RestController
@RequestMapping("api/vuelos")
public class VueloController {

    @Autowired
    VuelosRepository vuelosRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los vuelos.
     *
     * @return una lista de todos los vuelos.
     */
    @GetMapping("/")
    public List<Vuelo> getVuelos() {
        return vuelosRepository.findAll();
    }

    /**
     * Obtiene un vuelo por su ID.
     *
     * @param id el ID del vuelo.
     * @return el vuelo correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> getPoiById(@PathVariable String id) {
        if (vuelosRepository.existsById(id)) {
            var vuelo = vuelosRepository.findById(id).get();
            return new ResponseEntity<>(vuelo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un vuelo por su ID.
     *
     * @param id el ID del vuelo.
     * @param token el token de seguridad.
     * @return un mensaje de éxito si el vuelo fue eliminado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!vuelosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vuelosRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "El vuelo ha sido borrado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Crea un nuevo vuelo.
     *
     * @param vuelo el vuelo a crear.
     * @param token el token de seguridad.
     * @return el vuelo creado.
     */
    @PostMapping("/")
    public ResponseEntity<Vuelo> create(@RequestBody Vuelo vuelo, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (vuelo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        vuelosRepository.save(vuelo);
        return new ResponseEntity<>(vuelo, HttpStatus.OK);
    }

    /**
     * Actualiza un vuelo existente.
     *
     * @param id el ID del vuelo a actualizar.
     * @param vuelo el vuelo actualizado.
     * @param token el token de seguridad.
     * @return el vuelo actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vuelo> update(@PathVariable String id, @RequestBody Vuelo vuelo, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (vuelo == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!vuelosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vuelo.set_id(id);
        vuelosRepository.save(vuelo);
        return new ResponseEntity<>(vuelo, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de vuelos por origen.
     *
     * @param origen el origen de los vuelos.
     * @return una lista de vuelos con el origen especificado.
     */
    @GetMapping("/origen/{origen}")
    public ResponseEntity<List<Vuelo>> getVueloByOrigen(@PathVariable String origen) {
        List<Vuelo> resultado = vuelosRepository.findByOrigen(origen);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de vuelos por destino.
     *
     * @param destino el destino de los vuelos.
     * @return una lista de vuelos con el destino especificado.
     */
    @GetMapping("/destino/{destino}")
    public ResponseEntity<List<Vuelo>> getVueloByDestino(@PathVariable String destino) {
        List<Vuelo> resultado = vuelosRepository.findByDestino(destino);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de vuelos cuya duración está entre dos valores.
     *
     * @param duracionAfter la duración mínima.
     * @param duracionBefore la duración máxima.
     * @return una lista de vuelos cuya duración está entre los valores especificados.
     */
    @GetMapping("/duracion/{duracionAfter}&{duracionBefore}")
    public ResponseEntity<List<Vuelo>> getVueloByDuracionIsBetween(@PathVariable Integer duracionAfter, @PathVariable Integer duracionBefore) {
        List<Vuelo> resultado = vuelosRepository.findByDuracionBetween(duracionAfter, duracionBefore);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * Realiza una reserva de vuelo.
     *
     * @param id el ID del vuelo.
     * @param token el token de seguridad.
     * @return los datos del vuelo y del usuario que realizó la reserva.
     */
    @PostMapping("/reserva/{id}")
    public ResponseEntity<Map<String, Object>> reserva(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!vuelosRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vuelo vuelo = vuelosRepository.findById(id).get();
        Usuario usuario = usuarioRepository.findByToken(token);

        Map<String, Object> data = new HashMap<>();
        data.put("vuelo", vuelo);
        data.put("usuario", usuario);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}