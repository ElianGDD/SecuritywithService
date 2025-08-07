/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Alien 13
 */
@SpringBootTest
public class EstadoJUnitTest {
    @Autowired
    EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @Test
    public void GetEstadoByPais(){
        int idPais = 3;
         Result result = estadoJPADAOImplementation.GetEstadoByIdPais(idPais);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.objects, "Debe devolver un objeto UsuarioDireccion");
    
    }
    
}
