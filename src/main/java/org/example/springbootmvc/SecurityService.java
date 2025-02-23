package org.example.springbootmvc;

import org.example.springbootmvc.models.Usuario;
import org.example.springbootmvc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de seguridad para la validación de tokens.
 */
@Service
public class SecurityService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Valida si un token existe en el repositorio de usuarios.
     *
     * @param token el token a validar
     * @return true si el token existe, false en caso contrario
     */
    public Boolean requestValidation(String token){
        return usuarioRepository.existsByToken(token);
    }


    public Optional<Usuario> login(String nombre, String email){
        if(usuarioRepository.existsByEmailAndNombre(email, nombre)){
            return Optional.ofNullable( usuarioRepository.findByEmail(email) );
        }
        else return Optional.empty();
    }
}
