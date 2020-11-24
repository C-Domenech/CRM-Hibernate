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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Cristina Domenech y Javier Torres Sevilla
 */
public class Oportunidad implements Serializable {

    private Integer id_oportunidad;
    private String descripcion;
    private BigDecimal valor;
    private Date fecha;
    private String nivel;
    private String estado;

    private models.Cliente cliente;

    private Set<models.Actividad> actividades;

    /**
     *
     */
    public Oportunidad() {
    }

    /**
     *
     * @param cliente
     * @param descripcion
     * @param valor
     * @param fecha
     * @param nivel
     */
    public Oportunidad(models.Cliente cliente, String descripcion, BigDecimal valor, Date fecha, String nivel) {
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.valor = valor;
        this.fecha = fecha;
        this.nivel = nivel;
        this.estado = "NUEVA";
        this.actividades = new HashSet(0);

    }

    /**
     *
     * @return
     */
    public Integer getId_oportunidad() {
        return id_oportunidad;
    }

    /**
     *
     * @param id_oportunidad
     */
    public void setId_oportunidad(Integer id_oportunidad) {
        this.id_oportunidad = id_oportunidad;
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
    public BigDecimal getValor() {
        return valor;
    }

    /**
     *
     * @param valor
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    /**
     *
     * @return
     */
    public String getNivel() {
        return nivel;
    }

    /**
     *
     * @param nivel
     */
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    /**
     *
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     *
     * @param cliente
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     *
     * @return
     */
    public Set<Actividad> getActividades() {
        return actividades;
    }

    /**
     *
     * @param actividades
     */
    public void setActividades(Set<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id_oportunidad);
        hash = 97 * hash + Objects.hashCode(this.cliente);
        hash = 97 * hash + Objects.hashCode(this.descripcion);
        hash = 97 * hash + Objects.hashCode(this.valor);
        hash = 97 * hash + Objects.hashCode(this.fecha);
        hash = 97 * hash + Objects.hashCode(this.nivel);
        hash = 97 * hash + Objects.hashCode(this.estado);
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
        final Oportunidad other = (Oportunidad) obj;
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.nivel, other.nivel)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.id_oportunidad, other.id_oportunidad)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "> Id: " + id_oportunidad + "\n  - Descripcion: " + descripcion + "\n  - Valor: " + valor + " €\n  - Fecha de vencimiento: " + fecha + "\n  - Prioridad: " + nivel + "\n  - Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + " - Empresa: " + cliente.getEmpresa() + "\n  - Estado: " + estado;
    }

}
