package org.klisho.crawler.HibernateClass;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
//import org.postgis.Point;
//import org.postgis.Polygon;
import com.vividsolutions.jts.geom.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Ola-Mola on 08/03/17.
 */

@Entity
@Table( name = "photoFolder" )

public class PhotoFolder {

    @Id
    @Generated(value= GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folderId;
    private String folderPath;

    @Enumerated(EnumType.ORDINAL)
    private PhotoType photoType;

    public  enum PhotoType {
        RGB,
        RAW,
        NDVI,
        PSEUDO_RGB,
        THERMO
    }
    private Polygon extend;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PSproject> psProjects = new ArrayList<>();


    @OneToMany (mappedBy = "folder")
    private List<Photo> photos = new ArrayList<>();
// maybe not needed in this class
    private Double area;
    private boolean scannedFlag;
    private LocalDate scanDate;
    private Integer photoNum;

    public PhotoFolder() {
        // this form used by Hibernate
    }

    public PhotoFolder(String folderPath, PhotoType photoType, List<PSproject> pSprojects, Polygon extend,
                       Double area, boolean scannedFlag, LocalDate scanDate, Integer photoNum) {
        // for application use, to create new events

        this.folderPath = folderPath;
        this.photoType = photoType;
        this.extend = extend;
        this.area = area;
        this.scannedFlag = scannedFlag;
        this.scanDate = scanDate;

        this.photoNum = photoNum;
        this.psProjects = psProjects;

    }


   public Long getId() {
        return folderId;
    }

    private void setId(Long id) {
        this.folderId = id;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public Double getArea() {return area;}

    public void setArea(Double area) {this.area = area;}

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public Polygon getExtend() {return extend; }

    public void setExtend(Polygon extend) {this.extend = extend; }


    public void setFolderPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    public List<Photo> getFodlerPhotos() {
        return this.photos;
    }

    public List<PSproject> getProjList() {
        return this.psProjects;
    }
    public void setProjList (List<PSproject> projects) {
        this.psProjects = projects;
    }

    public boolean getScannedFlag() {return scannedFlag;}
    public void setScannedFlag(boolean scannedFlag) {this.scannedFlag = scannedFlag; }

    public LocalDate getScanDate() {return this.scanDate;}

    public void setScanDate(LocalDate date) {this.scanDate = scanDate;}

    public Integer getPhotoNum() {return this.photoNum;}

    public void setPhotoNum(Integer photoNum) {this.photoNum = photoNum;}

}
