package org.klisho.crawler.handlers;

import org.apache.commons.io.FilenameUtils;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.InfoOptions;
import org.gdal.gdal.Transformer;
import org.gdal.gdal.gdal;
import org.gdal.ogr.Geometry;
import org.gdal.ogr.ogr;
import org.gdal.osr.CoordinateTransformation;
import org.gdal.osr.SpatialReference;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Ola-Mola on 07/11/16.
 */
public class OrthoFileHandler implements Handler {

    private static final Set<String> ORTHO_FILES_EXTENTIONS = new HashSet<String>() {{
        add("tiff");
        add("tif");
    }};

    private long nGTifFiles = 0;

    public OrthoFileHandler(){
        ogr.RegisterAll();
        gdal.AllRegister(); //all gdal drivers configuration
    }


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

        Dataset ortho = gdal.Open(res.getPath());
        String prj = ortho.GetProjection();

        double[] geoTrans = ortho.GetGeoTransform();

        SpatialReference old_cs = new SpatialReference();
        old_cs.ImportFromWkt(ortho.GetProjectionRef());
        SpatialReference new_cs = new SpatialReference();
        new_cs.ImportFromEPSG(4326);

        CoordinateTransformation tranform = new CoordinateTransformation(old_cs,new_cs);


        double minx = geoTrans[0];
        double maxy = geoTrans[3];
        double maxx = minx + geoTrans[1] * ortho.getRasterXSize();
        double miny = maxy + geoTrans [5] * ortho.getRasterYSize();
        double[] latlonMin = tranform.TransformPoint(minx,miny);
        double[] latlonMax = tranform.TransformPoint(maxx,maxy);



        Vector vector = new Vector();
        InfoOptions infoOpt = new InfoOptions(vector);
        String info = gdal.GDALInfo(ortho, infoOpt);


        if (!prj.isEmpty()) {
            SpatialReference srs = new SpatialReference(prj);
            if (srs.IsProjected() == 1) {
                System.out.println("File: " + res.getAbsolutePath());
                System.out.println(srs.GetAttrValue("projcs"));
                System.out.println(srs.GetAttrValue("geogcs"));
                System.out.println(info);
               // System.out.println(vector);
//                System.out.println(prj);
                System.out.println("minx = " + minx + "; " + "maxx = " + maxx + "; "
                        + "miny = " + miny + "; " + "maxy = " + maxy);
                System.out.println("lat/lonMax: " + latlonMax[0] + "; lat/lonMin: " + latlonMin[0]);
                System.out.println("lat/lonMax: " + latlonMax[1] + "; lat/lonMin: " + latlonMin[1]);

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
