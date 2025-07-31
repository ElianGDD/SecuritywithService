/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alien 13
 */
public interface ColoniaJPARespositoryDAO extends JpaRepository<Colonia, Integer>{
    List<Colonia> findByMunicipio_IdMunicipio(int idMunicipio);
    List<Colonia> findByCodigoPostal(String codigoPostal);

}
