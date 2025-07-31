package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.ColoniaJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Municipio;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

public class ColoniaMockito {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Colonia> typedQuery;

    @InjectMocks
    private ColoniaJPADAOImplementation coloniaDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetColoniaByIdMunicipio_Success() {
        int municipioId = 1;
        Colonia colonia1 = new Colonia();
        colonia1.setIdColonia(1);
        colonia1.setNombre("Colonia A");
        Municipio municipio = new Municipio();
        municipio.setIdMunicipio(municipioId);
        colonia1.setMunicipio(municipio);

        List<Colonia> colonias = Arrays.asList(colonia1);

        when(entityManager.createQuery("FROM Colonia WHERE municipio.idMunicipio = : idmunicipio", Colonia.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("idmunicipio", municipioId)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(colonias);

        Result result = coloniaDAO.GetColoniaByIdMunicipio(municipioId);

        assertTrue(result.correct);
        assertNotNull(result.objects);
        assertEquals(1, result.objects.size());
        assertEquals("Colonia A", ((Colonia) result.objects.get(0)).getNombre());
    }

    @Test
    public void testGetColoniaByCP_Success() {
        // Arrange
        String cp = "12345";
        Colonia colonia1 = new Colonia();
        colonia1.setIdColonia(1);
        colonia1.setNombre("Colonia B");
        colonia1.setCodigoPostal(cp);

        List<Colonia> colonias = Arrays.asList(colonia1);

        when(entityManager.createQuery("FROM Colonia WHERE codigoPostal = :codigopostal", Colonia.class)).thenReturn(typedQuery);
        when(typedQuery.setParameter("codigopostal", cp)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(colonias);

        // Act
        Result result = coloniaDAO.GetColoniaByCP(cp);

        // Assert
        assertTrue(result.correct);
        assertNotNull(result.objects);
        assertEquals(1, result.objects.size());
        assertEquals("Colonia B", ((Colonia) result.objects.get(0)).getNombre());
    }

}
