package org.klisho.crawler.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by Ola-Mola on 17/03/17.
 */
    public class SessionF {

    private static SessionFactory sessionFactory;



    public Session openSession() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            System.err.println(e);
            StandardServiceRegistryBuilder.destroy(registry);
           // return;
        }
        org.hibernate.Session session = sessionFactory.openSession();
        return session;
    }

    public void closeSession(Session session){
        session.close();
    }
}
