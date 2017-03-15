package org.klisho.crawler.utils;
import com.sun.deploy.model.Resource;
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
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.klisho.crawler.handlers.PStxtFileHandler.PSTXT_FILES_EXTENTIONS;


/**
 * Created by Ola-Mola on 23/01/17.
 */
public class PStxtParser {
    public static ArrayList<String> photoNames = new ArrayList<>();
    public static ArrayList<Point> points = new ArrayList<>();

    public static ArrayList<Point> parse(File res) {


        if (res.isFile()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(res.getAbsolutePath()), StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (!line.contains("#")) {
                        String[] lineSep = line.split("\\t");
                        GeometryFactory geomFac = new GeometryFactory();
                        points.add(geomFac.createPoint(new Coordinate(Double.valueOf(lineSep[2]),
                                Double.valueOf(lineSep[1]), Double.valueOf(lineSep[3]))));
                        photoNames.add(lineSep[0]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return points;

    }

    public static Integer getPhotoIndex (File photo) {
        Integer photoIndx = null;
        File res = photo.getParentFile();
        File[] dirFiles = res.listFiles();
        String PStxtPath = null;
        for (File file : dirFiles) {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                PStxtPath = file.getAbsolutePath();
        }}
        //тут нужно просканить res и найти в нем файл PhotoScan.txt

        parse(new File(PStxtPath)); //method parses only the PStxtfiles

        String photoName = photo.getName();

        for (String name : photoNames){
            if (name.equals(photoName)){
                photoIndx = photoNames.indexOf(name);}
//            } else {
//                System.out.println("There is no such photo in this PStxt file");
//            }
        }
        return photoIndx;
    }
//TODO change constant read of PStxt file

    public static Point getPointByIndex (Integer index) {
        return points.get(index);
    }

    public static Point getPointByPhotoName (File photo) {
        Integer index = getPhotoIndex(photo);
        return getPointByIndex(index);
    }
}
