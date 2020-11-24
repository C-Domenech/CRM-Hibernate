/*
 * Copyright (C) 2020 Cristina Domenech <linkedin.com/in/c-domenech/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class Actividad implements Serializable{
    private Integer id_actividad;
    private models.Oportunidad oportunidad;
    private String tipo;
    private String descripcion;
    private Date fecha;

    /**
     *
     */
    public Actividad() {
    }

    /**
     *
     * @param oportunidad
     * @param tipo
     * @param descripcion
     * @param fecha
     */
    public Actividad(models.Oportunidad oportunidad, String tipo, String descripcion, Date fecha) {
        this.oportunidad = oportunidad;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    
    /**
     *
     * @return
     */
    public Integer getId_actividad() {
        return id_actividad;
    }

    /**
     *
     * @param id_actividad
     */
    public void setId_actividad(Integer id_actividad) {
        this.id_actividad = id_actividad;
    }

    /**
     *
     * @return
     */
    public Oportunidad getOportunidad() {
        return oportunidad;
    }

    /**
     *
     * @param oportunidad
     */
    public void setOportunidad(Oportunidad oportunidad) {
        this.oportunidad = oportunidad;
    }

    /**
     *
     * @return
     */
    public String getTipo() {
        return tipo;
    }

    /**
     *
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id_actividad);
        hash = 61 * hash + Objects.hashCode(this.oportunidad);
        hash = 61 * hash + Objects.hashCode(this.tipo);
        hash = 61 * hash + Objects.hashCode(this.descripcion);
        hash = 61 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actividad other = (Actividad) obj;
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id_actividad, other.id_actividad)) {
            return false;
        }
        if (!Objects.equals(this.oportunidad, other.oportunidad)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "> Id: " + id_actividad + "\n  - Tipo: " + tipo + "\n  - Descripción: " + descripcion + "\n  - Fecha: " + fecha + "\n  - Oportunidad: " + oportunidad.getDescripcion();
    }

    
}
