package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.klisho.crawler.utils.PStxtParser;
import org.postgis.Point;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 23/01/17.
 */
public class PStxtFileHandler implements Handler {

    private static final Set<String> PSTXT_FILES_EXTENTIONS = new HashSet<String>() {{
        add("txt");
    }};

    private long nPStxtFiles = 0;


    @Override
    public boolean canHandle(File res) {
        if (!res.isFile()) {
            return false;
        }

        String ext = FilenameUtils.getExtension(res.getAbsolutePath());
        if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
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
        return false;
    }

    @Override
    public void handle(File res) {
        System.out.println(res.getAbsolutePath());
//        System.out.println(this);
//        File [] files = res.listFiles();
//        for (File file : files) {
        ArrayList<Point> points = PStxtParser.parse(res);
        for (Point point : points) {
            System.out.println(point);}
//        }

//        PStxtParser parser = new PStxtParser(res);
//        points = parser.Parse(res);
       //points = PStxtParser.Parse(res);

    }

    public long getnPStxtFiles() {
        return nPStxtFiles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KmlFileHandler{");
        sb.append("nPStxtFiles=").append(nPStxtFiles);
        sb.append('}');
        return sb.toString();
    }
}
