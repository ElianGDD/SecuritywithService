/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UsuarioJPAREPOSITORYDAO usuarioJPAREPOSITORYDAO;
    @Autowired
    private DireccionJPAREPOSITORYDAO direccionJPAREPOSITORYDAO;

    @Transactional
    public UsuarioDireccion crearConDirecciones(UsuarioDireccion ud) {
        Usuario savedUser = usuarioJPAREPOSITORYDAO.save(ud.usuario);

        if (ud.Direcciones != null && !ud.Direcciones.isEmpty()) {
            ud.Direcciones.forEach(d -> d.setUsuario(savedUser));
            List<Direccion> dirsGuardadas
                    = direccionJPAREPOSITORYDAO.saveAll(ud.Direcciones);
            ud.Direcciones = dirsGuardadas;
        }

        ud.usuario = savedUser;
        return ud;
    }

    @Transactional
    public Result PostAll(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            Usuario usuario = usuarioJPAREPOSITORYDAO.save(usuarioDireccion.usuario);

            if (usuarioDireccion.Direccion != null) {
                usuarioDireccion.Direccion.usuario = new Usuario();
                usuarioDireccion.Direccion.usuario.setIdUsuario(usuarioDireccion.Direccion.usuario.getIdUsuario());
                Direccion direccion = direccionJPAREPOSITORYDAO.save(usuarioDireccion.Direccion);
            }

            result.correct = true;
            result.object = usuarioDireccion; 
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    public Result PostCargaMasiva(List<UsuarioDireccion> usuariosDireccions) {
        Result result = new Result();
        try {

            for (UsuarioDireccion usuarioDireccion : usuariosDireccions) {
                this.crearConDirecciones(usuarioDireccion);
            }

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

}
