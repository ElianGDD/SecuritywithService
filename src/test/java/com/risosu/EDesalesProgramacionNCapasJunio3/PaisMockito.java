/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.PaisJPADAOImplementatio;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Alien 13
 */
public class PaisMockito {
    
    @Mock
    private EntityManager entityManager;
    
    @Mock
    private  TypedQuery<Pais> typedQuery;
    
    @InjectMocks
    private PaisJPADAOImplementatio paisJPADAOImplementatio;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetAllPais(){
        int paisId = 1;
        Pais pais1 = new Pais();
        pais1.setIdPais(paisId);
        pais1.setNombre("Polonia");
        
        List<Pais> paises = Arrays.asList(pais1);
        when (entityManager.createQuery("FROM Pais", Pais.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(paises);
        Result result = paisJPADAOImplementatio.GetAllPais();
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        
    }
    
}
