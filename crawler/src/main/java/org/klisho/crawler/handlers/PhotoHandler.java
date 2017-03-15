package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import com.vividsolutions.jts.geom.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.klisho.crawler.HibernateClass.Photo;
import org.klisho.crawler.utils.PStxtParser;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static org.klisho.crawler.handlers.PStxtFileHandler.PSTXT_FILES_EXTENTIONS;

/**
 * Created by Ola-Mola on 13/03/17.
 */
public class PhotoHandler implements Handler{

//    ImageryDirHandler idh = new ImageryDirHandler();
//
//    public boolean canHandle(File res) {
//        return idh.canHandle(res);
//    }
//
//
private static final Set<String> IMAGERY_EXTENTIONS = new HashSet<String>() {{
    add("jpeg");
    add("jpg");
//    add("png");
//    add("arw");
//    add("tif");
//    add("tiff");
}};

    private static SessionFactory sessionFactory;

    private long nDirs = 0;
    private long nImages = 0;

    private File[] images = null;
    private String txtFile = null;

    @Override
    public boolean canHandle(File res) {

        images = null;
        txtFile = null;

        if (!res.isDirectory()) {
            return false;
        }
        File[] files = res.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (!pathname.isFile()) {
                            return false;
                        }

                        String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
                        if (ext != null && IMAGERY_EXTENTIONS.contains(ext.toLowerCase())) {
                            return true;
                        }
                        if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                            txtFile = pathname.getAbsolutePath();
                        }
                        return false;
                    }
                });



        if (files.length > 9 && txtFile != null) {
            Arrays.sort(files);
            nDirs++;
            nImages += files.length;
            images = files;


            return true;



            //TODO check for better ext distinguishing
        }
        return false;

    }

    @Override
    public void handle(File res) {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata()
                    .buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println(e);
            StandardServiceRegistryBuilder.destroy( registry );
            return;
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //здесь найти PStxt, его распарсить


        System.out.println(res.getAbsolutePath() + res.separator);
        System.out.println(this);
        int i = 0;
        PStxtParser parser = new PStxtParser();

        ArrayList<String> photoNames = new ArrayList<>();
        photoNames = parser.searchAndParse(res);

        for (File image : images) {
            Point centerCoord = parser.getPointByPhotoName(image, photoNames);
            System.out.println(centerCoord.getY());
            session.save(new Photo((long) 5, centerCoord, null, image.getName(), new Date()));

            i++;
            if ((i % 10) == 0) {
                session.getTransaction().commit();
                session.beginTransaction();
            }
        }
//TODO create session opening method in handler mngr
        session.getTransaction().commit();
        session.close();







    }

    public long getDirsNum() {
        return nDirs;
    }

    public long getImagesNum() {
        return nImages;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageryDirHandler{");
        sb.append("nDirs=").append(nDirs);
        sb.append(", nImages=").append(nImages);
        sb.append('}');
        return sb.toString();
    }

}
