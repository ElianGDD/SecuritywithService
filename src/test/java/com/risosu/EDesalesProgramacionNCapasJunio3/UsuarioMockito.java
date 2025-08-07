/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.IUsuarioJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class UsuarioMockito {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Usuario> usuarioQuery;

    @Mock
    private TypedQuery<Direccion> queryDireccion;

    @Spy
    @InjectMocks
    private IUsuarioJPADAOImplementation iUsuarioJPADAOImplementation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_Success() {
        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Juan");

        Usuario usuario2 = new Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("Ana");

        Usuario usuario3 = new Usuario();
        usuario3.setIdUsuario(3);
        usuario3.setNombre("Carlos");

        Direccion direccion1 = new Direccion();
        direccion1.setIdDireccion(1);
        direccion1.setCalle("Calle 123");

        Direccion direccion2 = new Direccion();
        direccion2.setIdDireccion(2);
        direccion2.setCalle("Calle 456");

        Direccion direccion3 = new Direccion();
        direccion3.setIdDireccion(3);
        direccion3.setCalle("Calle 789");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);

        List<Direccion> direcciones1 = new ArrayList<>();
        direcciones1.add(direccion1);

        List<Direccion> direcciones2 = new ArrayList<>();
        direcciones2.add(direccion2);

        List<Direccion> direcciones3 = new ArrayList<>();
        direcciones3.add(direccion3);

        when(entityManager.createQuery("FROM Usuario", Usuario.class)).thenReturn(usuarioQuery);
        when(usuarioQuery.getResultList()).thenReturn(usuarios);

        when(entityManager.createQuery("FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class))
                .thenReturn(queryDireccion);
        when(queryDireccion.setParameter(eq("idusuario"), eq(usuario1.getIdUsuario()))).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(direcciones1);

        when(queryDireccion.setParameter(eq("idusuario"), eq(usuario2.getIdUsuario()))).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(direcciones2);

        when(queryDireccion.setParameter(eq("idusuario"), eq(usuario3.getIdUsuario()))).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(direcciones3);

        Result result = iUsuarioJPADAOImplementation.GetAll();

        assertNotNull(result);
        assertTrue(result.isCorrect(), "La operación debería haber sido exitosa");
        assertNotNull(result.objects, "Los objetos no deberían ser nulos");
        assertEquals(3, result.objects.size(), "Debería haber 3 UsuarioDireccion");

        UsuarioDireccion usuarioDireccion1 = (UsuarioDireccion) result.objects.get(0);
        assertEquals(usuario1.getNombre(), usuarioDireccion1.usuario.getNombre(), "El nombre del usuario 1 no coincide");
        assertEquals(direccion1.getCalle(), usuarioDireccion1.Direcciones.get(0).getCalle(), "La dirección de usuario 1 no coincide");

        UsuarioDireccion usuarioDireccion2 = (UsuarioDireccion) result.objects.get(1);
        assertEquals(usuario2.getNombre(), usuarioDireccion2.usuario.getNombre(), "El nombre del usuario 2 no coincide");
        assertEquals(direccion2.getCalle(), usuarioDireccion2.Direcciones.get(0).getCalle(), "La dirección de usuario 2 no coincide");

        UsuarioDireccion usuarioDireccion3 = (UsuarioDireccion) result.objects.get(2);
        assertEquals(usuario3.getNombre(), usuarioDireccion3.usuario.getNombre(), "El nombre del usuario 3 no coincide");
        assertEquals(direccion3.getCalle(), usuarioDireccion3.Direcciones.get(0).getCalle(), "La dirección de usuario 3 no coincide");
    }

    @Test
    public void testGetUsuario_Success() {
        int usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.setNombre("Carlos");

        // Configuración de la consulta de usuario
        when(entityManager.createQuery("FROM Usuario WHERE idUsuario =: idusuario", Usuario.class)).thenReturn(usuarioQuery);
        when(usuarioQuery.setParameter("idusuario", usuarioId)).thenReturn(usuarioQuery);
        when(usuarioQuery.getSingleResult()).thenReturn(usuario);

        // Llamada al método a probar
        Result result = iUsuarioJPADAOImplementation.GetUsuario(usuarioId);

        // Verificaciones de los resultados
        assertNotNull(result);
        assertTrue(result.isCorrect(), "La operación debería haber sido exitosa");
        assertNotNull(result.object, "El objeto no debería ser nulo");

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        assertEquals(usuario.getNombre(), usuarioDireccion.usuario.getNombre(), "El nombre del usuario no coincide");
    }

    @Test
    public void testGetUsuarioDirecciones_Success() {
        int usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioId);
        usuario.setNombre("Pedro");

        Direccion direccion = new Direccion();
        direccion.setIdDireccion(1);
        direccion.setCalle("Calle Secundaria");

        List<Direccion> direcciones = new ArrayList<>();
        direcciones.add(direccion);

        when(entityManager.createQuery("FROM Usuario WHERE idUsuario =: idusuario", Usuario.class)).thenReturn(usuarioQuery);
        when(usuarioQuery.setParameter("idusuario", usuarioId)).thenReturn(usuarioQuery);
        when(usuarioQuery.getSingleResult()).thenReturn(usuario);

        when(entityManager.createQuery("FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class)).thenReturn(queryDireccion);
        when(queryDireccion.setParameter("idusuario", usuarioId)).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(direcciones);

        Result result = iUsuarioJPADAOImplementation.GetUsuarioDirecciones(usuarioId);

        assertNotNull(result);
        assertTrue(result.isCorrect(), "La operación debería haber sido exitosa");
        assertNotNull(result.object, "El objeto no debería ser nulo");

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        assertEquals(usuario.getNombre(), usuarioDireccion.usuario.getNombre(), "El nombre del usuario no coincide");
        assertEquals(direccion.getCalle(), usuarioDireccion.Direcciones.get(0).getCalle(), "La dirección no coincide");
    }

    @Test
    public void testPostCargaMasiva_Success() {
        Usuario usuario = new Usuario();
        Direccion direccion = new Direccion();
        UsuarioDireccion ud = new UsuarioDireccion();
        ud.usuario = usuario;
        ud.Direccion = direccion;

        List<UsuarioDireccion> lista = Arrays.asList(ud);

        Result mockResult = new Result();
        mockResult.correct = true;

        doReturn(mockResult).when(iUsuarioJPADAOImplementation).PostAll(any());

        Result result = iUsuarioJPADAOImplementation.PostCargaMasiva(lista);

        Assertions.assertTrue(result.correct);
    }

    @Test
    public void testPostAll_Success() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        Direccion direccion = new Direccion();
        direccion.setIdDireccion(1);

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = usuario;
        usuarioDireccion.Direccion = direccion;

        Result result = iUsuarioJPADAOImplementation.PostAll(usuarioDireccion);

        Assertions.assertTrue(result.correct);
        verify(entityManager, times(1)).persist(usuario);
        verify(entityManager, times(1)).persist(direccion);
    }

    @Test
    public void testPutUsuario_Success() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = usuario;

        Result result = iUsuarioJPADAOImplementation.PutUsuario(usuarioDireccion);

        Assertions.assertTrue(result.correct);
        verify(entityManager).merge(usuario);
    }

    @Test
    public void testDELETEALL_Success() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        Direccion dir = new Direccion();
        dir.setIdDireccion(1);

        UsuarioDireccion ud = new UsuarioDireccion();
        ud.usuario = usuario;

        when(entityManager.find(Usuario.class, 1)).thenReturn(usuario);
        when(entityManager.createQuery(anyString(), eq(Direccion.class))).thenReturn(queryDireccion);
        when(queryDireccion.setParameter(eq("idUsuario"), eq(1))).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(Arrays.asList(dir));
        when(entityManager.find(Direccion.class, dir.getIdDireccion())).thenReturn(dir);

        Result result = iUsuarioJPADAOImplementation.DELETEALL(ud);

        Assertions.assertTrue(result.correct);
        verify(entityManager).remove(dir);
        verify(entityManager).remove(usuario);
    }

    @Test
    public void testPostDinamico_Success() {
        Usuario filtroUsuario = new Usuario();
        filtroUsuario.setNombre("juan");
        filtroUsuario.setApellidoPaterno("perez");

        UsuarioDireccion filtro = new UsuarioDireccion();
        filtro.usuario = filtroUsuario;

        Usuario usuario1 = new Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Juan");
        usuario1.setApellidoPaterno("Perez");

        List<Usuario> usuariosMock = List.of(usuario1);

        Direccion direccion1 = new Direccion();
        direccion1.setIdDireccion(1);
        direccion1.setCalle("Calle Falsa 123");
        List<Direccion> direccionesMock = List.of(direccion1);

        when(entityManager.createQuery(contains("SELECT u FROM Usuario"), eq(Usuario.class)))
                .thenReturn(usuarioQuery);
        when(usuarioQuery.setParameter(eq("nombre"), any())).thenReturn(usuarioQuery);
        when(usuarioQuery.setParameter(eq("apellidoPaterno"), any())).thenReturn(usuarioQuery);
        when(usuarioQuery.getResultList()).thenReturn(usuariosMock);

        when(entityManager.createQuery(contains("FROM Direccion"), eq(Direccion.class)))
                .thenReturn(queryDireccion);
        when(queryDireccion.setParameter(eq("idusuario"), eq(1))).thenReturn(queryDireccion);
        when(queryDireccion.getResultList()).thenReturn(direccionesMock);

        Result result = iUsuarioJPADAOImplementation.PostDinamico(filtro);

        assertTrue(result.correct);
        assertNotNull(result.objects);
        assertEquals(1, result.objects.size());

        UsuarioDireccion resultado = (UsuarioDireccion) result.objects.get(0);
        assertEquals("Juan", resultado.usuario.getNombre());
        assertEquals("Calle Falsa 123", resultado.Direcciones.get(0).getCalle());

        verify(entityManager, times(1)).createQuery(contains("SELECT u FROM Usuario"), eq(Usuario.class));
        verify(entityManager, times(1)).createQuery(contains("FROM Direccion"), eq(Direccion.class));
    }

}
