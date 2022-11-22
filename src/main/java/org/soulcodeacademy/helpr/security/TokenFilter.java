package org.soulcodeacademy.helpr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// TokenFilter irá verificar as requisições por cliente uma vez por requisição

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UsuarioSecurityService usuarioSecurityService;

    // Este metodo é chamado para toda requisição feita pelo cliente
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!validarCabecalho(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.extrairToken(request);

        // Passo 3
        if(!this.tokenUtil.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Passo 4
        String email = this.tokenUtil.extrairEmail(token);
        UserDetails usuario = this.usuarioSecurityService.loadUserByUsername(email);
        // Configura o uusario encontrado como autenticado na aplicação
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities()));
        filterChain.doFilter(request,response);
        // Extrair do cabecalho as informacoes do token, com base nessas informações busca dados do usuário e então indica. Para a segurança da aplicação que o usuário válido. Os próximos filtros já irão conhecer usuário e permitir o acesso
    }

    private String extrairToken(HttpServletRequest request){
        // "Bearer <JWT>"
        String cabecalho =  request.getHeader("Authorization");
        return cabecalho.substring(7); // Retorna apenas o codigo do JWT
    }

    private boolean validarCabecalho(HttpServletRequest request){
        // extrai do cliente o cabecalho com o possivel token
        String cabecalho = request.getHeader("Authorization");
        // o cabeçalho enviado pelo cliente é valido se possuir o Authorization e o valor começar com o "Bearer"
        return cabecalho != null && cabecalho.startsWith("Bearer");
    }
}
