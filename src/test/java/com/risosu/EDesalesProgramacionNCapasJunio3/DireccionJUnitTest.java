package com.risosu.EDesalesProgramacionNCapasJunio3;

import com.risosu.EDesalesProgramacionNCapasJunio3.DAO.DireccionJPADAOImplementation;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


// Para que los cambios en la BD se reviertan después del test
@SpringBootTest
public class DireccionJUnitTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    DireccionJPADAOImplementation direccionJPADAOImplementation;

    @Test
    public void testGetDireccionExistente() {
        int idDireccionExistente = 201;

        Result result = direccionJPADAOImplementation.GetDireccion(idDireccionExistente);

        assertNotNull(result, "El resultado no debería ser nulo");
        assertTrue(result.isCorrect(), "La operación debería ser exitosa");
        assertNotNull(result.getObject(), "Debería contener un objeto UsuarioDireccion");

        if (result.getObject() != null) {
            assertTrue(result.getObject() instanceof UsuarioDireccion,
                    "El objeto debería ser de tipo UsuarioDireccion");
        }
    }

    @Test
    public void PostDireccion() {
        Usuario usuarioReal = entityManager.find(Usuario.class, 61);
        assertNotNull(usuarioReal, "El usuario no existe");

        Colonia coloniaReal = entityManager.find(Colonia.class, 7);
        assertNotNull(coloniaReal, "La colonia no existe");

        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        Direccion nuevaDireccion = new Direccion();

        nuevaDireccion.setCalle("Flores");
        nuevaDireccion.setNumeroInterior("132");
        nuevaDireccion.setNumeroExterior("117");
        nuevaDireccion.setColonia(coloniaReal);
        nuevaDireccion.setUsuario(usuarioReal);
        usuarioDireccion.Direccion = nuevaDireccion;

        Result result = direccionJPADAOImplementation.POSTDireccionByIdUsuario(usuarioDireccion);
        assertTrue(result.isCorrect());
        assertNull(result.getErrorMessage());

       
      }

    @Test
    public void TestDeleteDireccion() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Direccion = new Direccion();

        usuarioDireccion.Direccion.setIdDireccion(181);
        Result result = direccionJPADAOImplementation.DELETEDireccionByUsuario(usuarioDireccion);
        assertTrue(result.isCorrect());
        assertNull(result.getErrorMessage());
    }

}
