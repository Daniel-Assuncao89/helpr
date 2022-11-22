package org.soulcodeacademy.helpr.security;

// Objetivo desta classe é gerar tokens JWT, validar JWT e extrair dados do JWT.

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // instanciar automaticamente o TokenUtil
public class TokenUtil {
    @Value("${senhaJwt}") // injeta o valor da variavel no campo abaixo
    private String senhaJwt;

    @Value("${validadeJwt}")
    private Long validadeJwT;

    public String gerarToken(String email, String perfil) {
        // currentTimeMills() => Pega o momento atual em ms
        // new Date(System.currentTimeMillis() + this.validadeJwT => indica a data futura que o token vai expirar
        return JWT.create()
                .withSubject(email)
                .withClaim("perfil", perfil)
                .withExpiresAt(new Date(System.currentTimeMillis() + this.validadeJwT))
                .sign(Algorithm.HMAC512(this.senhaJwt));

    }

    public String extrairEmail(String token){
    return JWT.require((Algorithm.HMAC512(this.senhaJwt)))
            .build()
            .verify(token)
            .getSubject();
    }

    public boolean validarToken(String token){
        // caso ocorra erro na linha 42, o token passado é invalido. Não foi gerado por nós ou expirou
        try {
            JWT.require(Algorithm.HMAC512(this.senhaJwt))
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }
}
