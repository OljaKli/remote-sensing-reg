package org.klisho.crawler.utils;

import org.postgis.Point;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ola-Mola on 23/01/17.
 */
public class PStxtParser {

//    private final File res;
//    private final ArrayList<Point> points ;

//    public PStxtParser(File res) {
//
//        this.res = res;
//    }

    public static ArrayList<Point> parse(File res) {
        ArrayList<Point> points = new ArrayList<>();

        if (res.isFile()) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(res.getAbsolutePath()), StandardCharsets.UTF_8);
                for (String line : lines) {
                    if (!line.contains("#")) {
                        String[] lineSep = line.split("\\t");
                        points.add(new Point(Double.valueOf(lineSep[2]), Double.valueOf(lineSep[1]), Double.valueOf(lineSep[3])));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


//        if (res.isFile()) {
//            List<String> lines = null;
//            try {
//                lines = Files.readAllLines(Paths.get(res.getAbsolutePath()), StandardCharsets.UTF_8);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            for (String line : lines) {
//                if (!line.contains("#")) {
//                    String[] lineSep = line.split("\\t");
//                    points.add(new Point (Double.valueOf(lineSep[2]), Double.valueOf(lineSep[1]), Double.valueOf(lineSep[3])));
//                }
//
//            }
//        }



        }
        return points;

    }
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("PStxtParser{");
//        sb.append("points=").append(points);
//        sb.append('}');
//        return sb.toString();
//    }
}
