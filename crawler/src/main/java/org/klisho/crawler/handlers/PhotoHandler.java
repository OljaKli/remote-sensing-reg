package org.klisho.crawler.handlers;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.apache.commons.io.FilenameUtils;
import com.vividsolutions.jts.geom.*;
import org.hibernate.Session;
import org.klisho.crawler.Exception.NoDataFoundEx;
import org.klisho.crawler.HibernateClass.Photo;
import org.klisho.crawler.HibernateClass.PhotoFolder;
import org.klisho.crawler.utils.parsers.FrameZipParser;
import org.klisho.crawler.utils.parsers.KmlFileParser;
import org.klisho.crawler.utils.parsers.PStxtParser;
import org.klisho.crawler.utils.parsers.PhotoParserLight;
import org.opensphere.geometry.algorithm.ConcaveHull;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static org.klisho.crawler.handlers.KmlHandler.KML_FILES_EXTENTIONS;
import static org.klisho.crawler.handlers.PStxtFileHandler.PSTXT_FILES_EXTENTIONS;

/**
 * Created by Ola-Mola on 13/03/17.
 */
public class PhotoHandler implements Handler {


    //    ImageryDirHandler idh = new ImageryDirHandler();
//
//    public boolean canHandle(File res) {
//        return idh.canHandle(res);
//    }
//
//
    private static final Set<String> IMAGERY_EXTENTIONS = new HashSet<String>() {{
        add("jpeg");
        add("jpg");
//    add("png");
//    add("arw");
//    add("tif");
//    add("tiff");
    }};

    Session session;

    public PhotoHandler(Session session) {
        this.session = session;
    }


//    private static SessionFactory sessionFactory;

    private long nDirs = 0;
    private long nImages = 0;

    private File[] images = null;
    private String txtFile = null;
    private String kmlFile = null;

    @Override
    public boolean canHandle(File res) {

        images = null;
        txtFile = null;
        kmlFile = null;

        if (!res.isDirectory()) {
            return false;
        }
        File[] files = res.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }

                String ext = FilenameUtils.getExtension(pathname.getAbsolutePath());
                if (ext != null && IMAGERY_EXTENTIONS.contains(ext.toLowerCase())) {
                    return true;
                }
                if (ext != null && PSTXT_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                    txtFile = pathname.getAbsolutePath();
                }
                if (ext != null && KML_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                    kmlFile = pathname.getAbsolutePath();
                }
                return false;
            }
        });


        if (files.length > 9 && txtFile != null) {
            //Arrays.sort(files);
            nDirs++;
            nImages += files.length;
            images = files;
            Arrays.sort(images);


            return true;


            //TODO check for better ext distinguishing
        }
        return false;

    }

    @Override
    public void handle(File res) {

//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            sessionFactory = new MetadataSources( registry ).buildMetadata()
//                    .buildSessionFactory();
//        }
//        catch (Exception e) {
//            System.err.println(e);
//            StandardServiceRegistryBuilder.destroy( registry );
//            return;
//        }
//        Session session = sessionFactory.openSession();

        //здесь найти PStxt, его распарсить
//
//        FrameZipParser zipParser = new FrameZipParser();
//        String filesZip = zipParser.searchFilesZip(res);
//        File docXml = zipParser.frameZipParse(filesZip);

        System.out.println(res.getAbsolutePath() + res.separator);
        int i = 0;
        PStxtParser parser = new PStxtParser();
        KmlFileParser kmlParser = new KmlFileParser();


        ArrayList<String> photoNames = new ArrayList<>();
        photoNames = parser.searchAndParse(res);
        ArrayList<Point> points = parser.points;
        Geometry extent = null;

        session.beginTransaction();
        ArrayList<Point> flightPoints = new ArrayList<>(points.size());

        Integer shift = parser.getShiftMoment(points);

        for (int k = shift; k < points.size(); k++) {
            Point point = points.get(k);
            flightPoints.add(point);
        }
        if (kmlFile == null) {
            GeometryFactory factory = new GeometryFactory();

            Geometry[] geomArray = factory.toGeometryArray(flightPoints);
            GeometryCollection gc = factory.createGeometryCollection(geomArray);
            ConcaveHull ch = new ConcaveHull(gc, 0.001);
            extent = ch.getConcaveHull();
        } else
        {
            kmlFile = kmlParser.searchAndParse(res);
            try {
                Polygon poly = kmlParser.parseKml(kmlFile);
                extent = poly;
            } catch (NoDataFoundEx noDataFoundEx) {
                noDataFoundEx.printStackTrace();
            }

//            final Kml kml = Kml.unmarshal(new File(kmlFile));
//
//
//            Document document = (Document) kml.getFeature();
//
//            Folder folder = (Folder) document.getFeature().get(0);
//            int folderSize = folder.getFeature().size();
//
//            for (int k = 0; k < folderSize; k++) {
//                Placemark placemark = (Placemark) folder.getFeature().get(k);
//                de.micromata.opengis.kml.v_2_2_0.Polygon multigeometry =
//                        (de.micromata.opengis.kml.v_2_2_0.Polygon) placemark.getGeometry();
//                Boundary outerBoundaryIs = multigeometry.getOuterBoundaryIs();
//                LinearRing linearRing = outerBoundaryIs.getLinearRing();
//                List<Coordinate> coordinates = linearRing.getCoordinates();
//
//                com.vividsolutions.jts.geom.Coordinate[] coords =
//                        new com.vividsolutions.jts.geom.Coordinate[coordinates.size()];
//                for (int j=0; j<coordinates.size(); j++) {
//                    double lon = coordinates.get(j).getLatitude();
//                    double lat = coordinates.get(j).getLongitude();
//                    double alt = coordinates.get(j).getAltitude();
//                    coords[j] =  new com.vividsolutions.jts.geom.Coordinate(lat, lon, alt);
//                }
//
//                GeometryFactory geometryFactory = new GeometryFactory();
//                com.vividsolutions.jts.geom.LinearRing linear =
//                        new GeometryFactory().createLinearRing(coords);
//
//
//                Polygon polygon = geometryFactory.createPolygon(coords);
//                extent = polygon;
//            }

        }
        PhotoFolder folder = new PhotoFolder(res.getAbsolutePath(), PhotoFolder.PhotoType.RGB, (Polygon) extent);
        session.save(folder);
        session.getTransaction().commit();


        session.beginTransaction();

        PhotoParserLight parserLight = new PhotoParserLight();
        ArrayList<Date> imgTimes = parserLight.getAvgExposureTime(images);


        for (int j = 0; j < images.length; j++) {

            Point centerCoord = parser.getPointByPhotoName(images[j], photoNames);

            if (centerCoord != null) {
                System.out.println(centerCoord.getY());
            } else {
                System.out.println(images[j].getAbsolutePath() + " has no coord in PStxt file");
            }


            session.save(new Photo(folder, centerCoord, null, images[j].getAbsolutePath(), imgTimes.get(j)));

            i++;
            if ((i % 10) == 0) {
                session.getTransaction().commit();
                session.beginTransaction();
            }

        }


//TODO create session opening method in handler mngr
        session.getTransaction().commit();
//        session.close();
    }

    public long getDirsNum() {
        return nDirs;
    }

    public long getImagesNum() {
        return nImages;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ImageryDirHandler{");
        sb.append("nDirs=").append(nDirs);
        sb.append(", nImages=").append(nImages);
        sb.append('}');
        return sb.toString();
    }

}
