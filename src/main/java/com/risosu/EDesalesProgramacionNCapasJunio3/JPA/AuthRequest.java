package com.risosu.EDesalesProgramacionNCapasJunio3.JPA;

public class AuthRequest {
    private String nombre;
    private String password;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
