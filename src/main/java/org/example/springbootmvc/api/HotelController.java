package org.example.springbootmvc.api;

import org.example.springbootmvc.SecurityService;
import org.example.springbootmvc.models.Hotel;
import org.example.springbootmvc.models.Usuario;
import org.example.springbootmvc.repositories.HotelRepository;
import org.example.springbootmvc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar los hoteles.
 */
@RestController
@RequestMapping("api/hoteles")
public class HotelController {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    UsuarioRepository usuarioRepository;

    /**
     * Obtiene todos los hoteles.
     *
     * @return una lista de todos los hoteles.
     */
    @GetMapping("/")
    public List<Hotel> getHoteles() {
        return hotelRepository.findAll();
    }

    /**
     * Obtiene un hotel por su ID.
     *
     * @param id el ID del hotel.
     * @return el hotel correspondiente al ID proporcionado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelByID(@PathVariable String id) {
        if (!hotelRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var hotel = hotelRepository.findById(id).get();
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    /**
     * Elimina un hotel por su ID.
     *
     * @param id el ID del hotel.
     * @param token el token de seguridad.
     * @return un mensaje de éxito si el hotel fue eliminado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!hotelRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        hotelRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "El hotel ha sido borrado con éxito");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    /**
     * Crea un nuevo hotel.
     *
     * @param hotel el hotel a crear.
     * @param token el token de seguridad.
     * @return el hotel creado.
     */
    @PostMapping("/")
    public ResponseEntity<Hotel> create(@RequestBody Hotel hotel, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        hotelRepository.save(hotel);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    /**
     * Actualiza un hotel existente.
     *
     * @param id el ID del hotel a actualizar.
     * @param hotel el hotel actualizado.
     * @param token el token de seguridad.
     * @return el hotel actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Hotel> update(@PathVariable String id, @RequestBody Hotel hotel, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (hotel == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!hotelRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        hotel.set_id(id);
        hotelRepository.save(hotel);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    /**
     * Obtiene una lista de hoteles por ciudad.
     *
     * @param ciudad la ciudad de los hoteles.
     * @return una lista de hoteles en la ciudad especificada.
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Hotel>> getHotelByCiudad(@PathVariable String ciudad) {
        List<Hotel> resultado = hotelRepository.findByCiudad(ciudad);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de hoteles por número de estrellas.
     *
     * @param estrellas el número de estrellas de los hoteles.
     * @return una lista de hoteles con el número de estrellas especificado.
     */
    @GetMapping("/estrellas/{estrellas}")
    public ResponseEntity<List<Hotel>> getHotelByEstrellas(@PathVariable Integer estrellas) {
        List<Hotel> resultado = hotelRepository.findByEstrellas(estrellas);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado,HttpStatus.OK);
    }

    /**
     * Obtiene una lista de hoteles cuyo precio está entre dos valores.
     *
     * @param precioAfter el precio mínimo.
     * @param precioBefore el precio máximo.
     * @return una lista de hoteles cuyo precio está entre los valores especificados.
     */
    @GetMapping("/precio/{precioAfter}&{precioBefore}")
    public ResponseEntity<List<Hotel>> getHotelByPrecioIsBetween(@PathVariable Double precioAfter, @PathVariable Double precioBefore) {
        List<Hotel> resultado = hotelRepository.findByPrecioBetween(precioAfter, precioBefore);
        if (resultado.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * Realiza una reserva de hotel.
     *
     * @param id el ID del hotel.
     * @param token el token de seguridad.
     * @return los datos del hotel y del usuario que realizó la reserva.
     */
    @PostMapping("/reserva/{id}")
    public ResponseEntity<Map<String, Object>> reserva(@PathVariable String id, @RequestParam String token) {
        if (!securityService.requestValidation(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println(hotelRepository.existsById(id));
        if (!hotelRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Hotel hotel = hotelRepository.findById(id).get();
        Usuario usuario = usuarioRepository.findByToken(token);

        Map<String, Object> data = new HashMap<>();
        data.put("hotel", hotel);
        data.put("usuario", usuario);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
