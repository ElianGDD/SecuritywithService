
package com.risosu.EDesalesProgramacionNCapasJunio3.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Roll {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idroll")
    private int idRoll;
    @Column(name = "nombre")
    private String nombre;

    public int getIdRoll() {
        return idRoll;
    }

    public void setIdRoll(int idRoll) {
        this.idRoll = idRoll;
    }

 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
 
    
    
}
