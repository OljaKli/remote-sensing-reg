package org.klisho.crawler.utils.parsers;

import com.vividsolutions.jts.geom.*;
import org.apache.commons.io.FilenameUtils;
import org.klisho.crawler.handlers.PStxtFileHandler;
//import org.postgis.Point;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.klisho.crawler.handlers.PStxtFileHandler.PSTXT_FILES_EXTENTIONS;


/**
 * Created by Ola-Mola on 23/01/17.
 */
public class PStxtParser {

    public ArrayList<Point> points = new ArrayList<>();
    public ArrayList<String> photoNames = new ArrayList<>();

    public ArrayList<Point> parse(File res) {


        if (res.isFile()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(res.getAbsolutePath()),
                        StandardCharsets.UTF_8);
//                if (lines == null) {
//                    lines = Files.readAllLines(Paths.get(res.getAbsolutePath()),
//                            StandardCharsets.ISO_8859_1);
//                }

                for (String line : lines) {
                    if (!line.contains("#")) {
                        String[] lineSep = line.split("\\t");
                        if (lineSep != null && lineSep.length >= 4) {
                            GeometryFactory geomFac = new GeometryFactory();
                            points.add(geomFac.createPoint(new Coordinate(Double.valueOf(lineSep[2]),
                                    Double.valueOf(lineSep[1]), Double.valueOf(lineSep[3]))));
                            photoNames.add(lineSep[0]);
                        } else {
                            System.err.println("Inavlid coordinate string: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                List<String> lines = Files.readAllLines(Paths.get(res.getAbsolutePath()),
                        StandardCharsets.ISO_8859_1);

                for (String line : lines) {
                    if (!line.contains("#")) {
                        String[] lineSep = line.split("\\t");
                        if (lineSep != null && lineSep.length >= 4) {
                            GeometryFactory geomFac = new GeometryFactory();
                            points.add(geomFac.createPoint(new Coordinate(Double.valueOf(lineSep[2]),
                                    Double.valueOf(lineSep[1]), Double.valueOf(lineSep[3]))));
                            photoNames.add(lineSep[0]);
                        } else {
                            System.err.println("Inavlid coordinate string: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return points;

    }

    public ArrayList<String> searchAndParse(File res) {
        //res - photoFolder
        String PStxtPath = null;
        File[] files = res.listFiles();
        Arrays.sort(files);

        for (File file : files) {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                PStxtPath = file.getAbsolutePath();
            }
        }
        if (PStxtPath != null) {
            parse(new File(PStxtPath));
        }

        return photoNames;
    }

//    public  Integer getPhotoIndex (File photo) {
//        Integer photoIndx = null;
//        File res = photo.getParentFile();
//        File[] dirFiles = res.listFiles();
//        Arrays.sort(dirFiles);
//        String PStxtPath = null;
//
//        for (File file : dirFiles) {
//            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
//            if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
//                PStxtPath = file.getAbsolutePath();
//        }}
//        //тут нужно просканить res и найти в нем файл PhotoScan.txt
//
//        parse(new File(PStxtPath)); //method parses only the PStxtfiles
//
//        String photoName = photo.getName();
//
//        for (String name : photoNames){
//            if (name.equals(photoName)){
//                photoIndx = photoNames.indexOf(name);}
//
//        }
//        return photoIndx;
//    }

    public Integer getPhotoIndex(File photo, ArrayList<String> photoNamesAr) {
        Integer photoIndx = null;
        String photoName = photo.getName();

        for (String name : photoNamesAr) {
//            if (name.equals(photoName)){
            if (photoName.equals(name)) {
                photoIndx = photoNamesAr.indexOf(name);
            }
        }
        return photoIndx;
    }


//TODO change constant read of PStxt file

    public Point getPointByIndex(Integer index) {
        if (index != null) {
            return points.get(index);
        } else {
            return null;
        }
    }

    public Point getPointByPhotoName(File photo, ArrayList<String> photoNamesAr) {
        Integer index = getPhotoIndex(photo, photoNamesAr);
        return getPointByIndex(index);
    }

    public Integer getShiftMoment(ArrayList<Point> points) {
        Integer shift = null;

        int j = 0;

        while (shift == null && j < points.size() - 1) {
            double currentZ = points.get(j).getCoordinate().z;
            double nextZ = points.get(j + 1).getCoordinate().z;
            double dif = nextZ - currentZ;
            if (dif > 24.0) {
                shift = j + 1;
            }
            j++;
        }
//        for (int i = 0; i < points.size() - 1; i++) {
//            double currentZ = points.get(i).getCoordinate().z;
//            double nextZ = points.get(i + 1).getCoordinate().z;
//            double dif = nextZ - currentZ;
//            if (dif > 24.0) {
//                shift = i + 1;
//            }
////            //TODO: exit from the cycle after shift =! null
//        }
        return shift;
    }

}
