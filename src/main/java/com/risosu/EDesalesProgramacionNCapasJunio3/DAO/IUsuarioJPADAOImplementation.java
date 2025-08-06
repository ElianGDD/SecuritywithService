package com.risosu.EDesalesProgramacionNCapasJunio3.DAO;

import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Colonia;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Direccion;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.LoginRequest;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Roll;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Usuario;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.Result;
import com.risosu.EDesalesProgramacionNCapasJunio3.JPA.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class IUsuarioJPADAOImplementation implements IUsuarioJPADAO {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    JWTService jwtService;

    @Override
    public Result GetAll() {
        Result result = new Result();
        result.objects = new ArrayList<>();
        try {
            TypedQuery<Usuario> usuarioQuery = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usariosJPA = usuarioQuery.getResultList();

            for (Usuario usuario : usariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = usuario;

                TypedQuery<Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());
                List<Direccion> direccionesUsuarios = queryDireccion.getResultList();

                if (direccionesUsuarios.size() != 0) {

                    usuarioDireccion.Direcciones = direccionesUsuarios;
                }
                result.objects.add(usuarioDireccion);
            }
            result.correct = true;

        } catch (Exception ex) {
            result.objects = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;

    }

    @Override
    public Result GetUsuarioDirecciones(int IdUsuario) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> usarioQuery = entityManager.createQuery("FROM Usuario WHERE idUsuario =: idusuario", Usuario.class);
            usarioQuery.setParameter("idusuario", IdUsuario);
            Usuario usuarioJPA = usarioQuery.getSingleResult();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.usuario = usuarioJPA;

            TypedQuery<Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE usuario.idUsuario = :idusuario", Direccion.class);
            queryDireccion.setParameter("idusuario", usuarioJPA.getIdUsuario());
            List<Direccion> direccionesUsuarios = queryDireccion.getResultList();

            if (direccionesUsuarios.size() != 0) {
                usuarioDireccion.Direcciones = direccionesUsuarios;

            }
            result.object = usuarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.object = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetUsuario(int IdUsuario) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> usarioQuery = entityManager.createQuery("FROM Usuario WHERE idUsuario =: idusuario", Usuario.class);
            usarioQuery.setParameter("idusuario", IdUsuario);
            Usuario usarioJPA = usarioQuery.getSingleResult();

            UsuarioDireccion usarioDireccion = new UsuarioDireccion();
            usarioDireccion.usuario = usarioJPA;
            result.object = usarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.object = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result PostDinamico(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        result.objects = new ArrayList<>();

        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u LEFT JOIN u.Roll r WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            if (usuarioDireccion.usuario != null) {
                Usuario usuario = usuarioDireccion.usuario;

                if (usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()) {
                    jpql.append(" AND UPPER(u.nombre) LIKE :nombre");
                    parameters.put("nombre", "%" + usuario.getNombre().toUpperCase() + "%");
                }

                if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().trim().isEmpty()) {
                    jpql.append(" AND UPPER(u.apellidoPaterno) LIKE :apellidoPaterno");
                    parameters.put("apellidoPaterno", "%" + usuario.getApellidoPaterno().toUpperCase() + "%");
                }

                if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().trim().isEmpty()) {
                    jpql.append(" AND UPPER(u.apellidoMaterno) LIKE :apellidoMaterno");
                    parameters.put("apellidoMaterno", "%" + usuario.getApellidoMaterno().toUpperCase() + "%");
                }

                if (usuario.roll != null && usuario.roll.getNombre() != null && !usuario.roll.getNombre().trim().isEmpty()) {
                    jpql.append(" AND UPPER(r.nombre) = :rol");
                    parameters.put("rol", usuario.roll.getNombre().toUpperCase());
                }
            }

            TypedQuery<Usuario> usuarioQuery = entityManager.createQuery(jpql.toString(), Usuario.class);
            parameters.forEach(usuarioQuery::setParameter);

            List<Usuario> usuariosFiltrados = usuarioQuery.getResultList(); // ← AQUÍ se filtran los usuarios

            for (Usuario usuario : usuariosFiltrados) {
                UsuarioDireccion usuarioDireccionResult = new UsuarioDireccion();
                usuarioDireccionResult.usuario = usuario;

                TypedQuery<Direccion> queryDireccion = entityManager.createQuery(
                        "FROM Direccion d LEFT JOIN FETCH d.Colonia c "
                        + "LEFT JOIN FETCH c.Municipio m "
                        + "LEFT JOIN FETCH m.Estado e "
                        + "LEFT JOIN FETCH e.Pais p "
                        + "WHERE d.Usuario.idUsuario = :idusuario", Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<Direccion> direcciones = queryDireccion.getResultList();
                usuarioDireccionResult.Direcciones = direcciones;

                result.objects.add(usuarioDireccionResult); // ← AQUÍ se agregan los resultados finales
            }

            result.correct = true;

        } catch (Exception ex) {
            result.objects = null;
            result.errorMessage = ex.getMessage();
            result.correct = false;
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result PostAll(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            String rawPassword = usuarioDireccion.usuario.getPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            usuarioDireccion.usuario.setPassword(encodedPassword);

            entityManager.persist(usuarioDireccion.usuario);

            Direccion direccion = usuarioDireccion.Direccion;
            direccion.setUsuario(usuarioDireccion.usuario);
            entityManager.persist(direccion);

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result PostCargaMasiva(List<UsuarioDireccion> usuariosDireccions) {
        Result result = new Result();
        try {

            for (UsuarioDireccion usuarioDireccion : usuariosDireccions) {
                this.PostAll(usuarioDireccion);
            }

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result PutAll(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {

            entityManager.merge(usuarioDireccion.usuario);
            usuarioDireccion.Direccion.usuario = new Usuario();
            usuarioDireccion.Direccion.usuario.setIdUsuario(usuarioDireccion.usuario.getIdUsuario());
            entityManager.merge(usuarioDireccion.Direccion);

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }

        return result;

    }

    @Transactional
    @Override
    public Result PutUsuario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            entityManager.merge(usuarioDireccion.usuario);

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result PutStatus(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {

            entityManager.merge(usuarioDireccion.usuario);

            result.correct = true;
        } catch (Exception ex) {
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }

        return result;

    }

    @Transactional
    @Override
    public Result DELETEALL(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            int idUsuario = usuarioDireccion.usuario.getIdUsuario();

            Usuario usuario = entityManager.find(Usuario.class, idUsuario);

            List<Direccion> direcciones = entityManager.createQuery(
                    "FROM Direccion d WHERE usuario.idUsuario = :idUsuario", Direccion.class)
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();

            for (Direccion direccion : direcciones) {
                Direccion direccionGestionada = entityManager.find(Direccion.class, direccion.getIdDireccion());
                if (direccionGestionada != null) {
                    entityManager.remove(direccionGestionada);

                }
            }

            entityManager.remove(usuario);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetUsuarioByNombre(String Nombre) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> usarioQuery = entityManager.createQuery("FROM Usuario WHERE userName =: username", Usuario.class);
            usarioQuery.setParameter("username", Nombre);
            Usuario usarioJPA = usarioQuery.getSingleResult();

            UsuarioDireccion usarioDireccion = new UsuarioDireccion();
            usarioDireccion.usuario = usarioJPA;
            result.object = usarioDireccion;

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.object = null;
        }
        return result;
    }

    @Override
    public Result Login(LoginRequest loginRequest) {
        Result result = new Result();
        try {
            TypedQuery<Usuario> usarioQuery = entityManager.createQuery("FROM Usuario WHERE userName =: username", Usuario.class);
            usarioQuery.setParameter("username", loginRequest.getUserName());

            Usuario usuario = usarioQuery.getSingleResult();

            if (usuario == null || !passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
                result.correct = false;
                result.errorMessage = "Usuario o contraseña incorrectos";
                return result;
            }

            result.object = usuario;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.ex = ex;
            result.errorMessage = ex.getLocalizedMessage();
        }

        return result;
    }

}
