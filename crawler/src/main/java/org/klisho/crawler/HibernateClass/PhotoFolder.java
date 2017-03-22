package org.klisho.crawler.HibernateClass;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
//import org.postgis.Point;
//import org.postgis.Polygon;
import com.vividsolutions.jts.geom.*;

import javax.persistence.*;
import java.sql.Timestamp;
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



    //
   // private Set<Photo> photos = new HashSet<>(0);


//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "folder_id")
//    private Set<Photo> photos = new HashSet<Photo>();

    @OneToMany (mappedBy = "folder")
    private List<Photo> photos = new ArrayList<>();



    public PhotoFolder() {
        // this form used by Hibernate
    }

    public PhotoFolder(String folderPath, PhotoType photoType, Polygon extend) {
        // for application use, to create new events

        this.folderPath = folderPath;
        this.photoType = photoType;
        this.extend = extend;
    }

//    public PhotoFolder(String folderPath, PhotoType photoType, Polygon extend, Set<Photo> photos){
//        this.folderPath = folderPath;
//        this.photoType = photoType;
//        this.extend = extend;
//        this.photos = photos;
//    }

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

}
