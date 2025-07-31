/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * s
 *
 * @author Alien 13
 */
public interface UsuarioJPAREPOSITORYDAO extends JpaRepository<Usuario, Integer> {

    @Query("""
    SELECT u
    FROM Usuario u
      LEFT JOIN u.roll r
    WHERE (:nombre          IS NULL
           OR LOWER(u.nombre) LIKE LOWER(CONCAT('%',:nombre,'%')))
      AND (:apellidoPaterno IS NULL
           OR LOWER(u.apellidoPaterno)
              LIKE LOWER(CONCAT('%',:apellidoPaterno,'%')))
      AND (:apellidoMaterno IS NULL
           OR LOWER(u.apellidoMaterno)
              LIKE LOWER(CONCAT('%',:apellidoMaterno,'%')))
      AND (:idRoll          IS NULL
           OR r.idRoll = :idRoll)
  """)
    List<Usuario> buscarDinamico(
            @Param("nombre") String nombre,
            @Param("apellidoPaterno") String apellidoPaterno,
            @Param("apellidoMaterno") String apellidoMaterno,
            @Param("idRoll") Integer idRoll
    );

}
