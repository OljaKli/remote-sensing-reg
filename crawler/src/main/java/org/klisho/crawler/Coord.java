package org.klisho.crawler;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "coordinates" )


public class Coord {

        private Long id;

        private String title;
        private Double latitude;
        private Double longitude;
        private Double altitude;
        private Date dateC;

        public Coord() {
            // this form used by Hibernate
        }

        public Coord(String title, Double latitude, Double longitude, Double altitude, Date dateC) {
            // for application use, to create new events
            this.title = title;
            this.latitude = latitude;
            this.longitude = longitude;
            this.altitude = altitude;
            this.dateC = dateC;
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

    }

