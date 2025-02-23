package org.example.springbootmvc.api;

import org.example.springbootmvc.SecurityService;
import org.example.springbootmvc.models.POI;
import org.example.springbootmvc.models.Usuario;
import org.example.springbootmvc.repositories.POIRepository;
import org.example.springbootmvc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los puntos de interés (POI).
 */
@RestController
@RequestMapping("/api/pois")
public class POIController {

    @Autowired
    POIRepository poiRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los puntos de interés.
     *
     * @return una lista de todos los puntos de interés.
     */
    @GetMapping("/")
    public List<POI> getPois() {
        return poiRepository.findAll();
    }

    /**
     * Obtiene un punto de interés por su ID.
     *
     * @param id el ID del punto de interés.
     * @return el punto de interés correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<POI> getPoiById(@PathVariable String id) {
        if (poiRepository.existsById(id)) {
            var poi = poiRepository.findById(id).get();
            return new ResponseEntity<>(poi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un punto de interés por su ID.
     *
     * @param id el ID del punto de interés.
     * @param token el token de seguridad.
     * @return un mensaje de éxito si el punto de interés fue eliminado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!poiRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        poiRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "El punto de interés ha sido borrado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Crea un nuevo punto de interés.
     *
     * @param poi el punto de interés a crear.
     * @param token el token de seguridad.
     * @return el punto de interés creado.
     */
    @PostMapping("/")
    public ResponseEntity<POI> create(@RequestBody POI poi, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (poi == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        poiRepository.save(poi);
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    /**
     * Actualiza un punto de interés existente.
     *
     * @param id el ID del punto de interés a actualizar.
     * @param poi el punto de interés actualizado.
     * @param token el token de seguridad.
     * @return el punto de interés actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<POI> update(@PathVariable String id, @RequestBody POI poi, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (poi == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!poiRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        poi.set_id(id);
        poiRepository.save(poi);
        return new ResponseEntity<>(poi, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de puntos de interés por ciudad.
     *
     * @param ciudad la ciudad de los puntos de interés.
     * @return una lista de puntos de interés en la ciudad especificada.
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<POI>> getPOIbyCiudad(@PathVariable String ciudad) {
        List<POI> resultado = poiRepository.findByCiudad(ciudad);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de puntos de interés por tipo.
     *
     * @param tipo el tipo de los puntos de interés.
     * @return una lista de puntos de interés con el tipo especificado.
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<POI>> getPOIbyTipo(@PathVariable String tipo) {
        List<POI> resultado = poiRepository.findByTipo(tipo);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de puntos de interés cuyo precio está entre dos valores.
     *
     * @param precioAfter el precio mínimo.
     * @param precioBefore el precio máximo.
     * @return una lista de puntos de interés cuyo precio está entre los valores especificados.
     */
    @GetMapping("/precio/{precioAfter}&{precioBefore}")
    public ResponseEntity<List<POI>> getPOIbyPrecioIsBetween(@PathVariable Double precioAfter, @PathVariable Double precioBefore) {
        List<POI> resultado = poiRepository.findByPrecioBetween(precioAfter, precioBefore);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * Realiza una reserva de punto de interés.
     *
     * @param id el ID del punto de interés.
     * @param token el token de seguridad.
     * @return los datos del punto de interés y del usuario que realizó la reserva.
     */
    @PostMapping("/reserva/{id}")
    public ResponseEntity<Map<String, Object>> reserva(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!poiRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        POI poi = poiRepository.findById(id).get();
        Usuario usuario = usuarioRepository.findByToken(token);

        Map<String, Object> data = new HashMap<>();
        data.put("poi", poi);
        data.put("usuario", usuario);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}