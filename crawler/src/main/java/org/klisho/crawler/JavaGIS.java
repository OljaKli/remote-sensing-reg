package org.klisho.crawler;

import java.sql.*;
import java.util.*;
import java.lang.*;
import org.postgis.*;

/**
 * Created by Ola-Mola on 07/01/17.
 */
public class JavaGIS {

    public static void main(String[] args) {
        java.sql.Connection conn;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/postgis_testOK";
            conn = DriverManager.getConnection(url, "postgres", "postgres");

            ((org.postgresql.PGConnection)conn).addDataType("geometry", org.postgis.PGgeometry.class);
            ((org.postgresql.PGConnection)conn).addDataType("box3d", org.postgis.PGbox3d.class);

            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery("select all from gtest");
            while( r.next() ) {

                PGgeometry geom = (PGgeometry)r.getObject(1);
                int id = r.getInt(2);
                System.out.println("Row " + id + ":");
                System.out.println(geom.toString());
            }
            s.close();
            conn.close();
//            System.out.println(r.toString());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
