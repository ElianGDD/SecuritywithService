/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alien 13
 */
@Service
public class DireccionService {

  @Autowired
  private DireccionJPAREPOSITORYDAO direccionDAO;

  @Transactional
  public Result POSTDireccionByIdUsuario(UsuarioDireccion ud) {
    Result result = new Result();
    try {
      Integer idUsr = ud.usuario.getIdUsuario();

      Direccion d = ud.Direccion;
      int filas = direccionDAO.insertDireccion(
        d.getCalle(),
        d.getNumeroInterior(),
        d.getNumeroExterior(),
        d.Colonia.getIdColonia(),
        idUsr
      );

      if (filas > 0) {
        List<Direccion> dirs = 
          direccionDAO.findByUsuario_IdUsuario(idUsr);
        result.object = dirs;
        result.correct = true;
      } else {
        result.correct = false;
        result.errorMessage = "No se insertó la dirección";
      }
    } catch (Exception ex) {
      result.correct      = false;
      result.errorMessage = ex.getMessage();
    }
    return result;
  }
}
