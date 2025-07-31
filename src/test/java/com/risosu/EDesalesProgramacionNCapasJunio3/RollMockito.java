/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.RolJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
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
public class RollMockito {
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Roll> rollQuery;
    @InjectMocks
    private RolJPADAOImplementation rolJPADAOImplementation;
    
    @BeforeEach 
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testGetAllRoll_Success(){
        int rollId = 1;
        
        Roll roll1 = new Roll();
        roll1.setIdRoll(rollId);
        roll1.setNombre("Junior");
        
        List<Roll> roles = Arrays.asList(roll1);
        when(entityManager.createQuery("FROM Roll", Roll.class)).thenReturn(rollQuery);
        when(rollQuery.getResultList()).thenReturn(roles);
        
        Result result = rolJPADAOImplementation.GetAllRoll();
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.objects);
        Assertions.assertEquals(1, result.objects.size());
        
    }
    
}
