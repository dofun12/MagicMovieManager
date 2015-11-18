package org.lemanoman.testeweb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HistoricoPK implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Column
    private String episodio;
    
    @Column
    private Integer idSerie;
    
    
    public String getEpisodio() {
        return episodio;
    }
    public void setEpisodio(String episodio) {
        this.episodio = episodio;
    }
    public Integer getIdSerie() {
        return idSerie;
    }
    public void setIdSerie(Integer idSerie) {
        this.idSerie = idSerie;
    }
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((episodio == null) ? 0 : episodio.hashCode());
	result = prime * result + ((idSerie == null) ? 0 : idSerie.hashCode());
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
	HistoricoPK other = (HistoricoPK) obj;
	if (episodio == null) {
	    if (other.episodio != null)
		return false;
	} else if (!episodio.equals(other.episodio))
	    return false;
	if (idSerie == null) {
	    if (other.idSerie != null)
		return false;
	} else if (!idSerie.equals(other.idSerie))
	    return false;
	return true;
    }
    
}