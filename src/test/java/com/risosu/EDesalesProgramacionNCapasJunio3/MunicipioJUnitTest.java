/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.MunicipioJPADAOImplementation;
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
public class MunicipioJUnitTest {
    @Autowired
    MunicipioJPADAOImplementation municipioJPADAOImplementation;
    
    @Test
    public void GetMunicipioByIdEstado(){
        int idEstado = 3;
        Result result = municipioJPADAOImplementation.GetMunicipioByEstado(idEstado);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.objects, "Debe devolver una lista de estados");
    
        
    }
    
}
