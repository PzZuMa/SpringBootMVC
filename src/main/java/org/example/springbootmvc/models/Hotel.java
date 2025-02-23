package org.example.springbootmvc.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa el modelo de los hoteles.
 */
@Document(collection = "Hoteles")
@Data
public class Hotel {
    @Id
    private String _id;
    private String ciudad;
    private String direccion;
    private String email;
    private Integer estrellas;
    private String nombre;
    private Double precio;
    private String telefono;
    private String imagen;
}
