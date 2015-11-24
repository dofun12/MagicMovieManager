package org.lemanoman.testeweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EpisodioPK implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Column
    private Integer temporada;
    
    @Column
    private String name;
    
    @Column(name="id_serie")
    private Integer idSerie;

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((idSerie == null) ? 0 : idSerie.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((temporada == null) ? 0 : temporada.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EpisodioPK other = (EpisodioPK) obj;
	if (idSerie == null) {
	    if (other.idSerie != null)
		return false;
	} else if (!idSerie.equals(other.idSerie))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (temporada == null) {
	    if (other.temporada != null)
		return false;
	} else if (!temporada.equals(other.temporada))
	    return false;
	return true;
    }

    
    
    
    
}
