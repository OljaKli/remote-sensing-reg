package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.opensphere.geometry.algorithm.ConcaveHull;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 25/12/16.
 */
public class KmlHandler implements Handler{
    private static final Set<String> KML_FILES_EXTENTIONS = new HashSet<String>() {{
        add("kml");
        //add("kmz");
    }};

    private long nKmlFiles = 0;


    @Override
    public boolean canHandle(File res) {
        if (!res.isDirectory()) {
            return false;
        }

        File[] files = res.listFiles(
                new FileFilter() { //anonymous class
                    @Override
                    public boolean accept(File pathname) {
                        String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
                        if (ext != null && KML_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                            if (!pathname.isFile()) {
                                return false;
                            }
                            System.out.println(pathname.getAbsolutePath());
                            return true;
                        }
                        return false;
                    }
                });

        if (files.length > 0) {
            nKmlFiles += files.length;
            return true;
            //TODO check for better ext distinguishing
        }
        return false;
    }

    @Override
    public void handle(File res) {
        System.out.println(res.getAbsolutePath() + res.separator);
        System.out.println(this);

    }

    public long getnKmlFiles() {
        return nKmlFiles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KmlFileHandler{");
        sb.append("nKmlFiles=").append(nKmlFiles);
        sb.append('}');
        return sb.toString();
    }


}
