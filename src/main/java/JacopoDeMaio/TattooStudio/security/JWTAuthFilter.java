package JacopoDeMaio.TattooStudio.security;


import JacopoDeMaio.TattooStudio.entities.Generic;
import JacopoDeMaio.TattooStudio.exceptions.UnauthorizedException;
import JacopoDeMaio.TattooStudio.services.GenericService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private GenericService genericService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserisci correttamente il token nell'header");

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String utentiId = jwtTools.extractIdFromToken(accessToken);
        Generic currentGeneric = genericService.findById(UUID.fromString(utentiId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentGeneric, null, currentGeneric.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath()) ||
                new AntPathMatcher().match("/tattooArtists/**", request.getServletPath()) ||
                new AntPathMatcher().match("/tattoos/**", request.getServletPath());
    }
}
