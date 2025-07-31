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
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionJPADAOImplementation implements DireccionJPADAO {

    @Autowired
    EntityManager entityManager;

    @Override
    public Result GetDireccion(int IdDireccion) {
        Result result = new Result();

        try {
            TypedQuery<Direccion> direccionQuery = entityManager.createQuery(
                    "FROM Direccion d WHERE d.IdDireccion = :iddireccion", Direccion.class);
            direccionQuery.setParameter("iddireccion", IdDireccion);

            Direccion direccion = direccionQuery.getSingleResult();

            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            usuarioDireccion.Direccion = direccion;

            result.object = usuarioDireccion;
          

            result.correct = true;
        } catch (Exception ex) {
            result.objects = null;
            result.errorMessage = ex.getLocalizedMessage();
            result.correct = false;
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result POSTDireccionByIdUsuario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            usuarioDireccion.Direccion.usuario.setIdUsuario(usuarioDireccion.Direccion.usuario.getIdUsuario());
            entityManager.persist(usuarioDireccion.Direccion);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }
        return result;
    }

    @Transactional
    @Override
    public Result PUTDireccionByUsuario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {

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
    public Result DELETEDireccionByUsuario(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            int idDireccion = usuarioDireccion.Direccion.getIdDireccion();

            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            entityManager.remove(direccion);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
