package org.example.springbootmvc.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa el modelo de los puntos de interés.
 */
@Document(collection = "Puntos de Interes")
@Data
public class POI {
    @Id
    private String _id;
    private String ciudad;
    private String horario_apertura;
    private String nombre;
    private Double precio;
    private String tipo;
    private String imagen;
}
