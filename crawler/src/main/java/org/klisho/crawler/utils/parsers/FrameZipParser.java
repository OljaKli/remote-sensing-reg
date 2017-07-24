package org.klisho.crawler.utils.parsers;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipInputStream;

import static java.nio.file.AccessMode.READ;
import static org.klisho.crawler.handlers.PhotoScanFileHandler.ARCHIVE_EXTENTIONS;
import static org.klisho.crawler.handlers.PhotoScanFileHandler.PS_FILES_EXTENTIONS;

/**
 * Created by Ola-Mola on 02/04/17.
 */
public class FrameZipParser {

    public String searchFilesFolder(File res) {
        String filesPath = null;
        File[] files = res.listFiles();
        Arrays.sort(files);

        for (File file : files) {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if (ext != null && PS_FILES_EXTENTIONS.contains(ext.toLowerCase())) {
                filesPath = file.getAbsolutePath();
            }
        }
        return filesPath;
    }

    public String searchFilesZip(File res) {
        String frameZipPath = null;
        File[] files = res.listFiles();
        Arrays.sort(files);

        for (File file : files) {
            String ext = FilenameUtils.getExtension(file.getAbsolutePath());
            if (ext != null && ARCHIVE_EXTENTIONS.contains(ext.toLowerCase())
                    && file.getName().equals("frame")) {
                frameZipPath = file.getAbsolutePath();
            }
        }
        return frameZipPath;
    }

    public ArrayList<String> frameZipParse(String frameZip) {
        File frame = new File(frameZip);
        ArrayList<String> photoNames = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();

        byte[] zipFileBytes;

//        InputStream input = frame.;
//        zipFileBytes = IOUtils.toByteArray(InputStream input);
//        ByteArrayInputStream bis = new ByteArrayInputStream(zipFileBytes));
//        ZipInputStream zis = new ZipInputStream(bis);


        try {
            ZipFile zipFile = new ZipFile(frameZip);


            try {
                ZipArchiveEntry entry = zipFile.getEntry("doc.xml");
                InputStream content = zipFile.getInputStream(entry);

                try {
                    String name = entry.getName();
                    SAXReader reader = new SAXReader();
//                Document document = reader.read(new File(entry.getName()));
                    Document document = reader.read(content);
//                    Element root = document.getRootElement();

//                    Iterator itr = root.elements().iterator();
                    nodes = document.selectNodes("//photo/@path");


                    System.out.println("" + nodes.size());

//                        } catch (IOException e) {
//                            e.printStackTrace();
                    for (Node node : nodes){
                        String nodeStr = node.getStringValue();
                        //String[] lineSep = nodeStr.split("/");

//
//                        if (lineSep != null && lineSep.length > 2) {
//                            photoNames.add(lineSep[lineSep.length-1]);
//                        }

                        String nameFile = FilenameUtils.getName(nodeStr);
                        if (nameFile != null) {
                            photoNames.add(nameFile);
                        }

                        else {

                            System.err.println("Inavlid coordinate string: " + node);
                        }
                    }

                } catch (DocumentException de) {
                    de.printStackTrace();
                } finally {
                    content.close();
                }
            } finally {
                zipFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


       // File docXml = frame.listFiles()[0];
        return photoNames;
    }
}
