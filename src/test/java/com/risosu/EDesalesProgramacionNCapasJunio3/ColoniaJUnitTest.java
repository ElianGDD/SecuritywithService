/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaJPADAOImplementation;
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
public class ColoniaJUnitTest {
    @Autowired
    ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    
    @Test
    public void GetColoniaByIdMunicipio(){
        int idMunicipio = 4;
        Result result = coloniaJPADAOImplementation.GetColoniaByIdMunicipio(idMunicipio);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.objects, "Debe devolver una lista de colonias");
    
        
        
    }
    @Test
    public void GetDireccionByCP(){
        String CP = "55614";
        Result result = coloniaJPADAOImplementation.GetColoniaByCP(CP);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.objects, "Debe devolver una lista de colonias");
    
    }
    
}
