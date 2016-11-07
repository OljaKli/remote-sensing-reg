package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.gdal.gdal.gdal;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ola-Mola on 07/11/16.
 */
public class OrthoFileHandler implements Handler {

    private static final Set<String> ORTHO_FILES_EXTENTIONS = new HashSet<String>() {{
        add("tiff");
        add("tif");
    }};

    private long nOrthoFiles = 0;


    @Override
    public boolean canHandle(File res) {
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
                        if (ext != null && ORTHO_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                            return true;
                            //TODO gdal check of raster (gtif)
                        }
                        return false;
                    }
                });

        if (files.length > 0) {
            nOrthoFiles += files.length;
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

    public long getnOrthoFiles() {
        return nOrthoFiles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrthoFileHandler{");
        sb.append("nOrthoFiles=").append(nOrthoFiles);
        sb.append('}');
        return sb.toString();
    }

}
