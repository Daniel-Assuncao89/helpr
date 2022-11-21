package org.soulcodeacademy.helpr.security;

import org.soulcodeacademy.helpr.domain.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Esta classe serve de ponte para comunicação entre a presistencia e o framework
public class UsuarioSecurity implements UserDetails {
    // dados de autenticação
    private String senha;
    private String email;
    // dados de autorização
    private Perfil perfil;
    private ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public UsuarioSecurity(String email, String senha, Perfil perfil){
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.authorities.add(new SimpleGrantedAuthority(perfil.getDescricao()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Indica as permissões/papeis do usuario
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() { //username pode ser CPF, email...esses tipos de informaçoes
        // Indica para o security que nosso usuario possui o email abaixo
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //Flag que indica se a conta está valida
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //Flag que indica se a conta está desbloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //Flag que indica se as credenciais estão validas
        return true;
    }

    @Override
    public boolean isEnabled() {
        //Flag que indica se o usuario está habilitado
        return true;
    }
}
