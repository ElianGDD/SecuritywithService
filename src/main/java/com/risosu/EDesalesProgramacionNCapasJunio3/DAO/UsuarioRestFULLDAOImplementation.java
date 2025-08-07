/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Alien 13
 */
@Repository
public class UsuarioRestFULLDAOImplementation implements UsuarioRestFullDAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<UsuarioDireccion> getAllUsuarios() {
        List<UsuarioDireccion> lista = new ArrayList<>();

        TypedQuery<Usuario> usuarioQuery = entityManager.createQuery("FROM Usuario", Usuario.class);
        List<Usuario> usuarios = usuarioQuery.getResultList();

        for (Usuario usuario : usuarios) {
            UsuarioDireccion ud = new UsuarioDireccion();
            ud.setUsuario(usuario);

            TypedQuery<Direccion> direccionQuery = entityManager.createQuery(
                    "FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class);
            direccionQuery.setParameter("idusuario", usuario.getIdUsuario());
            List<Direccion> direcciones = direccionQuery.getResultList();

            if (!direcciones.isEmpty()) {
                ud.setDirecciones(direcciones);
            }

            lista.add(ud);
        }

        return lista;
    }

    @Override
    public UsuarioDireccion getUsuarioDirecciones(int idUsuario) {
        try {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            TypedQuery<Direccion> query = entityManager.createQuery(
                    "FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class);
            query.setParameter("idusuario", idUsuario);
            List<Direccion> direcciones = query.getResultList();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.setUsuario(usuario);
            usuarioDireccion.setDirecciones(direcciones);

            return usuarioDireccion; 
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", ex);
        }
    }

    @Override
    public Usuario getUsuario(int idUsuario) {
        try {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            return usuario; // Devuelves el objeto Usuario
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", ex);
        }
    }

    @Override
    public UsuarioDireccion createUsuario(UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getUsuario() == null) {
                throw new IllegalArgumentException("El usuario no puede ser nulo");
            }

            entityManager.persist(usuarioDireccion.getUsuario());

            if (usuarioDireccion.getDirecciones() != null) {
                for (Direccion direccion : usuarioDireccion.getDirecciones()) {
                    direccion.setUsuario(usuarioDireccion.getUsuario());
                    entityManager.persist(direccion);
                }
            }

            return usuarioDireccion; 
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al crear usuario", ex);
        }
    }

    @Override
    public UsuarioDireccion updateUsuario(int idUsuario, UsuarioDireccion usuarioDireccion) {
        try {
            Usuario usuarioExistente = entityManager.find(Usuario.class, idUsuario);
            if (usuarioExistente == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            usuarioExistente.setNombre(usuarioDireccion.getUsuario().getNombre());
            usuarioExistente.setApellidoPaterno(usuarioDireccion.getUsuario().getApellidoPaterno());
            usuarioExistente.setApellidoMaterno(usuarioDireccion.getUsuario().getApellidoMaterno());

            entityManager.merge(usuarioExistente);

            if (usuarioDireccion.getDirecciones() != null) {
                for (Direccion direccion : usuarioDireccion.getDirecciones()) {
                    direccion.setUsuario(usuarioExistente);
                    entityManager.merge(direccion); // Actualizamos las direcciones
                }
            }

            return usuarioDireccion; 
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno al actualizar usuario", ex);
        }
    }

    @Override
    public void deleteUsuario(int idUsuario) {
        try {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            List<Direccion> direcciones = entityManager.createQuery(
                    "FROM Direccion WHERE usuario.idUsuario = :idUsuario", Direccion.class)
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();
            for (Direccion direccion : direcciones) {
                entityManager.remove(direccion);
            }

            entityManager.remove(usuario);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar usuario", ex);
        }
    }

    @Override
    public void createUsuariosMasivos(List<UsuarioDireccion> usuariosDireccions) {
        try {
            for (UsuarioDireccion usuarioDireccion : usuariosDireccions) {
                this.createUsuario(usuarioDireccion);
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear usuarios masivos", ex);
        }
    }

    @Override
    public void updateUsuarioStatus(int idUsuario, UsuarioDireccion usuarioDireccion) {
        try {
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new EntityNotFoundException("Usuario no encontrado");
            }

            usuario.setStatus(usuario.getStatus());

            entityManager.merge(usuario);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado", e);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el estado", ex);
        }
    }

}
