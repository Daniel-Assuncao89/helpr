package org.soulcodeacademy.helpr.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class TokenDTO {
    @NotBlank(message = "Email obrigatório")
    private String acessToken;
    @NotBlank(message = "Senha obrigatório")
    private String tokenType = "Bearer";

    public TokenDTO(String acessToken){
        this.acessToken = acessToken;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
