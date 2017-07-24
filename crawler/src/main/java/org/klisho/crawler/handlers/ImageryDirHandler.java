package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.klisho.crawler.HibernateClass.PhotoFolder;

import java.io.File;
import java.io.FileFilter;
import java.security.cert.Extension;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 05/11/16.
 */
public class ImageryDirHandler implements Handler {

    private static SessionFactory sessionFactory;

    private static final Set<String> IMAGERY_EXTENTIONS = new HashSet<String>() {{
        add("jpeg");
        add("jpg");
//        add("png");
//        add("arw");
//        add("tif");
//        add("tiff");
    }};

    private PhotoFolder.PhotoType photoType = null;

    private long nDirs = 0;
    private long nImages = 0;


    @Override
    public boolean canHandle(File res) { //res - folder with photos
        if (!res.isDirectory()) {
            return false;
        }

        File[] files = res.listFiles(
                new FileFilter() { //anonymous class
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }

                String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
                if (ext != null && IMAGERY_EXTENTIONS.contains(ext.toLowerCase())) {
                    if (ext.toLowerCase().equals("jpeg")||ext.toLowerCase().equals("jpg")
                            ||ext.toLowerCase().equals("png") )
                    {
                        photoType = PhotoFolder.PhotoType.RGB;
                    }
                    if (ext.toLowerCase().equals("arw"))
                    {
                        photoType = PhotoFolder.PhotoType.NDVI;
                    }
                    return true;
                }
                return false;
            }
        });

        if (files.length > 9) {
            nDirs++;
            nImages += files.length;
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


        System.out.println(res.getAbsolutePath() + res.separator);
        System.out.println(this);

        session.save(new PhotoFolder(res.getAbsolutePath(), photoType, null, null, null, false, LocalDate.now(), null));

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
