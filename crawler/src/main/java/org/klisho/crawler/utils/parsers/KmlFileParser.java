package org.klisho.crawler.utils.parsers;

import com.vividsolutions.jts.geom.*;
import de.micromata.opengis.kml.v_2_2_0.*;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import org.apache.commons.io.FilenameUtils;
import org.klisho.crawler.Exception.NoDataFoundEx;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.klisho.crawler.handlers.KmlHandler.KML_FILES_EXTENTIONS;

/**
 * Created by Ola-Mola on 27/03/17.
 */
public class KmlFileParser {


    public String searchAndParse(File res) {
        String KmlFilePath = null;
        File[] files = res.listFiles();
        Arrays.sort(files);

        for (File file : files) {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if (ext != null && KML_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                KmlFilePath = file.getAbsolutePath();
            }
        }
        return KmlFilePath;
    }


    public com.vividsolutions.jts.geom.Polygon parseKml(String kmlFilePath) throws NoDataFoundEx {

        com.vividsolutions.jts.geom.Polygon polygon = null;
        final Kml kml = Kml.unmarshal(new File(kmlFilePath));
        Document document = (Document) kml.getFeature();

        if (document == null) {
            throw new NoDataFoundEx();
        }
        Folder folder = (Folder) document.getFeature().get(0);

        int folderSize = folder.getFeature().size();
        for (int k = 0; k < folderSize; k++)

        {
            Placemark placemark = (Placemark) folder.getFeature().get(k);
            de.micromata.opengis.kml.v_2_2_0.Polygon multigeom =
                    (de.micromata.opengis.kml.v_2_2_0.Polygon) placemark.getGeometry();

            Boundary outerBoundaryIs = multigeom.getOuterBoundaryIs();
            LinearRing linearRing = outerBoundaryIs.getLinearRing();
            List<Coordinate> coordinates = linearRing.getCoordinates();
//TODO insert a lot of checks
            com.vividsolutions.jts.geom.Coordinate[] coords =
                    new com.vividsolutions.jts.geom.Coordinate[coordinates.size()];

            for (int j = 0; j < coordinates.size(); j++) {
                double lon = coordinates.get(j).getLatitude();
                double lat = coordinates.get(j).getLongitude();
                double alt = coordinates.get(j).getAltitude();
                coords[j] = new com.vividsolutions.jts.geom.Coordinate(lat, lon, alt);
            }

            GeometryFactory geometryFactory = new GeometryFactory();
            com.vividsolutions.jts.geom.LinearRing linear =
                    new GeometryFactory().createLinearRing(coords);

            polygon = geometryFactory.createPolygon(coords);

        }
        if (polygon == null) {
            throw new NoDataFoundEx();
        }
        return polygon;


    }


}
