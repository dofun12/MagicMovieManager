package org.lemanoman.testeweb.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "episodio")
public class EpisodioModel {
    @EmbeddedId
    private EpisodioPK pk;

    @Column(name="id_mediafile",nullable=false)
    private Integer idMediafile;
    
    public EpisodioPK getPk() {
        return pk;
    }

    public void setPk(EpisodioPK pk) {
        this.pk = pk;
    }

    public Integer getIdMediafile() {
        return idMediafile;
    }

    public void setIdMediafile(Integer idMediafile) {
        this.idMediafile = idMediafile;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((idMediafile == null) ? 0 : idMediafile.hashCode());
	result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
	EpisodioModel other = (EpisodioModel) obj;
	if (idMediafile == null) {
	    if (other.idMediafile != null)
		return false;
	} else if (!idMediafile.equals(other.idMediafile))
	    return false;
	if (pk == null) {
	    if (other.pk != null)
		return false;
	} else if (!pk.equals(other.pk))
	    return false;
	return true;
    }

    
    
}
