
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;


public interface DireccionJPADAO {
    Result GetDireccion(int IdDireccion);
    Result POSTDireccionByIdUsuario( UsuarioDireccion usuarioDireccion);
    Result PUTDireccionByUsuario(UsuarioDireccion usuarioDireccion);
    Result DELETEDireccionByUsuario(UsuarioDireccion usuarioDireccion);
    
}

