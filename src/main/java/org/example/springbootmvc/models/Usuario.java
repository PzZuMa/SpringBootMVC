package org.example.springbootmvc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa el modelo de los usuarios.
 */
@Document(collection = "Usuarios")
@Data
public class Usuario {
    @Id
    private String _id;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    @JsonIgnore
    private String token;
}
