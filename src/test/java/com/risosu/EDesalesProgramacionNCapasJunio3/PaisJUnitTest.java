/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.PaisJPADAOImplementatio;
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
public class PaisJUnitTest {

    @Autowired
    PaisJPADAOImplementatio paisJPADAOImplementatio;

    @Test
    public void GetAllPais() {
        Result result = paisJPADAOImplementatio.GetAllPais();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que el método GetAll fuera exitoso");
        Assertions.assertNull(result.ex, "No debería haber excepción");
        Assertions.assertNotNull(result.objects, "La lista de objetos no debería ser null");

    }

}
