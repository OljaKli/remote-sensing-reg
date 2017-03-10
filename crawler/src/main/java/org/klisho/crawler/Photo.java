package org.klisho.crawler;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
//import org.postgis.Point;
//import org.postgis.Polygon;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Ola-Mola on 08/03/17.
 */

@Entity
@Table( name = "photo" )

public class Photo {

    @Id
    @Generated(value= GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long photoId;

        private Long folderId;
        private Point centerCoordinate;

        private com.vividsolutions.jts.geom.Geometry extent;
        private String fileName;
        private Date timeStamp;


        public Photo() {
            // this form used by Hibernate
        }

        public Photo(Long folderId, Point centerCoordinate, Polygon extend, String fileName, Date timeStamp) {
            // for application use, to create new events
            this.folderId = folderId;
            this.centerCoordinate = centerCoordinate;
            this.extent = extend;
            this.fileName = fileName;
            this.timeStamp = timeStamp;
        }



        public Long getId() {
            return photoId;
        }

        private void setId(Long id) {
            this.photoId = id;
        }

        public Long getFolderId() {
            return folderId;
        }

        public void setFolderId(Long folderId) {
            this.folderId = folderId;
        }

        public Point getCenterCoordinate() {
            return centerCoordinate;
        }

        public void setCenterCoordinate(Point centerCoordinate) {
            this.centerCoordinate = centerCoordinate;
        }

        public com.vividsolutions.jts.geom.Geometry getExtent() {return extent; }

        public void setExtent(com.vividsolutions.jts.geom.Geometry extent) {this.extent = extent; }

        public String getFileName() {return fileName; }

        public void setFileName(String fileName) {this.fileName = fileName; }

        public Date getTimeStamp() {return timeStamp; }

        public void setTimeStamp(Date timeStamp) {this.timeStamp = timeStamp; }

}
