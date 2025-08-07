package com.risosu.EDesalesProgramacionNCapasJunio3.JPA;

import java.util.List;


public class UsuarioDireccion {
    public Usuario usuario;
    public Direccion Direccion;
    public List<Direccion> Direcciones;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Direccion getDireccion() {
        return Direccion;
    }

    public void setDireccion(Direccion Direccion) {
        this.Direccion = Direccion;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    
    
}
