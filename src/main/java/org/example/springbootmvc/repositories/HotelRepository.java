package org.example.springbootmvc.repositories;

import org.example.springbootmvc.models.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interfaz de repositorio para entidades de Hotel.
 */
public interface HotelRepository extends MongoRepository<Hotel, String> {

    /**
     * Encuentra hoteles por ciudad.
     *
     * @param ciudad la ciudad para buscar hoteles
     * @return una lista de hoteles en la ciudad especificada
     */
    List<Hotel> findByCiudad(String ciudad);

    /**
     * Encuentra hoteles por calificación de estrellas.
     *
     * @param estrellas la calificación de estrellas para buscar hoteles
     * @return una lista de hoteles con la calificación de estrellas especificada
     */
    List<Hotel> findByEstrellas(Integer estrellas);

    /**
     * Encuentra hoteles dentro de un rango de precios.
     *
     * @param min el precio mínimo
     * @param max el precio máximo
     * @return una lista de hoteles dentro del rango de precios especificado
     */
    List<Hotel> findByPrecioBetween(Double min, Double max);
}