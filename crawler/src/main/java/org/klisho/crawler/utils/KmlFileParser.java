package org.klisho.crawler.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
}
