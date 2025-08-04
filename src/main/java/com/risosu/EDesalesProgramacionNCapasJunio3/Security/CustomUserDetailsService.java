/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.Security;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.IUsuarioJPADAO;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioJPADAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result result = usuarioDAO.GetUsuarioByNombre(username);

        if (!result.correct || result.object == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con username: " + username);
        }

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        Usuario usuario = usuarioDireccion.usuario;

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.getRoll() != null && usuario.getRoll().getNombre() != null) {
            // SIN "ROLE_" — usamos el nombre del rol tal cual lo defines en .hasAnyAuthority()
            authorities.add(new SimpleGrantedAuthority(usuario.getRoll().getNombre()));
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getUserName(),
                usuario.getPassword(),
                authorities
        );
    }
}
