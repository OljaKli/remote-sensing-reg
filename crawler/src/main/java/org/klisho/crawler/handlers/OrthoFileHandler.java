package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.gdal;
import org.gdal.ogr.ogr;
import org.gdal.osr.SpatialReference;

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

    private long nGTifFiles = 0;


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

                        String ext = FilenameUtils.getExtension(pathname.toString());
                        if (ext != null && ORTHO_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                            return orthoCheck(pathname);
//                            orthoCheck(pathname);
//                            return true;

                            //TODO gdal check of raster (gtiff)
                        }
                        return false;
                    }
                });

        if (files.length > 0) {
            nGTifFiles += files.length;
            return true;
            //TODO check for better ext distinguishing
        }
        return false;
    }

    public static boolean orthoCheck(File res) {

        ogr.RegisterAll();
        gdal.AllRegister();

        Dataset ortho = gdal.Open(res.getPath());
        String prj = ortho.GetProjection();

        if (!prj.isEmpty()) {
            SpatialReference srs = new SpatialReference(prj);
            if (srs.IsProjected() == 1) {
                System.out.println("File: " + res.getAbsolutePath());
                System.out.println(srs.GetAttrValue("projcs"));
                System.out.println(srs.GetAttrValue("geogcs"));
            }
            return true;
        }
//        } else {
//           // System.out.println("there is no ortho or DEM in selected path");
            return false;
        //}
    }

    @Override
    public void handle(File res) {
        System.out.println(res.getAbsolutePath() + res.separator);
        System.out.println(this);

    }

    public long getnOrthoFiles() {
        return nGTifFiles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrthoFileHandler{");
        sb.append("nOrthoFiles=").append(nGTifFiles);
        sb.append('}');
        return sb.toString();
    }

}
