package org.klisho.crawler;

import java.util.*;
import java.lang.*;

import com.vividsolutions.jts.geom.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.postgis.LinearRing;
import org.klisho.crawler.HibernateClass.Photo;
//import org.postgis.Point;
//import org.postgis.Polygon;


/**
 * Created by Ola-Mola on 07/01/17.
 */
public class JavaGIS {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
//        java.sql.Connection conn;
//
//        try {
//            Class.forName("org.postgresql.Driver");
//            String url = "jdbc:postgresql://localhost:5432/postgis_testOK";
//            conn = DriverManager.getConnection(url, "postgres", "postgres");
//
//            ((org.postgresql.PGConnection)conn).addDataType("geometry", org.postgis.PGgeometry.class);
//            ((org.postgresql.PGConnection)conn).addDataType("box3d", org.postgis.PGbox3d.class);
//
//            Statement s = conn.createStatement();
//            ResultSet rs = s.executeQuery("select auth_name from spatial_ref_sys");
//            while(rs.next() ) {
//
////                PGgeometry geom = (PGgeometry)rs.getObject(1);
//               String geom = (String)rs.getObject(1);
//
////                int id = r.getInt(2);
////                System.out.println("Row " + id + ":");
//                System.out.println(geom.toString());
//            }
//            s.close();
//            conn.close();
////            System.out.println(r.toString());
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        // test Hibernate
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.

            System.err.println(e);
            StandardServiceRegistryBuilder.destroy( registry );
            return ;
        }

//        @SuppressWarnings({ "unchecked" })
//        // create a couple of events...
//        SessionF session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save( new Coord( "Our very first coord!", 30.971, 60.451, 21.371, new Date(),
//                new Point(30.971, 60.451, 21.371 )) );
//        session.save( new Coord ( "Follow up coord!", 31.971, 61.451, 23.371, new Date(),
//                new Point(31.971, 61.451, 23.371)) );
//        session.getTransaction().commit();
//        session.close();

        // create a couple of events...
//        @SuppressWarnings({ "unchecked" })
//        SessionF session2 = sessionFactory.openSession();
//        session2.beginTransaction();
//        session2.save(new Photo((long) 5, new Point(30.971, 60.451, 21.371 ), new Polygon(new LinearRing[] {
//                new LinearRing(
//                        new Point[] {
//                                new Point(-1.0d, -1.0d,  0.5d)}
//                )
//        }), "fileName", new Date()));
//

        @SuppressWarnings({ "unchecked" })


        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();

//        Polygon geo = new Polygon(
//                new LinearRing[] {
//                       new LinearRing(
//                                new Point[] {
//                                        new Point(-1.0d, -1.0d,  0.5d),
//                                        new Point( 1.0d, -1.0d,  0.0d),
//                                        new Point( 1.0d,  1.0d, -0.5d),
//                                        new Point(-1.0d,  1.0d,  0.0d),
//                                        new Point(-1.0d, -1.0d,  0.5d)
//                                }
//                        )
//                }
        Coordinate[] pgc =new Coordinate[5];
        pgc[0]=new Coordinate(7,7, 9.1);
        pgc[1]=new Coordinate(6,9, 10.1);
        pgc[2]=new Coordinate(6,11.2, 3.11);
        pgc[3]=new Coordinate(7,12.22, 12.22);
        pgc[4]=new Coordinate(7,7, 9.1);
        GeometryFactory geomFac = new GeometryFactory();
        LinearRing rgG = geomFac.createLinearRing(pgc);
        Polygon poly = geomFac.createPolygon(rgG,null);

        Point point = geomFac.createPoint(new Coordinate(10.11, 40.44, 50.11));


       // session2.save(new Photo((long) 5, point, poly, "fileName", new Date()));


//        session.save( new Coord ( "Follow up coord!", 31.971, 61.451, 23.371, new Date(),
//                new Point(31.971, 61.451, 23.371)) );
        session2.getTransaction().commit();
        session2.close();

//        // now lets pull events from the database and list them
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List result = session.createQuery( "from Coord " ).list();
//        for ( Coord coord : (List<Coord>) result ) {
//            System.out.println( "Coord (" + coord.getAltitude() + ") : " + coord.getDateC() + " Point: "
//                    + coord.getPoint());
//        }

        // now lets pull events from the database and list them
        session2 = sessionFactory.openSession();
        session2.beginTransaction();
        List result = session2.createQuery( "from Photo" ).list();
        for ( Photo photo : (List<Photo>) result ) {
            System.out.println( "Photo (" + photo.getCenterCoordinate() + ") : " + photo.getTimeStamp());
        }

        }
}
