/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.MunicipioJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Estado;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Municipio;
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
public class MunicipioMockito {
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Municipio> typedQuery;
    @InjectMocks
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;
    
    @BeforeEach
    public void seUP(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetMunicipioByEstado(){
        int estadoId = 1;
        Municipio municipio1 = new Municipio();
        municipio1.setIdMunicipio(1);
        municipio1.setNombre("San lazadoro");
        Estado estado = new Estado();
        estado.setIdEstado(estadoId);
        municipio1.setEstado(estado);
        
        List<Municipio> municipios = Arrays.asList(municipio1);
        
        when(entityManager.createQuery("FROM Municipio WHERE estado.idEstado = :idestado", Municipio.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("idestado", estadoId)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(municipios);
        
        Result result = municipioJPADAOImplementation.GetMunicipioByEstado(estadoId);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        
    }
    
}
