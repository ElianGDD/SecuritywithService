/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import java.util.List;

/**
 *
 * @author Alien 13
 */
public interface UsuarioRestFullDAO {

    List<UsuarioDireccion> getAllUsuarios();
    UsuarioDireccion getUsuarioDirecciones(int idUsuario); 
    Usuario getUsuario(int idUsuario);  
    UsuarioDireccion createUsuario(UsuarioDireccion usuarioDireccion); 
    UsuarioDireccion updateUsuario(int idUsuario, UsuarioDireccion usuarioDireccion);  
    void deleteUsuario(int idUsuario);  
    void createUsuariosMasivos(List<UsuarioDireccion> usuariosDireccions);  
    void updateUsuarioStatus(int idUsuario, UsuarioDireccion usuarioDireccion);  
}

