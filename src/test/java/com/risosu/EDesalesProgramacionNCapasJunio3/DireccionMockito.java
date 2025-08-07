/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Alien 13
 */
public class DireccionMockito {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Usuario> usuarioQuery;

    @Mock
    private TypedQuery<Direccion> queryDireccion;

    @InjectMocks
    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDireccion_Success() {
        // Datos de prueba
        int direccionId = 1;
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(direccionId);
        direccion.setCalle("Calle Principal");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        direccion.setUsuario(usuario);

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = direccion;

        // Configuración de la consulta de dirección
        when(entityManager.createQuery("FROM Direccion d WHERE d.IdDireccion = :iddireccion", Direccion.class))
                .thenReturn(queryDireccion);
        when(queryDireccion.setParameter("iddireccion", direccionId)).thenReturn(queryDireccion);
        when(queryDireccion.getSingleResult()).thenReturn(direccion);

        // Llamada al método a probar
        Result result = direccionJPADAOImplementation.GetDireccion(direccionId);

        // Verificaciones
        assertNotNull(result);
        assertTrue(result.isCorrect(), "La operación debería haber sido exitosa");
        assertNotNull(result.object, "El objeto no debería ser nulo");

        UsuarioDireccion usuarioDireccionResult = (UsuarioDireccion) result.object;
        assertEquals(direccionId, usuarioDireccionResult.Direccion.getIdDireccion(), "El ID de la dirección no coincide");
        assertEquals(direccion.getCalle(), usuarioDireccionResult.Direccion.getCalle(), "La calle no coincide");
        assertEquals(usuario.getIdUsuario(), usuarioDireccionResult.Direccion.getUsuario().getIdUsuario(), "El ID de usuario no coincide");
    }

    @Test
    public void testPostDireccion() {
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(1);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = direccion;
        usuarioDireccion.Direccion.usuario = usuario;

        Result result = direccionJPADAOImplementation.POSTDireccionByIdUsuario(usuarioDireccion);

        assertTrue(result.correct);
        assertNull(result.errorMessage);
        verify(entityManager, times(1)).persist(direccion);

    }

    @Test
    public void testDeleteDireccion_Success() {
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(1);

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = direccion;

        when(entityManager.find(Direccion.class, 1)).thenReturn(direccion);
        doNothing().when(entityManager).remove(direccion);

        Result result = direccionJPADAOImplementation.DELETEDireccionByUsuario(usuarioDireccion);

        assertTrue(result.correct);
        assertNull(result.errorMessage);
        verify(entityManager).find(Direccion.class, 1);
        verify(entityManager).remove(direccion);
    }

}
