package org.lemanoman.testeweb.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mediafile")
public class MediaFileModel {
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    
    @Column
    private String name;
    
    @Column
    private String path;
    
    @Column
    private Long size;
    
    @Column
    private Long duration;
    
    @Column
    private Date dateAdded;
    
    @Column(name="id_tipomedia")
    private Integer idTipomedia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getIdTipomedia() {
        return idTipomedia;
    }

    public void setIdTipomedia(Integer idTipomedia) {
        this.idTipomedia = idTipomedia;
    }
    
    
}
