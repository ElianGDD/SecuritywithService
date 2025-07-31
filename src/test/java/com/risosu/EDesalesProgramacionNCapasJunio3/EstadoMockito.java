/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.EstadoJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Pais;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
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
public class EstadoMockito {
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Estado> typedQuery;
    
    @InjectMocks
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetEstadoByPais_Success(){
        int paisID = 1;
        Estado estado1 = new Estado();
        
        estado1.setIdEstado(1);
        estado1.setNombre("Toluca");
        Pais pais = new Pais();
        pais.setIdPais(paisID);
        estado1.setPais(pais);
        
        List<Estado> estados = Arrays.asList(estado1);
        
        when(entityManager.createQuery("FROM Estado WHERE pais.idPais = :idpais", Estado.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("idpais", paisID)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(estados);
        
        Result result = estadoJPADAOImplementation.GetEstadoByIdPais(paisID);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        
        
        
    }
    
}

