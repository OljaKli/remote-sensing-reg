//package org.klisho.crawler.utils;
//
//import java.util.Date;
//import java.util.List;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//import junit.framework.TestCase;
//import org.klisho.crawler.Coord;
//
///**
// * Created by Ola-Mola on 30/01/17.
// */
//public class AnnotationsTst extends TestCase{
//
//    private SessionFactory sessionFactory;
//
//    @Override
//    protected void setUp() throws Exception {
//        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
//        }
//        catch (Exception e) {
//            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//            // so destroy it manually.
//            StandardServiceRegistryBuilder.destroy( registry );
//        }
//    }
//
//    @Override
//    protected void tearDown() throws Exception {
//        if ( sessionFactory != null ) {
//            sessionFactory.close();
//        }
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    public void testBasicUsage() {
//        // create a couple of events...
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.save( new Coord( "Our very first coord!", 30.97, 60.45, 21.37, new Date() ) );
//        session.save( new Coord ( "Follow up coord!", 31.97, 61.45, 23.37, new Date() ) );
//        session.getTransaction().commit();
//        session.close();
//
//        // now lets pull events from the database and list them
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//        List result = session.createQuery( "from Coord " ).list();
//        for ( Coord coord : (List<Coord>) result ) {
//            System.out.println( "Coord (" + coord.getAltitude() + ") : " + coord.getTitle() );
//        }
//        session.getTransaction().commit();
//        session.close();
//    }
//
//
//}
