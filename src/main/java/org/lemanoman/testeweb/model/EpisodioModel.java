package org.lemanoman.testeweb.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "episodio")
public class EpisodioModel {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Column(name="id_serie",nullable=false)
    private Integer idSerie;

    @Column
    private String name;
    
    @Column(name="id_mediafile",nullable=false)
    private Integer idMediafile;
    
    public Integer getIdMediafile() {
        return idMediafile;
    }

    public void setIdMediafile(Integer idMediafile) {
        this.idMediafile = idMediafile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((idMediafile == null) ? 0 : idMediafile.hashCode());
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
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (idMediafile == null) {
	    if (other.idMediafile != null)
		return false;
	} else if (!idMediafile.equals(other.idMediafile))
	    return false;
	return true;
    }

    

    
    
}
