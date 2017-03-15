package org.klisho.crawler.HibernateClass;

import org.hibernate.annotations.GenericGenerator;
//import org.postgis.Point;
//import org.postgis.Polygon;
import com.vividsolutions.jts.geom.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Ola-Mola on 08/03/17.
 */

@Entity
@Table( name = "photoFolder" )

public class PhotoFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folderId;

    private String folderPath;

    private PhotoType photoType;

    public  enum PhotoType {
        RGB,
        RAW,
        NDVI,
        PSEUDO_RGB,
        THERMO
    }

    private Polygon extend;



    public PhotoFolder() {
        // this form used by Hibernate
    }

    public PhotoFolder(Long folderId, String folderPath, PhotoType photoType, Polygon extend) {
        // for application use, to create new events
        this.folderId = folderId;
        this.folderPath = folderPath;
        this.photoType = photoType;
        this.extend = extend;
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

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public Polygon getExtend() {return extend; }

    public void setExtend(Polygon extend) {this.extend = extend; }

}
