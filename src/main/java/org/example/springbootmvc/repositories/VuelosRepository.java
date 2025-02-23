package org.example.springbootmvc.repositories;

import org.example.springbootmvc.models.Vuelo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interfaz de repositorio para entidades de Vuelo.
 */
public interface VuelosRepository extends MongoRepository<Vuelo, String> {

    /**
     * Encuentra vuelos por origen.
     *
     * @param origen el origen para buscar vuelos
     * @return una lista de vuelos con el origen especificado
     */
    List<Vuelo> findByOrigen(String origen);

    /**
     * Encuentra vuelos por destino.
     *
     * @param destino el destino para buscar vuelos
     * @return una lista de vuelos con el destino especificado
     */
    List<Vuelo> findByDestino(String destino);

    /**
     * Encuentra vuelos dentro de un rango de duración.
     *
     * @param min la duración mínima
     * @param max la duración máxima
     * @return una lista de vuelos dentro del rango de duración especificado
     */
    List<Vuelo> findByDuracionBetween(Integer min, Integer max);
}
