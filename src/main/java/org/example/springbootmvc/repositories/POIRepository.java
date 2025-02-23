package org.example.springbootmvc.repositories;

import org.example.springbootmvc.models.POI;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interfaz de repositorio para entidades de POI.
 */
public interface POIRepository extends MongoRepository<POI, String> {

    /**
     * Encuentra puntos de interés por ciudad.
     *
     * @param ciudad la ciudad para buscar puntos de interés
     * @return una lista de puntos de interés en la ciudad especificada
     */
    List<POI> findByCiudad(String ciudad);

    /**
     * Encuentra puntos de interés por tipo.
     *
     * @param tipo el tipo para buscar puntos de interés
     * @return una lista de puntos de interés del tipo especificado
     */
    List<POI> findByTipo(String tipo);

    /**
     * Encuentra puntos de interés dentro de un rango de precios.
     *
     * @param min el precio mínimo
     * @param max el precio máximo
     * @return una lista de puntos de interés dentro del rango de precios especificado
     */
    List<POI> findByPrecioBetween(Double min, Double max);
}
