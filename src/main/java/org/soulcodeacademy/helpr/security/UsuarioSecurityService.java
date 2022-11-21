package org.soulcodeacademy.helpr.security;

import org.soulcodeacademy.helpr.domain.Usuario;
import org.soulcodeacademy.helpr.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Carregar a entidade do banco de dados e converter para o UsuarioSecurity
@Service
public class UsuarioSecurityService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Se comunica com o banco de dados em busca do usuario que tem username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(username);
//        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
        if(usuarioOptional.isEmpty()){
            throw new UsernameNotFoundException("usuario nao encontrad");
        } else {
            Usuario usuario = usuarioOptional.get();
            // Dessa forma o Spring Security seja capaz de conhecer o nosso usuario
            return new UsuarioSecurity(usuario.getEmail(), usuario.getSenha(), usuario.getPerfil());
        }
    }
}
