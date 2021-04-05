package com.platzi.market.web.security.filter;

import com.platzi.market.domain.service.PlatziUserDetailsService;
import com.platzi.market.web.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* */

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;


    /* Lo que hacemos con este método es verificar si lo que viene en el encabezado de la petición
    * es un token y si el token es correcto
    *
    * Primero capturamos el encabezado de la petición*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /* Primero capturamos el encabezado de la petición*/
        String authorizationHeader = request.getHeader("Authorization");

        /* Preguntamos si esa autorización es diferente a null, es decir que venga y empiece con Bearer
        * Si se cumple, presuntamente viene un JWT que, recordemos, inicia con Bearer*/
        if (authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){

            String jwt = authorizationHeader.substring(7);

            /* Capturamos el username que viene en el jwt*/
            String username = jwtUtil.extractUsernameToken(jwt);

            /* Verificamos que usuario sea diferente de nulo y que no tenga sesión iniciada*/
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                /* Si cumple, traemos los datos del usuario*/
                UserDetails userDetails = platziUserDetailsService.loadUserByUsername(username);

                /* Preguntamos si el JWT es correcto*/
                if (jwtUtil.validateToken(jwt, userDetails)) {

                    /* Si pasa la validación*/
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                            null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request,response);
    } //doFilter
} //clase
