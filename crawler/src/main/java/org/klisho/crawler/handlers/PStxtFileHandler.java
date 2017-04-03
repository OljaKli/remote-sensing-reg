package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.klisho.crawler.utils.parsers.PStxtParser;

import com.vividsolutions.jts.geom.*;
//import org.postgis.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 23/01/17.
 */
public class PStxtFileHandler implements Handler {

    public static final Set<String> PSTXT_FILES_EXTENTIONS = new HashSet<String>() {{
        add("txt");
    }};

    private long nPStxtFiles = 0;
    private String PStxtPath = null;


    @Override
    public boolean canHandle(File res) {
        if (!res.isFile()) {
            return false;
        }

        String ext = FilenameUtils.getExtension(res.getAbsolutePath());
        if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
            PStxtPath = res.getAbsolutePath();
            return true;
        }

//         File[] files = res.listFiles(
//                new FileFilter() { //anonymous class
//                    @Override
//                    public boolean accept(File pathname) {
//                        String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
//                        if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
//                            if (!pathname.isFile()) {
//                                return false;
//                            }
//                            System.out.println(pathname.getAbsolutePath());
//                            return true;
//                        }
//                        return false;
//                    }
//                });
//
//        if (files.length > 0) {
//            nPStxtFiles += files.length;
//            return true;
//            //TODO check for better ext distinguishing
//        }
        else return false;
    }

    @Override
    public void handle(File res) {
        System.out.println(res.getAbsolutePath());
        PStxtParser parser = new PStxtParser();
        ArrayList<Point> points = parser.parse(res);
        for (Point point : points) {
            System.out.println(point);}

    }

    public long getnPStxtFiles() {
        return nPStxtFiles;
    }
    public String getPStxtFilePath(File res) {

        return PStxtPath;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KmlFileHandler{");
        sb.append("nPStxtFiles=").append(nPStxtFiles);
        sb.append('}');
        return sb.toString();
    }
}
