package org.klisho.crawler.HibernateClass;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gdal.ogr.Geometry;
import org.hibernate.annotations.GenericGenerator;
import org.postgis.Point;

@Entity
@Table( name = "coordinates" )


public class Coord {

        private Long id;

        private String title;
        private Double latitude;
        private Double longitude;
        private Double altitude;
        private Date dateC;


        private Point point;


        public Coord() {
            // this form used by Hibernate
        }

    public String pointToString(Point point) {
        final StringBuilder sb = new StringBuilder("Coord{");
        sb.append("point=").append(point.getX());
        sb.append(point.getY());
        sb.append(point.getZ());
        sb.append('}');
        return sb.toString();
    }

    public Coord(String title, Double latitude, Double longitude, Double altitude, Date dateC, Point point) {
            // for application use, to create new events
            this.title = title;
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
            this.dateC = dateC;
            this.point = point;
        }

        @Id
        @GeneratedValue(generator="increment")
        @GenericGenerator(name="increment", strategy = "increment")
        public Long getId() {
            return id;
        }

        private void setId(Long id) {
            this.id = id;
        }

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "DATEC")
        public Date getDateC() {
            return dateC;
        }

        public void setDateC(Date dateC) {
            this.dateC = dateC;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Double getLatitude() {return latitude; }

        public void setLatitude(Double latitude) {this.latitude = latitude; }

        public Double getLongitude() {return longitude; }

        public void setLongitude(Double longitude) {this.longitude = longitude; }

        public Double getAltitude() {return altitude; }

        public void setAltitude(Double altitude) {this.altitude = altitude; }

        public Point getPoint() {return point;}

        public void setPoint(Point point) {this.point = point;}

    }

