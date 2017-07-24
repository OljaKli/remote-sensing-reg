package org.klisho.crawler.HibernateClass;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ola-Mola on 02/04/17.
 */

@Entity
@Table( name = "PSproject" )

public class PSproject {
    @Id
    @Generated(value = GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectId;


//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<PhotoFolder> photoFolders = new ArrayList<>();

    private String psxPath;
    private String filesPath;


    public PSproject() {
        // this form used by Hibernate
    }

    public PSproject(List<PhotoFolder> photoFolders, String psxPath, String filesPath) {
        // for application use, to create new events
       // this.photoFolders = photoFolders;
        this.psxPath = psxPath;
        this.filesPath = filesPath;
    }

    public Long getId() {
        return projectId;
    }

    private void setId(Long id) {
        this.projectId = id;
    }
//
//    public List<PhotoFolder> getFoldersList(){
//        return photoFolders;
//    }
//
//    public void setFoldersList(List<PhotoFolder> folders) {
//        this.photoFolders = folders;
//    }

    public String getPsxPath() {
        return psxPath;
    }

    public void setPsxPath(String psxPath) {
        this.psxPath = psxPath;
    }

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

}
