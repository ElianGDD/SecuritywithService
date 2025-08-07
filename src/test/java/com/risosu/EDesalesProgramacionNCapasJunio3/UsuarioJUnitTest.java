/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.IUsuarioJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alien 13
 */
@SpringBootTest
public class UsuarioJUnitTest {

    @Autowired
    IUsuarioJPADAOImplementation iUsuarioJPADAOImplementation;

    @Autowired
    EntityManager entityManager;

    @Test
    public void GetAll_OK() {
        Result result = iUsuarioJPADAOImplementation.GetAll();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que el método GetAll fuera exitoso");
        Assertions.assertNull(result.ex, "No debería haber excepción");
        Assertions.assertNotNull(result.objects, "La lista de objetos no debería ser null");
    }

    @Test
    public void testGetUsuarioByNombre_Success() {
        String username = "Boligrafo";
        Result result = iUsuarioJPADAOImplementation.GetUsuarioByNombre(username);
        assertTrue(result.correct);
        assertNotNull(result.object);
    }

    @Test
    public void GetUsuarioDireccionesExistente() {
        int idUsuarioExistente = 61;

        Result result = iUsuarioJPADAOImplementation.GetUsuarioDirecciones(idUsuarioExistente);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.object, "Debe devolver un objeto UsuarioDireccion");
    }

    @Test
    public void GetUsuarioDireccionesNoExistente() {
        int idUsuarioNoExistente = -1;

        Result result = iUsuarioJPADAOImplementation.GetUsuarioDirecciones(idUsuarioNoExistente);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.correct, "Se esperaba que result.correct fuera false");
        Assertions.assertNull(result.object, "El objeto debe ser null si no existe");
        Assertions.assertNotNull(result.ex, "Debe haber una excepción");
    }

    @Test
    public void testGetUsuarioExistente() {
        int idUsuario = 61;

        Result result = iUsuarioJPADAOImplementation.GetUsuario(idUsuario);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct, "Se esperaba que result.correct fuera true");
        Assertions.assertNotNull(result.object, "Debe retornar un UsuarioDireccion");
    }

    @Test
    public void testGetUsuarioNoExistente() {
        int idUsuario = -1;

        Result result = iUsuarioJPADAOImplementation.GetUsuario(idUsuario);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.correct, "Se esperaba que result.correct fuera false");
        Assertions.assertNull(result.object, "No debe retornar ningún objeto");
        Assertions.assertNotNull(result.ex, "Debe existir una excepción");
    }

    @Test
    public void testPostAll_Success() throws ParseException {

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = new Usuario();
        usuarioDireccion.usuario.setNombre("GTest");
        usuarioDireccion.usuario.setApellidoPaterno("Unitario1");
        usuarioDireccion.usuario.setApellidoMaterno("JUnit1");
        usuarioDireccion.usuario.setFechaNacimiento(new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"));
        usuarioDireccion.usuario.setUserName("DesarrolloEG");
        usuarioDireccion.usuario.setEmail("tessst@example.com");
        usuarioDireccion.usuario.setPassword("1234");
        usuarioDireccion.usuario.setSexo('M');
        usuarioDireccion.usuario.setTelefono("55512345321");
        usuarioDireccion.usuario.setCelular("5559876532");
        usuarioDireccion.usuario.setCurp("TEST900101HMNXXX12");

        usuarioDireccion.usuario.roll = new Roll();
        usuarioDireccion.usuario.roll.setIdRoll(2);
        usuarioDireccion.usuario.setImagenPerfil(null);
        usuarioDireccion.usuario.setStatus(1);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Calle pruebas");
        usuarioDireccion.Direccion.setNumeroExterior("222");
        usuarioDireccion.Direccion.setNumeroInterior("000");

        Result result = iUsuarioJPADAOImplementation.PostAll(usuarioDireccion);

        assertTrue(result.isCorrect(), "Debería ser exitoso");
        assertNull(result.getErrorMessage(), "No debería haber mensaje de error");

        Usuario usuarioPersistido = entityManager.find(Usuario.class, usuarioDireccion.usuario.getIdUsuario());
        assertNotNull(usuarioPersistido, "El usuario debería estar persistido");
        assertEquals("Test", usuarioPersistido.getNombre(), "El nombre no coincide");
    }

    @Test
    public void testPostCargaMasiva_Success() throws ParseException {
        List<UsuarioDireccion> usuarios = new ArrayList<>();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 1; i <= 3; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre("Usuario " + i);
            usuario.setFechaNacimiento(formatoFecha.parse("1990-01-0" + i));
            usuario.setUserName("usuario" + i);
            usuario.setApellidoPaterno("ApellidoPaterno" + i);
            usuario.setApellidoMaterno("ApellidoMaterno" + i);
            usuario.setEmail("usuario" + i + "@example.com");
            usuario.setPassword("Password" + i);
            usuario.setSexo('M');
            usuario.setTelefono("555000000" + i);
            usuario.setCelular("555999999" + i);
            usuario.setCurp("CURP000000" + i);

            Roll roll = new Roll();
            roll.setIdRoll(1);
            usuario.setRoll(roll);

            usuario.setImagenPerfil(null);
            usuario.setStatus(1);

            Direccion direccion = new Direccion();
            direccion.setCalle("Calle " + i);
            direccion.setNumeroInterior("Interior " + i);
            direccion.setNumeroExterior("Exterior " + i);

            Colonia colonia = new Colonia();
            colonia.setIdColonia(1);
            direccion.setColonia(colonia);

            direccion.setUsuario(usuario);

            UsuarioDireccion ud = new UsuarioDireccion();
            ud.setUsuario(usuario);
            ud.setDireccion(direccion);

            usuarios.add(ud);
        }

        Result result = iUsuarioJPADAOImplementation.PostCargaMasiva(usuarios);

        assertTrue(result.isCorrect(), "La carga masiva debería ser exitosa");
        assertNull(result.getErrorMessage(), "No debería haber mensaje de error");
    }

    @Test
    public void PutAll_Success() {
        int idUsuarioExistente = 324;
        Usuario usuarioExistente = entityManager.find(Usuario.class, idUsuarioExistente);
        assertNotNull(usuarioExistente, "El usuario con id " + idUsuarioExistente + " no existe en la base");

        Direccion direccionExistente = entityManager.createQuery(
                "SELECT d FROM Direccion d WHERE d.usuario.idUsuario = :id", Direccion.class)
                .setParameter("id", idUsuarioExistente)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró dirección para el usuario"));

        usuarioExistente.setNombre("Modificado");
        direccionExistente.setCalle("Modificada");

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = usuarioExistente;
        usuarioDireccion.Direccion = direccionExistente;

        Result result = iUsuarioJPADAOImplementation.PutAll(usuarioDireccion);
        assertTrue(result.isCorrect(), "La operación debería ser exitosa");

        Usuario usuarioActualizado = entityManager.find(Usuario.class, idUsuarioExistente);
        assertEquals("Modificado", usuarioActualizado.getNombre());

        Direccion direccionActualizada = entityManager.find(Direccion.class, direccionExistente.getIdDireccion());
        assertEquals("Modificada", direccionActualizada.getCalle());
    }

    @Test
    public void testPutStatus_Success() {

        Usuario usuario = new Usuario();
        usuario.setStatus(0);
        entityManager.persist(usuario);
        entityManager.flush();

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = new Usuario();
        usuarioDireccion.usuario.setIdUsuario(usuario.getIdUsuario());
        usuarioDireccion.usuario.setStatus(1);

        Result result = iUsuarioJPADAOImplementation.PutStatus(usuarioDireccion);

        assertTrue(result.isCorrect(), "La operación debería ser exitosa");
        assertNull(result.getErrorMessage(), "No debería haber mensaje de error");

        Usuario usuarioActualizado = entityManager.find(Usuario.class, usuario.getIdUsuario());
        assertEquals(1, usuarioActualizado.getStatus(), "El status debería haber cambiado a 1 (activo)");
    }

    @Test
    public void testPutUsuario_Success() {
        int usuarioId = 341;

        Usuario usuarioExistente = entityManager.find(Usuario.class, usuarioId);
        assertNotNull(usuarioExistente, "El usuario con id " + usuarioId + " debería existir en la base de datos");

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = new Usuario();

        usuarioDireccion.usuario.setIdUsuario(usuarioId);
        usuarioDireccion.usuario.setNombre("Mario");
        usuarioDireccion.usuario.setApellidoPaterno("Gomez");
        usuarioDireccion.usuario.setApellidoMaterno("Ruiz");
        usuarioDireccion.usuario.setEmail("carlos.gomez@mail.com");
        usuarioDireccion.usuario.setPassword("newPassword123");
        usuarioDireccion.usuario.setFechaNacimiento(Date.valueOf("1990-01-01"));
        usuarioDireccion.usuario.setUserName("carlosgomez");
        usuarioDireccion.usuario.setSexo('M');
        usuarioDireccion.usuario.setTelefono("1234567890");
        usuarioDireccion.usuario.setCelular("0987654321");
        usuarioDireccion.usuario.setCurp("GOMC900101HDFLRS09");
        usuarioDireccion.usuario.setImagenPerfil(null);
        usuarioDireccion.usuario.setStatus(1);

        Result result = iUsuarioJPADAOImplementation.PutUsuario(usuarioDireccion);

        assertTrue(result.isCorrect(), "La actualización debería haber sido exitosa");
        assertNull(result.getErrorMessage(), "No debería haber mensaje de error");

        Usuario usuarioActualizado = entityManager.find(Usuario.class, usuarioId);
        assertNotNull(usuarioActualizado, "El usuario actualizado debería existir en la base de datos");
        assertEquals("Mario", usuarioActualizado.getNombre(), "El nombre no fue actualizado correctamente");
        assertEquals("Gomez", usuarioActualizado.getApellidoPaterno(), "El apellido paterno no fue actualizado correctamente");
        assertEquals("Ruiz", usuarioActualizado.getApellidoMaterno(), "El apellido materno no fue actualizado correctamente");
        assertEquals("carlos.gomez@mail.com", usuarioActualizado.getEmail(), "El correo electrónico no fue actualizado correctamente");
        assertEquals("newPassword123", usuarioActualizado.getPassword(), "La contraseña no fue actualizada correctamente");
        assertEquals(Date.valueOf("1990-01-01"), usuarioActualizado.getFechaNacimiento(), "La fecha de nacimiento no fue actualizada correctamente");
        assertEquals("carlosgomez", usuarioActualizado.getUserName(), "El nombre de usuario no fue actualizado correctamente");
        assertEquals('M', usuarioActualizado.getSexo(), "El sexo no fue actualizado correctamente");
        assertEquals("1234567890", usuarioActualizado.getTelefono(), "El teléfono no fue actualizado correctamente");
        assertEquals("0987654321", usuarioActualizado.getCelular(), "El celular no fue actualizado correctamente");
        assertEquals("GOMC900101HDFLRS09", usuarioActualizado.getCurp(), "El CURP no fue actualizado correctamente");
        assertEquals(null, usuarioActualizado.getImagenPerfil(), "La imagen de perfil no fue actualizada correctamente");
        assertEquals(1, usuarioActualizado.getStatus(), "El estado del usuario no fue actualizado correctamente");
    }

    @Test
    public void testDeleteAll_Success() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = new Usuario();
        usuarioDireccion.usuario.setIdUsuario(361);

        Result result = iUsuarioJPADAOImplementation.DELETEALL(usuarioDireccion);

        assertTrue(result.isCorrect(), "Debería ser exitoso");
        assertNull(result.getErrorMessage(), "No debería haber mensaje de error");

    }

    @Test
    public void testDeleteAll_NonExistentUser_ShouldFail() {
        // Arrange
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.usuario = new Usuario();
        usuarioDireccion.usuario.setIdUsuario(-1); // ID que no existe

        // Act
        Result result = iUsuarioJPADAOImplementation.DELETEALL(usuarioDireccion);

        // Assert
        assertFalse(result.isCorrect(), "Debería fallar");
        assertNotNull(result.getErrorMessage(), "Debería tener mensaje de error");
    }

    @Test
    public void testPostDinamico_Success() {
        UsuarioDireccion filtro = new UsuarioDireccion();
        filtro.usuario = new Usuario();
        filtro.usuario.setNombre("a");

        Result result = iUsuarioJPADAOImplementation.PostDinamico(filtro);

        assertTrue(result.isCorrect(), "Debería ser exitoso");
        assertNotNull(result.getObjects(), "Debería devolver una lista de resultados");
    }

}
