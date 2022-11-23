package org.soulcodeacademy.helpr.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CredenciaisDTO {
    @NotBlank(message = "Email obrigatório")
    @Email
    private String email;
    @NotBlank(message = "Senha obrigatório")
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
