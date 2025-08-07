/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Alien 13
 */
public interface DireccionJPAREPOSITORYDAO extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByUsuario_IdUsuario(int idUsuario);

    long deleteByUsuario_IdUsuario(int idUsuario);

    @Modifying
    @Transactional
    @Query(value = """
    INSERT INTO direccion
      (calle,
       numerointerior,
       numeroexterior,
       idcolonia,
       idusuario)
    VALUES
      (:calle,
       :numInt,
       :numExt,
       :idColonia,
       :idUsuario)
  """, nativeQuery = true)
    int insertDireccion(
            @Param("calle") String calle,
            @Param("numInt") String numeroInterior,
            @Param("numExt") String numeroExterior,
            @Param("idColonia") Integer idColonia,
            @Param("idUsuario") Integer idUsuario
    );

}
