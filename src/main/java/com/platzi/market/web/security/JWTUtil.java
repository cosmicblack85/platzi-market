package com.platzi.market.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    private static final String KEY = "Pl4tzi";

    /* El parámetro es de la org.springframework pero lo vamos a enviar desde el Controlador para que
    * se genere el JWT */
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).
                setExpiration( new Date(System.currentTimeMillis() + 1000 * 60 * 10)).
                signWith(SignatureAlgorithm.HS256,KEY).compact();
    }



    /* Para validar un token hay que validar que sea para el usuaruio que está haciendo la petición
    * y que no haya vencido */

    public boolean validateToken (String token, UserDetails userDetails){
        return userDetails.getUsername().equals(extractUsernameToken(token)) && !isTokenExpired(token);
    }



    /* Meptodo que corrobora el nombre de usuario
    * getClaims(token) -> trae los datos del token
    * getSubject() -> trae el uusario del token que esta en el Subject del token*/
    public String extractUsernameToken(String token){
        return getCalims(token).getSubject();
    }



    /* Este método corrobora la fecha de expiración del token que nos trae el método getClaims
    * getClaims(token) -> trae los datos del token
    * getExpiration() -> usa los datos del token y trae la fecha de expiración
    * before -> verifica que la fecha de expiración este antes de la fecha actual, para eso se envía el
    *           parámetro de una nueva fecha creada en ese instante
    * si esta antes retornara true que quiere decir que ya expiro si no está antes false = vigente*/

    public boolean isTokenExpired(String token){
        return getCalims(token).getExpiration().before(new Date());
    }

    /* Los Claims son los objetos dentro del token
    * getBody() -> trae los datos/cuerpo del token siempre que el método anterior se cumpla
    * parseClaimsJws(token) -> permite traer el cuerop de la llave que se ingresa de parámetro
    * setSigningKey(KEY) -> Permite traer el token despues de verificar la llave que se envía de parámetro. Si no
    *                       no coincide no permitirá ejecutar lo demás
    * parser() -> recibe la llave y la envía al objeto Jwts
    * Jwts -> Es el objeto el cual a través de este podemos traer los Claims y "desencriptados"*/
    private Claims getCalims (String token){
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
